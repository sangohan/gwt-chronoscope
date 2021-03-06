package org.timepedia.chronoscope.client.util;

/**
 * Enumeration over common time units.
 * 
 * @author Chad Takahashi
 */
public enum TimeUnit {
  MS (1),
  TENTH_SEC (100),
  SEC (1000),
  QUARTER_MIN(SEC.ms * 15),
  HALF_MIN(SEC.ms * 30),
  MIN (SEC.ms * 60),
  QUARTER_HOUR(MIN.ms * 15),
  HALF_HOUR(MIN.ms * 30),
  HOUR (MIN.ms * 60),
  QUARTER_DAY(HOUR.ms * 6),
  HALF_DAY(HOUR.ms * 12),
  DAY (HOUR.ms * 24),
  WEEK (DAY.ms * 7),
  MONTH (DAY.ms * (365.2425 / 12.0)),
//  QUARTER (DAY.ms * 365.2425/4),
//  HALF_YEAR(DAY.ms * 365.2425/2),
  YEAR (DAY.ms * 365.2425), // avg # days in year according to Gregorian calendar
  DECADE (YEAR.ms * 10),
  CENTURY (DECADE.ms * 10),
  MILLENIUM (CENTURY.ms * 10);
  
  private final double ms;
  
  private TimeUnit(double lengthInMilliseconds) {
    this.ms = lengthInMilliseconds;
  }
  
  /**
   * Returns this time interval in milliseconds.
   */
  public double ms() {
    return ms;
  }
  
  /**
   * Returns the next largest time unit, or null if this is the largest time unit.
   */
  public TimeUnit nextLargest() {
    if (this.ordinal() < TimeUnit.values().length - 1) {
      return TimeUnit.values()[this.ordinal() + 1];
    }
    
    return null;
  }
  
  public TimeUnit nextSmallest() {
    if (this.ordinal() > 0) {
      return TimeUnit.values()[this.ordinal() - 1];
    }
    
    return null;
  }
  
  public static final void main(String[] args) {
    System.out.println((long)TimeUnit.YEAR.ms);
  }
}
