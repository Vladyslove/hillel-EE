package hillelee.pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    //@Convert(converter = HibernateDateConverter.class) // instead of @Converter(autoApply = true) under class
    private LocalDate birthDate;
    @OneToOne(cascade = CascadeType.ALL/*for saving at first MedicalCard table*/)
    // medicalCard - is not just a simple field but is table (@OneToOne says it)
    //@Fetch(FetchMode.JOIN)
    private MedicalCard medicalCard;
    @OneToMany(cascade = CascadeType.ALL/*, fetch = FetchType.EAGER */ /*чтобы доставать коллекцию жадно, не lazy*/)
    //@Fetch(FetchMode.JOIN) // ставится над вложенным полем, чтобы сказать Хибернейту, что не нужно поле selectами вытаскивать
    // не работает - значит нужно указать это в @Query :"JOIN FETCH pet.prescriptions" и так же medicalCard
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