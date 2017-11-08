package hillelee.apple;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class AppleSelectorTest {

    @Test
    public void selectHeaviest() throws Exception {
        List<Apple> apples = ImmutableList.of(new Apple("RED", 100),
                new Apple("RED", 120),
                new Apple("GREEN", 110),
                new Apple("GREEN", 130),
                new Apple("RED", 150),
                new Apple("RED", 123));

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
        List<Apple> apples = ImmutableList.of(new Apple("RED", 100),
                new Apple("RED", 120),
                new Apple("GREEN", 110),
                new Apple("GREEN", 130),
                new Apple("RED", 150),
                new Apple("RED", 123));

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
        List<Apple> apples = ImmutableList.of(new Apple("RED", 100),
                new Apple("RED", 120),
                new Apple("GREEN", 110),
                new Apple("GREEN", 130),
                new Apple("RED", 150),
                new Apple("RED", 123));
        List<Apple> filtered = AppleSelector.filter(apples, new ColorPredicate());

        assertThat(filtered, hasSize(2));
    }

    @Test
    public void filterByAnonymousPredicate () throws Exception {
        List<Apple> apples = ImmutableList.of(new Apple("RED", 100),
                new Apple("RED", 120),
                new Apple("GREEN", 110),
                new Apple("GREEN", 130),
                new Apple("RED", 150),
                new Apple("RED", 123));

/*
        // with Lambda
        List<Apple> filtered = AppleSelector.filter(apples, apple -> apple.getWeight() > 120);
*/
        List<Apple> filtered = AppleSelector.filter(apples, new ApplePredicate() {
            @Override
            public Boolean test(Apple apple) {
                return apple.getWeight() > 120;
            }
        });

        assertThat(filtered, hasSize(2));
    }



}