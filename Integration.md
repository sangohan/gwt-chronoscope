

## Chronoscope in Maven2 projects ##
In order to use chronoscope in a maven project you have to add to your pom.xml the repositories where `chronoscope` and `gwtexporter` are deployed, and the following dependencies:
```
  <repositories>
    ...
    <repository>
      <id>timepedia</id>
      <url>http://gwt-chronoscope.googlecode.com/svn/mavenrepo/</url>
    </repository>
    <repository>
      <id>timefire</id>
      <url>http://timefire-repository.googlecode.com/svn/mavenrepo/</url>
    </repository>
  </repositories>
  <dependencies>
    ...
    <dependency>
      <groupId>org.timepedia.chronoscope</groupId>
      <artifactId>chronoscope</artifactId>
      <version>2.0-SNAPSHOT</version>
    </dependency>
    <dependency>
      <groupId>org.timepedia.exporter</groupId>
      <artifactId>gwtexporter</artifactId>
      <version>2.0.10</version>
      <scope>provided</scope>
      <exclusions>
        <exclusion>
          <groupId>com.google.gwt</groupId>
          <artifactId>gwt-user</artifactId>
        </exclusion>
      </exclusions>
    </dependency>
    <dependency>
      <groupId>com.google.gwt.inject</groupId>
      <artifactId>gin</artifactId>
      <version>1.0</version>
      <scope>provided</scope>
    </dependency>
  </dependencies>
```



## Chronoscope integration with GXT ##

This example is taken from [issue33](https://code.google.com/p/gwt-chronoscope/issues/detail?id=33), thanks to [akbertram](http://code.google.com/u/akbertram/)

This is a wrapper class to use chronoscope in a GXT [ContentPanel](http://dev.sencha.com/deploy/gxtdocs/com/extjs/gxt/ui/client/widget/ContentPanel.html)
```
public abstract class ChronoscopePanel extends ContentPanel {
  protected ChartPanel chartPanel;


  public ChronoscopePanel() {
    this.setLayout(new FitLayout());
    this.setBodyStyle("padding:10px");
  }

  public void setDataSets(List<DatasetRequest> datasetRequests) {
    if(chartPanel != null) {
      removeAll();
    }
    chartPanel = new ChartPanel();
    chartPanel.setDatasets( makeDatasets(datasetRequests) );
    chartPanel.setDimensions(this.getInnerWidth()-20, this.getInnerHeight()-20);
    chartPanel.setDomElement(DOM.createDiv());
    chartPanel.init();

    initializeChronoscope();

    add(chartPanel);
    layout(true);
  }

  /**
   * Subclasses should override to seet any cosmetic properties of the plot 
   * area or chart.
   */
  protected void initializeChronoscope() {

  }

  private Dataset[] makeDatasets(List<DatasetRequest> datasetRequests) {
    Dataset[] ds = new Dataset[datasetRequests.size()];
    for(int i=0; i!=datasetRequests.size();++i) {
      ds[i] = new ArrayDataset2D(datasetRequests.get(i));
    }
    return ds;
  }

  @Override
  protected void onResize(int width, int height) {
    super.onResize(width, height);
    if(chartPanel != null) {
      chartPanel.setDimensions(this.getInnerWidth()-20,this.getInnerHeight()-20);
    }
  }
}

```