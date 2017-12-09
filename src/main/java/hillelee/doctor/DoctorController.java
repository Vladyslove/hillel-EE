package hillelee.doctor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
public class DoctorController {

    private Map<Integer, Doctor> doctors = new HashMap<>();

    {
        addDoctor(new Doctor(createId(),"John Doe", "Dentist"));
        addDoctor(new Doctor(createId(),"Jane Roe", "Therapist"));
        addDoctor(new Doctor(createId(),"Drake Ramore", "Surgeon"));
    }

    @PostMapping("/doctors")
    public ResponseEntity<? /*super Doctor*/> createDoctor(@RequestBody Doctor doctor) {
        if (doctor.getId() == null) doctor.setId(createId());
        if (doctors.containsKey(doctor.getId())) {
            return ResponseEntity.badRequest().build();
        }
        doctors.put(doctor.getId(), doctor);
        return ResponseEntity.created(URI.create("doctors/" + doctor.getId())).build();
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

    private void addDoctor(Doctor doctor) {
        doctors.put(doctor.getId(), doctor);
    }

    private Integer createId() {
        return new Random().nextInt(100000);

    }
}

