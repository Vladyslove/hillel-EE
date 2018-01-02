package hillelee.doctor;

import hillelee.HilleleeConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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


    /*@GetMapping("/doctors")
    public List<Doctor> getDoctors(@RequestParam (required = false)String name ,
                                   @RequestParam (required = false) List <String> specializations) {
        return doctorService.getDoctors(name, specializations);
    }*/

    @GetMapping(value = "/doctors")
    public List<Doctor> getDoctors (@RequestParam Optional<String> name,
                                    @RequestParam Optional<List<String>> specialization) {

        return doctorService.getDoctorsUsingSingleJpaMethods(name, specialization);
    }

    @GetMapping(value = "/doctors/{id}/schedule/{date}")
    public ResponseEntity<?> getSchedule(@PathVariable Integer id,
                                         @PathVariable String date) {
        if (!doctorService.exists(id)) {
            return ResponseEntity.notFound().build();
        } else if (LocalDate.parse(date).isBefore(LocalDate.now())) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Doctor> findDoctor = doctorService.getById(id);
        return ResponseEntity.ok(findDoctor.get().getSchedule().getHourToPetId());
    }



    @PostMapping("/doctors")
    @ResponseStatus(HttpStatus.CREATED)
    public Doctor createDoctor(@RequestBody Doctor doctor) {
            return doctorService.createDoctor(doctor);
    }

    @PostMapping("/doctors/{id}/schedule/{date}/{recordingTime}")
    public ResponseEntity<Void> recordingToDoctor(@PathVariable Integer id,
                                                  @PathVariable String date,
                                                  @PathVariable Integer recordingTime,
                                                  @RequestBody Integer petId) {
        if (!doctorService.exists(id)) {
            return ResponseEntity.notFound().build();
        } else if (LocalDate.parse(date).isBefore(LocalDate.now())) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Doctor> doctor = doctorService.getById(id);
        Schedule schedule = doctor.get().getSchedule();
        Map<Integer, Integer> hourToPetId = schedule.getHourToPetId();
        if (recordingTime <= 7 && recordingTime >= 16) {
            return ResponseEntity.badRequest().build();
        } else if (hourToPetId.containsKey(recordingTime)) {
            return ResponseEntity.badRequest().build();
        }
        hourToPetId.put(recordingTime, petId);
        schedule.setHourToPetId(hourToPetId);
        doctor.get().setSchedule(schedule);
        doctorService.update(id, doctor.get());
        return ResponseEntity.created(URI.create("doctors/" + id + "/schedule/" + date + "/" + petId)).build();
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

