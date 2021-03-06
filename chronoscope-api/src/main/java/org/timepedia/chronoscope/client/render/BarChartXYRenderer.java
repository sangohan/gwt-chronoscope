package org.timepedia.chronoscope.client.render;

import org.timepedia.chronoscope.client.canvas.Layer;
import org.timepedia.chronoscope.client.canvas.View;
import org.timepedia.chronoscope.client.data.tuple.Tuple2D;
import org.timepedia.chronoscope.client.gss.GssProperties;
import org.timepedia.chronoscope.client.util.Array1D;
import org.timepedia.chronoscope.client.util.Interval;
import org.timepedia.chronoscope.client.util.MathUtil;
import org.timepedia.chronoscope.client.util.date.ChronoDate;

public class BarChartXYRenderer<T extends Tuple2D> extends LineXYRenderer<T> {

  private double currInterval, prevInterval;
  private double barWidthAsPercent;
  private GssProperties activeGssBarProps, gssBarPropsEnabled, gssBarPropsDisabled;
  
  @Override
  public void beginCurve(Layer layer, RenderState renderState) {
    layer.save();
    
    lx = -1;
    currInterval = prevInterval = -1;

    final boolean isDisabled = renderState.isDisabled();
    activeGssBarProps = isDisabled ? gssBarPropsDisabled : gssBarPropsEnabled;
    assignGssPropsToLayer(activeGssBarProps, layer);
  }

  @Override
  public void drawCurvePart(int datasetIndex, int domainIndex, Layer layer, T point, int methodCallCount, RenderState renderState) {
    
    final double dataX = point.getDomain();
    final double dataY = point.getRange0();
    final double ux = plot.domainToScreenX(dataX, datasetIndex);
    final double uy = plot.rangeToScreenY(dataY, datasetIndex);

    addClickable(datasetIndex, domainIndex, renderState.getPassNumber(), dataX, dataY, ux, uy);

    ChronoDate d = ChronoDate.get(dataX);

    if (methodCallCount == 0) {
      // nothing to do
    }
    else {
      double x, y, width, height;

      currInterval = ux - lx;
      height = plot.getInnerBounds().bottomY() - ly;
      y = ly;

      if (methodCallCount == 1) {
        // Calculate the screen-x and width of first bar
        width = currInterval * barWidthAsPercent;
      }
      else {
        width = MathUtil.min(prevInterval, currInterval) * barWidthAsPercent;
      }
      
      x = lx - (width / 2.0);
      drawVerticalBar(layer, x, y ,width, height);

      prevInterval = currInterval;
    }
    
    lx = ux;
    ly = uy;
  }

  @Override
  public int getMaxDrawableDatapoints() {
    return 100;
  }
  
  @Override
  protected Interval getDrawableDomain(Array1D mipmappedDomain) {
    if (mipmappedDomain.size() <= 1) {
      return super.getDrawableDomain(mipmappedDomain);
    }
    else {
      // Barcharts require some padding before the first bar and
      // after the last bar so that these bars are not cropped.

      double x0 = mipmappedDomain.get(0);
      double firstInterval = mipmappedDomain.get(1) - x0;
      double minDomain = x0 - (firstInterval / 2.0);
      
      int lastIdx = mipmappedDomain.size() - 1;
      double xLast = mipmappedDomain.get(lastIdx);
      double lastInterval = xLast - mipmappedDomain.get(lastIdx - 1);
      double maxDomain = xLast + (lastInterval / 2.0);
      
      return new Interval(minDomain, maxDomain);
    }
  }
  
  @Override
  public String getType() {
    return "bar";
  }

  @Override
  public void endCurve(Layer layer, RenderState renderState) {
    // Render the final bar (i.e. te furthest one to the right)
    double width = currInterval * barWidthAsPercent;
    double x = lx - (width / 2.0);
    double height = plot.getInnerBounds().bottomY() - ly;
    double y = ly;

    drawVerticalBar(layer, x, y, width, height);
    
    layer.restore();
  }
  
  @Override
  public void initGss(View view) {
    super.initGss(view);
    gssBarPropsEnabled = view.getGssProperties(this, "");
    gssBarPropsDisabled = view.getGssProperties(this, "disabled");
    
    double barWidthAsPercent = (double)gssBarPropsEnabled.width / 100.0;
    this.barWidthAsPercent = MathUtil.bound(barWidthAsPercent, 0.05, 1.0);
  }
  
  /**
   * Draws a vertical bar whose upper-left corner is positioned at coordinate (x,y),
   * and has width=w and height=h.
   */
  private void drawVerticalBar(Layer layer, double x, double y, double w, double h) {
    layer.beginPath();
    layer.moveTo(x, y);
    layer.lineTo(x + w, y);
    layer.lineTo(x + w, y + h);
    layer.lineTo(x, y + h);
    layer.closePath();
    layer.fill();
  }
  
  private void assignGssPropsToLayer(GssProperties gss, Layer layer) {
    layer.setFillColor(gss.color);
    layer.setLineWidth(gss.lineThickness);
    layer.setShadowBlur(gss.shadowBlur);
    layer.setShadowColor(gss.shadowColor);
    layer.setShadowOffsetX(gss.shadowOffsetX);
    layer.setShadowOffsetY(gss.shadowOffsetY);
    layer.setStrokeColor(gss.color);
    layer.setTransparency((float) gss.transparency);
 }
}
