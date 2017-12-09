package hillelee.doctor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;

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
        return doctor -> doctor.getSpecialisation().equals(specialisation);
    }
    private Predicate<Doctor> filterByName(String name) {

        return doctor -> doctor.getName().equals(name);
    }


    public Optional <Doctor> save(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Optional<Doctor> updateDoctor(Integer id, Doctor doctor) {
        return doctorRepository.updateDoctor(id, doctor);
    }

    public Optional<Doctor> delete(Integer id) {
        return doctorRepository.delete(id);
    }
}
