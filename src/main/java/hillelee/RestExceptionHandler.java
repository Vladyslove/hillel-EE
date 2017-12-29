package hillelee;

import hillelee.doctor.exceptions.DoctorAlreadyExistsException;
import hillelee.doctor.exceptions.DoctorNotFoundException;
import hillelee.doctor.exceptions.IdModificationIsForbiddenException;
import hillelee.doctor.exceptions.UnconfirmedDoctorSpecializationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(value = UnconfirmedDoctorSpecializationException.class)
    public ResponseEntity<Object> HandleUnconfirmedSpecialization(RuntimeException ex, WebRequest request) {
        String body = "Specified specialization is forbidden (use '/specialties' endpoint to get no forbidden list).";
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler(value = IdModificationIsForbiddenException.class)
    public ResponseEntity<Object> HandleIdModification(RuntimeException ex, WebRequest request) {
        String body = "Id modification is forbidden";
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = DoctorNotFoundException.class)
    protected ResponseEntity<Object> HandleDoctorNotFound(RuntimeException ex, WebRequest request) {
        String body = "Doctor with such ID does not exists";
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = DoctorAlreadyExistsException.class)
    private ResponseEntity<Object> HandleDoctorAlreadyExists(RuntimeException ex, WebRequest request) {
        String body = "Doctor with such ID is already exists";
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }



}
