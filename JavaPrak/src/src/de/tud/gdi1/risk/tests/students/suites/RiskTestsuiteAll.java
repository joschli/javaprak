package src.de.tud.gdi1.risk.tests.students.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ RiskTestsuiteExtended1.class, RiskTestsuiteExtended2.class,
		RiskTestsuiteExtended3.class, RiskTestsuiteMinimal.class })
public class RiskTestsuiteAll {

}
