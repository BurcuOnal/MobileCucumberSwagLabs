package com.Mobile.SwagLabs;

import com.Mobile.SwagLabs.helper.RandomString;
import com.Mobile.SwagLabs.helper.StoreHelper;
import com.Mobile.SwagLabs.model.SelectorInfo;
import com.Mobile.SwagLabs.selector.Selector;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.KeyEventMetaModifier;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import io.cucumber.java.en.And;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertTrue;

public class StepImpl {


    public Logger logger = Logger.getLogger(getClass());
    protected static AppiumDriver<MobileElement> appiumDriver;
    protected static FluentWait<AppiumDriver<MobileElement>> appiumFluentWait;
    protected static Selector selector;
    protected boolean localAndroid = true;
    public int answerClickCount = 1;
    private Assert Assertions;

    public StepImpl() {
        this.appiumDriver = HookImpl.appiumDriver;
        this.appiumFluentWait = HookImpl.appiumFluentWait;
        this.selector = HookImpl.selector;
        this.localAndroid = true;

    }

    public List<MobileElement> findElements(By by) throws Exception {
        List<MobileElement> webElementList = null;
        try {
            webElementList = appiumFluentWait.until(new ExpectedCondition<List<MobileElement>>() {
                @Nullable
                @Override
                public List<MobileElement> apply(@Nullable WebDriver driver) {
                    List<MobileElement> elements = driver.findElements(by);
                    return elements.size() > 0 ? elements : null;
                }
            });
            if (webElementList == null) {
                throw new NullPointerException(String.format("by = %s Web element list not found", by.toString()));
            }
        } catch (Exception e) {
            throw e;
        }
        return webElementList;
    }

    public List<MobileElement> findElementsWithoutAssert(By by) {

        List<MobileElement> mobileElements = null;
        try {
            mobileElements = findElements(by);
        } catch (Exception e) {
        }
        return mobileElements;
    }

    public List<MobileElement> findElementsWithAssert(By by) {

        List<MobileElement> mobileElements = null;
        try {
            mobileElements = findElements(by);
        } catch (Exception e) {
            //Assertions.fail("by = %s Elements not found ", by.toString());
            e.printStackTrace();
        }
        return mobileElements;
    }


    public MobileElement findElement(By by) throws Exception {
        MobileElement mobileElement;
        try {
            mobileElement = findElements(by).get(0);
        } catch (Exception e) {
            throw e;
        }
        return mobileElement;
    }

    public MobileElement findElementWithoutAssert(By by) {
        MobileElement mobileElement = null;
        try {
            mobileElement = findElement(by);
        } catch (Exception e) {
            //   e.printStackTrace();
        }
        return mobileElement;
    }

    public MobileElement findElementWithAssertion(By by) {
        MobileElement mobileElement = null;
        try {
            mobileElement = findElement(by);
        } catch (Exception e) {
            //Assertions.fail("by = %s Element not found ", by.toString());
            e.printStackTrace();
        }
        return mobileElement;
    }

    public void sendKeysTextByAndroidKey(String text) {

        AndroidDriver androidDriver = (AndroidDriver) appiumDriver;
        char[] chars = text.toCharArray();
        String stringValue = "";
        for (char value : chars) {
            stringValue = String.valueOf(value);
            if (Character.isDigit(value)) {
                androidDriver.pressKey(new KeyEvent().withKey(AndroidKey.valueOf("DIGIT_" + String.valueOf(value))));
            } else if (Character.isLetter(value)) {
                if (Character.isLowerCase(value)) {
                    androidDriver.pressKey(new KeyEvent().withKey(AndroidKey.valueOf(stringValue.toUpperCase())));
                } else {
                    androidDriver.pressKey(new KeyEvent().withKey(AndroidKey.valueOf(stringValue))
                            .withMetaModifier(KeyEventMetaModifier.SHIFT_ON));
                }
            } else if (stringValue.equals("@")) {
                androidDriver.pressKey(new KeyEvent().withKey(AndroidKey.AT));
            } else if (stringValue.equals(".")) {
                androidDriver.pressKey(new KeyEvent().withKey(AndroidKey.PERIOD));
            } else if (stringValue.equals(" ")) {
                androidDriver.pressKey(new KeyEvent().withKey(AndroidKey.SPACE));
            } else {
                Assert.fail("Metod " + stringValue + " desteklemiyor.");
            }
        }
        logger.info(text + " texti AndroidKey yollanarak yazıldı.");
    }

    public MobileElement findElementByKeyWithoutAssert(String key) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);

        MobileElement mobileElement = null;
        try {
            mobileElement = selectorInfo.getIndex() > 0 ? findElements(selectorInfo.getBy())
                    .get(selectorInfo.getIndex()) : findElement(selectorInfo.getBy());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mobileElement;
    }

    public MobileElement findElementByKey(String key) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);

        MobileElement mobileElement = null;
        try {
            mobileElement = selectorInfo.getIndex() > 0 ? findElements(selectorInfo.getBy())
                    .get(selectorInfo.getIndex()) : findElement(selectorInfo.getBy());
        } catch (Exception e) {
            //Assertions.fail("key = %s by = %s Element not found ", key, selectorInfo.getBy().toString());
            e.printStackTrace();
        }
        return mobileElement;
    }


    public List<MobileElement> findElemenstByKeyWithoutAssert(String key) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);
        List<MobileElement> mobileElements = null;
        try {
            mobileElements = findElements(selectorInfo.getBy());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mobileElements;
    }

    public List<MobileElement> findElemenstByKey(String key) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);
        List<MobileElement> mobileElements = null;
        try {
            mobileElements = findElements(selectorInfo.getBy());
        } catch (Exception e) {
            //Assertions.fail("key = %s by = %s Elements not found ", key, selectorInfo.getBy().toString());
            e.printStackTrace();
        }
        return mobileElements;
    }

    @And("{int} kere yukarı doğru kaydır")
    public void swipeUP(int times) {
        for (int i = 0; i < times; i++) {
            logger.info(times + " kere yukarı doğru kaydır");
            swipeUpAccordingToPhoneSize();
            waitBySecond(1);
        }
    }

    @And("Değeri {string} e eşit olan elementli bul ve tıkla")
    public void clickByText(String text) {
        findElementWithAssertion(By.xpath(".//*[contains(@text,'" + text + "')]")).click();
    }


    @And("{string} elemente tıkla")
    public void clickByKey(String key) {
        doesElementExistByKey(key, 5);
        findElementByKey(key).click();
        logger.info(key + "elemente tıkladı");


    }

    @And("{string} li element sayfada bulunuyor mu kontrol et")
    public void existElement(String key) {
        assertTrue(key + " elementi sayfada bulunamadı!", findElementByKey(key).isEnabled());
        logger.info("Found element: " + key);
    }


    @And("{string} li elementi bul ve varsa tıkla")
    public void existClickByKey(String key) {

        MobileElement element;

        element = findElementByKeyWithoutAssert(key);

        if (element != null) {
            System.out.println("  varsa tıklaya girdi");
            Point elementPoint = ((MobileElement) element).getCenter();
            TouchAction action = new TouchAction(appiumDriver);
            action.tap(PointOption.point(elementPoint.x, elementPoint.y)).perform();
        }
    }

    @And("{string} li elementi bul ve varsa dokun")
    public void existTapByKey(String key) {

        WebElement element = null;
        try {
            element = findElementByKey(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (element != null) {
            element.click();
        }
    }

    @And("sayfadaki <X> <Y>  alana dokun")
    public void coordinateTap(int X, int Y) {

        Dimension dimension = appiumDriver.manage().window().getSize();
        int width = dimension.width;
        int height = dimension.height;

        TouchAction action = new TouchAction(appiumDriver);
        action.tap(PointOption.point((width * X) / 100, (height * Y) / 100))
                .release().perform();

    }


    @And("{string} li elementi bul, temizle ve {string} değerini yaz")
    public void sendKeysByKey(String key, String text) {
        MobileElement webElement = findElementByKey(key);
        webElement.click();
        webElement.clear();
        webElement.setValue(text);
    }

    @And("{string} li elementi bul ve temizle")
    public void ClearText(String key) {
        MobileElement webElement = findElementByKey(key);
        webElement.clear();
        logger.info(key + " elementinin değeri temizlendi.");
        if (appiumDriver instanceof IOSDriver) {
            findElementByKey(key).clear();
            findElementByKey(key).clear();
            findElementByKey(key).clear();
            logger.info("IOS değeri temizlendi.");
        }

    }


    @And("{string} li elementi bul ve {string} değerini yaz")
    public void sendKeysByKeyNotClear(String key, String text) {
        doesElementExistByKey(key, 5);
        findElementByKey(key).sendKeys(text);

    }

    @And("{string} değerini klavye ile yaz")
    public void sendKeysWithAndroidKey(String text) {
        sendKeysTextByAndroidKey(text);

    }

    @And("{string} li elementi bul ve değerini <saveKey> olarak sakla")
    public void saveTextByKey(String key, String saveKey) {
        StoreHelper.INSTANCE.saveValue(saveKey, findElementByKey(key).getText());
        logger.info(saveKey + " variable is stored with: " + findElementByKey(key).getText());
    }

    @And("{string} li elementi bul ve değerini <saveKey> saklanan değer ile karşılaştır")
    public void equalsSaveTextByKey(String key, String saveKey) {
        Assert.assertEquals(StoreHelper.INSTANCE.getValue(saveKey), findElementByKey(key).getText());
        logger.info("Element by " + key + " is equal to " + StoreHelper.INSTANCE.getValue(saveKey));
        logger.info("Element by " + key + " contains: " + findElementByKey(key).getText() + " and Equals to " + saveKey + ": " + StoreHelper.INSTANCE.getValue(saveKey));
    }


    @And("{string} li ve değeri {string} e eşit olan elementli bul ve tıkla")
    public void clickByIdWithContains(String key, String text) {
        List<MobileElement> elements = findElemenstByKey(key);
        for (MobileElement element : elements) {
            logger.info("Text !!!" + element.getText());
            if (element.getText().toLowerCase().contains(text.toLowerCase())) {
                element.click();
                break;
            }
        }
    }

    @And("{string} li ve değeri {string} e eşit olan elementli bulana kadar swipe et ve tıkla")
    public void clickByKeyWithSwipe(String key, String text) throws InterruptedException {
        boolean find = false;
        int maxRetryCount = 10;
        while (!find && maxRetryCount > 0) {
            List<MobileElement> elements = findElemenstByKey(key);
            for (MobileElement element : elements) {
                if (element.getText().contains(text)) {
                    element.click();
                    find = true;
                    break;
                }
            }
            if (!find) {
                maxRetryCount--;
                if (appiumDriver instanceof AndroidDriver) {
                    swipeUpAccordingToPhoneSize();
                    waitBySecond(1);
                } else {
                    swipeDownAccordingToPhoneSize();
                    waitBySecond(1);
                }
            }
        }
    }

    @And("{string} li elementi bulana kadar swipe et ve tıkla")
    public void clickByKeyWithSwipe(String key) throws InterruptedException {
        int maxRetryCount = 10;
        while (maxRetryCount > 0) {
            List<MobileElement> elements = findElemenstByKey(key);
            if (elements.size() > 0) {
                if (elements.get(0).isDisplayed() == false) {
                    maxRetryCount--;
                    swipeDownAccordingToPhoneSize();
                    waitBySecond(1);

                } else {
                    elements.get(0).click();
                    logger.info(key + " elementine tıklandı");
                    break;
                }
            } else {
                maxRetryCount--;
                swipeDownAccordingToPhoneSize();
                waitBySecond(1);
            }
        }
    }


    private int getScreenWidth() {
        return appiumDriver.manage().window().getSize().width;
    }

    private int getScreenHeight() {
        return appiumDriver.manage().window().getSize().height;
    }

    private int getScreenWithRateToPercent(int percent) {
        return getScreenWidth() * percent / 100;
    }

    private int getScreenHeightRateToPercent(int percent) {
        return getScreenHeight() * percent / 100;
    }


    public void swipeDownAccordingToPhoneSize(int startXLocation, int startYLocation, int endXLocation, int endYLocation) {
        startXLocation = getScreenWithRateToPercent(startXLocation);
        startYLocation = getScreenHeightRateToPercent(startYLocation);
        endXLocation = getScreenWithRateToPercent(endXLocation);
        endYLocation = getScreenHeightRateToPercent(endYLocation);

        new TouchAction(appiumDriver)
                .press(PointOption.point(startXLocation, startYLocation))
                .waitAction(WaitOptions.waitOptions(ofMillis(1000)))
                .moveTo(PointOption.point(endXLocation, endYLocation))
                .release()
                .perform();
    }

    @And("{string} id'li elementi bulana kadar <times> swipe yap ")
    public void swipeDownUntilSeeTheElement(String element, int limit) throws InterruptedException {
        for (int i = 0; i < limit; i++) {
            List<MobileElement> meList = findElementsWithoutAssert(By.id(element));
            meList = meList != null ? meList : new ArrayList<MobileElement>();

            if (meList.size() > 0 &&
                    meList.get(0).getLocation().x <= getScreenWidth() &&
                    meList.get(0).getLocation().y <= getScreenHeight()) {
                break;
            } else {
                swipeDownAccordingToPhoneSize(50, 80, 50, 30);
                waitBySecond(1);

                break;
            }
        }
    }


    @And("{string} li elementi bulana kadar swipe et")
    public void findByKeyWithSwipe(String key) {
        int maxRetryCount = 10;
        while (maxRetryCount > 0) {
            List<MobileElement> elements = findElemenstByKey(key);
            if (elements.size() > 0) {
                if (elements.get(0).isDisplayed() == false) {
                    maxRetryCount--;

                    swipeDownAccordingToPhoneSize();

                } else {
                    logger.info(key + " element bulundu");
                    break;
                }
            } else {
                maxRetryCount--;
                swipeDownAccordingToPhoneSize();

            }

        }
    }


    @And(" <yön> yönüne swipe et")
    public void swipe(String yon) {

        Dimension d = appiumDriver.manage().window().getSize();
        int height = d.height;
        int width = d.width;

        if (yon.equals("SAĞ")) {

            int swipeStartWidth = (width * 80) / 100;
            int swipeEndWidth = (width * 30) / 100;

            int swipeStartHeight = height / 2;
            int swipeEndHeight = height / 2;

            //appiumDriver.swipe(swipeStartWidth, swipeStartHeight, swipeEndWidth, swipeEndHeight, 1000);
            new TouchAction(appiumDriver)
                    .press(PointOption.point(swipeStartWidth, swipeStartHeight))
                    .waitAction(WaitOptions.waitOptions(ofMillis(1000)))
                    .moveTo(PointOption.point(swipeEndWidth, swipeEndHeight))
                    .release()
                    .perform();
        } else if (yon.equals("SOL")) {

            int swipeStartWidth = (width * 30) / 100;
            int swipeEndWidth = (width * 80) / 100;

            int swipeStartHeight = height / 2;
            int swipeEndHeight = height / 2;

            //appiumDriver.swipe(swipeStartWidth, swipeStartHeight, swipeEndWidth, swipeEndHeight, 1000);

            new TouchAction(appiumDriver)
                    .press(PointOption.point(swipeStartWidth, swipeStartHeight))
                    .waitAction(WaitOptions.waitOptions(ofMillis(1000)))
                    .moveTo(PointOption.point(swipeEndWidth, swipeEndHeight))
                    .release()
                    .perform();

        }
    }

    @And("{string} li elementi bul yoksa <message> mesajını hata olarak göster")
    public void isElementExist(String key, String message) {
        assertTrue(message, findElementByKey(key) != null);
    }

    @And("{string} li elementin değeri {string} e içerdiğini kontrol et")
    public void containsTextByKey(String key, String text) {
        By by = selector.getElementInfoToBy(key);
        assertTrue(appiumFluentWait.until(new ExpectedCondition<Boolean>() {
            private String currentValue = null;

            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    currentValue = driver.findElement(by).getAttribute("value");
                    return currentValue.contains(text);
                } catch (Exception e) {
                    return false;
                }
            }

            @Override
            public String toString() {
                return String.format("text contains be \"%s\". Current text: \"%s\"", text, currentValue);
            }
        }));
    }

    @And("{string} li elementin değeri {string} e eşitliğini kontrol et")
    public void equalsTextByKey(String key, String text) {
        assertTrue(key + " elementinin text değeri " + text + " e eşit değil!",
                appiumFluentWait.until(ExpectedConditions.textToBe(selector.getElementInfoToBy(key), text)));
        logger.info(key + " elementinin text değerinin " + text + " e eşit olduğu kontrol edildi");
    }

    public void swipeUpAccordingToPhoneSize() {
        if (appiumDriver instanceof AndroidDriver) {
            Dimension d = appiumDriver.manage().window().getSize();
            int height = d.height;
            int width = d.width;
            System.out.println(width + "  " + height);

            int swipeStartWidth = width / 2, swipeEndWidth = width / 2;
            int swipeStartHeight = (height * 20) / 100;
            int swipeEndHeight = (height * 60) / 100;
            logger.info("Start width: " + swipeStartWidth + " - Start height: " + swipeStartHeight + " - End height: " + swipeEndHeight);
            new TouchAction((AndroidDriver) appiumDriver)
                    .press(PointOption.point(swipeStartWidth, swipeEndHeight))
                    .waitAction(WaitOptions.waitOptions(ofMillis(2000)))
                    .moveTo(PointOption.point(swipeEndWidth, swipeStartHeight))
                    .release()
                    .perform();
        } else {
            Dimension d = appiumDriver.manage().window().getSize();
            int height = d.height;
            int width = d.width;

            int swipeStartWidth = width / 2, swipeEndWidth = width / 2;
            int swipeStartHeight = (height * 70) / 100;
            int swipeEndHeight = (height * 30) / 100;
            new TouchAction(appiumDriver)
                    .press(PointOption.point(swipeStartWidth, swipeEndHeight))
                    .waitAction(WaitOptions.waitOptions(ofMillis(2000)))
                    .moveTo(PointOption.point(swipeEndWidth, swipeStartHeight))
                    .release()
                    .perform();
        }
    }


    public void swipeDownAccordingToPhoneSize() {
        if (appiumDriver instanceof AndroidDriver) {
            Dimension d = appiumDriver.manage().window().getSize();
            int height = d.height;
            int width = d.width;
            System.out.println("Android1 : " + width + " - " + height);
            int swipeStartWidth = width / 2, swipeEndWidth = width / 2;
            int swipeStartHeight = (height * 60) / 100;
            int swipeEndHeight = (height * 20) / 100;
            System.out.println("Start width: " + swipeStartWidth + " - Start height: " + swipeStartHeight + " - End height: " + swipeEndHeight);
            //appiumDriver.swipe(swipeStartWidth, swipeStartHeight, swipeEndWidth, swipeEndHeight, 1000);
            new TouchAction(appiumDriver)
                    .press(PointOption.point(swipeStartWidth, swipeEndHeight))
                    .waitAction(WaitOptions.waitOptions(ofMillis(1000)))
                    .moveTo(PointOption.point(swipeEndWidth, swipeStartHeight))
                    .release()
                    .perform();
        } else {
            Dimension d = appiumDriver.manage().window().getSize();
            int height = d.height;
            int width = d.width;

            int swipeStartWidth = width / 2, swipeEndWidth = width / 2;
            int swipeStartHeight = (height * 40) / 100;
            int swipeEndHeight = (height * 20) / 100;
            // appiumDriver.swipe(swipeStartWidth, swipeStartHeight, swipeEndWidth, swipeEndHeight, 1000);
            new TouchAction(appiumDriver)
                    .press(PointOption.point(swipeStartWidth, swipeStartHeight))
                    .waitAction(WaitOptions.waitOptions(ofMillis(1000)))
                    .moveTo(PointOption.point(swipeEndWidth, swipeEndHeight))
                    .release()
                    .perform();
        }
    }

    public boolean isElementPresent(By by) {
        return findElementWithoutAssert(by) != null;
    }


    @And("<times> kere aşağıya kaydır")
    public void swipe(int times) throws InterruptedException {
        for (int i = 0; i < times; i++) {
            swipeDownAccordingToPhoneSize();
            waitBySecond(2);

            System.out.println("-----------------------------------------------------------------");
            System.out.println("SWiPE EDiLDi");
            System.out.println("-----------------------------------------------------------------");

        }
    }

    @And("Sayfanın  ortasından itibaren  <times> kere aşağıya kaydır")
    public void MiddlePageswipe(int times) {
        for (int i = 0; i < times; i++) {
            if (appiumDriver instanceof AndroidDriver) {
                Dimension d = appiumDriver.manage().window().getSize();
                int width = d.width;

                int swipeStartWidth = width / 2, swipeEndWidth = width / 2;
                int swipeStartHeight = (400 * 90) / 100;
                int swipeEndHeight = (400 * 50) / 100;
                //appiumDriver.swipe(swipeStartWidth, swipeStartHeight, swipeEndWidth, swipeEndHeight, 1000);
                new TouchAction(appiumDriver)
                        .press(PointOption.point(swipeStartWidth, swipeStartHeight))
                        .waitAction(WaitOptions.waitOptions(ofMillis(1000)))
                        .moveTo(PointOption.point(swipeEndWidth, swipeEndHeight))
                        .release()
                        .perform();
            } else {
                Dimension d = appiumDriver.manage().window().getSize();
                // int height = d.height;
                int width = d.width;

                int swipeStartWidth = width / 2, swipeEndWidth = width / 2;
                int swipeStartHeight = (400 * 90) / 100;
                int swipeEndHeight = (400 * 40) / 100;
                // appiumDriver.swipe(swipeStartWidth, swipeStartHeight, swipeEndWidth, swipeEndHeight, 1000);
                new TouchAction(appiumDriver)
                        .press(PointOption.point(swipeStartWidth, swipeStartHeight))
                        .waitAction(WaitOptions.waitOptions(ofMillis(1000)))
                        .moveTo(PointOption.point(swipeEndWidth, swipeEndHeight))
                        .release()
                        .perform();
            }
        }
        waitBySecond(3);

        System.out.println("-----------------------------------------------------------------");
        System.out.println("SWiPE EDiLDi");
        System.out.println("-----------------------------------------------------------------");

    }

    @And("Klavyeyi kapat")
    public void hideAndroidKeyboard() {
        try {

            if (localAndroid == false) {
                findElementWithoutAssert(By.xpath("//XCUIElementTypeButton[@name='Toolbar Done Button']")).click();
            } else {
                appiumDriver.hideKeyboard();
            }

        } catch (Exception ex) {
            logger.error("Klavye kapatılamadı " + ex.getMessage());
        }
    }

    @And("{string} değerini sayfa üzerinde olup olmadığını kontrol et.")
    public void getPageSourceFindWord(String text) {
        logger.info("Page sources   !!!!!   " + appiumDriver.getPageSource());
        assertTrue(text + " sayfa üzerinde bulunamadı.",
                appiumDriver.getPageSource().contains(text));

        logger.info(text + " sayfa üzerinde bulundu");
    }


    @And("<length> uzunlugunda random bir kelime üret ve <saveKey> olarak sakla")
    public void createRandomNumber(int length, String saveKey) {
        StoreHelper.INSTANCE.saveValue(saveKey, new RandomString(length).nextString());
    }

    @And("Geri butonuna bas")
    public void clickBybackButton() {
        if (!localAndroid) {
            backPage();
        } else {
            ((AndroidDriver) appiumDriver).pressKey(new KeyEvent().withKey(AndroidKey.BACK));
        }

    }

    @And("<StartX>,<StartY> oranlarından <EndX>,<EndY> oranlarına <times> kere swipe et")
    public void pointToPointSwipe(int startX, int startY, int endX, int endY, int count) {
        Dimension d = appiumDriver.manage().window().getSize();
        int height = d.height;
        int width = d.width;

        startX = (width * startX) / 100;
        startY = (height * startY) / 100;
        endX = (width * endX) / 100;
        endY = (height * endY) / 100;


        for (int i = 0; i < count; i++) {
            TouchAction action = new TouchAction(appiumDriver);
            action.press(PointOption.point(startX, startY))
                    .waitAction(WaitOptions.waitOptions(ofMillis(1000)))
                    .moveTo(PointOption.point(endX, endY))
                    .release().perform();
        }
    }

    @And("uygulamayı yeniden başlat")
    public void restart() throws InterruptedException {
        appiumDriver.closeApp();
        appiumDriver.launchApp();
        logger.info("uygulama yeniden başlatıldı");
        waitBySecond(5);


    }


    private void backPage() {
        appiumDriver.navigate().back();
    }

    @And("{string} li elementin yüklenmesini <time> saniye bekle")
    public boolean doesElementExistByKey(String key, int time) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);
        try {
            WebDriverWait elementExist = new WebDriverWait(appiumDriver, time);
            elementExist.until(ExpectedConditions.visibilityOfElementLocated(selectorInfo.getBy()));
            return true;
        } catch (Exception e) {
            logger.info(key + " aranan elementi bulamadı");
            return false;
        }

    } @And("{string} li elementin yüklenmesini {int} saniye bekle ve tıkla")
    public boolean waitAndClick(String key, int time) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);
        try {
            WebDriverWait elementExist = new WebDriverWait(appiumDriver, time);
            elementExist.until(ExpectedConditions.visibilityOfElementLocated(selectorInfo.getBy())).click();
            logger.info(key+ "Element is clicked");
            return true;
        } catch (Exception e) {
            logger.info(key + " aranan elementi bulamadı");
            return false;
        }

    }

    public boolean doesElementClickableByKey(String key, int time) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);
        try {
            WebDriverWait elementExist = new WebDriverWait(appiumDriver, time);
            elementExist.until(ExpectedConditions.elementToBeClickable(selectorInfo.getBy()));
            return true;
        } catch (Exception e) {
            logger.info(key + " aranan elementi bulamadı");
            return false;
        }

    }



    @And("Verilen <x> <y> koordinatına tıkla")
    public void tapElementWithCoordinate(int x, int y) {
        TouchAction a2 = new TouchAction(appiumDriver);
        a2.tap(PointOption.point(x, y)).perform();
    }



    @And("{string} li element varsa  <x> <y> koordinatına tıkla ")
    public void tapElementWithKeyCoordinate(String key, int x, int y) {
        logger.info("element varsa verilen koordinata tıkla ya girdi");
        MobileElement mobileElement;

        mobileElement = findElementByKeyWithoutAssert(key);

        if (mobileElement != null) {

            Point point = mobileElement.getLocation();
            logger.info(point.x + "  " + point.y);
            Dimension dimension = mobileElement.getSize();
            logger.info(dimension.width + "  " + dimension.height);
            TouchAction a2 = new TouchAction(appiumDriver);
            a2.tap(PointOption.point(point.x + (dimension.width * x) / 100, point.y + (dimension.height * y) / 100)).perform();
        }
    }



    @And("{string} li elementi rasgele sec")
    public void chooseRandomProduct(String key) {

        List<MobileElement> productList = new ArrayList<>();
        List<MobileElement> elements = findElemenstByKey(key);
        int elementsSize = elements.size();
        int height = appiumDriver.manage().window().getSize().height;
        for (int i = 0; i < elementsSize; i++) {
            MobileElement element = elements.get(i);
            int y = element.getCenter().getY();
            if (y > 0 && y < (height - 100)) {
                productList.add(element);
            }
        }
        Random random = new Random();
        int randomNumber = random.nextInt(productList.size());
        productList.get(randomNumber).click();
    }


    @And("{string}li elementi bulana kadar <limit> kere swipe yap ve elementi bul")
    public void swipeKeyy(String key, int limit) throws InterruptedException {


        boolean isAppear = false;

        int windowHeight = this.getScreenHeight();
        for (int i = 0; i < limit; ++i) {
            try {

                Point elementLocation = findElementByKeyWithoutAssert(key).getLocation();
                logger.info(elementLocation.x + "  " + elementLocation.y);
                Dimension elementDimension = findElementByKeyWithoutAssert(key).getSize();
                logger.info(elementDimension.width + "  " + elementDimension.height);
                // logger.info(appiumDriver.getPageSource());
                if (elementDimension.height > 20) {
                    isAppear = true;
                    logger.info("aranan elementi buldu");
                    break;
                }
            } catch (Exception e) {
                System.out.println("Element ekranda görülmedi. Tekrar swipe ediliyor");
            }
            System.out.println("Element ekranda görülmedi. Tekrar swipe ediliyor");

            swipeUpAccordingToPhoneSize();
            waitBySecond(1);
        }

    }


    private Long getTimestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return (timestamp.getTime());
    }


    @And("{string} li elementi bul basılı tut")
    public void basiliTut(String key) {
        MobileElement mobileElement = findElementByKey(key);

        TouchAction action = new TouchAction<>(appiumDriver);
        action
                .longPress(LongPressOptions.longPressOptions()
                        .withElement(ElementOption.element(mobileElement)).withDuration(Duration.ofSeconds(4)))
                .release()
                .perform();

    }



    @And("Android telefonda klavye üzerinden esc tuşuna bas")
    public void pressEscKeyForAndroid() {
        AndroidDriver androidDriver = (AndroidDriver) appiumDriver;
        ((AndroidDriver<MobileElement>) appiumDriver).pressKey(new KeyEvent().withKey(AndroidKey.ESCAPE));
    }

    @And("Android telefondan klavye üzerinden Enter butonuna bas")
    public void pressEnterKeyForAndroid() {
        AndroidDriver androidDriver = (AndroidDriver) appiumDriver;
        ((AndroidDriver<MobileElement>) appiumDriver).pressKey(new KeyEvent().withKey(AndroidKey.ENTER));
    }

    @And("Android telefondan klavye üzerinden Tab butonuna bas")
    public void pressTabKeyForAndroid() {
        AndroidDriver androidDriver = (AndroidDriver) appiumDriver;
        ((AndroidDriver<MobileElement>) appiumDriver).pressKey(new KeyEvent().withKey(AndroidKey.TAB));
    }

    @And("{string} li elementi bul ve tıkla, sonra klavyeyi kapat")
    public void clickByKeyAndThenCloseKeyboard(String key) {
        doesElementExistByKey(key, 5);
        doesElementClickableByKey(key, 5);
        findElementByKey(key).click();
        logger.info(key + " elementine tıklandı");
        closeKeyboard();
    }


    @And("Check if element {string} not exists")
    public void checkElementNotExists(String key) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);

        int loopCount = 0;
        while (loopCount < 70) {
            if (appiumDriver.findElements(selectorInfo.getBy()).size() == 0) {
                logger.info(key + " elementinin olmadığı kontrol edildi.");
                return;
            }
            loopCount++;
        }
        Assert.fail("Element '" + key + "' still exist.");
    }

    @And("Klavye kapat")
    public void closeKeyboard() {
        appiumDriver.hideKeyboard();
    }

    public void swipeDownAccordingToPhoneSizeFromSpecificPosition() {
        if (appiumDriver instanceof AndroidDriver) {
            Dimension d = appiumDriver.manage().window().getSize();
            int height = d.height;
            int width = d.width;
            int swipeStartWidth = width / 2, swipeEndWidth = width / 2;
            int swipeStartHeight = (int) (height * 0.35);
            int swipeEndHeight = (int) (height * 0.8);
            System.out.println("Start width: " + swipeStartWidth + " - Start height: " + swipeStartHeight + " - End height: " + swipeEndHeight);
            //appiumDriver.swipe(swipeStartWidth, swipeStartHeight, swipeEndWidth, swipeEndHeight, 1000);
            new TouchAction(appiumDriver)
                    .press(PointOption.point(swipeStartWidth, swipeStartHeight))
                    .waitAction(WaitOptions.waitOptions(ofMillis(1000)))
                    .moveTo(PointOption.point(swipeEndWidth, swipeEndHeight))
                    .release()
                    .perform();
        } else {
            Dimension d = appiumDriver.manage().window().getSize();
            int height = d.height;
            int width = d.width;

            int swipeStartWidth = width / 2, swipeEndWidth = width / 2;
            int swipeStartHeight = (int) (height * 0.35);
            int swipeEndHeight = (int) (height * 0.8);
            // appiumDriver.swipe(swipeStartWidth, swipeStartHeight, swipeEndWidth, swipeEndHeight, 1000);
            new TouchAction(appiumDriver)
                    .press(PointOption.point(swipeStartWidth, swipeStartHeight))
                    .waitAction(WaitOptions.waitOptions(ofMillis(1000)))
                    .moveTo(PointOption.point(swipeEndWidth, swipeEndHeight))
                    .release()
                    .perform();
        }
    }

    @And("{string} li ve değeri {string} e eşit olan elementi bulana kadar aşağı swipe et ve tıkla")
    public void clickByKeyWithSwipeDown(String key, String text) throws InterruptedException {
        boolean find = false;
        int maxRetryCount = 10;
        while (!find && maxRetryCount > 0) {
            List<MobileElement> elements = findElemenstByKey(key);
            for (MobileElement element : elements) {
                if (element.getText().contains(text)) {
                    element.click();
                    find = true;
                    break;
                }
            }
            if (!find) {
                maxRetryCount--;
                swipeDownAccordingToPhoneSizeFromSpecificPosition();
                waitBySecond(1);
            }
        }
    }

    @And("{string} li elementi bulana kadara yukarı swipe et")
    public void swipeUntilToFindElement(String key) {
        int maxRetryCount = 10;
        while (maxRetryCount > 0) {
            MobileElement element = findElementByKeyWithoutAssert(key);
            if (element != null) {
                break;
            }
            maxRetryCount--;
            swipeUpAccordingToPhoneSize();
            waitBySecond(1);
        }
    }



    @And("Context {string} olarak değiştir")
    public void setContext(String key) {
        waitBySecond(10);
        appiumDriver.context(key);
        logger.info("Context " + key + " olarak değiştirildi.");
    }


    public void swipeDownFromMiddleOfPage() {
        if (appiumDriver instanceof AndroidDriver) {
            Dimension d = appiumDriver.manage().window().getSize();
            int height = d.height;
            int width = d.width;
            System.out.println("Android1 : " + width + " - " + height);
            int swipeStartWidth = width / 2, swipeEndWidth = width / 2;
            int swipeStartHeight = height / 2;
            int swipeEndHeight = swipeStartHeight + (swipeStartHeight * 60) / 100;
            System.out.println("Start width: " + swipeStartWidth + " - Start height: " + swipeStartHeight + " - End height: " + swipeEndHeight);
            //appiumDriver.swipe(swipeStartWidth, swipeStartHeight, swipeEndWidth, swipeEndHeight, 1000);
            new TouchAction(appiumDriver)
                    .press(PointOption.point(swipeStartWidth, swipeStartHeight))
                    .waitAction(WaitOptions.waitOptions(ofMillis(1000)))
                    .moveTo(PointOption.point(swipeEndWidth, swipeEndHeight))
                    .release()
                    .perform();
        } else {
            Dimension d = appiumDriver.manage().window().getSize();
            int height = d.height;
            int width = d.width;

            int swipeStartWidth = width / 2, swipeEndWidth = width / 2;
            int swipeStartHeight = height / 2;
            int swipeEndHeight = swipeStartHeight + (height * 40) / 100;
            // appiumDriver.swipe(swipeStartWidth, swipeStartHeight, swipeEndWidth, swipeEndHeight, 1000);
            new TouchAction(appiumDriver)
                    .press(PointOption.point(swipeStartWidth, swipeStartHeight))
                    .waitAction(WaitOptions.waitOptions(ofMillis(1000)))
                    .moveTo(PointOption.point(swipeEndWidth, swipeEndHeight))
                    .release()
                    .perform();
        }

    }



    private void waitByMilliSeconds(int i) {
    }

    public String getElementText(String key) {
        return findElementByKey(key).getText().trim();
    }

    public String convertIntegerToStringValue(int key) {
        String value = new Integer(key).toString();
        return value;
    }

    @And("{string} elementi {string} değerini içeriyor mu kontrol et")
    public void checkElementContainsText(String key, String expectedText) {
        logger.info("Expected: " + expectedText);
        logger.info("Screen : " + findElementByKey(key).getText());
        assertTrue("Expected text is not contained", findElementByKey(key).getText().contains(expectedText));
    }


    @And("{string} elementi {string} değerini eşit mi kontrol et")
    public void checkElementEqualsText(String key, String expectedText) {
        String elementText = findElementByKey(key).getText().trim();
        Assert.assertEquals("Değerler eşleşmiyor.", elementText, expectedText);
        logger.info(key + "'li elementin değerinin " + expectedText + " olduğu doğrulandı.");
    }

    @And("Click if element with {string} exists, otherwise continue")
    public void tapElementWithKeyControlArea(String key) {
        waitBySecond(5);

        MobileElement mobileElement;

        mobileElement = findElementByKeyWithoutAssert(key);

        if (mobileElement != null) {

            doesElementExistByKey(key, 5);
            findElementByKey(key).click();
            logger.info(key + " exists and clicked");

        }
        else {
            logger.info(key + " does not exist and continue.");

        }}

    @And("{int} saniye bekle")
    public void waitBySecond(int seconds) {
            try {
                Thread.sleep(1000*seconds);
                logger.info(seconds + " Saniye kadar beklendi.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }



    @And("Element var mı kontrol et <key> yoksa <message> yazdir")
    public void getElementWithKeyIfExists(String key, String message) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        long timeout = 30000; // 1 dakika = 60000 milisaniye

        while ((System.currentTimeMillis() - startTime) < timeout) {
            try {
                if (findElementByKey(key).isDisplayed()) {
                    logger.info(key + " elementi bulundu.");
                    return;
                }
            } catch (Exception e) {
                // Element henüz bulunamadı, beklemeye devam et
            }
            Thread.sleep(500); // 0.5 saniye bekle ve tekrar dene
        }

        // Eğer döngü tamamlanırsa ve element hala bulunamazsa, hata fırlat
        Assertions.fail(message);
    }
    @And("Element yok mu kontrol et <key>")
    public void getElementWithKeyIfNotExists(String key) throws InterruptedException {
        boolean isNotExist = false;
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);
        try{
            appiumDriver.findElement(selectorInfo.getBy());
            isNotExist = false;
        }catch (NoSuchElementException e){
            isNotExist = true;
        }
        assertTrue(selectorInfo.getBy().toString()+" elementi var", isNotExist);
    }

















}


