package hillelee.knight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class  FairyTale {
    public static void main(String[] args) {
//        Knight knight = new Knight(new Quest());
//        without Spring
        ApplicationContext ctx = new AnnotationConfigApplicationContext("hillelee");
//        System.out.println(ctx.getBean("knight"));
        Knight knight1 = ctx.getBean(Knight.class);
        Knight knight2 = ctx.getBean(Knight.class);
        System.out.println("Knighs: " + knight1);
        System.out.println("Knighs are same:" + (knight1 == knight2));
        System.out.println("Quests are same:" + (knight1.getQuest() == knight2.getQuest()));
//        System.out.println(knight1 == knight2);
    }
}

@Configuration
class Config {

    @Bean
//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    Knight knight(@Qualifier("quest") Quest quest) {
        return new Knight(quest);
    }

    @Bean
    Quest killDragon() {
        return new Quest("Kill th Dragon");
    }

    @Bean
    Quest rescuePrincess() {
        return new Quest("Rescue the Princess");
    }

    @Bean
    String task() {
        return "do nothing";
    }

}

@Data
//@Component("myKnight")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class Knight {
    private final Quest quest;
}

@Data
@Component
//@Scope("prototype")
class Quest {
    private final String task;
}
