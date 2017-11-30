package hillelee.doctor;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DoctorController {

    private Map<Integer, Doctor> doctors = new HashMap<Integer, Doctor>(){{
        put(0,new Doctor(1, "John Doe", "Dentist"));
        put(1,new Doctor(2, "Jane Roe", "Therapist"));
        put(2,new Doctor(3, "Drake Ramore", "Surgeon"));
    }};

    private Integer counter = doctors.size();


    @PostMapping("/doctors")
    public ResponseEntity<Void> createDoctor(@RequestBody Doctor doctor) {
        doctors.put(counter++, doctor);
        return ResponseEntity.created(URI.create("doctors/" + counter)).build();
    }

    @GetMapping("doctors")
    public Map<Integer, Doctor> getDoctor() {
        return doctors;
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
//@NoArgsConstructor
class Doctor {
    private Integer id;
    private String name;
    private String specializaion;

}
