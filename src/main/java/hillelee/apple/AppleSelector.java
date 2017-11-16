package hillelee.apple;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

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

    public static List<Apple> filterHeavy(List<Apple> apples, Integer weight) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : apples) {
            if (apple.getWeight() > weight) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterByColor(List<Apple> apples, String color) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : apples) {
            if (apple.getColor().equals(color)) {
                result.add(apple);
            }
        }
        return result;
    }
    /*// before making <T> list
    public static List<Apple> filter(List<Apple> apples, ApplePredicate predicate){
        List<Apple> result = new ArrayList<>();
        for (Apple apple : apples) {
            if (predicate.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }*/
    public static <T> List<T> filter(List<T> items, Predicate<T> predicate){
        List<T> result = new ArrayList<>();
        for (T item : items) {
            if (predicate.test(item)) {
                result.add(item);
            }
        }
            return result;
    }
}
    /*// added by me
    class WeightPredicate implements ApplePredicate {
        @Override
        public Boolean test(Apple apple) {
            return apple.getWeight() > 120;
        }
    }*/

    /*// before making <T> list
        @FunctionalInterface
        interface ApplePredicate {
            Boolean test(Apple apple);
        }*/
   /* //delete because Java has own Predicate
@FunctionalInterface
interface Predicate <T> {
    Boolean test(T apple);
}*/
    /*// before making <T> list
    class ColorPredicate implements ApplePredicate {
        @Override
            public Boolean test(Apple apple) {
            return apple.getColor().equals("GREEN");
        }
    }*/
    class ColorPredicate implements Predicate<Apple> {
    @Override
    public boolean test(Apple apple) {
        return apple.getColor().equals("GREEN");
    }
}

