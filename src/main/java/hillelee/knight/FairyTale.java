package hillelee.knight;


import lombok.Data;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

public class FairyTale {
    public static void main(String[] args) {
//        Knight knight = new Knight(new Quest());
//        without Spring
        ApplicationContext ctx = new AnnotationConfigApplicationContext("hillelee");

        System.out.println(ctx.getBean("knight"));
        System.out.println(ctx.getBean(Knight.class));

    }
}

@Data
@Component
class Knight{
    private final Quest quest;
}

@Data
@Component
class Quest {
    private String task = "Kill the Dragon";
}
