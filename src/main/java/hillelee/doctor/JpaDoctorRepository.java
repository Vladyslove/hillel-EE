package hillelee.doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaDoctorRepository extends JpaRepository<Doctor, Integer> {

/*
    @Query("SELECT doctor FROM Doctor AS doctor WHERE " +
    "(:name OR LOWER (d.name) LIKE LOWER(concat(:name, '%'))) AND " +
    "(doctor.specialization =: specialization OR :specialization IS NULL)")
    List<Doctor> findByNameAndSpecialization(@Param("name") String name, @Param("specialization") List<String> specialization);
*/

    @Query("SELECT doctor FROM Doctor AS doctor WHERE " +
            "(LOWER (doctor.name) LIKE :name% OR :name IS NULL) AND "  +
            "(doctor.specialization IN :specialization OR :specialization IS NULL)")
    List<Doctor> findByNameAndSpecialization(@Param("name") String name,
                                             @Param("specialization") List<String> specialization);
}
