package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features",       // Path to your .feature files
    glue = "stepdefinitions",                  // Package with step definitions
    plugin = {
        "pretty",
        "html:target/cucumber-reports/report.html",
        "json:target/cucumber-reports/report.json"
    },
    monochrome = true
)
public class TestNGRunner extends AbstractTestNGCucumberTests {
}
