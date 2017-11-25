package hillelee.pet;

//import hillelee.RandomGreetingComponent;
import hillelee.RandomGreetingComponent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PetController {

    // for HW4 part 3
    private RandomGreetingComponent randomGreetingComponent;
    public PetController (RandomGreetingComponent randomGreetingComponent) {
        this.randomGreetingComponent = randomGreetingComponent;
    }

    //    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    @GetMapping(value = "/greeting")
    public String helloWorld() {
        return "helloWorld";
    }

    @GetMapping(value = "/greeting2")
    public String helloWorld2() {
        return "helloWorld22";
    }

    /**
     * @HW4 1.Изменить выводимое сообщение с “hello world” на случайное:
     * “hello world”
     * “hola world”
     * “bonjour world”...
     */
    @GetMapping(value = "/greeting3")
    public String helloWorld3() {
        List<String> listOfOutputMessages = new ArrayList<>();
        listOfOutputMessages.add("hello world");
        listOfOutputMessages.add("hola world");
        listOfOutputMessages.add("bonjour world");

        String output = listOfOutputMessages.get((int) (Math.random() * 3));
        return output;
    }


    // for HW4 part 2
    @GetMapping(value = "/greeting4")
    public String helloWorld4() {
        return RandomGreeting.displayRandomGreeting();
    }

    // for HW4 part 3
    @GetMapping(value = "/greeting5")
    public String helloWorld5() {
        return randomGreetingComponent.displayRandomGreetingComponent();
    }

    // for test - can be deleted
    @GetMapping(value = "/greeting6")
    public String helloWorld6() {
        return randomGreetingComponent.displayRandomGreetingComponent();
    }
}
    /** @HW4
    2.    Выделить код, который выдает случайные приветствия в отдельный класс
     */
class RandomGreeting {
    public static String displayRandomGreeting() {
        List<String> listOfOutputMessages = new ArrayList<>();
        listOfOutputMessages.add("hello world with Class and method");
        listOfOutputMessages.add("hola world with Class and method");
        listOfOutputMessages.add("bonjour world with Class and method");
        String output = listOfOutputMessages.get((int) (Math.random() * 3));
        return output;
    }
}
