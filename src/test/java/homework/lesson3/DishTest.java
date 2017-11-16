package homework.lesson3;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static homework.lesson3.Dish.DishType.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class DishTest {
    private List<Dish> dishes;

    private static boolean test(Dish dish) {
        return dish.getCalories() > 900;
    }

    @Before
    public void setUp() {
        dishes = ImmutableList.of(new Dish("Kebab", 1800, false, BEEF),
        new Dish("Chicken's wings", 1200, true, CHICKEN),
        new Dish("Beefsteak", 1400, false, BEEF),
        new Dish("Vegetables salad", 600, true, VEGETABLES),
        new Dish("Greek salad", 800, true, VEGETABLES));
    }


    //with Streams 1part
    @Test
    public void mostLowCaloriesDishesStreamsTest() throws Exception {

        Predicate<Dish> lowCalories = dish -> dish.getCalories() < 900;

        List<String> lowCaloriesDishes = dishes.stream()
                .filter(lowCalories)
                .map(Dish::getName)
                .collect(Collectors.toList());
        for (String lowCaloriesDish : lowCaloriesDishes) {
            System.out.println(lowCaloriesDish);
        }
        assertThat(lowCaloriesDishes, hasSize(2));
    }

    @Test
    public void mostRichCaloriesDishesStreamsTest() throws Exception {

        Predicate<Dish> richCalories = dish -> dish.getCalories() > 1000;

        List<String> lowCaloriesDishes = dishes.stream()
                .filter(richCalories)
                .map(Dish::getName)
                .collect(Collectors.toList());
        for (String lowCaloriesDish : lowCaloriesDishes) {
            System.out.println(lowCaloriesDish);
        }
        assertThat(lowCaloriesDishes, hasSize(3));
    }

    //TODO: add sorting by alphabet functionality
    @Test
    public void pickedByBioAndSortedByAlphabetDishesStreamsTest() throws Exception {

        Predicate<Dish> bio = dish -> dish.isBio;


        List<String> bioDishes = dishes.stream()
                .filter(bio)
                .map(Dish::getName)
                .collect(Collectors.toList());
        for (String bioDish : bioDishes) {
            System.out.println(bioDish);
        }

        assertThat(bioDishes, hasSize(3));



    }

    //with Streams 2part
    @Test
    public void averageIsBioStreamTest() throws Exception {

        Predicate<Dish> bio = dish -> dish.getIsBio();
        IntSummaryStatistics intSummaryStatistics = dishes.stream()
                .filter(bio)
                .mapToInt(Dish::getCalories)
                .summaryStatistics();
        System.out.println(intSummaryStatistics);

    }

    @Test
    public void averageIsBioAndNotStreamTest1() throws Exception {

        Predicate<Dish> bio = dish -> dish.getIsBio();
        IntSummaryStatistics intSummaryStatistics = dishes.stream()
                .filter(bio)
                .mapToInt(Dish::getCalories)
                .summaryStatistics();
        System.out.println(intSummaryStatistics);

    }
}