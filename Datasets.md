## Json data files ##

  * The easier way to load data is generate dinamically a javascript file with the information and load it using a script html tag:

```
  <script language='javascript' src='dataset.js'></script>
```


  * The data file should have the following format:
```
  dataset = {
    id: "unrate",                                         // Unique id for this dataset
    domainscale: 1000,                                    // Scale domain by domainscale to convert to native microseconds
    domain: [-694306739,-691628339,-689122739,-686444339] // UTC time in milliseconds
    range:  [3.4, 3.8, 4.0, 3.9],                         // Values
    label:  "Unemployment",                               // Dataset label, should be unique per page
    axis:   "percent"                                     // Range axis unit/dimension, multiple datasets can share 
                                                          // the same axis when using the same unit, useful for
                                                          // hours, km, kbps, $, etc.
  }
```



  * minimal dataset
```
 dataset = {
    id: "id_unique_per_page",
    domain: [ 1214277862000, 1214277862000, 1214277862000 ]
    range: [ 42, 101.11, -33 ]
    label: "label for the dataset",
    axis: "units" 
 }
```

  * some convenience
```
 dataset = {
    id: "id_unique_per_page",
    domainscale: 1000,
    domain: [ 1214277862, 1214277862, 1214277862 ]
    range: [ 42, 101.11, -33 ]
    label: "label for the dataset",
    axis: "units" 
 }
```

  * more convenience
```
 dataset = {
    id: "id_unique_per_page",
    dtformat: "yyyy-MM-dd",
    domain: [ "2008-11-14", "2009-03-18", "2009-05-22" ]
    range: [ 42, 101.11, -33 ]
    label: "label for the dataset",
    axis: "units" 
 }
```