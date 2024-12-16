package com.Mobile.SwagLabs;

import com.Mobile.SwagLabs.selector.SelectorFactory;
import com.Mobile.SwagLabs.selector.SelectorType;
import com.Mobile.SwagLabs.selector.Selector;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class HookImpl {

    static AppiumDriver<MobileElement> appiumDriver;
    protected static FluentWait<AppiumDriver<MobileElement>> appiumFluentWait;
    public static boolean localAndroid = true;
    protected static Selector selector;
    private DesiredCapabilities capabilities;
    private URL localUrl;
    private Logger logger = Logger.getLogger(getClass());



    @Before
    public void beforeScenario() {
        try {
            logger.info("************************************  BeforeScenario  ************************************");

            localUrl = new URL("http://127.0.0.1:4723/wd/hub");

            if (StringUtils.isEmpty(System.getProperty("platform")) || System.getProperty("platform").equals("ANDROID")) {
                logger.info("Local cihazda Android ortamında test ayağa kalkacak");
                appiumDriver = new AndroidDriver(localUrl, androidCapabilities());
            } else {
                logger.info("Local cihazda IOS ortamında test ayağa kalkacak");
                appiumDriver = new IOSDriver<>(localUrl, iosCapabilities());
            }
            selector = SelectorFactory
                    .createElementHelper(localAndroid ? SelectorType.ANDROID : SelectorType.IOS);
            appiumFluentWait = new FluentWait<AppiumDriver<MobileElement>>(appiumDriver);
            appiumFluentWait.withTimeout(Duration.ofSeconds(30))
                    .pollingEvery(Duration.ofMillis(250))
                    .ignoring(NoSuchElementException.class);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }


    @After
    public void afterScenario() {
        if (appiumDriver != null)
            appiumDriver.quit();
    }

    public DesiredCapabilities androidCapabilities() {

        capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
        capabilities.setCapability("unicodeKeyboard", false);
        capabilities.setCapability("resetKeyboard", false);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.swaglabsmobileapp");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.swaglabsmobileapp.MainActivity");
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 600);
        capabilities.setCapability(MobileCapabilityType.PLATFORM, MobilePlatform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
        //capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300);

        return capabilities;
    }


    public DesiredCapabilities iosCapabilities() {

        capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
        capabilities
                .setCapability("bundleId", "com.mobiltest");
        capabilities.setCapability(MobileCapabilityType.PLATFORM, MobilePlatform.IOS);
        capabilities
                .setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");

        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "12.5");

        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300);
        capabilities.setCapability("sendKeyStrategy", "setValue");
        capabilities.setCapability("app", "appUrl");

        return capabilities;
    }
}