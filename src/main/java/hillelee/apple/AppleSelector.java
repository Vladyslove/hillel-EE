package hillelee.apple;

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
}
