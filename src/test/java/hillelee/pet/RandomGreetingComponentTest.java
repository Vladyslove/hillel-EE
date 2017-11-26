package hillelee.pet;

import hillelee.RandomGreetingComponent;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RandomGreetingComponentTest {

    @Test
    public void displayRandomGreetingComponent() throws Exception {
        RandomGreetingComponent randomGreetingComponent = new RandomGreetingComponent();
        Map<String, Integer> greetingsMap = new HashMap<>();
        int sizeOfSelection = 100;
        String greeting;
        for (int i = 0; i < sizeOfSelection; i++) {
            greeting = randomGreetingComponent.displayRandomGreetingComponent();
            if (greetingsMap.containsKey(greeting)) {
                greetingsMap.put(greeting, greetingsMap.get(greeting) + 1);
            } else {
                greetingsMap.put(greeting, 1);
            }
        }
            // without if else - it's hard to understand at once
//            greetingsMap.put(greeting, greetingsMap.containsKey(greeting)? greetingsMap.get(greeting) + 1 : 1);
            Set<Map.Entry<String, Integer>> entrySet = greetingsMap.entrySet();
//        double percent = computePercentage(e.getValue(), sizeOfSelection);

        entrySet.stream()
                .peek(e -> {
                    System.out.println(computePercentage(e.getValue(), sizeOfSelection));
                    Assert.assertTrue(computePercentage(e.getValue(), sizeOfSelection) <=10);
                });
        greetingsMap.entrySet();

    }

    private double computePercentage(Integer actual, Integer sizeOfSelection) {
        double expected = sizeOfSelection / 3;
        return (Math.abs(actual - expected) * 100.0) / expected;
    }



}