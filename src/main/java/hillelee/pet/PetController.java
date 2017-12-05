package hillelee.pet;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import hillelee.util.ErrorBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PetController {

    private Map<Integer, Pet> pets = new HashMap<Integer, Pet>() {{
        put(0, new Pet("Tom", "Cat", 3));
        put(1, new Pet("Jerry", "Mouse", 1));
    }};

    private Integer counter = 1;

    @GetMapping(value = "/greeting")
    public String helloWorld(){
        return "Hello world!";
    }

    @GetMapping("/pets")
    public List<Pet> getPets(@RequestParam Optional<String> specie,
                             @RequestParam Optional<Integer> age) {

        Predicate<Pet> specieFilter = specie.map(this::filterByScpecie)
                .orElse(pet -> true);

        Predicate<Pet> ageFilter = age.map(this::filterByAge)
                .orElse(pet -> true);

        Predicate<Pet> complexFilter = ageFilter.and(specieFilter);

        return pets.values().stream()
                .filter(complexFilter)
                .collect(Collectors.toList());
    }

    private Predicate<Pet> filterByAge(Integer age) {
        return pet -> pet.getAge().equals(age);
    }

    @GetMapping("/pets/{id}")
    public ResponseEntity<?> getPetById(@PathVariable Integer id) {
        if (id >= pets.size()) {
            return ResponseEntity.badRequest()
                    .body(new ErrorBody("there is no pet with ID = " + id));
        }

        return ResponseEntity.ok(pets.get(id));
    }

    @PostMapping("/pets")
    public ResponseEntity<Void> createPet(@RequestBody Pet pet) {
        Integer id;

        synchronized (this) {
            id = ++counter;
            pets.put(id, pet);
        }

        return ResponseEntity.created(URI.create("/pets/" + id)).build();
    }

    @PutMapping("/pets/{id}")
    public synchronized void updatePet(@PathVariable Integer id,
                                       @RequestBody Pet pet) {
        pets.put(id, pet);
    }

    @DeleteMapping("/pets/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePet(@PathVariable Integer id) {
        if(!pets.containsKey(id)){
            throw new NoSuchPetException();
        }
        pets.remove(id);
    }

    private Predicate<Pet> filterByScpecie(String specie) {
        return pet -> pet.getSpecie().equals(specie);
    }
}

