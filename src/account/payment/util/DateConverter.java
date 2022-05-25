package account.payment.util;

import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateConverter {

    public static YearMonth stringToDate(String date) {

        if(date == null || !date.matches("(0?[1-9]|1[012])-((?:19|20)[0-9][0-9])")){
            throw new IllegalArgumentException("Wrong date format!");
        }

        return YearMonth.parse(date, DateTimeFormatter.ofPattern("MM-yyyy"));
    }

    public static String dateToString(YearMonth date) {

        if (date == null) {
            throw new IllegalArgumentException("Date can`t be null!");
        }

        return  date.format(DateTimeFormatter.ofPattern("LLLL-yyyy", Locale.ENGLISH));
    }

}
