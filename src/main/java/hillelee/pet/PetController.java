package hillelee.pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//@AllArgsConstructor
@RestController
public class PetController {

    private List<Pet> pets = new ArrayList<Pet>(){{
                    add(new Pet("Tom", "Cat", 3));
                    add(new Pet("Jerry", "Mouse", 5));
            }};

    //CW5
    @GetMapping("/pets")
    public List<Pet> getPets(@RequestParam/*(required = false)*/ Optional<String> specie,
                             @RequestParam String gentle) {
    /*(required = false)- don't need because of Optional*/

        Predicate<Pet> specieFilter = specie.map(this::filterBySpecie)
                // after operation .map - we have Optional with filter
                                                .orElse(pet -> true);

       /* if (!specie.isPresent()) { // instead of if specie == null
            return pets;
        } else {*/
                 return  pets.stream()
                .filter(specieFilter)
                .collect(Collectors.toList());
        /*}*/
    }

    // method which accept String and return Predicate

    @GetMapping("/pets/{id}")
    public Pet getPetById(@PathVariable Integer id) {
        if (id >= pets.size()) {
            return 404;
        } else {
            return pets.get(id);
        }
    }

    private Predicate<Pet> filterBySpecie(String specie) {
        return pet -> pet.getSpecies().equals(specie);
    }

}

@Data
@AllArgsConstructor
class Pet {
    private String name;
    private String species;
    private Integer age;
}

