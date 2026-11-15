import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class StationeryManagerRemoveByPriceTest {

    private static final String PHONE = "+79991234567";

    @Parameters(name = "REM PRICE {0} {1} -> удалено {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            {">", 20.0, 2},
            {">", 40.0, 0},
            {"<", 20.0, 1},
            {"<", 10.0, 0},
            {">=", 20.0, 3},
            {"<=", 20.0, 2},
            {"<=", 100.0, 4},
            {"==", 30.0, 1},
            {"==", 25.0, 0},
            {"!=", 30.0, 3},
            {"!=", 25.0, 4},
        });
    }

    private final String operator;
    private final double price;
    private final int expectedRemoved;

    public StationeryManagerRemoveByPriceTest(
            final String operator, final double price,
            final int expectedRemoved) {
        this.operator = operator;
        this.price = price;
        this.expectedRemoved = expectedRemoved;
    }

    @Test
    public void removesExpectedNumberOfItems() throws Exception {
        final StationeryManager manager = new StationeryManager();
        manager.add(new Pencil(2, PencilColor.RED, 10, PHONE));
        manager.add(new Pen(PenType.GEL, 0.5, 20, PHONE));
        manager.add(new Pen(PenType.BALLPOINT, 0.7, 30, PHONE));
        manager.add(new Paper(80, 210, 297, 40, PHONE));

        final String output = ConsoleCapture.capture(
                () -> manager.removeByPrice(operator, price));

        assertEquals("Удалено объектов: " + expectedRemoved, output.trim());
        assertEquals(4 - expectedRemoved, ConsoleCapture.countItems(manager));
    }
}
