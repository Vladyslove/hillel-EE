package homework.lesson3;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dish {
    String name;
    Integer calories;
    Boolean isBio;
    DishType type;

    public void setType(DishType type) {
        this.type = type;
    }

    enum DishType{
            BEEF,
            CHICKEN,
            VEGETABLES
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Restaurant {
    List<Dish> menu;
}
