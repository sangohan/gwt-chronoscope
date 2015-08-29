## Introduction ##
We want non-programmers to be able to utilize Chronoscope functionality as well, and to do that, we use some (for the moment) non-standard microformats to describe the datasets and inject markers.
The microformat is simply an HTML table with class="cmf-chart", with the first row of the THEAD element specifying axis labels, and the rows of the TBODY element specifying the domain, and range axis values. The domain values are always in the first column, and one or more range values follow.

Finally, you may give the table an ID, and target Anchor links (&lt;A>) at it using the fragment identifier (href=#id:date). Any date appearing after the ID is interpreted to mean a point in time to place a pushpin marker. The marker's label comes from the "accesskey" attribute of the A element, and the link must have a class of "cmf-marker". To see an info window display when the marker is clicked simply place a SPAN element inside the A element, with a class name of "cmf-infowindow". This span will be invisible but its contents will be displayed when the marker is clicked.

## Example ##

```
       <table id="microformatdemo" class="cmf-chart">
          <colgroup>
            <col class="cmf-dateformat" title="MM/dd/yyyy">
            <col class="cmf-numberformat" title="#,##0.00">
          </colgroup>
          <thead>
            <tr>
                <th>Time</th><th axis="$">GDP</th>
            </tr>
          </thead>
          <tbody>
            <tr> <td>1/1/1953</td><td>1000</td> </tr>
            <tr> <td>1/1/1954</td><td>3000</td> </tr>
            <tr> <td>1/1/1955</td><td>3100</td> </tr>
            <tr> <td>1/1/1956</td><td>3200</td> </tr>
            <tr> <td>1/1/1957</td><td>3250</td> </tr>
            <tr> <td>1/1/1958</td><td>3300</td> </tr>
            <tr> <td>1/1/1959</td><td>3325</td> </tr>
            <tr> <td>1/1/1960</td><td>1900</td> </tr>
            <tr> <td>1/1/1961</td><td>1800</td> </tr>
            <tr> <td>1/1/1962</td><td>2000</td> </tr>
            <tr> <td>1/1/1963</td><td>2100</td> </tr>
            <tr> <td>1/1/1964</td><td>2200</td> </tr>
          </tbody>
      </table>
      
      <p>
         As you can see, in
         <a accesskey="G" 
            href="#microformatdemo:1/1/1954"
            class="cmf-marker">1954<span 
            class="cmf-infowindow">GDP Popup<br>GDP Grew Considerably</span></a>,
         the Gross Domestic Product grew considerably. However, in 
         <a accesskey="D" 
            href="#microformatdemo:1/1/1960"
            class="cmf-marker">1960<span
            class="cmf-infowindow">GDP Popup<br>GDP dropped a lot.</span></a>
         the GDP dropped a lot.
      </p>

```

## Initialization ##
Adding this code in your page, the table will be replaced by a chronoscope chart, the anchors will point to the marks in the chart, and the spans will be hidden and shown when you pass over the marks:
```
<script type="text/javascript" src="http://api.timepedia.org/widget/"></script>
<script type="text/javascript">
 function onChronoscopeLoaded(chrono) { chrono.Chronoscope.setErrorReporting(true);}
</script>
```

## IE6 Note ##
If the cmf-chart table is inside a paragraph or form tag it produces an error in IE6, the workaround is to not put the table inside paragraph tags or in forms (&lt;p> or &lt;form>).