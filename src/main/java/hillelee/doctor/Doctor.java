package hillelee.doctor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> specializations;
    @OneToOne(cascade = CascadeType.ALL)
    private Schedule schedule;

    public Doctor(String name, List<String> specializations, Schedule schedule) {
        this.name = name;
        this.specializations = specializations;
        this.schedule = schedule;
    }

}
