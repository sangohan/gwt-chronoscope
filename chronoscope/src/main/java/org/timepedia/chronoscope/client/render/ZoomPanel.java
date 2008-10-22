package org.timepedia.chronoscope.client.render;

import com.google.gwt.core.client.GWT;

import org.timepedia.chronoscope.client.Cursor;
import org.timepedia.chronoscope.client.canvas.Layer;
import org.timepedia.chronoscope.client.util.MathUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * UI panel containing the clickable zoom levels (e.g. "1d 5d 1m 6m 1y ...").
 * Listeners interested in knowing when the user clicks on a zoom level
 * should register themselves via {link {@link #addListener(ZoomListener)}.
 * 
 * @author Chad Takahashi
 */
public class ZoomPanel extends AbstractPanel {
  private static final int MAX_ZOOM_LINKS = 20;
  private static final String SPACE = "\u00A0";
  private static final String ZOOM_PREFIX = "Zoom:";
  
  //private int fullZoomStringWidth;
  private List<ZoomListener> listeners;
  private int[] zoomLinkWidths = new int[MAX_ZOOM_LINKS];
  private ZoomIntervals zooms;
                                                                                      
  private UIString spaceShort, spaceLong, space;
  private UIString zoomPrefixShort, zoomPrefixLong, zoomPrefix;
  
  private boolean compactMode = false;
  private boolean isMetricsComputed = false;
  private boolean doShow = true;
  
  public ZoomPanel() {
    this.listeners = new ArrayList<ZoomListener>();
  }
  
  public void init(Layer layer) { 
    height = super.calcHeight("X", layer);
    
    // Store the short and long versions of the following strings
    // so that during layout, the container can choose the best fit.
    spaceShort = new UIString("", 2);
    spaceLong = new UIString("", calcWidth(SPACE, layer) + 1);
    zoomPrefixShort = new UIString("", 0);
    zoomPrefixLong = new UIString(ZOOM_PREFIX, calcWidth(ZOOM_PREFIX, layer));
    
    computeMetrics(layer);
    
    if (compactMode) {
      resizeToMinimalWidth();
    }
    else {
      resizeToIdealWidth();
    }
  }

  public ZoomIntervals getZoomIntervals() {
    return this.zooms;
  }
  
  public void setZoomIntervals(ZoomIntervals zooms) {
    this.zooms = zooms;
  }
  
  public void addListener(ZoomListener l) {
    this.listeners.add(l);
  }

  public boolean click(int x, int y) {
    if (!doShow) {
      return false;
    }
    
    ZoomInterval zoom = detectHit(x, y);
    boolean hitDetected = (zoom != null);

    if (hitDetected) {
      for (ZoomListener listener : listeners) {
        listener.onZoom(zoom.getInterval());
      }
    }

    return hitDetected;
  }

  public void draw(Layer layer) {
    if (!doShow) {
      return;
    }
    
    layer.setStrokeColor(gssProperties.color);

    if (!isMetricsComputed) {
      computeMetrics(layer);
      isMetricsComputed = true;
    }
    
    double xCursor = x;
    drawZoomLink(layer, xCursor, y, zoomPrefix.value, false);
    xCursor += zoomPrefix.pixelWidth + space.pixelWidth;

    int i = 0;
    for (ZoomInterval zoom : zooms) {
      drawZoomLink(layer, xCursor, y, zoom.getName(), true);
      xCursor += zoomLinkWidths[i++] + space.pixelWidth;
    }
  }

  public void show(boolean b) {
    doShow = b;
  }
  
  /**
   * TODO: This method only needs to be fired when a dataset has been
   * updated (e.g. if there's only 4 years of data, and then someone
   * adds another 2 years worth of data, then the "5y" zoom link
   * will now need to be shown).
   */
  private void computeMetrics(Layer layer) {
    //fullZoomStringWidth = zoomPrefix.pixelWidth;
    int i = 0;
    for (ZoomInterval zoom : zooms) {
      //fullZoomStringWidth += space.pixelWidth;
      int w = calcWidth(zoom.getName(), layer);
      zoomLinkWidths[i++] = w;
      //fullZoomStringWidth += w;
    }
  }

  private void drawZoomLink(Layer layer, double x, double y, String label,
      boolean clickable) {
    layer.drawText(x, y, label, gssProperties.fontFamily,
        gssProperties.fontWeight, gssProperties.fontSize, textLayerName,
        clickable ? Cursor.CLICKABLE : Cursor.DEFAULT);
  }

  /**
   * If (x,y) falls on a zoom link, then return the corresponding ZoomInterval, or null if nothing was "hit".
   */
  private ZoomInterval detectHit(int x, int y) {
    if (!MathUtil.isBounded(y, this.y, this.y + this.height)) {
      return null;
    }
    
    double bx, be;
    
    // Move cursor to the 1st zoom link
    bx = this.x + zoomPrefix.pixelWidth + space.pixelWidth;

    // Rifle through the zoom links and see if any of them were clicked on.
    int i = 0;
    for (ZoomInterval zoom : zooms) {
      be = bx + this.zoomLinkWidths[i++];
      if (MathUtil.isBounded(x, bx, be)) {
        return zoom;
      }
      bx = be + space.pixelWidth;
    }

    return null;
  }

  public void resizeToMinimalWidth() {
    if (!compactMode || zoomPrefix == null) {
      zoomPrefix = zoomPrefixShort;
      space = spaceShort;
      width = calcPanelWidth();
    }
    compactMode = true;
  }

  public void resizeToIdealWidth() {
    if (compactMode || zoomPrefix == null) {
      zoomPrefix = zoomPrefixLong;
      space = spaceLong;
      width = calcPanelWidth();
    }
    compactMode = false;
  }

  private double calcPanelWidth() {
    width = zoomPrefix.pixelWidth + space.pixelWidth;

    int i = 0;
    for (ZoomInterval zoom : zooms) {
      width += zoomLinkWidths[i++];
      width += space.pixelWidth;
    }
    
    return width;
  }
  
  /**
   * Represents a string with an associated pixel width.
   */
  private static final class UIString {
    public UIString(String value, int pixelWidth) {
      this.value = value;
      this.pixelWidth = pixelWidth;
    }
    
    public String value;
    public int pixelWidth;
  }
}
