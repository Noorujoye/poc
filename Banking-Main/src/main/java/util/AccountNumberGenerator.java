package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class AccountNumberGenerator {

    private final AtomicInteger val = new AtomicInteger(1000);

    public String generateAccountNumber() {

        String timeStamp =
                LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
        int suffix =
                val.updateAndGet(
                s -> (s >= 9999) ? 1000 : s + 1
                );

        final String accountNumber = timeStamp + suffix;
        return accountNumber;
    }
}

/*
        int seq = sequence.getAndIncrement(); // updateAndGet()
        if (seq > 9999) {
            sequence.set(1000);
        }
        Atomic integer ensure no two threads get the same number ,

Refer line no : 16
     it is more secure
     this handles the reset logic automatically,
     prevents from multiple thread to get the same values

 */