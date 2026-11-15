import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    StationeryTest.class,
    PencilTest.class,
    PenTest.class,
    PaperTest.class,
    StationeryManagerTest.class,
    StationeryManagerRemoveByPriceTest.class,
    MainCommandTest.class,
    MainInvalidCommandTest.class,
    MainFileTest.class
})
public class AllTests {
}
