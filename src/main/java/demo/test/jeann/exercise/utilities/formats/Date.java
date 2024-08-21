package demo.test.jeann.exercise.utilities.formats;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static demo.test.jeann.exercise.utilities.AppConstant.DATE_PATTERN;

public final class Date {

  public static String dateNow() {
    return DateTimeFormatter.ofPattern(DATE_PATTERN)
        .withZone(ZoneOffset.UTC)
        .format(LocalDateTime.now());
  }

  public Date() {}
}
