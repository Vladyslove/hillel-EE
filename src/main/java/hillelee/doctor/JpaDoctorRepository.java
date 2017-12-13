package hillelee.doctor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaDoctorRepository extends JpaRepository<Doctor, Integer> {

    Optional<Doctor> getDoctorByID(Integer id);
    Optional<Doctor> saveDoctor(Doctor doctor);
    Optional<Doctor> updateDoctor(Integer id, Doctor doctor);
}