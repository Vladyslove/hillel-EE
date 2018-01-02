package hillelee.pet;

import hillelee.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetService {
    private final JpaPetRepository petRepository;
    private final StoreService storeService;


    public List<Pet> getPetsUsingSeparateJpaMethods(Optional<String> specie, Optional<Integer> age) {
        if (specie.isPresent()&& age.isPresent()){
            petRepository.findBySpecieAndAge(specie.get(), age.get());
        }
        if (specie.isPresent()){
            return petRepository.findBySpecie(specie.get());
        }
        if (age.isPresent()) {
            return petRepository.findByAge(age.get());
        }
        return petRepository.findAll();
    }

    public List<Pet> getPetUsingStreamFilters(Optional<String> specie, Optional<Integer> age) {
        Predicate<Pet> specieFilter = specie.map(this::filterByScpecie)
                .orElse(pet -> true);

        Predicate<Pet> ageFilter = age.map(this::filterByAge)
                .orElse(pet -> true);

        Predicate<Pet> complexFilter = ageFilter.and(specieFilter);

        return petRepository.findAll().stream()
                .filter(complexFilter)
                .collect(Collectors.toList());
    }

    @Transactional // чтобы транзакция была в сервисе, а не в репо; работает только на бинах спринга
    public List<Pet> getPetsUsingSingleJpaMethod(Optional<String> specie, Optional<Integer> age){
        List<Pet> nullableBySpecieAndAge = petRepository.findNullableBySpecieAndAge(specie.orElse(null),
                age.orElse(null));

        nullableBySpecieAndAge.forEach(pet -> System.out.println(pet.getPrescriptions()));

        return nullableBySpecieAndAge;
    }

    private Predicate<Pet> filterByAge(Integer age) {
        return pet -> pet.getAge().equals(age);
    }

    private Predicate<Pet> filterByScpecie(String specie) {
        return pet -> pet.getSpecie().equals(specie);
    }

    public Optional<Pet> getById(Integer id) {
        return petRepository.findById(id);
    }

    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    public Optional<Pet> delete(Integer id) {
        Optional<Pet> mayBePet = petRepository.findById(id);
        mayBePet.ifPresent(pet -> petRepository.delete(pet.getId()));

/*
        mayBePet.map(Pet::getId)
                .ifPresent(petRepository::delete);
*/

        return mayBePet;
    }

    @Transactional
        @Retryable(ObjectOptimisticLockingFailureException.class /*There is no reason to retry
        another time this method if we don't have enough amount of medicine*/)
    public void prescribe(Integer petId,
                          String description,
                          String medicineName,
                          Integer quantity,
                          Integer timesPerDay) {

        Pet pet = petRepository.findById(petId).orElseThrow(RuntimeException::new);

        pet.getPrescriptions().add(new Prescription(description, LocalDate.now(), timesPerDay));

        petRepository.save(pet);

        storeService.decrement(medicineName, quantity);

    }
}
