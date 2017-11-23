package homework.lesson3;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dmitriy.chebotarev@hpe.com on 11/17/2017.
 *
 1. Вывести названия блюд:
 - только низкокаллорийных
 - топ-3 самых питательных
 - всех, но отсортированных сначала по БИО, потом по алфавиту
 2. Посчитать среднюю калорийность по группам: говядина, курица, овощи (Map<DishType, Double>)
 3. Сгрупировать в Map<DishType, List<String>> БИО блюда
 4. Класс hillelee.reflection.ProblemSolver переделать на использование Stram API
 */

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
    private List<Dish> menu;

    // 1a
    public List<Dish> mostLowCaloriesDishes() {
        return menu.stream()
                .filter(dish -> dish.getCalories() < 850)
//                .map(Dish::getName)
                .collect(Collectors.toList());
    }

    public List<String> mostLowCaloriesDishesIfNeeDJustNames() {
        return menu.stream()
                .filter(dish -> dish.getCalories() < 850)
                .map(Dish::getName)
                .collect(Collectors.toList());
    }
}


