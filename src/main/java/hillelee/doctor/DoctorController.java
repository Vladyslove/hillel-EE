package hillelee.doctor;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.net.URI;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
public class DoctorController {

    private Map<Integer, Doctor> doctors = new HashMap<Integer, Doctor>() {{
        put(0, new Doctor(1, "John Doe", "Dentist"));
        put(1, new Doctor(2, "Jane Roe", "Therapist"));
        put(2, new Doctor(3, "Drake Ramore", "Surgeon"));
    }};

    private Integer counter = doctors.size();

   /* //POST2
    List<Map.Entry<Integer, Doctor>> idOfDoctors = doctors.entrySet().
            stream()
//                .filter((Predicate<? super Entry<Integer, Doctor>>) doctors.values()
//                        .stream())
            .peek
                    (e -> e.getValue().getId()).
                    collect(Collectors.toList());

    @PostMapping("/doctors")
    public ResponseEntity<?> createDoctor(@PathVariable Integer id) {
        for (int i = 0; i < idOfDoctors.size(); i++) {
            if (id.equals(idOfDoctors.get(i))) {
                return ResponseEntity.badRequest()
                        .body(new ErrorBody("Doctor with such id [ " + id + " already exist"));
            } return (ResponseEntity<?>) ResponseEntity.ok();
        }
        return null;
    }*/

    //POST1
    @PostMapping("/doctors")
    public ResponseEntity<Void> createDoctor(@RequestBody Doctor doctor) {
        doctors.put(counter++, doctor);
        return ResponseEntity.created(URI.create("doctors/" + counter)).build();
    }

    // GET1
    @GetMapping("/doctors")
    public Map<Integer, Doctor> getDoctor() {
        return doctors;
    }

    // GET2
    @GetMapping("/doctors/{id}")
    public ResponseEntity<? super Doctor> getDoctorById(@PathVariable Integer id) {
        if (id >= doctors.size()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(doctors.get(id));
    }

    //GET3
    @GetMapping("/doctors")
    public List<Doctor> getDoctors(@RequestParam Optional<String> specialisation) {

        Predicate<Doctor> specialisationFilter = specialisation.map(this::filterBySpecialisation)
                .orElse(doctor -> true);

        return doctors.values().stream()
                .filter(specialisationFilter)
                .collect(Collectors.toList());
    }
        private Predicate<Doctor> filterBySpecialisation (String specialisation){
            return doctor -> doctor.getSpecialisation().equals(specialisation);
        }

    //GET4
    @GetMapping("/doctors")
    public List<Doctor> getDoctorsByName(@RequestParam Optional<String> name) {

        final Predicate<Doctor> nameFilter = name.map(this::filterByName).
                orElse(doctor -> true);

        return doctors.values().stream()
                .filter(nameFilter)
                .collect(Collectors.toList());
    }
    private Predicate<Doctor> filterByName(String name) {
            return doctor -> doctor.getName().equals(name);
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
@NoArgsConstructor
class Doctor {
    private Integer id;
    private String name;
    private String specialisation;
}
