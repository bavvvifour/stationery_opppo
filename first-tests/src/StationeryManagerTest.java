import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class StationeryManagerTest {

    private static final String PHONE = "+79991234567";

    private StationeryManager manager;

    @Before
    public void setUp() {
        manager = new StationeryManager();
    }

    private void fill() {
        manager.add(new Pencil(2, PencilColor.RED, 10, PHONE));
        manager.add(new Pen(PenType.GEL, 0.5, 20, PHONE));
        manager.add(new Pen(PenType.BALLPOINT, 0.7, 30, PHONE));
        manager.add(new Paper(80, 210, 297, 40, PHONE));
    }

    // ---------- add / print ----------

    @Test
    public void newManagerIsEmpty() throws Exception {
        final String output = ConsoleCapture.capture(manager::print);
        assertEquals("Контейнер пуст.", output.trim());
    }

    @Test
    public void addedItemsArePrintedInOrderWithNumbers() throws Exception {
        final Pencil pencil = new Pencil(2, PencilColor.RED, 10, PHONE);
        final Paper paper = new Paper(80, 210, 297, 40, PHONE);
        manager.add(pencil);
        manager.add(paper);

        final String[] lines =
                ConsoleCapture.capture(manager::print).split("\\R");

        assertEquals(4, lines.length);
        assertEquals("СОДЕРЖИМОЕ КОНТЕЙНЕРА", lines[0]);
        assertEquals("1. " + pencil, lines[1]);
        assertEquals("2. " + paper, lines[2]);
        assertEquals("!!!!!!!!", lines[3]);
    }

    @Test
    public void addNullIsRejected() throws Exception {
        final IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class, () -> manager.add(null));
        assertTrue(e.getMessage().contains("пустую"));
        assertEquals(0, ConsoleCapture.countItems(manager));
    }

    // ---------- removeByType ----------

    @Test
    public void removeByTypeEqualRemovesOnlyThatType() throws Exception {
        fill();
        final String output = ConsoleCapture.capture(
                () -> manager.removeByType("==", "PEN"));

        assertEquals("Удалено объектов: 2", output.trim());
        final String content = ConsoleCapture.capture(manager::print);
        assertTrue(content.contains("Карандаш"));
        assertTrue(content.contains("Бумага"));
        assertTrue(!content.contains("Ручка"));
    }

    @Test
    public void removeByTypeNotEqualKeepsOnlyThatType() throws Exception {
        fill();
        final String output = ConsoleCapture.capture(
                () -> manager.removeByType("!=", "PEN"));

        assertEquals("Удалено объектов: 2", output.trim());
        assertEquals(2, ConsoleCapture.countItems(manager));
        assertTrue(!ConsoleCapture.capture(manager::print)
                .contains("Карандаш"));
    }

    @Test
    public void removeByTypeIsCaseInsensitive() throws Exception {
        fill();
        ConsoleCapture.capture(() -> manager.removeByType("==", "paper"));
        assertEquals(3, ConsoleCapture.countItems(manager));
    }

    @Test
    public void removeByUnknownTypeRemovesNothing() throws Exception {
        fill();
        final String output = ConsoleCapture.capture(
                () -> manager.removeByType("==", "BOOK"));
        assertEquals("Удалено объектов: 0", output.trim());
        assertEquals(4, ConsoleCapture.countItems(manager));
    }

    @Test
    public void removeByTypeOnEmptyManagerRemovesNothing() throws Exception {
        final String output = ConsoleCapture.capture(
                () -> manager.removeByType("!=", "PEN"));
        assertEquals("Удалено объектов: 0", output.trim());
    }

    @Test
    public void removeByTypeRejectsComparisonOperator() throws Exception {
        fill();
        final IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> manager.removeByType(">", "PEN"));
        assertTrue(e.getMessage().contains("== и !="));
        assertEquals("контейнер не должен измениться",
                4, ConsoleCapture.countItems(manager));
    }

    // ---------- removeByPrice: ошибки и граничные случаи ----------

    @Test
    public void removeByPriceRejectsUnknownOperator() throws Exception {
        fill();
        final IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> manager.removeByPrice("=>", 10));
        assertTrue(e.getMessage().contains("Неизвестный оператор"));
        assertEquals(4, ConsoleCapture.countItems(manager));
    }

    @Test
    public void removeByPriceEqualUsesTolerance() throws Exception {
        fill();
        ConsoleCapture.capture(() -> manager.removeByPrice("==", 20.0000001));
        assertEquals(3, ConsoleCapture.countItems(manager));
    }

    @Test
    public void removeByPriceOnEmptyManagerRemovesNothing() throws Exception {
        final String output = ConsoleCapture.capture(
                () -> manager.removeByPrice(">", 0));
        assertEquals("Удалено объектов: 0", output.trim());
    }
}
