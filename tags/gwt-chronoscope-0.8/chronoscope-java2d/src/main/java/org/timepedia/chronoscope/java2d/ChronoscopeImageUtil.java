package org.timepedia.chronoscope.java2d;

import org.timepedia.chronoscope.client.Chart;
import org.timepedia.chronoscope.client.XYDataset;
import org.timepedia.chronoscope.client.XYPlot;
import org.timepedia.chronoscope.client.data.MockXYDataset;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author Ray Cromwell &lt;ray@timepedia.org&gt;
 */
public class ChronoscopeImageUtil {

  public static void main(String[] args) {
    JFrame jf = new JFrame();
    jf.setSize(800, 400);
    MockXYDataset dff = new MockXYDataset();
    XYDataset ds[] = new XYDataset[1];
    ds[0] = dff;
    double domainOrigin = 0;
    double currentDomain = 0;
    Image im = renderChronoscopeImage(ds, 400, 300, null, true, domainOrigin,
        currentDomain, false);
    JLabel jl = new JLabel(new ImageIcon(im));
    jf.getContentPane().add(jl);
    jf.show();
  }

  public static BufferedImage renderChronoscopeImage(XYDataset[] ds, int width,
      int height, String css, boolean interactive, double domainOrigin,
      double currentDomain, boolean az) {
    StaticImageChartPanel sicp = new StaticImageChartPanel(ds, width, height);

    Chart c = sicp.getChart();

    XYPlot xyPlot = c.getPlot();
    xyPlot.getRangeAxis(0).setAutoZoomVisibleRange(az);

    xyPlot.setDomainOrigin(domainOrigin);
    xyPlot.setCurrentDomain(currentDomain);
    c.redraw();

    BufferedImage bimg = (BufferedImage) sicp.getImage();
    return bimg;
  }
}
