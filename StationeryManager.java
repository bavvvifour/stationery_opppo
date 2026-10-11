import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StationeryManager {

    private final List<Stationery> container;

    public StationeryManager() {
        container = new ArrayList<>();
    }

    public void add(Stationery stationery) {
        container.add(stationery);
    }

    public void print() {
        if (container.isEmpty()) {
            System.out.println("Контейнер пуст.");
            return;
        }

        System.out.println("СОДЕРЖИМОЕ КОНТЕЙНЕРА");

        for (int i = 0; i < container.size(); i++) {
            System.out.println((i + 1) + ". " + container.get(i));
        }

        System.out.println("!!!!!!!!");
    }

    public void removeByPrice(String operator, double value) {
        Iterator<Stationery> iterator = container.iterator();

        int removed = 0;

        while (iterator.hasNext()) {
            Stationery stationery = iterator.next();

            if (compare(stationery.getPrice(), operator, value)) {
                iterator.remove();
                removed++;
            }
        }

        System.out.println(
                "Удалено объектов: " + removed
        );
    }

    public void removeByType(String operator, String type) {
        Iterator<Stationery> iterator = container.iterator();

        int removed = 0;

        while (iterator.hasNext()) {
            Stationery stationery = iterator.next();

            boolean condition;

            if (operator.equals("==")) {
                condition = stationery.getType().equalsIgnoreCase(type);
            } else if (operator.equals("!=")) {
                condition = !stationery.getType().equalsIgnoreCase(type);
            } else {
                throw new IllegalArgumentException(
                        "Для TYPE допустимы только == и !="
                );
            }

            if (condition) {
                iterator.remove();
                removed++;
            }
        }

        System.out.println(
                "Удалено объектов: " + removed
        );
    }

    private boolean compare(double actual,
                            String operator,
                            double expected) {

        switch (operator) {
            case ">":
                return actual > expected;

            case "<":
                return actual < expected;

            case ">=":
                return actual >= expected;

            case "<=":
                return actual <= expected;

            case "==":
                return Math.abs(actual - expected) < 0.000001;

            case "!=":
                return Math.abs(actual - expected) >= 0.000001;

            default:
                throw new IllegalArgumentException(
                        "Неизвестный оператор: " + operator
                );
        }
    }
}