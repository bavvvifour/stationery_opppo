import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class StationeryTest {

    private static final String PHONE = "+79991234567";

    /** Минимальная конкретная реализация абстрактного класса. */
    private static Stationery create(final double price, final String phone) {
        return new Stationery(price, phone) {
            @Override
            public String getType() {
                return "TEST";
            }

            @Override
            public String toString() {
                return "test";
            }
        };
    }

    @Test
    public void createsWithValidPriceAndPhone() {
        final Stationery item = create(25.5, PHONE);
        assertEquals(25.5, item.getPrice(), 0.0);
        assertEquals(PHONE, item.getOwnerPhone());
        assertEquals("TEST", item.getType());
    }

    @Test
    public void zeroPriceIsAllowed() {
        assertEquals(0.0, create(0.0, PHONE).getPrice(), 0.0);
    }

    @Test
    public void negativePriceIsRejected() {
        final IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class, () -> create(-0.01, PHONE));
        assertTrue(e.getMessage().contains("Цена"));
    }

    @Test
    public void nanPriceIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> create(Double.NaN, PHONE));
    }

    @Test
    public void positiveInfinityPriceIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> create(Double.POSITIVE_INFINITY, PHONE));
    }

    @Test
    public void negativeInfinityPriceIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> create(Double.NEGATIVE_INFINITY, PHONE));
    }

    @Test
    public void nullPhoneIsRejected() {
        final IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class, () -> create(10, null));
        assertTrue(e.getMessage().contains("телефон"));
    }

    @Test
    public void emptyPhoneIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> create(10, ""));
    }
}
