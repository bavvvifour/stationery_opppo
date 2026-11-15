import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;


final class ConsoleCapture {

    /** Действие, которое может выбросить исключение. */
    @FunctionalInterface
    interface Action {
        void run() throws Exception;
    }

    private ConsoleCapture() {
    }

    /**
     * Выполняет действие и возвращает всё, что оно напечатало.
     *
     * @param action действие
     * @return перехваченный вывод
     * @throws Exception если действие выбросило исключение
     */
    static String capture(final Action action) throws Exception {
        final PrintStream original = System.out;
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (PrintStream stream =
                     new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            System.setOut(stream);
            action.run();
        } finally {
            System.setOut(original);
        }
        return buffer.toString(StandardCharsets.UTF_8);
    }

    /**
     * Возвращает число элементов, напечатанных командой PRINT
     * (строки вида «1. ...», «2. ...»).
     *
     * @param manager менеджер
     * @return количество элементов в контейнере
     * @throws Exception при ошибке вывода
     */
    static int countItems(final StationeryManager manager) throws Exception {
        final String output = capture(manager::print);
        int count = 0;
        for (String line : output.split("\\R")) {
            if (line.matches("\\d+\\. .*")) {
                count++;
            }
        }
        return count;
    }
}
