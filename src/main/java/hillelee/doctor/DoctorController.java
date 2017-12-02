package hillelee.doctor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
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
        put(25, new Doctor(25, "John Doe", "Dentist"));
        put(50, new Doctor(50, "Jane Roe", "Therapist"));
        put(100, new Doctor(100, "Drake Ramore", "Surgeon"));
    }};

    private Integer counter = doctors.size();

    @PostMapping("/doctors")
    public ResponseEntity<? /*super Doctor*/> createDoctor(@RequestBody Doctor doctor) {

        if (doctors.containsKey(doctor.getId())) {
            return ResponseEntity.badRequest().build();
        }
        doctors.put(++counter, doctor);
        return ResponseEntity.created(URI.create("doctors/" + counter)).build();
    }

    @GetMapping("/doctors/{id}")
    public ResponseEntity<? super Doctor> getDoctorById(@PathVariable Integer id) {
        if (!doctors.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(doctors.get(id));
    }

    @GetMapping("/doctors")
    public List<Doctor> getDoctors(@RequestParam Optional<String> specialisation,
                                   @RequestParam Optional<String> name) {

        Predicate<Doctor> specialisationFilter = specialisation.map(this::filterBySpecialisation)
                .orElse(doctor -> true);
        Predicate<Doctor> nameFilter = name.map(this::filterByName).
                orElse(doctor -> true);
        Predicate<Doctor> complexFilter = specialisationFilter.and(nameFilter);

        return doctors.values().stream()
                .filter(complexFilter)
                .collect(Collectors.toList());
    }
    private Predicate<Doctor> filterBySpecialisation (String specialisation){
        return doctor -> doctor.getSpecialisation().equals(specialisation);
    }
    private Predicate<Doctor> filterByName(String name) {

        return doctor -> doctor.getName().equals(name);
    }

    @PutMapping("/doctors/{id}")
    public ResponseEntity<? super Doctor> updateDoctor (@PathVariable Integer id,
                                                @RequestBody Doctor doctor){
        if (!doctors.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        if (!Objects.equals(doctor.getId(), doctors.get(id).getId())){
            return ResponseEntity.badRequest().body("ID changing is forbidden!");
        }
            doctors.put(id,doctor);
            return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/doctors/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDoctors(@PathVariable Integer id) {
        if (!doctors.containsKey(id)) {
            throw new NoSuchDoctorException();
        }
        doctors.remove(id);
    }
}

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "There is no doctor with such number"  )
class NoSuchDoctorException extends RuntimeException {
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Doctor {
    private Integer id;
    private String name;
    private String specialisation;
}
