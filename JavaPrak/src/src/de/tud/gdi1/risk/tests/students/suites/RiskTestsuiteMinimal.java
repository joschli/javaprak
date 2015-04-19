package src.de.tud.gdi1.risk.tests.students.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import src.de.tud.gdi1.risk.tests.students.testcases.MinimalMapTest;
import src.de.tud.gdi1.risk.tests.students.testcases.MinimalPhaseTest;
import src.de.tud.gdi1.risk.tests.students.testcases.MinimalWinTest;

@RunWith(Suite.class)
@SuiteClasses({ MinimalMapTest.class, MinimalPhaseTest.class,
		MinimalWinTest.class })
public class RiskTestsuiteMinimal {

}
