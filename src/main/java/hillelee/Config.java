package hillelee;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "clinic-info")
@Getter
public class Config {
    private List<String> specializations = new ArrayList<>();
}
