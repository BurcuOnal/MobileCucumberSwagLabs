package com.Mobile.SwagLabs.model;

import org.openqa.selenium.By;

public class SelectorInfo {

  private By by;
  private int index;

  public SelectorInfo() {
  }

  public SelectorInfo(By by) {
    this.by = by;
    this.index = 0;
  }

  public SelectorInfo(By by, int index) {
    this.by = by;
    this.index = index;
  }

  public By getBy() {
    return by;
  }

  public void setBy(By by) {
    this.by = by;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }
}
