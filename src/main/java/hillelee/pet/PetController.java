package hillelee.pet;

import hillelee.RandomGreetingVendor;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class PetController {

    // for HW4 part 2-3
    private RandomGreetingVendor randomGreetingVendor;

    @GetMapping(value = "/greeting2")
    public String getRandomGreeting() {
        return randomGreetingVendor.getRandomGreeting();
    }

    /**
     * @HW4 1.Изменить выводимое сообщение с “hello world” на случайное:
     * “hello world”
     * “hola world”
     * “bonjour world”...
     */
    //    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    @GetMapping(value = "/greeting")
    public String getRandomGreetingWithoutClass() {
        List<String> listOfOutputMessages = new ArrayList<>();
        listOfOutputMessages.add("hello world");
        listOfOutputMessages.add("hola world");
        listOfOutputMessages.add("bonjour world");
        return listOfOutputMessages.get((int) (Math.random() * 3));
    }
}

