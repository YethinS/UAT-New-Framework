package uat;

import org.openqa.selenium.WebElement;

import java.io.FileNotFoundException;


public interface UatBase {
    //base model for common function


    /**
     * startApp used to open web-browser with url and default driver chrome.
     * @throws FileNotFoundException 
     *
     */
    void startApp() throws FileNotFoundException;

    /**
     * startApp used to open web-browser with url and driver as required .
     * @param url : url of the page
     * @param driverName : name of the driver eg: chrome ,firefox......
     
    void startApp(String url,String driverName);*/

    /**
     * quit the browser with driver
     */
    void tearDown();

    /**
     *  To find a web-element using css
     * @param css : css selector (id,class etc. or combination of all)
     * @return a web-element
     */
    WebElement locator(String css);

    /**
     *  To find a web-element using xpath
     * @param xpath : css selector (id,class etc. or combination of all)
     * @return a web-element
     */
    WebElement xpathLocator(String xpath);

    /**
     *   wait until element is visible
     *   default one using css to find element
     * @param css : css selector (id,class etc. or combination of all)
     */
    void waitMethod(String css);

    /**
     *   wait until element is visible
     *   using xpath to find element
     * @param css : css selector (id,class etc. or combination of all)
     */
    void xpathWaitMethod(String css);

    /**
     * Used to click a clickable web-element
     * @param ele : represent web-element eg: a button element
     */
    void click(WebElement ele);

    /**
     * Used to check whether an element is displayed or not
     * @param ele :represent web-element
     * @return a boolean value
     */
    boolean isDisplayed(WebElement ele);

    /**
     *Used to whether an element is visible or not including bundled wait and assert
     *with default message "element is visible " if the condition passed
     * @param ele:  represent web-element
     *
     */
    void visibilityCheck(WebElement ele);


    /**
     * Used to whether an element is visible or not including bundled wait and assert
     * with custom message
     * @param ele :represent web-element
     * @param msg:represent the msg need to print on the console or terminal
     */
    void visibilityCheck(WebElement ele,String msg);

    /**
     * sleep function
     * @param i: time in millisecond
     */
    void sleep(long i);

    /**
     * Used to get the screenshot
     * @param fileName : name of the file to be saved
     */
    void screenShot(String fileName);

    /**
     * Used to send input to an element eg: text-box
     * @param ele: web-element
     * @param input: message tobe send to the element
     */
    void elementInput(WebElement ele,String input);

    /**
     *Used to send input to an element eg: text-box
     * @param css: web-element's css selector
     * @param input: message tobe send to the element
     */
    void elementInput(String css, String input);



    /**
     *Used to send input to an element eg: text-box
     * @param xpath: web-element's xpath
     * @param input: message tobe send to the element
     */
    void xpathElementInput(String xpath, String input);

    /**
     * Used to check whether  to strings are equal or not; if it not it will throw an error
     * @param actual : actual string
     * @param expected : expected string
     */
    void assertEqualCheck(String actual,String expected);


}

