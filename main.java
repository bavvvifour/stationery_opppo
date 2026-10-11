import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class main {

    public static void main(String[] args) {

        String fileName = "input.txt";

        StationeryManager manager = new StationeryManager();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(fileName))) {

            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {

                lineNumber++;

                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                if (line.startsWith("#")) {
                    continue;
                }

                try {
                    processCommand(line, manager);
                } catch (Exception e) {
                    System.out.println(
                            "Ошибка в строке " + lineNumber + ": "
                                    + e.getMessage()
                    );
                }
            }

        } catch (IOException e) {
            System.out.println(
                    "Ошибка при чтении файла: " + e.getMessage()
            );
        }
    }

    private static void processCommand(
            String line,
            StationeryManager manager) {

        String[] parts = line.split("\\s+");

        if (parts.length == 0) {
            return;
        }

        String command = parts[0].toUpperCase();

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
                        "Неизвестная команда: " + command
                );
        }
    }

    private static void processAdd(
            String[] parts,
            StationeryManager manager) {

        if (parts.length < 2) {
            throw new IllegalArgumentException(
                    "Не указан тип канцелярской принадлежности"
            );
        }

        String type = parts[1].toUpperCase();

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
                        "Неизвестный тип: " + type
                );
        }
    }

    private static void addPencil(
            String[] parts,
            StationeryManager manager) {

        /*
         * ADD PENCIL
         * плотность
         * цвет
         * цена
         * телефон
         *
         * Пример:
         * ADD PENCIL 3 RED 25.50 +79991234567
         */

        if (parts.length != 6) {
            throw new IllegalArgumentException(
                    "Формат: ADD PENCIL <плотность> <цвет> <цена> <телефон>"
            );
        }

        int hardness = Integer.parseInt(parts[2]);

        PencilColor color =
                PencilColor.valueOf(parts[3].toUpperCase());

        double price =
                Double.parseDouble(parts[4]);

        String phone = parts[5];

        Pencil pencil =
                new Pencil(
                        hardness,
                        color,
                        price,
                        phone
                );

        manager.add(pencil);

        System.out.println("Добавлен: " + pencil);
    }

    private static void addPen(
            String[] parts,
            StationeryManager manager) {

        /*
         * ADD PEN
         * тип
         * диаметр
         * цена
         * телефон
         *
         * Пример:
         * ADD PEN BALLPOINT 0.7 15.90 +79991234567
         */

        if (parts.length != 6) {
            throw new IllegalArgumentException(
                    "Формат: ADD PEN <тип> <диаметр> <цена> <телефон>"
            );
        }

        PenType penType =
                parsePenType(parts[2]);

        double diameter =
                Double.parseDouble(parts[3]);

        double price =
                Double.parseDouble(parts[4]);

        String phone = parts[5];

        Pen pen =
                new Pen(
                        penType,
                        diameter,
                        price,
                        phone
                );

        manager.add(pen);

        System.out.println("Добавлена: " + pen);
    }

    private static void addPaper(
            String[] parts,
            StationeryManager manager) {

        /*
         * ADD PAPER
         * плотность
         * ширина
         * высота
         * цена
         * телефон
         *
         * Пример:
         * ADD PAPER 80 210 297 120.00 +79991234567
         */

        if (parts.length != 7) {
            throw new IllegalArgumentException(
                    "Формат: ADD PAPER <плотность> <ширина> <высота> <цена> <телефон>"
            );
        }

        int density =
                Integer.parseInt(parts[2]);

        int width =
                Integer.parseInt(parts[3]);

        int height =
                Integer.parseInt(parts[4]);

        double price =
                Double.parseDouble(parts[5]);

        String phone = parts[6];

        Paper paper =
                new Paper(
                        density,
                        width,
                        height,
                        price,
                        phone
                );

        manager.add(paper);

        System.out.println("Добавлена: " + paper);
    }

    private static void processRemove(
            String[] parts,
            StationeryManager manager) {

        /*
         * REM PRICE > 20
         * REM TYPE == PENCIL
         */

        if (parts.length != 4) {
            throw new IllegalArgumentException(
                    "Формат: REM <поле> <оператор> <значение>"
            );
        }

        String field = parts[1].toUpperCase();
        String operator = parts[2];

        switch (field) {

            case "PRICE":

                double price =
                        Double.parseDouble(parts[3]);

                manager.removeByPrice(
                        operator,
                        price
                );

                break;

            case "TYPE":

                manager.removeByType(
                        operator,
                        parts[3]
                );

                break;

            default:
                throw new IllegalArgumentException(
                        "Неизвестное поле условия: " + field
                );
        }
    }

    private static PenType parsePenType(String value) {

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
                        "Неизвестный тип ручки: " + value
                );
        }
    }
}