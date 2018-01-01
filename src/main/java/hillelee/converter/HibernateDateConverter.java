package hillelee.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by JavaEE on 16.12.2017.
 */

//@Converter(autoApply = true) // we say to Hiber: "If you need to convert sqlDate in LocalDate use this converter(class)
    // autoApply - is not good because it uses for all fields
public class HibernateDateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate attribute) {
        if (attribute == null) {
            return null;
        }
        return Date.valueOf(attribute);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date dbData) {
        if (dbData == null) {
            return null;
        }
        return dbData.toLocalDate();
    }
}
