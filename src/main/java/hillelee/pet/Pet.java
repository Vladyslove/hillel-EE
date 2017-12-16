package hillelee.pet;

import hillelee.converter.HibernateDateConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String specie;
    private Integer age;
//    @Convert(converter = HibernateDateConverter.class)
    private LocalDate birthDate;

    public Pet(String name, String specie, Integer age,  LocalDate birthDate) {
        this.name = name;
        this.specie = specie;
        this.age = age;
        this.birthDate = birthDate;
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }
}
