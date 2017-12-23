package hillelee.store;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by JavaEE on 23.12.2017.
 */
@RestController
@AllArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/medicine-store")
    private List<Medicine> getAll() {
        return storeService.findAll();
    }

    @PostMapping("/medicine-store")
    public void add(@RequestBody Medicine medicine) {
        storeService.add(medicine.getName(), medicine.getQuantity());
    }


}
