package com.calderon.denv.pep.constant;

public class Constant {
  public static final String TOKEN_COOKIE_NAME = "X-Authorization";
  public static final int TOKEN_EXPIRATION_TIME = 24 * 60 * 60 * 1000 * 15;
  public static final int START_WORK_HOUR = 8;
  public static final int END_WORK_HOUR = 20;
  public static final String YYYY_MM_DD_REGEX = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";

  public static final String AUTH_HEADER = "Authorization";

  private Constant() {}
}
