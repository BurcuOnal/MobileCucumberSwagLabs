package com.Mobile.SwagLabs.selector;




import com.Mobile.SwagLabs.helper.StoreHelper;
import com.Mobile.SwagLabs.model.ElementInfo;
import com.Mobile.SwagLabs.model.SelectorInfo;
import org.openqa.selenium.By;

public interface Selector {

  default ElementInfo getElementInfo(String key) {
    return StoreHelper.INSTANCE.findElementInfoByKey(key);
  }

  default By getElementInfoToBy(String key) {
    return getElementInfoToBy(getElementInfo(key));
  }

  default SelectorInfo getSelectorInfo(ElementInfo elementInfo) {
    return new SelectorInfo(getElementInfoToBy(elementInfo), getElementInfoToIndex(elementInfo));
  }

  default SelectorInfo getSelectorInfo(String key) {
    return new SelectorInfo(getElementInfoToBy(key), getElementInfoToIndex(key));
  }

  By getElementInfoToBy(ElementInfo elementInfo);

  int getElementInfoToIndex(ElementInfo elementInfo);

  default int getElementInfoToIndex(String key) {
    return getElementInfoToIndex(getElementInfo(key));
  }
}
