package hillelee.apple;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AppleSelector {
    public static Optional<Apple> getHeaviest (List<Apple> apples) {
        Apple heaviest = null;
        for (Apple apple:apples ) {
            if (heaviest == null || apple.getWeight() > heaviest.getWeight()) {
                heaviest = apple;
            }
        }
        return Optional.ofNullable(heaviest);
    }

    public static List<Apple> filterHeavy(List<Apple > apples, Integer weight) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : apples) {
            if (apple.getWeight() > weight) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterByColor(List<Apple > apples, String color) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : apples) {
            if (apple.getColor().equals(color)) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filter(List<Apple > apples, ApplePredicate predicate) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : apples) {
            if (predicate.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

}

interface ApplePredicate {
    Boolean test(Apple apple);
}

class ColorPredicate implements ApplePredicate {
    @Override
    public Boolean test(Apple apple) {
        return apple.getColor().equals("GREEN");
    }
}


