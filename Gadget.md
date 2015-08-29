## Introduction ##
This page explains how to use Chronoscope as a Google gadget for line or bar charts in Google spreadsheets, in iGoogle, or in Open Social containers.

An example Google-Docs Spreadsheet with a Chronoscope visualization gadget: [Chronoscope Spreadsheet](http://spreadsheets.google.com/pub?key=pChH4pq_9_M6JGO-zSoSHWw)

Use the arrow keys to move around, mouse wheel to zoom in/out, tab/shift+tab key to change focus point.
You may click or hover over points on the curve, or drag select a range with shift+drag. Home key zooms the chart to maxmimum again.

## Using the gadget from Google-Docs Spreadsheets ##

The gadget is easy to use from Google Spreadsheets, just select a range of cells in a spreadsheet (including the header rows), and then click the "Insert" button on the toolbar and select Gadget. When the dialog box appears, select "Custom", and enter the following URL:
```
http://api.timepedia.org/gadget/gadget.xml?nocache
```

The chart requires that the first column of the selection be a date-format Date column. Other columns may be numbers or markers. A Marker column has the title "Markers" and contains text. For each non-empty cell, a popup marker is placed on the chart containing that text. It is bound to the timeseries column immediately to the left of it.

A timeseries data column is a number column, containing the Y-axis values. Empty cells are allowed. The title of the timeseries is in the header and is displayed in the legend. The "units" of the timeseries, such as dollars or meters, are placed in parenthesis next to the title, for example "Gas (Gallons)". Each unique unit gets its own axis, and timeseries of the same unit share the same axis. The unit is displayed as a label next to the axis if axis label display is enabled.