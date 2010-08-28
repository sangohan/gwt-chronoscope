package org.timepedia.chronoscope.client.render.domain;

import org.timepedia.chronoscope.client.util.TimeUnit;

/**
 * @author chad takahashi
 */
public class HoursTickFormatter extends DateTickFormatter {

  public HoursTickFormatter(DateTickFormatter superFormatter) {
    super("00xx"); // e.g. "12pm"
    this.superFormatter = superFormatter;
    this.subFormatter = new MinutesTickFormatter(this);
    this.possibleTickSteps = new int[] {1, 3, 6, 12};
    this.timeUnitTickInterval = TimeUnit.HOUR;
  }

  public String formatTick() {
    int hourOfDay = currTick.getHour();
    switch (hourOfDay) {
      case 0:
        return dateFormat.dayAndMonth(currTick);
      default:
        return dateFormat.hour(hourOfDay);
    }
  }

  public int getSubTickStep(int primaryTickStep) {
    switch (primaryTickStep) {
      case 12:
      case 1:
        return 4;
      case 6:
        return 2;
      default:
        return super.getSubTickStep(primaryTickStep);
    }
  }

}