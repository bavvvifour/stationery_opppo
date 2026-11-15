import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PaperTest {

    private static final String PHONE = "+79991234567";

    @Test
    public void createsPaperWithValidData() {
        final Paper paper = new Paper(80, 210, 297, 120, PHONE);
        assertEquals(80, paper.getDensity());
        assertEquals(210, paper.getWidth());
        assertEquals(297, paper.getHeight());
        assertEquals(120.0, paper.getPrice(), 0.0);
        assertEquals(PHONE, paper.getOwnerPhone());
        assertEquals("PAPER", paper.getType());
    }

    @Test
    public void toStringContainsAllFields() {
        final Paper paper = new Paper(120, 297, 420, 250, PHONE);
        final String expected = String.format(
                "Бумага: плотность=%d, ширина=%d, высота=%d, "
                        + "цена=%.2f, телефон владельца=%s",
                120, 297, 420, 250.0, PHONE);
        assertEquals(expected, paper.toString());
    }

    @Test
    public void zeroDensityIsRejected() {
        final IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> new Paper(0, 210, 297, 10, PHONE));
        assertTrue(e.getMessage().contains("Плотность"));
    }

    @Test
    public void negativeDensityIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new Paper(-80, 210, 297, 10, PHONE));
    }

    @Test
    public void zeroWidthIsRejected() {
        final IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> new Paper(80, 0, 297, 10, PHONE));
        assertTrue(e.getMessage().contains("Ширина и высота"));
    }

    @Test
    public void negativeHeightIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new Paper(80, 210, -1, 10, PHONE));
    }

    @Test
    public void zeroHeightIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new Paper(80, 210, 0, 10, PHONE));
    }

    @Test
    public void nullPhoneIsRejectedByBaseClass() {
        assertThrows(IllegalArgumentException.class,
                () -> new Paper(80, 210, 297, 10, null));
    }
}
