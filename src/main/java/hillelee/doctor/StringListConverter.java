package hillelee.doctor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> list) {
        if (list == null) return null;
        return String.join(",",list);
    }

    @Override
    public List<String> convertToEntityAttribute(String string) {
        if (string == null) return null;
        return Arrays.asList(string.split(","));
    }
}
