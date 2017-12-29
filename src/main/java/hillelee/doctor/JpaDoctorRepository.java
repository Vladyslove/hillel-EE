package hillelee.doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaDoctorRepository extends JpaRepository<Doctor, Integer> {

    Optional<Doctor> findById(Integer id);

    @Query("SELECT doctor FROM Doctor AS doctor WHERE " +
    "(doctor.name = :name OR :name IS NULL) AND " +
    "(doctor.specialisation =: specialisation OR :specialisation IS)")
    List<Doctor> findByNameAndSpecialisation(@Param("name") String name, @Param("specialisation") String specialisation);
}
