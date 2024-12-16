package com.Mobile.SwagLabs.selector;



import com.Mobile.SwagLabs.model.ElementInfo;
import org.openqa.selenium.By;

public class AndroidSelector implements Selector {

  @Override
  public By getElementInfoToBy(ElementInfo elementInfo) {
    By by = null;
    if (elementInfo.getAndroidType().equals("css")) {
      by = By.cssSelector(elementInfo.getAndroidValue());
    } else if (elementInfo.getAndroidType().equals("id")) {
      by = By.id(elementInfo.getAndroidValue());
    } else if (elementInfo.getAndroidType().equals("xpath")) {
      by = By.xpath(elementInfo.getAndroidValue());
    } else if (elementInfo.getAndroidType().equals("class")) {
      by = By.className(elementInfo.getAndroidValue());
    }
    else if (elementInfo.getAndroidType().equals("text")){
      by = By.linkText(elementInfo.getAndroidValue());
    }
    return by;
  }

  @Override
  public int getElementInfoToIndex(ElementInfo elementInfo) {
    return elementInfo.getAndroidIndex();
  }
}
