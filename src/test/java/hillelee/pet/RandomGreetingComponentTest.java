package hillelee.pet;

import hillelee.RandomGreetingComponent;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class RandomGreetingComponentTest {

    @Test
    public void displayRandomGreetingComponent() throws Exception {
        RandomGreetingComponent randomGreetingComponent = new RandomGreetingComponent();
        Map<String, Integer> greetingsMap = new HashMap<>();
        int sizeOfSelection = 1_000;
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
//            Set<Map.Entry<String, Integer>> entrySet = greetingsMap.entrySet();
//        double percent = computePercentage(e.getValue(), sizeOfSelection);
//        entrySet.stream()
//                .peek(e -> {
//                    System.out.println(computePercentage(e.getValue(), sizeOfSelection));
//                    Assert.assertTrue(computePercentage(e.getValue(), sizeOfSelection) <=10);
//                });
//        greetingsMap.entrySet();
        printMap(greetingsMap);

        List<String> phrases = new ArrayList<>();
        phrases.add("bonjour world with Class and method and Component");
        phrases.add("hello world with Class and method and Component");
        phrases.add("hola world with Class and method and Component");

        double percent1 = (double) (greetingsMap.get(phrases.get(0))) /sizeOfSelection * 100;
        double percent2 = (double) (greetingsMap.get(phrases.get(1))) /sizeOfSelection * 100;
        double percent3 = (double) (greetingsMap.get(phrases.get(2))) / sizeOfSelection * 100;

        double deviation1 = Math.abs(1 - (percent1/(100/3))) * 100;
        double deviation2 = Math.abs(1 - (percent2/(100/3))) * 100;
        double deviation3 = Math.abs(1 - (percent3/(100/3))) * 100;

        double averageDeviation = (deviation1 + deviation2 + deviation3) / 3;
        /*System.out.println("percent of 'bonjour world with Class and method and Component' is: " + percent1);
        System.out.println("deviation percent 1 is : " + deviation1);
        System.out.println("percent of 'hello world with Class and method and Component' is: " + percent2);
        System.out.println("deviation percent 2 is : " + deviation2);
        System.out.println("percent of 'hola world with Class and method and Component' is: " + percent3);
        System.out.println("deviation percent 3 is : " + deviation3);*/
        System.out.println("average deviation is: " + averageDeviation);
        Assert.assertTrue(averageDeviation < 10);
    }

    private static void printMap(Map<String, Integer> map) {
        for (Map.Entry<String, Integer> pair : map.entrySet()) {
            String key = pair.getKey();
            Integer value = pair.getValue();
            System.out.println("key is: " + key + ". value is: " + value);
        }
    }

    /*private double computePercentage(Integer actual, Integer sizeOfSelection) {
        double expected = sizeOfSelection / 3;

        return (Math.abs(actual - expected)) / expected * 100;
    }*/
}