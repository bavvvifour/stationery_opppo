import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PencilTest {

    private static final String PHONE = "+79991234567";

    @Test
    public void createsPencilWithValidData() {
        final Pencil pencil = new Pencil(3, PencilColor.RED, 25.5, PHONE);
        assertEquals(3, pencil.getLeadHardness());
        assertEquals(PencilColor.RED, pencil.getColor());
        assertEquals(25.5, pencil.getPrice(), 0.0);
        assertEquals(PHONE, pencil.getOwnerPhone());
        assertEquals("PENCIL", pencil.getType());
    }

    @Test
    public void toStringContainsAllFields() {
        final Pencil pencil = new Pencil(2, PencilColor.BLUE, 12, PHONE);
        final String expected = String.format(
                "Карандаш: твёрдость грифеля=%d, цвет=%s, цена=%.2f, "
                        + "телефон владельца=%s",
                2, PencilColor.BLUE, 12.0, PHONE);
        assertEquals(expected, pencil.toString());
    }

    @Test
    public void acceptsEveryColor() {
        for (PencilColor color : PencilColor.values()) {
            assertEquals(color, new Pencil(1, color, 1, PHONE).getColor());
        }
    }

    @Test
    public void zeroHardnessIsRejected() {
        final IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> new Pencil(0, PencilColor.RED, 10, PHONE));
        assertTrue(e.getMessage().contains("Твёрдость"));
    }

    @Test
    public void negativeHardnessIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new Pencil(-1, PencilColor.RED, 10, PHONE));
    }

    @Test
    public void nullColorIsRejected() {
        final IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> new Pencil(2, null, 10, PHONE));
        assertTrue(e.getMessage().contains("Цвет"));
    }

    @Test
    public void invalidPriceIsRejectedByBaseClass() {
        assertThrows(IllegalArgumentException.class,
                () -> new Pencil(2, PencilColor.RED, -1, PHONE));
    }

    @Test
    public void emptyPhoneIsRejectedByBaseClass() {
        assertThrows(IllegalArgumentException.class,
                () -> new Pencil(2, PencilColor.RED, 1, ""));
    }
}
