package hillelee.doctor;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//@NoArgsConstructor
public class Doctor {
    private Integer id;
    private String name;
    private String specialization;
}
