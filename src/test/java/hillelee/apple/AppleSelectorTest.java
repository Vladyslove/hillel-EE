package hillelee.apple;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
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
//            Apple heaviest = maybeApple.get();
            fail();

        }
    }
}