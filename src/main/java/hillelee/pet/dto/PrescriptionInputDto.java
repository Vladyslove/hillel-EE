package hillelee.pet.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * Created by JavaEE on 23.12.2017.
 */

@Data
public class PrescriptionInputDto {

    private String description;
    private LocalDate start;
    private Integer timesPerDay;
    private String medicineName;
    private Integer quantity;

}
