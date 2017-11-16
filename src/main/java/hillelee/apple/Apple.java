package hillelee.apple;

import com.google.common.collect.ImmutableList;
import hillelee.defaultMethod.Fruit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Apple implements Fruit {

    private String color;
    private Integer weight;
    private List<String> worms = ImmutableList.of("worm1", "worm2", "worm3");

    public Apple(String color, Integer weight) {
        this.color = color;
        this.weight = weight;
    }
}
