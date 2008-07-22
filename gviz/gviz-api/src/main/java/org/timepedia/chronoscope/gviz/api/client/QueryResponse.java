package org.timepedia.chronoscope.gviz.api.client;

import com.google.gwt.core.client.JavaScriptObject;

/**
 *
 */
public final class QueryResponse extends JavaScriptObject {

  protected QueryResponse() {
  }

  public native DataTable getDataTable() /*-{
     return this.getDataTable();
  }-*/;

}
