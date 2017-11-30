package hillelee.pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//@AllArgsConstructor
@RestController
public class PetController {

    private Integer counter = 1;

    private Map<Integer, Pet> pets = new HashMap<Integer, Pet>(){{
        put(0,new Pet("Tom", "Cat", 3));
        put(1,new Pet("Jerry", "Mouse", 5));
    }};

    //CW5
    @GetMapping("/pets")
    public List<Pet> getPets(@RequestParam/*(required = false)*/ Optional<String> specie,
                             @RequestParam Optional<Integer> age) {
    /*(required = false)- don't need because of Optional*/

        Predicate<Pet> ageFilter = age.map(this::filterByAge)
                .orElse(pet -> true);
        Predicate<Pet> specieFilter = specie.map(this::filterBySpecie)
                // after operation .map - we have Optional with filter
                                                .orElse(pet -> true);

        Predicate<Pet> complexFilter = ageFilter.and(specieFilter);
       /* if (!specie.isPresent()) { // instead of if specie == null
            return pets;
        } else {*/
        return  pets.values().stream()
                .filter(complexFilter)
                .collect(Collectors.toList());
        /*}*/

    }

    private Predicate<Pet> filterByAge(Integer age) {
        return pet -> pet.getAge().equals(age);
    }

    // method which accept String and return Predicate
    private Predicate<Pet> filterBySpecie(String specie) {
        return pet -> pet.getSpecies().equals(specie);
    }

    @GetMapping("/pets/{id}")
    public ResponseEntity<? /*super Pet*/> getPetById(@PathVariable Integer id) {
        if (id >= pets.size()) {
            return ResponseEntity.badRequest() // badRequest()- give us 400 BadRequest in postMan
                    .body(new ErrorBody("There is no pets with id " + id));
        }
            return ResponseEntity.ok(pets.get(id));
    }

    @PostMapping("/pets")
    public ResponseEntity<Void> createPet(@RequestBody Pet pet) {
        pets.put(++counter, pet);
        return ResponseEntity.created(URI.create("pets/" + counter)).build();
    }

    @PutMapping("/pets/{id}")
    public void updatePet(@PathVariable Integer id,
                          @RequestBody Pet pet) {
        pets.put(id, pet);
    }

  /*  added by me PUT with exception handling
    @PutMapping("/pets/{id}")
    public ResponseEntity<? *//*super Pet*//*> updatePet(@PathVariable Integer id,
                                                     @RequestBody Pet pet) {
        if (id >=pets.size()) {
            return ResponseEntity.badRequest()
                    .body(new ErrorBody("There is not pets with id (for Put method)" + id));
        } return ResponseEntity.ok(pets.set(id, pet)) ;
    }*/

    @DeleteMapping("/pets/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePet(@PathVariable Integer id) {
        pets.remove(id.intValue( ));
    }
}

@Data
@AllArgsConstructor
class ErrorBody {
    private final Integer code = 400;
    private String body;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Pet {
    private String name;
    private String species;
    private Integer age;
}

