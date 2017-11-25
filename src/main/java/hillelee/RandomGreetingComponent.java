package hillelee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/** @HW4
3.    Связать контроллер и компонент случайных приветствий используя dependency-injection
 */

@Component
public class RandomGreetingComponent {

    private static List<String> listOfOutputMessages = new ArrayList<>();

    @Bean
    public static String displayRandomGreetingComponent() {
        listOfOutputMessages.add("hello world with Class and method and Component");
        listOfOutputMessages.add("hola world with Class and method and Component");
        listOfOutputMessages.add("bonjour world with Class and method and Component");
        String output = listOfOutputMessages.get((int) (Math.random() * 3));
        return output;
    }
}
