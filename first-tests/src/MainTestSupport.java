import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

final class MainTestSupport {

    private MainTestSupport() {
    }

    /**
     * Выполняет одну команду. Исключение из команды пробрасывается как есть.
     *
     * @param line строка команды
     * @param manager менеджер
     * @throws Exception исключение, выброшенное командой
     */
    static void process(final String line, final StationeryManager manager)
            throws Exception {
        final Method method = Main.class.getDeclaredMethod(
                "processCommand", String.class, StationeryManager.class);
        method.setAccessible(true);
        try {
            method.invoke(null, line, manager);
        } catch (InvocationTargetException e) {
            final Throwable cause = e.getCause();
            if (cause instanceof Exception) {
                throw (Exception) cause;
            }
            throw (Error) cause;
        }
    }
}
