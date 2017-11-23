package homework.lesson3;

import com.google.common.collect.ImmutableList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static homework.lesson3.Dish.DishType.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class DishTest {
    private List<Dish> dishes;

    //for BDD
    private List<Dish> menu = new ArrayList<Dish>(){{
        add(new Dish("Chicken's wings", 1200, true, CHICKEN));
        add(new Dish("Kebab", 1800, false, BEEF));
        add(new Dish("AChicken's kebab", 1150, false, CHICKEN));
        add(new Dish("Beefsteak", 1400, false, BEEF));
        add(new Dish("Vegetables salad", 700, true, VEGETABLES));
        add(new Dish("Greek salad", 800, true, VEGETABLES));
    }};
    private Restaurant restaurant = new Restaurant(menu);


    public static void printMapValuesIntegerDish(Map<Integer, Dish> map) {
        for (Map.Entry<Integer, Dish> pair : map.entrySet()) {
            Integer key = pair.getKey();
            Dish value = pair.getValue();
            System.out.println("key is: " + key + ". value is: " + value);
        }
    }

    public static void printMapValuesDishTypeListOfDish(Map<Dish.DishType, List<Dish>> map) {
        for (Map.Entry<Dish.DishType, List<Dish>> pair : map.entrySet()) {
            Dish.DishType key = pair.getKey();
            List<Dish> value = pair.getValue();
            System.out.println("key is: " + key + ". value is: " + value);
        }
    }

    public static void printMapValueTypeOfDishListOfByNamesOfDish(Map<Dish.DishType, List<Dish>> map) {
        for (Map.Entry<Dish.DishType, List<Dish>> pair : map.entrySet()) {
            Dish.DishType key = pair.getKey();
            List<Dish> value = pair.getValue();
            System.out.println("key is: " + key + ". value is: " + value);
        }
    }
    public static void printMapKeyTypeOfDishValueDouble(Map<Dish.DishType, Double> map) {
        for (Map.Entry<Dish.DishType, Double> pair : map.entrySet()) {
            Dish.DishType key = pair.getKey();
            Double value = pair.getValue();
            System.out.println("key is: " + key + ". value is: " + value);
        }
    }

    public static void printMapKeyTypeOfDishValueListOfString(Map<Dish.DishType, List<String>> map) {
        for (Map.Entry<Dish.DishType, List<String>> pair : map.entrySet()) {
            Dish.DishType key = pair.getKey();
            List<String> value = pair.getValue();
            System.out.println("key is: " + key + ". value is: " + value);
        }
    }

    @Before
    public void setUp() {

        dishes = ImmutableList.of(new Dish("Chicken's wings", 1200, true, CHICKEN),
                new Dish("Kebab", 1800, false, BEEF),
                new Dish("AChicken's kebab", 1150, false, CHICKEN),
                new Dish("Beefsteak", 1400, false, BEEF),
                new Dish("Vegetables salad", 700, true, VEGETABLES),
                new Dish("Greek salad", 800, true, VEGETABLES));
    }


    //without Stream 1 part
    //for BDD

    @Test
    public void mostLowCaloriesDisheBDD() {
        List<Dish> list = restaurant.mostLowCaloriesDishes();
        System.out.println(list);
        assertThat(list, hasSize(2));
    }

    @Test
    public void mostLowCaloriesDishesIfNeeDJustNamesBDD() {
        List<String> list = restaurant.mostLowCaloriesDishesIfNeeDJustNames();
        System.out.println(list);
        assertThat(list, hasSize(2));
    }



    //1a-Done

    @Test
    public void mostLowCaloriesDishes() {
        List<Dish> list = restaurant.mostLowCaloriesDishes();
        System.out.println(list);
        assertThat(list, hasSize(2));
    }

    @Test
    public void mostLowCaloriesDishesTest() throws Exception {

        // first variant 43-85 ms
        List<String> lowCaloriesDishes = new ArrayList<>();
        for (Dish dish : dishes) {
            if (dish.getCalories() < 850) {
                lowCaloriesDishes.add(dish.getName());
            }
        }

        for (String name : lowCaloriesDishes) {
            System.out.println(name);
            assertThat(lowCaloriesDishes, hasSize(2));
        }

        // second variant 48-95 ms
//        Predicate<Dish> lowCalories = new Predicate<Dish>() {
//            @Override
//            public boolean test(Dish dish) {
//                return dish.getCalories() < 850;
//            }
//        };
//
//        List<String> lowCaloriesDishes = new ArrayList<>();
//        for (Dish dish : dishes) {
//            if (lowCalories.test(dish)) {
//                String name = dish.getName();
//                lowCaloriesDishes.add(name);
//            }
//        }
//
//        for (String lowCaloriesDish : lowCaloriesDishes) {
//            System.out.println(lowCaloriesDish);
//        }
//
//        /*// with Lambda
//        lowCaloriesDishes.forEach(x -> System.out.println(x));*/
//
//        assertThat(lowCaloriesDishes, hasSize(2));

    }

    //1b- DONE
    @Test
    public void twoMostRichCaloriesDishesTest() throws Exception {

        // first variant 43-70 ms
        List<String> richCaloriesDishes1 = new ArrayList<>();
        long count = 0;
        for (Dish dish : dishes) {
            if (dish.getCalories() > 1100) {
                richCaloriesDishes1.add(dish.getName());
                count++;
                if (count == 3) break;
            }
        }
        for (String s : richCaloriesDishes1) {
            System.out.println(s);

        }
        assertThat(richCaloriesDishes1, hasSize(3));

        /*// second variant 36-88 ms
        Predicate<Dish> richCalories = new Predicate<Dish>() {
            @Override
            public boolean test(Dish dish) {
                return dish.getCalories() > 1100;
            }
        };

        List<String> richCaloriesDishes = new ArrayList<>();
        long limit = 3;
        for (Dish dish : dishes) {
            if (richCalories.test(dish)) {
                String name = dish.getName();
                if (limit-- == 0) break;
                richCaloriesDishes.add(name);
            }
        }

        for (String richCaloriesDish : richCaloriesDishes) {
            System.out.println(richCaloriesDish);
        }

        assertThat(richCaloriesDishes, hasSize(3));*/
    }

    //1c-
    @Test
    public void allDishesSortedByBioThenByAlphabet() throws Exception {

        dishes = new ArrayList<>(dishes);

        dishes.sort((new Comparator<Dish>() {
            @Override
            public int compare(Dish o2, Dish o1) {
                return o1.getIsBio().compareTo(o2.getIsBio());
            }
        }).thenComparing(new Comparator<Dish>() {
            @Override
            public int compare(Dish o1, Dish o2) {
                return o1.getName().compareTo(o2.getName());
            }
        }));

        List<String> sortedByBioAndName = new ArrayList<>();
        for (Dish dish : dishes) {
            String name = dish.getName();
            sortedByBioAndName.add(name);
        }
        sortedByBioAndName.forEach(System.out::println);
    }


    //with Streams 1part
    //1a- DONE
    @Test
    public void mostLowCaloriesDishesStreamsTest() throws Exception {

        // 146-180 mls it's longer then without Streams
        Predicate<Dish> lowCalories = dish -> dish.getCalories() < 850;

        List<String> lowCaloriesDishes = dishes.stream()
                .filter(lowCalories)
                .map(Dish::getName)
                .collect(Collectors.toList());
       /* for (String lowCaloriesDish : lowCaloriesDishes) {
            System.out.println(lowCaloriesDish);
        }
        // don't write like above!!! use more readable and shorter statement like below*/
        lowCaloriesDishes.forEach(System.out::println);

        assertThat(lowCaloriesDishes, hasSize(2));
    }

    //1b- DONE
    @Test
    public void twoMostRichCaloriesDishesStreamsTest() throws Exception {

        // 144-180 mls AGAIN it's longer then without Streams

        Predicate<Dish> richCalories = dish -> dish.getCalories() > 1100;

        List<String> richCaloriesDishes = dishes.stream().
                filter(richCalories::test).
                map(Dish::getName)
                .limit(3)
                .collect(Collectors.toList());

        richCaloriesDishes.forEach(System.out::println);

        assertThat(richCaloriesDishes, hasSize(3));
    }

    //1c- DONE
    @Test
    public void allDishesSortedByBioThenByAlphabetStreamsTest() throws Exception {

        dishes = new ArrayList<>(dishes);

        dishes.sort(((Comparator<Dish>) (o2, o1) -> {
            return o1.getIsBio().compareTo(o2.getIsBio());
        }).thenComparing((o1, o2) -> {
            return o1.getName().compareTo(o2.getName());
        }));
        // the same is in the ONLY 1 line

        dishes.sort(Comparator.comparing(Dish::getIsBio).thenComparing(Dish::getName));
/*
        dishes.forEach(dish -> System.out.println(dish.getName()));
        // not usual statement that replaces previous, but it looks better
        // and in this case I don't need to do sort by stream
        //dishes.forEach(DishTest::accept);
*/
        List<String> sortedByBioAndName = dishes.stream()
                .map(dish -> dish.getName())
                .collect(Collectors.toList());
        sortedByBioAndName.forEach(System.out::println);
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
                .map(Dish::getCalories)
                .mapToInt(Integer::intValue)
                .average();
        OptionalDouble averagevegetables = dishes.stream()
                .filter(vegetables)
                .mapToInt(Dish::getCalories)
                .average();

      /*  List<Predicate<Dish>> predicateList = new ArrayList<>();
        predicateList.add(beef);
        predicateList.add(chicken);
        predicateList.add(vegetables);*/

      /*  List<OptionalDouble> averageCalories = new ArrayList<>();
        averageCalories.add(averagebeef);
        averageCalories.add(averagechicken);
        averageCalories.add(averagevegetables);
        averageCalories.forEach(System.out::println);*/

        System.out.println(averagebeef);
        System.out.println(averagechicken);
        System.out.println(averagevegetables);

        Assert.assertEquals(OptionalDouble.of(1600), averagebeef);
        Assert.assertEquals(OptionalDouble.of(1175), averagechicken);
        Assert.assertEquals(OptionalDouble.of(750), averagevegetables);
    }

    // cannot do it(
    // need to be able to use .average() on all of DishTypesSimultaneously?
    // but I can do it only on one Type of Dish
    @Test
    public void averageDishesCaloriesSeparatedByTypesWithMapStreamTest() throws Exception {

        Map<Integer, Dish> averageCaloriesOfDishTypes = dishes.stream()
                .collect(Collectors.toMap(Dish::getCalories, Function.identity()));
        printMapValuesIntegerDish(averageCaloriesOfDishTypes);

        Map<Dish.DishType, List<Dish>> groupedByType = dishes.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.toList()));
        printMapValuesDishTypeListOfDish(groupedByType);

    }


    // 2 - DONE. This part is done it was unexpectedly easy
    @Test
    public void avDishesCaloriesSeparatedByTypesWithMapStreamTest() throws Exception {
        Map<Dish.DishType, Double> dishTypeDoubleMap = dishes.stream()
                .collect(Collectors.groupingBy(
                        Dish::getType, Collectors.averagingDouble(Dish::getCalories)));
        printMapKeyTypeOfDishValueDouble(dishTypeDoubleMap);

    }

    // 3 part with streams
    // not done
    // need to transform List<Dish> to List<String> with ONLY Names of Dishes
    @Test
    public void mapDishTypeListOfStringsBioDishesTest() throws Exception {

        Map<Dish.DishType, List<Dish>> groupedByType = dishes.stream()
                .filter(Dish::getIsBio)
                .collect(Collectors.groupingBy(Dish::getType, Collectors.toList()));
        printMapValueTypeOfDishListOfByNamesOfDish(groupedByType);

        List<String> collect777 = dishes.stream().
                filter(Dish::getIsBio)
                .map(Dish::getName)
                .collect(Collectors.toList());
        collect777.forEach(System.out::println);

    }

    // 3part is done it was again unexpectedly easy
    @Test
    public void mapDishTypeAndListOfStringsBioDishesTest() throws Exception {
        Map<Dish.DishType, List<String>> dishTypeListMap = dishes.stream()
                .filter(Dish::getIsBio)
                .collect(Collectors.groupingBy(
                        Dish::getType, Collectors.mapping(Dish::getName, Collectors.toList())));

        printMapKeyTypeOfDishValueListOfString(dishTypeListMap);

    }

}