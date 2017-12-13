package hillelee.doctor;

import hillelee.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final JpaDoctorRepository doctorRepository;
    private final Config config;

    public Optional<Doctor> getDoctorByID(Integer id) {
        return doctorRepository.getDoctorByID(id);
    }

    public List<Doctor> getDoctors(Optional<String> name, Optional<String> specialisation) {
        Predicate<Doctor> specialisationFilter = specialisation.map(this::filterBySpecialisation)
                .orElse(doctor -> true);
        Predicate<Doctor> nameFilter = name.map(this::filterByName).
                orElse(doctor -> true);
        Predicate<Doctor> complexFilter = specialisationFilter.and(nameFilter);

        return doctorRepository.findAll().stream()
                .filter(complexFilter)
                .collect(Collectors.toList());
    }
    private Predicate<Doctor> filterBySpecialisation (String specialisation){
        return doctor -> doctor.getSpecialization().equals(specialisation);
    }
    private Predicate<Doctor> filterByName(String name) {

        return doctor -> doctor.getName().equals(name);
    }


    public Optional <Doctor> save(Doctor doctor) {
        confirmSpecialization(doctor);
        Optional<Doctor> savedDoctor = doctorRepository.ge
        return doctorRepository.save(doctor);
    }

    private void confirmSpecialization(Doctor doctor) {
        if (!config.getSpecializations().contains(doctor.getSpecialization())) {
            throw new UnconfirmedDoctorSpecialization("Specialization " + doctor.getSpecialization() +
            "is forbidden (use '/doctors/specializations' endpoint to check permitted list");
        }
    }

    public Optional<Doctor> updateDoctor(Integer id, Doctor doctor) {
        confirmIdNotChanged(id, doctor);
        return doctorRepository.updateDoctor(id, doctor);
    }

    private void confirmIdNotChanged(Integer id, Doctor doctor) {
        if (!Objects.equals(id, doctor.getId())) {
            throw new IdChangingIsForbidden("Existing doctor ID changing is forbidden");
        }
    }

    public Optional<Doctor> delete(Integer id) {
        Optional<Doctor> mayBeDoctor = doctorRepository.getDoctorByID(id);
        mayBeDoctor.ifPresent(doctor -> doctorRepository.delete(doctor.getId()));

        mayBeDoctor.map(Doctor::getId)
                .ifPresent(id1 -> doctorRepository.delete(id1));
        return mayBeDoctor;
    }
}
