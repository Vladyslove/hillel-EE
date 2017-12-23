package hillelee.pet;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@RequiredArgsConstructor
public class PetService {
    private final JpaPetRepository petRepository;

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

    @Transactional
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
}
