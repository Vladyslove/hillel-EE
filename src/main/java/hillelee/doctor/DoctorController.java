package hillelee.doctor;

import hillelee.HilleleeConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DoctorController {

    private final HilleleeConfig config;

    private final DoctorService doctorService;

    @GetMapping("/doctors/{id}")
    public Doctor getDoctorById(@PathVariable Integer id) {
        return doctorService.getDoctorByID(id);
    }

    @GetMapping("/doctors/{id}/spec")
    public List<String> getDoctorSpecializations(@PathVariable Integer id) {
        return doctorService.getDoctorSpecializations(id);
    }


    @GetMapping("/doctors")
    public List<Doctor> getDoctors(@RequestParam (required = false)String name ,
                                   @RequestParam (required = false) List <String> specializations) {
        return doctorService.getDoctors(name, specializations);
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
    public List<String> getSpecializations() {
        return config.getSpecializations();
    }
}

