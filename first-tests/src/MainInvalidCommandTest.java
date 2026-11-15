import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class MainInvalidCommandTest {

    @Parameters(name = "{index}: \"{0}\"")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            // неизвестные команды и типы
            {"FOO", "Неизвестная команда"},
            {"DELETE PRICE > 1", "Неизвестная команда"},
            {"ADD", "Не указан тип"},
            {"ADD BOOK 1 2 3 +7", "Неизвестный тип"},
            // неверное число аргументов
            {"ADD PENCIL 3 RED 25.50", "Формат: ADD PENCIL"},
            {"ADD PENCIL 3 RED 25.50 +7 extra", "Формат: ADD PENCIL"},
            {"ADD PEN GEL 0.5 +7", "Формат: ADD PEN"},
            {"ADD PAPER 80 210 297 +7", "Формат: ADD PAPER"},
            {"REM PRICE >", "Формат: REM"},
            {"REM PRICE > 10 20", "Формат: REM"},
            // неверные значения
            {"ADD PENCIL abc RED 25.50 +7", "abc"},
            {"ADD PENCIL 2.5 RED 25.50 +7", "2.5"},
            {"ADD PENCIL 3 PURPLE 25.50 +7", "PURPLE"},
            {"ADD PENCIL 3 RED дорого +7", "дорого"},
            {"ADD PEN FOUNTAIN 0.5 10 +7", "Неизвестный тип ручки"},
            {"ADD PEN GEL thin 10 +7", "thin"},
            {"ADD PAPER 80 A4 297 10 +7", "A4"},
            {"ADD PAPER 80 210 297 NaN +7", "Цена"},
            // нарушение ограничений предметной области
            {"ADD PENCIL 0 RED 10 +7", "Твёрдость"},
            {"ADD PENCIL 3 RED -5 +7", "Цена"},
            {"ADD PEN GEL 0 10 +7", "Диаметр"},
            {"ADD PAPER 0 210 297 10 +7", "Плотность"},
            {"ADD PAPER 80 -210 297 10 +7", "Ширина и высота"},
            {"ADD PEN GEL 0.5 Infinity +7", "Цена"},
            // ошибки в REM
            {"REM COLOR == RED", "Неизвестное поле"},
            {"REM PRICE ~ 10", "Неизвестный оператор"},
            {"REM PRICE > ten", "ten"},
            {"REM TYPE > PEN", "== и !="},
        });
    }

    private final String command;
    private final String expectedMessagePart;

    public MainInvalidCommandTest(final String command,
                                  final String expectedMessagePart) {
        this.command = command;
        this.expectedMessagePart = expectedMessagePart;
    }

    @Test
    public void invalidCommandThrowsAndKeepsContainerUnchanged()
            throws Exception {
        final StationeryManager manager = new StationeryManager();
        manager.add(new Pencil(2, PencilColor.RED, 10, "+7"));

        final IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> ConsoleCapture.capture(
                        () -> MainTestSupport.process(command, manager)));

        assertTrue("сообщение «" + e.getMessage() + "» должно содержать «"
                        + expectedMessagePart + "»",
                e.getMessage().contains(expectedMessagePart));
        assertEquals(1, ConsoleCapture.countItems(manager));
    }
}
