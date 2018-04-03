package hillelee.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LatinNameValidator implements ConstraintValidator<LatinName, String> {
   public void initialize(LatinName constraintAnnotation) {
   }

   public boolean isValid(String value, ConstraintValidatorContext context) {
      if (value == null || value.isEmpty()) {
         return true;
      }
      return value.contains("um") || value.contains("us") ;
   }
}
