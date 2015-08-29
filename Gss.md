## Introduction ##

Chronoscope uses a special css syntax to customize each element in the chart which is called GSS (Graph Stylesheets).


## Example Code ##

  * Create a file with the customization of the chart.
```
axes axis {
  background-color: #ffffff;
  color:black;
}

plot {
  background-color: #ffffff;
}

axis.a0 label, axis.a0 {
  color: rgb( 70, 118, 118 );
}

.s0 line, axis.a0 tick {
  color: rgb( 70, 118, 118 );

}

axislegend daterange {
  date-format:yyyy-MM-dd;
}

```

  * Include the file in your page as a normal stylesheet link, but changing the type:
```
 <link rel="stylesheet" href="simple.css" type="text/gss">
```


  * note: ` color: white; ` doesn't work in the IE flash canvas fallback, so you might want to use #rrggbb; or rgb(rrr,ggg,bbb); color values instead.

## Gss Reference ##
The [GSS reference](http://gwt-chronoscope.googlecode.com/svn/apidocs/gssdoc.html), is generated automatically when the project is deployed.