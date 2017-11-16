package hillelee.apple;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

public class AppleSelectorTest {
    private List<Apple> apples;

    @Before
    public void setUp() {
        apples = ImmutableList.of(new Apple("RED", 100),
                new Apple("RED", 120),
                new Apple("RED", 110),
                new Apple("GREEN", 130),
                new Apple("GREEN", 101),
                new Apple("RED", 150));
    }

    @Test
    public void selectHeaviest() throws Exception {
       /* List<Apple> apples = ImmutableList.of(new Apple("RED", 100),
                new Apple("RED", 120),
                new Apple("GREEN", 110),
                new Apple("GREEN", 130),
                new Apple("RED", 150),
                new Apple("RED", 100));*/

        Optional<Apple> maybeHaviest = AppleSelector.getHeaviest(apples);
        if (maybeHaviest.isPresent()) {
            Apple heaviest = maybeHaviest.get();
            assertThat(heaviest.getWeight(), is(150));
        } else {
            fail();
        }
    }

    @Test
    public void selectHeaviestFromEmptyList() throws Exception {
        List<Apple> apples = ImmutableList.of();
        Optional<Apple> maybeApple = AppleSelector.getHeaviest(apples);
        if (maybeApple.isPresent()) {
            fail();

        }
    }

    @Test
    public void sort() throws Exception {
        apples = new ArrayList<>(apples);

        apples.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight().compareTo(o2.getWeight());
            }
        });
        System.out.println(apples);
    }

    @Test
    public void mapDefault() throws Exception {
        Map<String, Integer> nameToSalary = ImmutableMap.of("Ivan", 200);

        Integer salary = nameToSalary.getOrDefault("Stephan", 0);
    }

    @Test
    public void filterByPredicate() throws Exception {
        List<Apple> filtered = AppleSelector.filter(apples, new ColorPredicate());

        assertThat(filtered, hasSize(2));
    }

    @Test
    public void filterByAnonymousPredicateWithoutStreams () throws Exception {
        // with Lambda
        List<Apple> filtered = AppleSelector.filter(apples, apple -> apple.getWeight() > 120);
        assertThat(filtered, hasSize(2));

       /*  // Doesn't work with Object notation here - namely, with <T> List
        List<Apple> filtered = AppleSelector.filter(apples, new Predicate() {

            public Boolean test(Apple apple) {
                return apple.getWeight() > 120;
            }
        });*/
    }

    @Test
    public void filterByAnonymousPredicateWithSTREAMS () throws Exception {
      /*
        // with Lambda
        List<Apple> filtered = AppleSelector.filter(apples, apple -> apple.getWeight() > 120);
*/

        Predicate<Apple> heavierThan120 = apple -> apple.getWeight() > 120;
        List<Apple> filtered = apples.stream()
                .filter(heavierThan120 /*or apple -> apple.getWeight() > 120*/)
                .collect(Collectors.toList());

        assertThat(filtered, hasSize(2));
    }

    @Test
    public void filterByAnonymousPredicatesCompositionWithStreams () throws Exception {

        Predicate<Apple> heavierThan120 = apple -> apple.getWeight() > 120;
        Predicate<Apple> isRed = apple -> apple.getColor().equals("RED");
        Predicate<Apple> HeaavierThan120AndRed = isRed.and(heavierThan120);
        /* the same Without variables - too UGLYYYYYYYYYY:
        HeaavierThan120AndRed = ((Predicate<Apple>) (apple -> apple.getWeight() > 120)).
                and((apple -> apple.getColor().equals("RED")));*/
//        Predicate<Apple> HeaavierThan120OrRed = isRed.or(heavierThan120);
//        Predicate<Apple> HeaavierThan120ExceptRed = (isRed.negate()).and(heavierThan120);
        List<Apple> filtered = apples.stream()
                .filter(HeaavierThan120AndRed)
                .collect(Collectors.toList());

        assertThat(filtered, hasSize(1));
    }
/*//    added before <T> list
    @Test
    public void filterByPredicateMINE *//*added before <T> list *//*() throws Exception {
        List<Apple> apples = ImmutableList.of(new Apple("RED", 100),
                new Apple("RED", 120),
                new Apple("GREEN", 110),
                new Apple("GREEN", 130),
                new Apple("RED", 150),
                new Apple("RED", 123));
        List<Apple> filtered = AppleSelector.filter(apples, new Predicate());

        assertThat(filtered, hasSize(2));
    }*/

    @Test
    public void mapToColor() throws Exception {
        List<String> colors = apples.stream()
//                .map(apple -> apple.getColor())
                //again the same 2 lines - IIdea give a hint to replace lambda with method reference
                // не до конца понял объяснение по Function/Consumer для себя запомнил, что
                // 2 apple сокращаются до одного Apple и ставится :: как отображение 2ух этих объектов
                .map(Apple::getColor)
                .collect(Collectors.toList());

        assertThat(colors, hasSize(6));
        assertSame(colors.get(0), "RED");

        for (String color : colors) {
            System.out.println(color);
        }
    }

    @Test
    public void printApples() throws Exception {

        // the same as with Streams in next line
        for (Apple apple : apples) System.out.print(apple);

        apples.stream().forEach(apple -> System.out.print(apple));
        // 2 Consumers - that's why we can replace
        //the same 2 lines - IIdea give a hint to replace lambda with method reference
        apples.stream().forEach(System.out::print);
    }

    @Test
    public void adjustColor() throws Exception {
        ColorAdjuster colorAdjuster = new ColorAdjuster();

        /*      Function<String, String> adjust = colorAdjuster::adjust;

        BiFunction<ColorAdjuster, String, String > adjustWithAdjuster = ColorAdjuster::adjust;*/

        apples.stream()
                .map(Apple::getColor)
                .map(colorAdjuster::adjust)
                .forEach(System.out::println);
    }

    @Test
    public void executionFlow() throws Exception {
        /*List<String> collect = */apples.stream()
                .filter(apple -> apple.getWeight() > 113)
                .map(Apple::getColor)
                .peek(System.out::println)
                .limit(3)
                .collect(Collectors.toList());
    }

    @Test
    public void findFirst() throws Exception {
        apples.stream()
                .filter(apple -> apple.getWeight() > 159)
                .findFirst()
                .map(Apple::getColor)
                .ifPresent(System.out::println);
    }

    @Test
    public void intStream() throws Exception {
        IntSummaryStatistics sum = apples.stream()
//                .mapToInt(Apple::getWeight)
                // the same 1 line above and 2 below
                .map(Apple::getWeight)
                .mapToInt(Integer::intValue)
                .summaryStatistics();
        System.out.println(sum);
    }

    @Test
    public void hashMapWhereKeyIsWeightAndValueIsAppleTest() throws Exception {
        Map<Integer, Apple> weightToApple = apples.stream()
                .collect(Collectors.toMap(Apple::getWeight, Function.identity()));

                assertThat(weightToApple.get(100), is(apples.get(0)));
    }

    @Test
    public void hashMapWhereKeyIsColorAndValueIsListOfWeights() throws Exception {
        Map<String, List<Apple>> collect = apples.stream().
                collect(Collectors.groupingBy(Apple::getColor, Collectors.toList()));
        System.out.println(collect.get("RED"));
    }

    @Test
    public void getAllWorms() throws Exception {
        apples.stream()
                .flatMap(apple -> apple.getWorms().stream())
                /*.map(Apple::getWorms)
                .flatMap(List::stream)*/
                .forEach(System.out::print);
    }
}