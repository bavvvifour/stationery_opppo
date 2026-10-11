public class Pen extends Stationery {
    private PenType penType;
    private double rodDiameter;

    public Pen(PenType penType, double rodDiameter,
               double price, String ownerPhone) {

        super(price, ownerPhone);

        if (penType == null) {
            throw new IllegalArgumentException("Тип ручки не может быть пустым");
        }

        if (rodDiameter <= 0) {
            throw new IllegalArgumentException(
                    "Диаметр стержня должен быть положительным"
            );
        }

        this.penType = penType;
        this.rodDiameter = rodDiameter;
    }

    public PenType getPenType() {
        return penType;
    }

    public double getRodDiameter() {
        return rodDiameter;
    }

    @Override
    public String getType() {
        return "PEN";
    }

    @Override
    public String toString() {
        return String.format(
                "Ручка: тип=%s, диаметр стержня=%.2f, цена=%.2f, телефон владельца=%s",
                penType,
                rodDiameter,
                getPrice(),
                getOwnerPhone()
        );
    }
}