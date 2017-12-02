package hillelee.doctor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    //POST
    @PostMapping("/doctors")
    public ResponseEntity<? super Doctor> createDoctor(@RequestBody Doctor doctor) {

        if (doctors.containsKey(doctor.getId())) {
            return ResponseEntity.badRequest().build();
        }
        doctors.put(++counter, doctor);
        return ResponseEntity.created(URI.create("doctors/" + counter)).build();
    }

    // GET1
//    @GetMapping("/doctors")
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
//    @GetMapping("/doctors")
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

    //GET4 (not finished)
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

    //PUT1-3
    @PutMapping("/doctors/{id}")
    public ResponseEntity<Doctor> updateDoctor (@PathVariable Integer id,
                                                @RequestBody Doctor doctor){
        if (doctors.containsKey(doctor.getId())) {
           return ResponseEntity.badRequest().build();
        }
        if (id < doctors.size()) {
            doctors.put(id,doctor);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //Delete
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
