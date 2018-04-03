package hillelee.doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

public interface JpaDoctorRepository extends JpaRepository<Doctor, Integer> {

    Optional<Doctor> findById(Integer id);

    /*@Query("SELECT doctor FROM Doctor AS doctor WHERE " +
            "(LOWER (doctor.name) LIKE :name% OR :name IS NULL) AND "  +
            "(doctor.specializations s WHERE s IN :specializations OR :specializations IS NULL)")
    List<Doctor> findByNameAndSpecialization(@Param("name") String name,
                                             @Param("specializations") List<String> specializations);*/

    @Query("SELECT DISTINCT doctor FROM Doctor AS doctor JOIN doctor.specializations s \n" +
            " WHERE (s IN :specializations OR :specializations IS NULL) \n" +
            " AND (LOWER(doctor.name) = LOWER(cast(:name as text)) OR :name IS NULL) \n" +
            " ORDER BY doctor.id ")
    List<Doctor> findByNameAndSpecialization(@Param("name") String name,
                                                     @Param("specializations")List<String> specializations);

/*    @Query("SELECT doctor.specializations FROM Doctor AS doctor WHERE doctor.id = :id)")
    List<String> getSpecializations(@Param("id") Integer id);*/
}
