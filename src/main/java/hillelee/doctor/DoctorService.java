package hillelee.doctor;

import hillelee.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final Config config;
    private final JpaDoctorRepository doctorRepository;
    //    private final DoctorRepository doctorRepository;

    /*public List<Doctor> getDoctors(Optional<String> name, Optional<String> specialisation) {
        Predicate<Doctor> specialisationFilter = specialisation.map(this::filterBySpecialisation)
                .orElse(doctor -> true);
        Predicate<Doctor> nameFilter = name.map(this::filterByName).
                orElse(doctor -> true);
        Predicate<Doctor> complexFilter = specialisationFilter.and(nameFilter);

        return doctorRepository.findAll().stream()
                .filter(complexFilter)
                .collect(Collectors.toList());
    }*/

    public List<Doctor> getDoctors(Optional<String>name, Optional<String> specialisation) {
        return doctorRepository.findByNameAndSpecialisation(name.orElse(null), specialisation.orElse(null));
    }

    /*private Predicate<Doctor> filterByName(String name) {
        return doctor -> doctor.getName().equals(name);
    }

    private Predicate<Doctor> filterBySpecialisation (String specialisation){
        return doctor -> doctor.getSpecialization().equals(specialisation);
    }*/


    public Optional<Doctor> getDoctorByID(Integer id) {
        return doctorRepository.findById(id);
    }

    public Doctor createDoctor(Doctor doctor) {
        confirmSpecialization(doctor);
        return doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(Integer id, Doctor doctor) {
        confirmIdNotChanged(id, doctor);
        confirmSpecialization(doctor);
        return doctorRepository.save(doctor);
    }

    public Optional<Doctor> deleteDoctor(Integer id) {
        Optional<Doctor> mayBeDoctor = doctorRepository.findById(id);
        mayBeDoctor.ifPresent(doctor -> doctorRepository.delete(doctor.getId()));
        return mayBeDoctor;
    }

    private void confirmSpecialization(Doctor doctor) {
        if (!config.getSpecializations().contains(doctor.getSpecialization())) {
            throw new UnconfirmedDoctorSpecialization("Specialization " + doctor.getSpecialization() +
            "is forbidden (use '/doctors/specializations' endpoint to check permitted list");
        }
    }

    private void confirmIdNotChanged(Integer id, Doctor doctor) {
        if (!Objects.equals(id, doctor.getId())) {
            throw new IdChangingIsForbidden("Existing doctor ID changing is forbidden");
        }
    }
}
