package hillelee.pet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
    //@Convert(converter = HibernateDateConverter.class)
    private LocalDate birthDate;
    @OneToOne(cascade = CascadeType.ALL)
    //@Fetch(FetchMode.JOIN)
    private MedicalCard medicalCard;
    @OneToMany(cascade = CascadeType.ALL/*, fetch = FetchType.EAGER*/)
    //@Fetch(FetchMode.JOIN)
    private List<Prescription> prescriptions;

    public Pet(String name, String specie, Integer age, LocalDate birthDate, MedicalCard medicalCard,
               List<Prescription> prescriptions) {
        this.name = name;
        this.specie = specie;
        this.age = age;
        this.birthDate = birthDate;
        this.medicalCard = medicalCard;
        this.prescriptions = prescriptions;
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }
}