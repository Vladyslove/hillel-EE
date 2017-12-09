package hillelee.doctor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "There is no doctor with such number"  )
public class NoSuchDoctorException extends RuntimeException {
}
