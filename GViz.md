

# Google Visualization API #

## Introduction ##
Chronoscope is an interactive time series chart that can be used as an embedded widget, a Google Gadget, or with the Google visualization API. This page explains how to use Chronoscope with the Google Visualization API.


## Example Code ##
```
<html>
 <head>
  <script src="http://www.google.com/jsapi"></script>
  <script src="http://gwt-chronoscope.googlecode.com/svn/gviz/chronoscope.js"></script>
  <script> 
	  var c, table; 
	  function drawVisualization() { 
		  var data = new google.visualization.DataTable(); 
		  data.addColumn('date', 'Date'); 
		  data.addColumn('number', 'Sold Pencils'); 
		  data.addColumn('number', 'Sold Pens'); 
		  data.addColumn('string', 'Markers'); 
		  data.addRows(6); 
		  data.setValue(0, 0, new Date(2008, 1 ,1)); 
		  data.setValue(0, 1, 30000); 
		  data.setValue(0, 2, 40645); 
		  data.setValue(1, 0, new Date(2008, 1 ,2)); 
		  data.setValue(1, 1, 14045); 
		  data.setValue(1, 2, 20374); 
		  data.setValue(2, 0, new Date(2008, 1 ,3)); 
		  data.setValue(2, 1, 55022); 
		  data.setValue(2, 2, 50766); 
		  data.setValue(3, 0, new Date(2008, 1 ,4)); 
		  data.setValue(3, 1, 75284); 
		  data.setValue(3, 2, 14334); 
		  data.setValue(3, 3, 'Ran out of stock on pens at 4pm'); 
		  data.setValue(4, 0, new Date(2008, 1 ,5)); 
		  data.setValue(4, 1, 46476); 
		  data.setValue(4, 2, 56467); 
		  data.setValue(4, 3, 'Bought 200k pens at 11am'); 
		  data.setValue(5, 0, new Date(2008, 1 ,6)); 
		  data.setValue(5, 1, 33322); 
		  data.setValue(5, 2, 39463); 
		  c = new chronoscope.ChronoscopeVisualization(document.getElementById('foo')); 
		  google.visualization.events.addListener(c, 'select', function(f) { 
			  var sel = c.getSelection(); 
			  table.setSelection(sel); 
		  }); 
		  c.draw(data, {legend: "false", overview: "false"}); 
		  table = new google.visualization.Table(document.getElementById('table_div')); 
		  table.draw(data, {showRowNumber: true}); 
		  google.visualization.events.addListener(table, 'select', function() { 
			  var sel = table.getSelection(); 
			  c.setSelection([{row: sel[0].row, col: 1}]); 
	      }); 
	   } 
	   function onChronoscopeLoaded() { 
		  google.load("visualization", "1", {packages:["table"], callback: drawVisualization}); 
	   } 
  </script>
 </head>
 <body>
  <div id="foo" style="width: 600px; height: 400px"></div>
  <div id="table_div"></div>
  <input type="submit" onClick="c.setSelection([{col: 2, row: 3}])" value="Click Me">
 </body>
</html>
```

## Loading your visualization ##

You can load the visualization using synchronous, or asynchronous techniques. Due to a shortcoming in Internet Explorer at the moment which breaks synchronous loads of our visualization, we recommend asynchronous loading.

  * The following is an example of _synchronous_ loading.
```
<html>
 <head>
  <script src="http://www.google.com/jsapi"></script>
  <script src="http://api.timepedia.org/gviz"></script>
  <script> 
     google.load("visualization", "1"); 
     google.setOnLoadCallback(drawVisualization); 
     var chrono; 
     function drawVisualization() { 
         // legible axis labels 
         chronoscope.Chronoscope.setFontBookRendering(true); 
         // the div in the body that will be replaced with a chart 
         chronoscopeDivID = "chart"; 
         // create visualization 
         var chrono = new chronoscope.ChronoscopeVisualization(document.getElementById(chronoscopeDivID)); 
         var data = new google.visualization.DataTable(); 
         // now load your data into the data table 
         // headers are the first row, data in the rest, see example above         
         
         // finally render chart 
         chrono.draw(data, { legend: "true", overview: "true"}); 
     } 
  </script>
 </head>
 <body>
  <div id="chart" style="width: 600px; height: 400px"></div>
 </body>
</html>
```

  * But the example you see above uses _asynchronous_ loading kicked off from an `onChronoscopeLoaded` function.
```
<html>
 <head>
  <script src="http://www.google.com/jsapi"></script>
  <script src="http://api.timepedia.org/gviz"></script>
  <script> 
    var chrono; 
    function drawVisualization() { 
      // legible axis labels 
      chronoscope.Chronoscope.setFontBookRendering(true); 
      // the div in the body that will be replaced with a chart 
      var chronoscopeDivID = "chart"; 
      // create visualization 
      var chrono = new chronoscope.ChronoscopeVisualization(document.getElementById(chronoscopeDivID)); 
      var data = new google.visualization.DataTable(); 
      // now load your data into the data table 
      // headers are the first row, data in the rest, see example above 
      
      // render chart 
      chrono.draw(data, {legend: "true", overview: "true"}); 
    } 
    function onChronoscopeLoaded() { 
      google.load("visualization", "1", {packages:["table"], callback: drawVisualization}); 
    } 
  </script>
 </head>
 <body>
  <div id="chart" style="width: 600px; height: 400px"></div>
 </body>
</html>
```


## Expected data, formats and types ##

  * Chronoscope expects the time values to be in the first column of the DataTable with a type of 'date'.
  * Columns following column 0 can either be number or string columns.
    * A `number` column becomes a separate timeseries.
      * The title of number columns are used as legend labels.
      * If the title contains a string in parenthesis, it's used to assign the values units, like $ or meters.
      * Columns with identical units share the same y-axis. For example, columns oil (gallons) and milk (gallons) would share the (gallons) axis.
    * A `string` column with a header matching the string "Markers" binds to the preceding data column to the left, placing the contents of the cells as popup annotations on the chart.

```
		  var data = new google.visualization.DataTable(); 
		  data.addColumn('date', 'Date'); 
		  data.addColumn('number', 'Sold Pencils ($)'); 
		  data.addColumn('number', 'Sold Pens ($)'); 
		  data.addColumn('string', 'Markers'); 
		  data.addRows(6); 
		  data.setValue(0, 0, new Date(2008, 1 ,1)); 
		  data.setValue(0, 1, 30000); 
		  data.setValue(0, 2, 40645); 
		  data.setValue(1, 0, new Date(2008, 1 ,2)); 
		  data.setValue(1, 1, 14045); 
		  data.setValue(1, 2, 20374); 
```

## Configuration options ##


  * `legend`
    * `true`   show a legend on the chart (this is the default)
    * `false`   do not show a legend on the chart
  * `overview`
    * `true`   show an overview below the chart (this is the default)
    * `false`   do not show a chart overview
  * `axisLabels`
    * `true`   show axis labels on the chart (this is the default)
    * `false`  do not show axis labels
  * `style`
    * `clean`   clean style, simple colors, white background
    * `gfinance`   the style inspired by Google Finance
    * `bluegradient`   blue gradient style
  * `zoomStartTime`  defines the start point of the domain to chart
  * `zoomEndTime`    is the end point to chart
  * `scaleType`
    * `maximum`


```
  chrono.draw(data, {legend: "true", overview: "true", style: "clean"});
```


## Methods & Events ##



  * `microformatToDataTable(tableId)`: tableId is the ID attribute of an HTML table formatted according to the Chronoscope Microformat syntax. This static method parses an HTML table found in the current document and returns a GViz DataTable. If the table does not correctly adhere to the Chronoscope Microformat syntax, an exception will be thrown with details as to where parsing fails.

```
    var dataTable = chronoscope.ChronoscopeVisualization.microformatToDataTable("mydata");
```

  * `draw(dataTable, configOpts)`: draw the chart.
  * `highlight(from, to)`: highlight the interval delimited by `from` and `to`
  * `InfoWindow openInfoWindow(selection, html)`: open an information window with the `html` content at the range defined by `selection`.
  * `pageLeft(perc)`: Animated pan to the left by the designated percentage (0.0, 1.0) of the currently visible domain.
  * `pageRight(perc)`: Animated pan to the right by the designated percentage (0.0, 1.0) of the currently visible domain.
  * `zoomIn()`: Animated zoom-in of the currently visible domain by a fixed zoomfactor
  * `zoomOut()`: Animated zoom-out of the currently visible domain by a fixed zoomfactor
  * `getSelection()`: returns an array of the selected cells, i.e. an array in which each element is an object with properties row and column and these are indexes of rows and columns in the `DataTable`. If only row is specified, the selected element is a row. If only column is specified, the selected element is a column.

```
    selectedCells = chart.getSelection();
```

  * `setSelection(selection)`: where selection is an array of cells to select

```
    chart.setSelection([{col: 2, row: 3}, {col: 4, row:2}]);
```

```
  <input type="submit" onClick="chrono.setSelection([{col: 2, row: 3}])" value="Click Me">
```

  * The **select event** is the only event supported right now. As laid out in the Google Visualization API overview, the select event triggers when the user selects data within.

```
  google.visualization.events.addListener(chrono, 'select', function() { 
    var sel = chrono.getSelection(); 
    alert("someone selected row=" + sel[0].row + ", col=" + sel[0].col); 
  });
```

## Timefire data policy ##
We do not retain any data other than standard web traffic analytics data when you use our public site to get the Chronoscope Google Visualization API (http://api.timepedia.org/gviz/).

In a future version we'll offer an option where people can publish data they wish to share, in which case data meant to be shared would be retained.