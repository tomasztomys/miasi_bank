package miasi_bank.interests;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
  public static int getDiffYears(Date first, Date last) {
    Calendar a = getCalendar(first);
    Calendar b = getCalendar(last);
    int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
    if (a.get(Calendar.DAY_OF_YEAR) > b.get(Calendar.DAY_OF_YEAR)) {
      diff--;
    }
    return diff;
  }

  public static Calendar getCalendar(Date date) {
    Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    cal.setTime(date);
    return cal;
  }
}
