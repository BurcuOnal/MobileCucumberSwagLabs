package com.Mobile.SwagLabs.selector;

public class SelectorFactory {

  private SelectorFactory() {

  }

  public static Selector createElementHelper(SelectorType selectorType) {
    Selector elementHelper = null;
    if (selectorType == SelectorType.ANDROID) {
      elementHelper = new AndroidSelector();
    } else if (selectorType == SelectorType.IOS) {
      elementHelper = new IOSSelector();
    }
    return elementHelper;
  }
}
