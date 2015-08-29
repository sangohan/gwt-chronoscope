


# Introduction #

Chronoscope is a fast, scalable, charting toolkit designed to work well in most web browsers and developed using Gwt.
It is packaged in a library ready to use in any Gwt application. This document describes how to setup your application and start using Chronoscope.

# Creating a new project #

  * Assuming you have [Google Web Toolkit](http://code.google.com/webtoolkit) installed already.  If not, [get started](http://code.google.com/webtoolkit/gettingstarted.html)

  * Create a new project using the `webAppCreator` command line tool:
```
 webAppCreator -out HelloChart com.example.client.HelloChart
```

  * Edit `src/com/example/HelloChart.gwt.xml` and add a line to inherit the `Chronoscope` module.
```
<module>
    <inherits name='org.timepedia.chronoscope.Chronoscope' />
    <entry-point class='com.example.client.HelloChart' />;
</module>
```

  * You'll also need to add chronoscope, chronoscope-api, gwtexporter and gin libraries to your project classpath. So select the needed libraries form the [list](GwtAPI#Dependencies.md) below and copy these libraries to the `lib` folder of the project:
```
  cp path_to/gwtexporter-x.x.x.jar path_to/chronoscope-api-x.x.x.jar \
     path_to/chronoscope-x.x.x.jar path_to/gin-x.x.x.jar \
     war/WEB-INF/lib
```


# Create a dataset file #
  * We'll use a json file for the [dataset](Datasets.md), so put it in your `war/` foder and make sure it should be in the format:
```
  dataset = {
    "id": "unrate",                                           // Unique id for this dataset
    "domainscale": 1000,                                      // Scale
    "domain": [-694306739,-691628339,-689122739,-686444339],  // UTC time in milliseconds
    "range":  [3.4, 3.8, 4.0, 3.9,],                          // Values
    "label":  "Unemployment",                                 // Dataset label
    "axis":   "percent"                                       // Chart unit
  }
```

  * Modify the index.html `webAppCreator` made, and add a script tag to load the data:
```
  <script type="text/javascript" src="dataset.js"></script>
```

# Use chronoscope in your javacode #
  * Modify your javacode `HelloChart.java`:
```
public class DevTest implements EntryPoint {
  public void onModuleLoad() {
      double GOLDEN_RATIO = 1.618;
      int chartWidth = 600, chartHeight = (int) ( chartWidth / GOLDEN_RATIO );
      Chronoscope.setFontBookRendering(true);

      Dataset[] dataset = new Dataset[1];
      Chronoscope.initialize();
      Chronoscope chronoscope = Chronoscope.getInstance();
      dataset[0] = chronoscope.createDataset(getJson("dataset"));
      
      VerticalPanel vp=new VerticalPanel();
      vp.add(new Label(dataset[0].getRangeLabel()));
      ChartPanel chartPanel = Chronoscope.createTimeseriesChart(dataset, chartWidth, chartHeight);
      vp.add(chartPanel);
      RootPanel.get().add(vp);
  }

  private static native JsonDatasetJSO getJson(String varName) /*-{
     return $wnd[varName];
  }-*/;

}
```

  * Versions of Chronoscope older than 1.0 use a different API
```
public class HelloChart implements EntryPoint {

    public void onModuleLoad() {
        double GOLDEN_RATIO = 1.618;
        int chartWidth = 600, chartHeight = (int) ( chartWidth / GOLDEN_RATIO );
        Chronoscope.setFontBookRendering(true);

        XYDataset[] dataset = new XYDataset[1];
        dataset[0] = Chronoscope.createXYDataset(getJson("dataset"));
        
        VerticalPanel vp=new VerticalPanel();
        vp.add(new Label(dataset[0].getRangeLabel()));
        ChartPanel chartPanel = Chronoscope.createTimeseriesChart(dataset, chartWidth, chartHeight);
        vp.add(chartPanel);
        RootPanel.get().add(vp);
    }

    private static native JavaScriptObject getJson(String varName) /*-{
       return $wnd[varName];
    }-*/;
}
```


# Exporting the Chronoscope Javascript API #
You could use the Chronoscope API from native javascript in your pages simply exporting it during the compilation of your application.
Add this code to your application entry-point:
```
  Chronoscope chronoscope = new Chronoscope();
  chronoscope.init();
```
Set the property export in your module file:
```
  <set-property name="export" value="yes"/>
```

# Gwt API Documentation #

The Chronoscope Gwt API documentation is javadoc format, and it is deployed in [here](http://gwt-chronoscope.googlecode.com/svn/apidocs/index.html).

You should start reading the [Chronoscope](http://gwt-chronoscope.googlecode.com/svn/apidocs/org/timepedia/chronoscope/client/browser/Chronoscope.html) class documentation

# Dependencies #

Chronoscope has a bunch of dependencies you have to be aware of. These dependecies depends on the combination of Chronoscope and Gwt versions.

**Note:** that lately, chronoscope has been splitted into two libraries: `chronoscope-x.x.x.jar` which is the Gwt module to use via module inheritance, and `chronoscope-api-xxx.jar` which is the stuff shared between other chronoscope modules

## Trunk version ##
  * `chronoscope-2.0-SNAPSHOT.jar` depends on:
    * `gwt-user-2.0.x` and `gwt-servlet-2.0.x`
    * `chronoscope-api-2.0-SNAPSHOT.jar`
    * `gwtexporter-2.0.10.jar`
    * `gin-1.0.jar`
## Stable version ##
  * `chronoscope-1.0.jar` in gwt-2.0.x applications:
    * `gwt-user-2.0.x` and `gwt-servlet-2.0.x`
    * `chronoscope-api-1.0.jar`
    * `gwtexporter-2.0.10.jar`
    * `gin-1.0.jar`
  * `chronoscope-1.0.jar` in gwt-1.6.x applications:
    * `gwt-user-1.6.x` and `gwt-servlet-1.6.x`
    * `chronoscope-api-1.0.jar`
    * `gwtexporter-2.06.jar`
    * `gin-1.0.jar`
## Legacy versions ##
  * `chronoscope-0.86.jar`
    * `gwt-user-1.6.x` and `gwt-servlet-1.6.x`
    * `gwtexporter-2.06.jar`
    * `gwt-incubator-march-02-2009`
  * `chronoscope-0.7.jar`
    * It should work with any version of `gwt-user` and `gwt-servlet` from 1.5.x to 2.0.x
    * It has no external dependencies since it comes with `gwtexporter` embedded.

# Maven configuration #
If you are already using maven in your application, take a look to this [page](Integration#Chronoscope_in_Maven2_projects.md).