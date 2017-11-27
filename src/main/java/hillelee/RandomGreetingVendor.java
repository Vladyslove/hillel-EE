package hillelee;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
/** @HW4
2.    Выделить код, который выдает случайные приветствия в отдельный класс
3.    Связать контроллер и компонент случайных приветствий используя dependency-injection
 */

@Component
public class RandomGreetingVendor {

    private List<String> listOfOutputMessages = new ArrayList<String>(){{
    listOfOutputMessages.add("hello world via using dependency-injection");
    listOfOutputMessages.add("hola world via using dependency-injection");
    listOfOutputMessages.add("bonjour world via using dependency-injection");
    }};

    public String getRandomGreeting() {
        return listOfOutputMessages.get((int) (Math.random() * 3));
    }
}
