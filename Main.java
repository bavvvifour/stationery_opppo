import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Запускает программу обработки команд для канцелярских принадлежностей.
 */
public final class Main {

    /** Количество аргументов команды ADD PENCIL. */
    private static final int PENCIL_ARGUMENT_COUNT = 6;

    /** Количество аргументов команды ADD PEN. */
    private static final int PEN_ARGUMENT_COUNT = 6;

    /** Количество аргументов команды ADD PAPER. */
    private static final int PAPER_ARGUMENT_COUNT = 7;

    /** Количество аргументов команды REM. */
    private static final int REMOVE_ARGUMENT_COUNT = 4;

    /** Минимальное количество аргументов команды ADD. */
    private static final int MIN_ADD_ARGUMENT_COUNT = 2;

    /** Имя входного файла. */
    private static final String INPUT_FILE = "input.txt";

    /** Индекс типа принадлежности в команде ADD. */
    private static final int TYPE_INDEX = 1;

    /** Индекс первого параметра принадлежности. */
    private static final int FIRST_PARAM_INDEX = 2;

    /** Индекс второго параметра принадлежности. */
    private static final int SECOND_PARAM_INDEX = 3;

    /** Индекс третьего параметра принадлежности. */
    private static final int THIRD_PARAM_INDEX = 4;

    /** Индекс четвёртого параметра принадлежности. */
    private static final int FOURTH_PARAM_INDEX = 5;

    /** Индекс пятого параметра принадлежности. */
    private static final int FIFTH_PARAM_INDEX = 6;

    /** Индекс поля условия в команде REM. */
    private static final int REM_FIELD_INDEX = 1;

    /** Индекс оператора в команде REM. */
    private static final int REM_OPERATOR_INDEX = 2;

    /** Индекс значения в команде REM. */
    private static final int REM_VALUE_INDEX = 3;

    private Main() {
    }

    /**
     * Запускает чтение команд из входного файла.
     *
     * @param args аргументы командной строки
     */
    public static void main(final String[] args) {
        final StationeryManager manager = new StationeryManager();

        try (BufferedReader reader = Files.newBufferedReader(
                Path.of(INPUT_FILE),
                StandardCharsets.UTF_8)) {

            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                try {
                    processCommand(line, manager);
                } catch (IllegalArgumentException exception) {
                    System.out.println(
                            "Ошибка в строке " + lineNumber + ": "
                                    + exception.getMessage());
                }
            }

        } catch (IOException exception) {
            System.out.println(
                    "Ошибка при чтении файла: " + exception.getMessage());
        }
    }

    /**
     * Обрабатывает одну команду.
     *
     * @param line строка команды
     * @param manager менеджер принадлежностей
     */
    private static void processCommand(
            final String line,
            final StationeryManager manager) {
        final String[] parts = line.split("\\s+");
        if (parts.length == 0) {
            return;
        }

        final String command = parts[0].toUpperCase();
        switch (command) {
            case "ADD":
                processAdd(parts, manager);
                break;
            case "REM":
                processRemove(parts, manager);
                break;
            case "PRINT":
                manager.print();
                break;
            default:
                throw new IllegalArgumentException(
                        "Неизвестная команда: " + command);
        }
    }

    /**
     * Обрабатывает команду добавления принадлежности.
     *
     * @param parts части команды ADD
     * @param manager менеджер принадлежностей
     */
    private static void processAdd(
            final String[] parts,
            final StationeryManager manager) {
        if (parts.length < MIN_ADD_ARGUMENT_COUNT) {
            throw new IllegalArgumentException(
                    "Не указан тип канцелярской принадлежности");
        }

        final String type = parts[TYPE_INDEX].toUpperCase();
        switch (type) {
            case "PENCIL":
                addPencil(parts, manager);
                break;
            case "PEN":
                addPen(parts, manager);
                break;
            case "PAPER":
                addPaper(parts, manager);
                break;
            default:
                throw new IllegalArgumentException(
                        "Неизвестный тип: " + type);
        }
    }

    /**
     * Создаёт и добавляет карандаш.
     *
     * @param parts части команды ADD PENCIL
     * @param manager менеджер принадлежностей
     */
    private static void addPencil(
            final String[] parts,
            final StationeryManager manager) {
        if (parts.length != PENCIL_ARGUMENT_COUNT) {
            throw new IllegalArgumentException(
                    "Формат: ADD PENCIL <твёрдость> <цвет> "
                            + "<цена> <телефон>");
        }

        final int hardness = Integer.parseInt(parts[FIRST_PARAM_INDEX]);
        final PencilColor color =
                PencilColor.valueOf(
                        parts[SECOND_PARAM_INDEX].toUpperCase());
        final double price =
                Double.parseDouble(parts[THIRD_PARAM_INDEX]);
        final String phone = parts[FOURTH_PARAM_INDEX];
        final Pencil pencil =
                new Pencil(hardness, color, price, phone);

        manager.add(pencil);
        System.out.println("Добавлен: " + pencil);
    }

    /**
     * Создаёт и добавляет ручку.
     *
     * @param parts части команды ADD PEN
     * @param manager менеджер принадлежностей
     */
    private static void addPen(
            final String[] parts,
            final StationeryManager manager) {
        if (parts.length != PEN_ARGUMENT_COUNT) {
            throw new IllegalArgumentException(
                    "Формат: ADD PEN <тип> <диаметр> "
                            + "<цена> <телефон>");
        }

        final PenType penType = parsePenType(parts[FIRST_PARAM_INDEX]);
        final double diameter =
                Double.parseDouble(parts[SECOND_PARAM_INDEX]);
        final double price =
                Double.parseDouble(parts[THIRD_PARAM_INDEX]);
        final String phone = parts[FOURTH_PARAM_INDEX];
        final Pen pen = new Pen(penType, diameter, price, phone);

        manager.add(pen);
        System.out.println("Добавлена: " + pen);
    }

    /**
     * Создаёт и добавляет бумагу.
     *
     * @param parts части команды ADD PAPER
     * @param manager менеджер принадлежностей
     */
    private static void addPaper(
            final String[] parts,
            final StationeryManager manager) {
        if (parts.length != PAPER_ARGUMENT_COUNT) {
            throw new IllegalArgumentException(
                    "Формат: ADD PAPER <плотность> <ширина> "
                            + "<высота> <цена> <телефон>");
        }

        final int density = Integer.parseInt(parts[FIRST_PARAM_INDEX]);
        final int width = Integer.parseInt(parts[SECOND_PARAM_INDEX]);
        final int height = Integer.parseInt(parts[THIRD_PARAM_INDEX]);
        final double price =
                Double.parseDouble(parts[FOURTH_PARAM_INDEX]);
        final String phone = parts[FIFTH_PARAM_INDEX];
        final Paper paper =
                new Paper(density, width, height, price, phone);

        manager.add(paper);
        System.out.println("Добавлена: " + paper);
    }

    /**
     * Обрабатывает команду удаления.
     *
     * @param parts части команды REM
     * @param manager менеджер принадлежностей
     */
    private static void processRemove(
            final String[] parts,
            final StationeryManager manager) {
        if (parts.length != REMOVE_ARGUMENT_COUNT) {
            throw new IllegalArgumentException(
                    "Формат: REM <поле> <оператор> <значение>");
        }

        final String field = parts[REM_FIELD_INDEX].toUpperCase();
        final String operator = parts[REM_OPERATOR_INDEX];
        switch (field) {
            case "PRICE":
                manager.removeByPrice(
                        operator,
                        Double.parseDouble(parts[REM_VALUE_INDEX]));
                break;
            case "TYPE":
                manager.removeByType(operator, parts[REM_VALUE_INDEX]);
                break;
            default:
                throw new IllegalArgumentException(
                        "Неизвестное поле условия: " + field);
        }
    }

    /**
     * Преобразует текстовое название в тип ручки.
     *
     * @param value название типа
     * @return соответствующий тип ручки
     */
    private static PenType parsePenType(final String value) {
        switch (value.toUpperCase()) {
            case "BALLPOINT":
            case "BALL":
            case "ШАРИКОВАЯ":
                return PenType.BALLPOINT;
            case "GEL":
            case "ГЕЛЕВАЯ":
                return PenType.GEL;
            default:
                throw new IllegalArgumentException(
                        "Неизвестный тип ручки: " + value);
        }
    }
}
