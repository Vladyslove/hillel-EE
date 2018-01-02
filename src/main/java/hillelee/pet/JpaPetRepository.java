package hillelee.pet;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaPetRepository extends JpaRepository<Pet, Integer> {
    Optional<Pet> findById(Integer id);

    List<Pet> findBySpecieAndAge(String specie, Integer age);

    List<Pet> findBySpecie(String specie);

    List<Pet> findByAge(Integer integer);

    @Query("SELECT pet FROM Pet AS pet " +
            "WHERE (pet.specie = :specie OR :specie IS NULL)" +
            "  AND (pet.age = :age OR :age IS NULL ) " +
            "  AND (pet.birthDate = :birthDate OR :birthDate IS NULL ) ")
    List<Pet> findNullableBySpecieAndAge(@Param("specie") String specie,
                                         @Param("age") Integer age,
                                         @Param("birthDate")LocalDate birthDate);
}