package hillelee.pet.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Created by JavaEE on 23.12.2017.
 */

@Data
public class PrescriptionInputDto {

    private String description;
    private LocalDate start;
    @Min(1)
    @Max(12)
    @NotNull
    private Integer timesPerDay;
    @NotEmpty
    private String medicineName;
    @NotNull
    @Min(1)
    private Integer quantity;

}
