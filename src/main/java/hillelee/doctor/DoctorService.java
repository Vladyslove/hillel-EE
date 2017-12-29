package hillelee.doctor;

import hillelee.HilleleeConfig;
import hillelee.doctor.exceptions.DoctorAlreadyExistsException;
import hillelee.doctor.exceptions.DoctorNotFoundException;
import hillelee.doctor.exceptions.IdModificationIsForbiddenException;
import hillelee.doctor.exceptions.UnconfirmedDoctorSpecializationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final HilleleeConfig config;
    private final JpaDoctorRepository doctorRepository;

    public List<Doctor> getDoctors(String name, List<String> specialization) {
        if (name != null) name = name.toLowerCase();
        return doctorRepository.findByNameAndSpecialization(name, specialization);
    }

    public Doctor getDoctorByID(Integer id) {
        confirmNotExists(id);
        return doctorRepository.findOne(id);
    }

    public Doctor createDoctor(Doctor doctor) {
        confirmAlreadyExists(doctor.getId());
        confirmSpecialization(doctor);
        return doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(Integer id, Doctor doctor) {
        confirmNotExists(id);
        confirmIdNotChanged(id, doctor);
        confirmSpecialization(doctor);
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Integer id) {
        confirmNotExists(id);
        doctorRepository.delete(id);
    }

    private void confirmSpecialization(Doctor doctor) {
        if (!config.getSpecializations().contains(doctor.getSpecialization())) {
            throw new UnconfirmedDoctorSpecializationException();
        }
    }

    private void confirmIdNotChanged(Integer id, Doctor doctor) {
        if (!Objects.equals(id, doctor.getId())) {
            throw new IdModificationIsForbiddenException();
        }
    }

    private void confirmNotExists(Integer id) {
        if (!doctorRepository.exists(id)) {
            throw new DoctorNotFoundException();
        }
    }

    private void confirmAlreadyExists(Integer id) {
        if (doctorRepository.exists(id)) {
            throw new DoctorAlreadyExistsException();
        }
    }
}
