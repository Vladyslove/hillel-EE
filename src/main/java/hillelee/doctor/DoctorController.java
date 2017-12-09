package hillelee.doctor;

import hillelee.util.ErrorBody;
import lombok.AllArgsConstructor;
import org.springframework.expression.spel.ast.OpInc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.net.URI;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class DoctorController {

    private DoctorService doctorService;

    @GetMapping("/doctors/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable Integer id) {
        Optional<Doctor> mayBeDoctor = doctorService.getDoctorByID(id);

        return mayBeDoctor.map(Object.class::cast)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest()
                        .body(new ErrorBody("there is no pet with ID = " + id)));

    }

    @GetMapping("/doctors")
    public List<Doctor> getDoctors(@RequestParam Optional<String> specialisation,
                                   @RequestParam Optional<String> name) {
        return doctorService.getDoctors(name, specialisation);
    }


    @PostMapping("/doctors")
    public ResponseEntity<? super Doctor> createDoctor(@RequestBody Doctor doctor) {
        Optional <Doctor> saved = doctorService.save(doctor);
        if (saved.isPresent()) {
           return ResponseEntity.created(URI.create("/doctors/" + saved.get().getId())).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/doctors/{id}")
    public ResponseEntity<?> updateDoctor (@PathVariable Integer id,
                                                @RequestBody Doctor doctor){
        Optional<Doctor> updated = doctorService.updateDoctor(id, doctor);

        if (updated.isPresent()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
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

