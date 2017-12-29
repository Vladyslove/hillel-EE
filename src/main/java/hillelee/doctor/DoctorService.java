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

    public List<Doctor> getDoctors(String name, List<String> specialisation) {
        if (name != null) name = name.toLowerCase();
        return doctorRepository.findByNameAndSpecialisation(name, specialisation);
    }

    public Doctor getDoctorByID(Integer id) {
        return doctorRepository.findOne(id);
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

    public void deleteDoctor(Integer id) {
        doctorRepository.delete(id);
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
