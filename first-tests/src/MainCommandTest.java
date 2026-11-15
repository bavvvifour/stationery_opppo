import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.Before;
import org.junit.Test;


public class MainCommandTest {

    private StationeryManager manager;

    @Before
    public void setUp() {
        manager = new StationeryManager();
    }

    private String run(final String line) throws Exception {
        return ConsoleCapture.capture(
                () -> MainTestSupport.process(line, manager));
    }

    @Test
    public void addPencil() throws Exception {
        final String output = run("ADD PENCIL 3 RED 25.50 +79991234567");
        final Pencil expected =
                new Pencil(3, PencilColor.RED, 25.5, "+79991234567");
        assertEquals("Добавлен: " + expected, output.trim());
        assertEquals(1, ConsoleCapture.countItems(manager));
    }

    @Test
    public void addPencilWithLowercaseColor() throws Exception {
        final String output = run("ADD PENCIL 2 green 1 +7");
        assertTrue(output.contains("цвет=GREEN"));
    }

    @Test
    public void addPen() throws Exception {
        final String output = run("ADD PEN GEL 0.5 35.50 +78881234567");
        final Pen expected = new Pen(PenType.GEL, 0.5, 35.5, "+78881234567");
        assertEquals("Добавлена: " + expected, output.trim());
    }

    @Test
    public void penTypeAliasesAreRecognized() throws Exception {
        final String[][] cases = {
            {"BALLPOINT", "BALLPOINT"},
            {"ball", "BALLPOINT"},
            {"шариковая", "BALLPOINT"},
            {"GEL", "GEL"},
            {"Гелевая", "GEL"},
        };
        for (String[] c : cases) {
            final String output = run("ADD PEN " + c[0] + " 0.7 10 +7");
            assertTrue("алиас " + c[0], output.contains("тип=" + c[1]));
        }
        assertEquals(cases.length, ConsoleCapture.countItems(manager));
    }

    @Test
    public void addPaper() throws Exception {
        final String output = run("ADD PAPER 80 210 297 120.00 +79991234567");
        final Paper expected = new Paper(80, 210, 297, 120, "+79991234567");
        assertEquals("Добавлена: " + expected, output.trim());
    }

    @Test
    public void commandsAreCaseInsensitiveAndToleratePadding()
            throws Exception {
        run("add   pencil 1 black 5   +7");
        run("Add Paper 80 210 297 1 +7");
        assertEquals(2, ConsoleCapture.countItems(manager));
    }

    @Test
    public void printCommandPrintsContainer() throws Exception {
        assertEquals("Контейнер пуст.", run("PRINT").trim());
        run("ADD PENCIL 3 RED 25.50 +7");
        final String output = run("print");
        assertTrue(output.startsWith("СОДЕРЖИМОЕ КОНТЕЙНЕРА"));
        assertTrue(output.contains("1. Карандаш"));
    }

    @Test
    public void removeByPriceCommand() throws Exception {
        run("ADD PENCIL 3 RED 25.50 +7");
        run("ADD PEN GEL 0.5 35.50 +7");
        run("ADD PAPER 80 210 297 120.00 +7");
        assertEquals("Удалено объектов: 2", run("REM PRICE > 30").trim());
        assertEquals(1, ConsoleCapture.countItems(manager));
    }

    @Test
    public void removeByTypeCommand() throws Exception {
        run("ADD PENCIL 3 RED 25.50 +7");
        run("ADD PEN GEL 0.5 35.50 +7");
        run("ADD PEN BALL 0.7 15 +7");
        assertEquals("Удалено объектов: 2", run("rem type == pen").trim());
        assertEquals(1, ConsoleCapture.countItems(manager));
    }

    @Test
    public void mainIsUtilityClassWithPrivateConstructor() throws Exception {
        final Constructor<Main> constructor = Main.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
