package com.calderon.denv.pep.util;

import static com.calderon.denv.pep.constant.Constant.YYYY_MM_DD_REGEX;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {
  public static Optional<LocalDate> parseDate(String date) {
    if (date == null || date.isBlank() || date.equals("null") || date.equals("undefined"))
      return Optional.empty();
    Pattern pattern = Pattern.compile(YYYY_MM_DD_REGEX);
    Matcher matcher = pattern.matcher(date);
    if (!matcher.find())
      throw new IllegalArgumentException(
          String.format("Cannot parse date %s, expected pattern yyyy-MM-dd", date));
    return Optional.of(LocalDate.parse(matcher.group(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
  }

  private Tools() {}
}
