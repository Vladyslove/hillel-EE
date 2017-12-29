package hillelee.doctor;

import hillelee.Config;
import hillelee.util.ErrorBody;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import javax.security.auth.login.Configuration;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class DoctorController {

    private final Config config;

    private final DoctorService doctorService;

    @GetMapping("/doctors/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable Integer id) {
        Doctor doctor = doctorService.getDoctorByID(id);

        if (doctor == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(doctor);

    }

    @GetMapping("/doctors")
    public List<Doctor> getDoctors(@RequestParam (required = false)String specialisation,
                                   @RequestParam (required = false) String name) {
        return doctorService.getDoctors(name, specialisation);
    }

    @PostMapping("/doctors")
    @ResponseStatus(HttpStatus.CREATED)
    public Doctor createDoctor(@RequestBody Doctor doctor) {
            return doctorService.createDoctor(doctor);
    }

    @PutMapping("/doctors/{id}")
    public Doctor updateDoctor (@PathVariable Integer id,
                             @RequestBody Doctor doctor){
            return doctorService.updateDoctor(id, doctor);
    }

    @DeleteMapping("/doctors/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDoctor(@PathVariable Integer id) {
            doctorService.deleteDoctor(id);
    }

    @GetMapping("/doctors/specializations")
    public List<String> getSpecialization() {
        return config.getSpecializations();
    }
}

