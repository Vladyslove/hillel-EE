package hillelee.doctor;

public class IdChangingIsForbidden extends RuntimeException {
    public IdChangingIsForbidden(String message) {
        super(message);
    }
}
