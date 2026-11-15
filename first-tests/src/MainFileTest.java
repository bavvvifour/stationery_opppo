import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class MainFileTest {

    private Path input;
    private Path backup;

    @Before
    public void setUp() throws Exception {
        input = Path.of(System.getProperty("user.dir"), "input.txt");
        backup = Path.of(System.getProperty("user.dir"),
                "input.txt.test-backup");
        if (Files.exists(input)) {
            Files.move(input, backup, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(input);
        if (Files.exists(backup)) {
            Files.move(backup, input, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private void writeInput(final String... lines) throws Exception {
        Files.write(input, Arrays.asList(lines), StandardCharsets.UTF_8);
    }

    private List<String> runMain() throws Exception {
        return Arrays.asList(ConsoleCapture
                .capture(() -> Main.main(new String[0])).split("\\R"));
    }

    @Test
    public void processesValidFile() throws Exception {
        writeInput(
                "ADD PENCIL 3 RED 25.50 +79991234567",
                "ADD PEN GEL 0.5 35.50 +78881234567",
                "ADD PAPER 80 210 297 120.00 +79991234567",
                "REM PRICE > 30",
                "PRINT");

        final List<String> out = runMain();

        assertTrue(out.get(0).startsWith("Добавлен: Карандаш"));
        assertTrue(out.get(1).startsWith("Добавлена: Ручка"));
        assertTrue(out.get(2).startsWith("Добавлена: Бумага"));
        assertEquals("Удалено объектов: 2", out.get(3));
        assertEquals("СОДЕРЖИМОЕ КОНТЕЙНЕРА", out.get(4));
        assertTrue(out.get(5).startsWith("1. Карандаш"));
        assertEquals("!!!!!!!!", out.get(6));
        assertEquals(7, out.size());
    }

    @Test
    public void skipsBlankLinesAndComments() throws Exception {
        writeInput(
                "# комментарий",
                "",
                "     ",
                "   # комментарий с отступом",
                "PRINT");

        assertEquals(List.of("Контейнер пуст."), runMain());
    }

    @Test
    public void reportsErrorsWithLineNumberAndContinues() throws Exception {
        writeInput(
                "ADD PENCIL 3 RED 25.50 +7",   // 1
                "# комментарий",               // 2
                "ADD PENCIL x RED 25.50 +7",   // 3 — ошибка числа
                "",                            // 4
                "JUMP",                        // 5 — неизвестная команда
                "ADD PEN GEL 0 10 +7",         // 6 — диаметр 0
                "PRINT");                      // 7

        final List<String> out = runMain();

        assertTrue(out.get(0).startsWith("Добавлен:"));
        assertTrue(out.get(1).startsWith("Ошибка в строке 3: "));
        assertEquals("Ошибка в строке 5: Неизвестная команда: JUMP",
                out.get(2));
        assertTrue(out.get(3).startsWith("Ошибка в строке 6: Диаметр"));
        assertEquals("СОДЕРЖИМОЕ КОНТЕЙНЕРА", out.get(4));
        assertEquals("после ошибок в контейнере остаётся 1 предмет",
                "!!!!!!!!", out.get(6));
    }

    @Test
    public void emptyFileProducesNoOutput() throws Exception {
        writeInput();
        assertEquals(List.of(""), runMain());
    }

    @Test
    public void missingFileIsReported() throws Exception {
        assertFalse(Files.exists(input));
        final List<String> out = runMain();
        assertEquals(1, out.size());
        assertTrue(out.get(0).startsWith("Ошибка при чтении файла:"));
    }
}
