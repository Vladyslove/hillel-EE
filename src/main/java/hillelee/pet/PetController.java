package hillelee.pet;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PetController {

//    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    @GetMapping(value = "/greeting")
    public String helloWorld() {
        return "helloWorld";
    }

    @GetMapping(value = "/greeting2")
    public String helloWorld2() {
        return "helloWorld22";
    }
}
