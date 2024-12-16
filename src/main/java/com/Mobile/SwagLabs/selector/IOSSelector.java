package com.Mobile.SwagLabs.selector;

import com.Mobile.SwagLabs.model.ElementInfo;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;

public class IOSSelector implements Selector {

  @Override
  public By getElementInfoToBy(ElementInfo elementInfo) {
    By by = null;
    if (elementInfo.getIosType().equals("css")) {
      by = MobileBy.cssSelector(elementInfo.getIosValue());
    } else if (elementInfo.getIosType().equals("id")) {
      by = MobileBy.id(elementInfo.getIosValue());
    } else if (elementInfo.getIosType().equals("xpath")) {
      by = MobileBy.xpath(elementInfo.getIosValue());
    } else if (elementInfo.getIosType().equals("class")) {
      by = MobileBy.className(elementInfo.getIosValue());
    } else if (elementInfo.getIosType().equals("name")) {
      by = MobileBy.name(elementInfo.getIosValue());
    }else if (elementInfo.getIosType().equals("classChain")){
      by = MobileBy.iOSClassChain(elementInfo.getIosValue());
    }
    return by;
  }

  @Override
  public int getElementInfoToIndex(ElementInfo elementInfo) {
    return elementInfo.getIosIndex();
  }
}
