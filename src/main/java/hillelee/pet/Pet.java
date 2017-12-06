package hillelee.pet;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
    private Integer id;
    private String name;
    private String specie;
    private Integer age;

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }
}
