package org.timepedia.chronoscope.client;

import org.timepedia.chronoscope.client.data.tuple.Tuple2D;
import org.timepedia.chronoscope.client.plot.DefaultXYPlot;
import org.timepedia.chronoscope.client.render.XYPlotRenderer;

/**
 *
 */
public class ChronoscopeMock {

  public static MockChartPanel createTimeseriesChart(Dataset[] ds, int width,
      int height) {

    Datasets<Tuple2D> datasets = new Datasets<Tuple2D>(ds);
    XYPlotRenderer plotRenderer = new XYPlotRenderer();

    DefaultXYPlot plot = new DefaultXYPlot();
    plot.setDatasets(datasets);
    plot.setPlotRenderer(plotRenderer);

    return new MockChartPanel(plot, width, height);
  }
}
