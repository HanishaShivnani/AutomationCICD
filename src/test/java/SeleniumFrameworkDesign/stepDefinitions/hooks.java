package SeleniumFrameworkDesign.stepDefinitions;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import SeleniumFrameworkDesign.TestComponents.BaseTest;

public class hooks extends BaseTest{
		
	@Before
    public void setUp() throws Exception {
        launchApplication();
    }

    @After
    public void tearDown() {
        closePage();
    }
	
}
