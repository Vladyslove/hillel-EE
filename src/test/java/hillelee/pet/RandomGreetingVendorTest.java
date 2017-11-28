package hillelee.pet;

import hillelee.RandomGreetingVendor;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;

public class RandomGreetingVendorTest {

    @Test
    public void getRandomGreetingWithoutUsingStreams() throws Exception {
        RandomGreetingVendor RandomGreetingVendor = new RandomGreetingVendor();
        Map<String, Integer> greetingsMap = new HashMap<>();
        int sizeOfSelection = 1_000;
        String greeting;

        for (int i = 0; i < sizeOfSelection; i++) {
            greeting = RandomGreetingVendor.getRandomGreeting();
            if (greetingsMap.containsKey(greeting)) {
                greetingsMap.put(greeting, greetingsMap.get(greeting) + 1);
            } else {
                greetingsMap.put(greeting, 1);
            }
        }

        printMap(greetingsMap);

        List<String> phrases = new ArrayList<>();
        phrases.add("bonjour world via using dependency-injection");
        phrases.add("hello world via using dependency-injection");
        phrases.add("hola world via using dependency-injection");

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

    @Test
    public void getRandomGreetingWithStreams() throws Exception {
        RandomGreetingVendor RandomGreetingVendor = new RandomGreetingVendor();
        Map<String, Integer> greetingsMap = new HashMap<>();
        Integer sizeOfSelection = 3_000;
        String greeting;

        /*for (int i = 0; i < sizeOfSelection; i++) {
            greeting = RandomGreetingVendor.getRandomGreeting();
            greetingsMap.put(greeting, greetingsMap.containsKey(greeting) ? greetingsMap.get(greeting) + 1 : 1);
        }*/


        /*for (int i = 0; i < sizeOfSelection; i++) {
            greeting = RandomGreetingVendor.getRandomGreeting();
            if (greetingsMap.containsKey(greeting)) {
                greetingsMap.put(greeting, greetingsMap.get(greeting) + 1);
            } else {
                greetingsMap.put(greeting, 1);
            }
        }*/

        for (int i = 0; i < sizeOfSelection; i++) {
            greeting = RandomGreetingVendor.getRandomGreeting();
            greetingsMap.put(greeting, greetingsMap.containsKey(greeting) ? greetingsMap.get(greeting) + 1 : 1);

        }

        Set<Map.Entry<String, Integer>> set = greetingsMap.entrySet();
        set.stream().
                peek(entry -> {
                    System.out.println(RandomGreetingVendorTest.this.calculatePercentage(entry.getValue(), sizeOfSelection));
                    Assert.assertTrue(RandomGreetingVendorTest.this.calculatePercentage(entry.getValue(), sizeOfSelection) <= 10);
                    Assert.assertFalse(RandomGreetingVendorTest.this.calculatePercentage(entry.getValue(), sizeOfSelection) < 0);
                }).toArray();
        printMap(greetingsMap);
    }

    private double calculatePercentage(Integer actual, Integer sizeOfSelection) {
        double expected = sizeOfSelection / 3;

        return (Math.abs(actual - expected)) / expected * 100;
    }
    private static void printMap(Map<String, Integer> map) {
        for (Map.Entry<String, Integer> pair : map.entrySet()) {
            String key = pair.getKey();
            Integer value = pair.getValue();
            System.out.println("key is: " + key + ". value is: " + value);
        }
    }
}