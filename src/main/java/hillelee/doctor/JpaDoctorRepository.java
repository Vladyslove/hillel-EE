package hillelee.doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaDoctorRepository extends JpaRepository<Doctor, Integer> {

    @Query("SELECT doctor FROM Doctor AS doctor WHERE " +
    "(doctor.name = :name OR :name IS NULL) AND " +
    "(doctor.specialisation =: specialisation OR :specialisation IS)")
    List<Doctor> findByNameAndSpecialisation(@Param("name") String name, @Param("specialisation") List<String> specialisation);
}
