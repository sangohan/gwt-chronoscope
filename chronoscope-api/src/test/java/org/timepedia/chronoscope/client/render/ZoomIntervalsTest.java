package org.timepedia.chronoscope.client.render;

import junit.framework.TestCase;

import org.timepedia.chronoscope.client.util.Interval;

import java.util.Date;
import java.util.Iterator;

/**
 * @author Chad Takahashi
 *
 */
public class ZoomIntervalsTest extends TestCase {
  private ZoomInterval z1d, z1m, z1y, z5y, zMax;
  
  public void setUp() {
    z1d = new ZoomInterval("1d", 1);
    z1m = new ZoomInterval("1m", 30);
    z1y = new ZoomInterval("1y", 365);
    z5y = new ZoomInterval("5y", z1y.getInterval() * 5);
    zMax = new ZoomInterval("max", Double.MAX_VALUE).filterExempt(true);
  }
  
  public void testConstructor() {
    ZoomIntervals z = new ZoomIntervals();
    assertEquals(0, calcSize(z));
  }

  public void testAdd() {
    ZoomIntervals z = new ZoomIntervals();
    
    // Add ZoomIntervals out of order to test sorting
    z.add(z1m);
    z.add(z1y);
    z.add(z1d);
    z.add(z1m); // verify that dupes get removed
    
    // Verify that elements are iterated in sorted order.
    Iterator<ZoomInterval> itr = z.iterator();
    assertEquals(z1d, itr.next());
    assertEquals(z1m, itr.next());
    assertEquals(z1y, itr.next());
    assertFalse("Unexpected element in iterator", itr.hasNext());
  }
  
  public void testFilter() {
    ZoomIntervals z = new ZoomIntervals();
    z.add(z1d);
    z.add(z1m);
    z.add(z1y);
    z.add(z5y);
    z.add(zMax); // zMax is filter-exempt.
    
    // "1d" and "5y" should drop out, but "max" should still hang around since it's 
    // filter-exempt.
    double minInterval = z1d.getInterval() + 1;
    double timeStart = new Date().getTime();
    double timeEnd = timeStart + z5y.getInterval() - 1;
    z.applyFilter(new Interval(timeStart, timeEnd), minInterval);
    assertZoomEquals(z.iterator(), z1m, z1y, zMax);
    
    // Now clear the filter and make sure it goes back to initial state
    z.clearFilter();
    assertZoomEquals(z.iterator(), z1d, z1m, z1y, z5y, zMax);
    
    // TODO: add more filter test cases
    
  }
  
  private static int calcSize(Iterable<?> i) {
    int size = 0;
    for (Object obj : i) {
      ++size;
    }
    return size;
  }
  
  private static void assertZoomEquals(Iterator<ZoomInterval> actual, ZoomInterval... expected) {
    for (int i = 0; i < expected.length; i++) {
      TestCase.assertEquals(expected[i], actual.next());
    }
    assertFalse(actual.hasNext());
  }
}
