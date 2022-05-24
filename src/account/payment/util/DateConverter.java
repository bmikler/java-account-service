package account.payment.util;

import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateConverter {

    public static YearMonth stringToDate(String date) {
        return YearMonth.parse(date, DateTimeFormatter.ofPattern("MM-yyyy"));
    }

    public static String dateToString(YearMonth date) {
        return  date.format(DateTimeFormatter.ofPattern("LLLL-yyyy", Locale.ENGLISH));
    }

}
