package exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DeadLineException extends Exception {
    public DeadLineException(String message) {
        super(message);
    }
    public static void checkDeadLine(String deadLine) throws DeadLineException {
        LocalDateTime deadLineData =LocalDateTime.parse(deadLine , DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime now = LocalDateTime.now();

        if(!deadLineData.isAfter(now)){
            throw new DeadLineException("The time must be in the future.");
        }
    }
}
