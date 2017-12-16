package hillelee.pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
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
    @OneToOne(cascade = CascadeType.ALL)
    private MedicalCard medicalCard;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Prescription> prescriptions;

    public Pet(String name, String specie, Integer age,  LocalDate birthDate, MedicalCard medicalCard) {
        this.name = name;
        this.specie = specie;
        this.age = age;
        this.birthDate = birthDate;
        this.medicalCard = medicalCard;
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }
}
