# Assert Lazy

Assert lazy is a functional automation friendly library built on top of `junit-jupiter`, and takes the executable api to
performs hard and soft assertions. Unlike the name suggests, assertions are not lazy like the `junit-jupiter`
`assertAll(Collection<Executable>)` as functional testing verifications are never that straight forward.

## How to be lazy 

In the below example you can keep adding any number of `AssertStatement.statement(...)` all the assertions will be 
performed, and the result will be stored internally which can be accessed by `assert.getErrors()`. Every time the lazy
assert is invoked the internal list keeps getting updated, but the test will not fail. When `asserts.assertAll()` is 
invoked, if there is any failure the test execution would exit.

```java
import static com.qualizeal.community.asserts.AssertStatement.*;

class TestClass {
    @Test
    public void lazyTestExample() {
        AssertLazy asserts = new AssertLazy();
        //...
        //...
        asserts.lazyAssert(
                statement("Tea should be available in the Page",
                        () -> assertThat(driver.findElement(teaElement).isDisplayed()).isEqualTo(true)),
                statement("Coffee should be present in the page",
                        () -> assertThat(driver.findElement(cofeeElement).isDisplayed()).isEqualTo(true))
        );
        //...
        //...
        asserts.assertAll();
    }
}
```
## How to be super lazy
Sometimes it not just enough to assert, there might be need to perform some complex operations if the test fails. The 
below example shows how to use a PostProcessor assuming you are trying to capture a screenshot with selenium webdriver.

```java
class TestClass {
    @Test
    public void lazyTestExample() {
        AssertLazy asserts = new AssertLazy();
        PostProcessor<String> takeScreenshotProcessor = TakeScreenShotProcessor(driver);
        //...
        //...
        asserts.lazyAssert(
                statement("Tea should be available in the Page",
                        () -> assertThat(driver.findElement(teaElement).isDisplayed()).isEqualTo(true)),
                statement("Coffee should be present in the page",
                        () -> assertThat(driver.findElement(cofeeElement).isDisplayed()).isEqualTo(true))
        ).onFailure(takeScreenshotProcessor).process();
        //...
        //...
    }
}
```
```java
class TakeScreenShotProcessor implements PostProcessor<String> {
    @Getter @Setter
    private List<String> errors;
    private WebDriver driver;
    
    public TakeScreenShotProcessor(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public String process() {
        if (!errors.isEmpty())
            return browser.takeScreenShot(OutputType.BASE64);
        return "";
    }

    @Override
    public void fail() {
        if (!errors.isEmpty())
            throw new AssertionError(errors);
    }
}
```
---

## Note
AssertLazy also contains `eagerAssert(Statement...)` which works very similar to the lazy assert except that it cannot 
have post processors and would fail immediately invoked once all statements are checked. 
