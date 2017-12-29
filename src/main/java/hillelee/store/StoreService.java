package hillelee.store;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
@Slf4j
public class StoreService {
    private final MedicineRepository medicineRepository;

    public List<Medicine> findAll() {
        return medicineRepository.findAll();
    }

    public void add(String name, Integer quantity){
        Optional<Medicine> mayBeMedicine = medicineRepository.findByName(name);

        Medicine medicine = mayBeMedicine.orElseGet(() -> new Medicine(name, 0));

        medicine.setQuantity(medicine.getQuantity() + quantity);

        medicineRepository.save(medicine);
    }

    public void decrement(String medicineName, Integer quantity){
        log.warn("In decrement method" + Thread.currentThread().getName());

        Optional<Medicine> byName = medicineRepository.findByName(medicineName);

        log.warn("read version " + byName.get().getVersion());
        Medicine medicine = byName
                            .filter(m -> m.getQuantity() >= quantity)
                            .orElseThrow(NoSuchMedicineException::new);
        log.warn("before sleep");

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        medicine.setQuantity(medicine.getQuantity() - quantity);

        medicineRepository.save(medicine);
    }
}
