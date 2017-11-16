package hillelee.apple;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class AppleSelectorTest {
    private List<Apple> apples;

    @Before
    public void setUp() {
        apples = ImmutableList.of(new Apple("RED", 100),
                new Apple("RED", 120),
                new Apple("GREEN", 110),
                new Apple("GREEN", 130),
                new Apple("RED", 150),
                new Apple("RED", 100));
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
        List<Apple> filtered = apples.stream()
                .filter(apple -> apple.getWeight() > 120)
                .collect(Collectors.toList());

        assertThat(filtered, hasSize(2));
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

    }
}