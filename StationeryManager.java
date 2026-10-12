import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Управляет коллекцией канцелярских принадлежностей.
 */
public final class StationeryManager {
    /** Допуск при сравнении цен. */
    private static final double EPSILON = 0.000001;

    /** Хранилище принадлежностей. */
    private final List<Stationery> container;

    /** Создаёт пустой менеджер. */
    public StationeryManager() {
        container = new ArrayList<>();
    }

    /**
     * Добавляет принадлежность в коллекцию.
     *
     * @param stationeryValue принадлежность для добавления
     */
    public void add(final Stationery stationeryValue) {
        if (stationeryValue == null) {
            throw new IllegalArgumentException(
                    "Нельзя добавить пустую принадлежность");
        }
        container.add(stationeryValue);
    }

    /** Выводит содержимое коллекции. */
    public void print() {
        if (container.isEmpty()) {
            System.out.println("Контейнер пуст.");
            return;
        }

        System.out.println("СОДЕРЖИМОЕ КОНТЕЙНЕРА");
        for (int index = 0; index < container.size(); index++) {
            System.out.println(
                    (index + 1) + ". " + container.get(index));
        }
        System.out.println("!!!!!!!!");
    }

    /**
     * Удаляет принадлежности по условию для цены.
     *
     * @param operatorValue оператор сравнения
     * @param priceValue цена для сравнения
     */
    public void removeByPrice(
            final String operatorValue,
            final double priceValue) {
        final Iterator<Stationery> iterator = container.iterator();
        int removed = 0;

        while (iterator.hasNext()) {
            final Stationery stationery = iterator.next();
            if (compare(
                    stationery.getPrice(), operatorValue, priceValue)) {
                iterator.remove();
                removed++;
            }
        }

        System.out.println("Удалено объектов: " + removed);
    }

    /**
     * Удаляет принадлежности по условию для типа.
     *
     * @param operatorValue оператор == или !=
     * @param typeValue тип принадлежности
     */
    public void removeByType(
            final String operatorValue,
            final String typeValue) {
        if (!"==".equals(operatorValue) && !"!=".equals(operatorValue)) {
            throw new IllegalArgumentException(
                    "Для TYPE допустимы только == и !=");
        }

        final Iterator<Stationery> iterator = container.iterator();
        int removed = 0;

        while (iterator.hasNext()) {
            final Stationery stationery = iterator.next();
            final boolean matches = stationery.getType()
                    .equalsIgnoreCase(typeValue);
            final boolean condition = "==".equals(operatorValue)
                    ? matches : !matches;

            if (condition) {
                iterator.remove();
                removed++;
            }
        }

        System.out.println("Удалено объектов: " + removed);
    }

    /**
     * Сравнивает два значения цены заданным оператором.
     *
     * @param actual фактическая цена
     * @param operatorValue оператор сравнения
     * @param expected ожидаемая цена
     * @return результат сравнения
     */
    private boolean compare(
            final double actual,
            final String operatorValue,
            final double expected) {
        switch (operatorValue) {
            case ">":
                return actual > expected;
            case "<":
                return actual < expected;
            case ">=":
                return actual >= expected;
            case "<=":
                return actual <= expected;
            case "==":
                return Math.abs(actual - expected) < EPSILON;
            case "!=":
                return Math.abs(actual - expected) >= EPSILON;
            default:
                throw new IllegalArgumentException(
                        "Неизвестный оператор: " + operatorValue);
        }
    }
}
