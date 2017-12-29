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

    @Convert(converter = StringListConverter.class)
    private List<String> specializations;

    public Doctor(String name, List<String> specializations) {
        this.name = name;
        this.specializations = specializations;
    }

}
