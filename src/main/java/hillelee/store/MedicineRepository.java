package hillelee.store;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by JavaEE on 23.12.2017.
 */
public interface MedicineRepository extends JpaRepository<Medicine, Integer> {

    Optional<Medicine> findByName(String name);
}
