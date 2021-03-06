package org.timepedia.chronoscope.client.render.domain;

import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Export;

/**
 * Formats domain ticks as generic integers.
 *
 * @author chad takahashi
 */
@ExportPackage("chronoscope")
public final class IntTickFormatterFactory extends TickFormatterFactory<Long>
    implements Exportable {

  @Export
  public IntTickFormatterFactory() {
  }

  private static final String[] ZERO_STRINGS = {"", "0", "00", "000", "0000",
      "00000", "000000", "0000000", "00000000", "000000000", "0000000000",
      "00000000000", "000000000000"};

  @Override
  protected TickFormatter<Long> createRootTickFormatter() {
    int[] domainLengths = {1, 10, 20, 50, 100, 200, 400, 500, 1000, 10000,
        100000, 1000000, 10000000, 100000000, 1000000000};

    IntTickFormatter prev = null;
    for (int i = 0; i < domainLengths.length; i++) {
      final int domainLength = domainLengths[i];

      // This creates a dummy label whose length corresponds to a typical range of
      // svn rev#'s (i.e. it assumes that revs started at 1 and are consecutive).
      // TODO: Modify TickFormatter framework to allow for dynamic tick label 
      // width calcs.
      String prototypeLabel = ZERO_STRINGS[1 + Integer.toString(domainLength)
          .length()];

      IntTickFormatter curr = new IntTickFormatter(domainLength,
          prototypeLabel);
      if (prev != null) {
        curr.subFormatter = prev;
        prev.superFormatter = curr;
      }

      prev = curr;
    }

    return prev;
  }

  @Override
  protected double getAffinityFactor() {
    return 0.25;
  }

}
