

# Chronoscope Javascript API #

## Introduction ##

Chronoscope Js API, is the exported version of the library ready to use from any html page. It is thought to be used by users who don't know java nor gwt.

Even though it is the same version of the gwt library, there are some features which could not been ported yet. If you miss any feature in the javascript API which is already present in the gwt API, please open an issue.

## Initialization ##

The function "onChronoscopeLoaded" is called once the chart is initialized, and this is where you can customize this function to setup various charts in your page.

Previously you have to load the chart data.

```
<html>
 <head>
  <script type="text/javascript" src="dataset.js"></script>
  <script type="text/javascript" src="http://gwt-chronoscope.googlecode.com/svn/widget/chronoscope.js"></script>
  <script type="text/javascript">
    function onChronoscopeLoaded(chrono) {
     var data = [dataset];
     chronoscope.Chronoscope.setErrorReporting(true);
     chronoscope.Chronoscope.createTimeseriesChartById("chartid", data, 650, 433, function(view) {
       view.getChart().redraw();
       largeview = view;
     });
   }
  </script>
 </head>
 <body>
   <div id="chartid">Loading ...</div>
 </body>
</html>
```

## Data files ##

The data file is a json properties file with the following format:
```
  dataset = {
    "id": "unrate",                                         // Unique id for this dataset
    "domainscale": 1000,                                    // Scale factor to convert domain values to microseconds (optional)
    "domain": [-694306739,-691628339,-689122739,-686444339],// UTC time in milliseconds
    "range":  [3.4, 3.8, 4.0, 3.9,],                        // Values
    "label":  "Unemployment",                               // Dataset label
    "axis":   "percent"                                     // Chart unit
  }
```

## API Documentation ##

The [Javascript API documentation](http://gwt-chronoscope.googlecode.com/svn/apidocs/jsdoc.html), is generated automatically when the project is deployed.
If you find any mistake please report the [issue](http://code.google.com/p/gwt-chronoscope/issues).