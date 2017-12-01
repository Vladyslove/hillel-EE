package hillelee.doctor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //POST1
    @PostMapping("/doctors")
    public ResponseEntity<Void> createDoctor(@RequestBody Doctor doctor) {
        doctors.put(++counter, doctor);
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

    //PUT1-2
    @PutMapping("/doctors/{id}")
    public ResponseEntity<Doctor> updateDoctor (@PathVariable Integer id,
                                                @RequestBody Doctor doctor){

        if (id < doctors.size()) {
            doctors.put(id,doctor);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //Delete1-2
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
