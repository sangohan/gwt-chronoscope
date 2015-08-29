To support mashups and ease of use, you don't neccessarily need to deploy Chronoscope yourself. Timepedia is hosting a live version of Chronoscope deployed as a mashable widget. To use Chronoscope in your website, simply add the following script tag to your HTML.

```
 <script src="http://api.timepedia.org/widget/chronoscope.js"></script>
```

Once this is done, you can use the JS API adding [Microformat](microformats.md) or [Datasets](dataset.md) to utilize the Widget in your pages.

If you wish to deploy your own instance of the widget, you have two options.
  1. The first option is to deploy a WAR file into a Java Servlet container, which includes the widget as well as the Font Service for rotated fonts. You can find a downloadable war file at the Chronoscope Project site.
  1. The other option is to deploy a Javascript only library. Uncompress the content of the downloaded .war file (use unzip utility) into an HTTP accessible directory, remove the unneeded folder WEB-INF if you want, and Chronoscope will work, but without rotated fonts. Nevertheless you could enable them to use the Chronoscope FontService by adding the following line of code to your HTML.

```
 function onChronoscopeLoaded() { 
  chronoscope.Chronoscope.setFontBookRendering(true); 
  chronoscope.Chronoscope.setFontBookServiceEndpoint("http://api.timepedia.org/fr"); 
 } 
```