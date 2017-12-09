package hillelee.doctor;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class DoctorRepository {
    private Map<Integer, Doctor> doctors = new HashMap<>();
    {
        addDoctor(new Doctor(createId(),"John Doe", "Dentist"));
        addDoctor(new Doctor(createId(),"Jane Roe", "Therapist"));
        addDoctor(new Doctor(createId(),"Drake Ramore", "Surgeon"));
    }

    private void addDoctor(Doctor doctor) {
        doctors.put(doctor.getId(), doctor);
    }

    private Integer createId() {
        return new Random().nextInt(100000);
    }

    public Optional<Doctor> getDoctorByID(Integer id) {
        return Optional.ofNullable(doctors.get(id));
    }

    public Collection<Doctor> findAll() {
        return doctors.values();
    }

    public Optional <Doctor> save(Doctor doctor) {
        if (doctor.getId() == null) {
            doctor.setId(createId());
        }
        doctors.put(doctor.getId(), doctor);
        return Optional.of(doctor);
    }

    public Optional<Doctor> updateDoctor(Integer id, Doctor doctor) {

        if (!doctors.containsKey(id)) {
            return Optional.empty();
        }
        doctors.put(id, doctor);
        return Optional.of(doctor);
    }

    public Optional<Doctor> delete(Integer id) {
        return Optional.ofNullable(doctors.remove(id));
    }
}
