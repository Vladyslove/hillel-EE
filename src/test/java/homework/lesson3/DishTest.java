package homework.lesson3;

import com.google.common.collect.ImmutableList;
import hillelee.apple.Apple;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static homework.lesson3.Dish.DishType.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class DishTest {
    private List<Dish> dishes;

    public static void printValues(Map<Integer, Apple> map) {
        for (Map.Entry<Integer, Apple> pair : map.entrySet()) {
            Apple value = pair.getValue();
            Integer key = pair.getKey();
            System.out.println("key is: " + key + ". value is: " + value);
        }
    }

    private static boolean test(Dish dish) {
        return dish.getCalories() > 900;
    }

    private static int compare(Dish o1, Dish o2) {
        return o2.getIsBio().compareTo(o1.getIsBio());
    }

    @Before
    public void setUp() {
        dishes = ImmutableList.of(new Dish("Chicken's wings", 1200, true, CHICKEN),
        new Dish("Kebab", 1800, false, BEEF),
        new Dish("Chicken's kebab", 1150, false, CHICKEN),
        new Dish("Beefsteak", 1400, false, BEEF),
        new Dish("Vegetables salad", 700, true, VEGETABLES),
        new Dish("Greek salad", 800, true, VEGETABLES));
    }

    //with Streams 1part
    //1a- DONE
    @Test
    public void mostLowCaloriesDishesStreamsTest() throws Exception {

        Predicate<Dish> lowCalories = dish -> dish.getCalories() < 850;

        List<String> lowCaloriesDishes = dishes.stream()
                .filter(lowCalories)
                .map(Dish::getName)
                .collect(Collectors.toList());
        for (String lowCaloriesDish : lowCaloriesDishes) {
            System.out.println(lowCaloriesDish);
        }
        assertThat(lowCaloriesDishes, hasSize(2));
    }

    //1b- DONE
    @Test
    public void twoMostRichCaloriesDishesStreamsTest() throws Exception {

        Predicate<Dish> richCalories = dish -> dish.getCalories() > 1100;

        List<String> lowCaloriesDishes = dishes.stream().
                filter(richCalories::test).
                map(Dish::getName)
                .limit(3)
                .collect(Collectors.toList());
        for (String lowCaloriesDish : lowCaloriesDishes) {
            System.out.println(lowCaloriesDish);
        }
        assertThat(lowCaloriesDishes, hasSize(3));
    }

    //1c- DONE
    @Test
    public void allDishesSortedByBioThenByAlphabet() throws Exception {

        dishes = new ArrayList<>(dishes);

        dishes.sort(new Comparator<Dish>() {
            @Override
            public int compare(Dish o2, Dish o1) {
                return o1.getIsBio().compareTo(o2.getIsBio());
            }
        }.thenComparing(new Comparator<Dish>() {
            @Override
            public int compare(Dish o1, Dish o2) {
                return o1.getName().compareTo(o2.getName());
            }
        }));
        // the same is in the ONLY 1 line
//        dishes.sort(Comparator.comparing(Dish::getIsBio).thenComparing(Dish::getName));
        System.out.println(dishes);
    }

    //just for myself
    @Test
    public void pickedDishesByBioAndSortedByAlphabetStreamsTest() throws Exception {

        Predicate<Dish> bio = dish -> dish.isBio;
        List <String> bioDishes = dishes.stream()
                .filter(bio)
                .map(Dish::getName)
                .collect(Collectors.toList());

        bioDishes.sort(Comparator.naturalOrder());

        for (String bioDish : bioDishes) {
            System.out.println(bioDish);
        }

        assertThat(bioDishes, hasSize(3));
    }

    //with Streams 2part
    @Test
    public void averageDishesCaloriesSeparatedByTypesWithoutMapStreamTest() throws Exception {

        Predicate<Dish> beef = dish -> dish.getType() == BEEF;
        Predicate<Dish> chicken = dish -> dish.getType() == CHICKEN;
        Predicate<Dish> vegetables = dish -> dish.getType() == VEGETABLES;
        OptionalDouble averagebeef = dishes.stream()
                .filter(beef)
                .mapToInt(Dish::getCalories)
                .average();
        OptionalDouble averagechicken = dishes.stream()
                .filter(chicken)
                .mapToInt(Dish::getCalories)
                .average();
        OptionalDouble averagevegetables = dishes.stream()
                .filter(vegetables)
                .mapToInt(Dish::getCalories)
                .average();

        System.out.println(averagebeef);
        System.out.println(averagechicken);
        System.out.println(averagevegetables);

        Assert.assertEquals(OptionalDouble.of(1600), averagebeef);
        Assert.assertEquals(OptionalDouble.of(1175), averagechicken);
        Assert.assertEquals(OptionalDouble.of(750), averagevegetables);
    }

    @Test
    public void averageDishesCaloriesSeparatedByTypesWithMapStreamTest2() throws Exception {

    }
}