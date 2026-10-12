/**
 * Представляет ручку.
 */
public final class Pen extends Stationery {
    /** Тип ручки. */
    private final PenType penType;

    /** Диаметр стержня. */
    private final double rodDiameter;

    /**
     * Создаёт ручку.
     *
     * @param penTypeValue тип ручки
     * @param rodDiameterValue диаметр стержня
     * @param priceValue цена
     * @param ownerPhoneValue телефон владельца
     */
    public Pen(
            final PenType penTypeValue,
            final double rodDiameterValue,
            final double priceValue,
            final String ownerPhoneValue) {
        super(priceValue, ownerPhoneValue);

        if (penTypeValue == null) {
            throw new IllegalArgumentException(
                    "Тип ручки не может быть пустым");
        }
        if (rodDiameterValue <= 0) {
            throw new IllegalArgumentException(
                    "Диаметр стержня должен быть положительным");
        }

        penType = penTypeValue;
        rodDiameter = rodDiameterValue;
    }

    /**
     * Возвращает тип ручки.
     *
     * @return тип ручки
     */
    public PenType getPenType() {
        return penType;
    }

    /**
     * Возвращает диаметр стержня.
     *
     * @return диаметр стержня
     */
    public double getRodDiameter() {
        return rodDiameter;
    }

    /** {@inheritDoc} */
    @Override
    public String getType() {
        return "PEN";
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return String.format(
                "Ручка: тип=%s, диаметр стержня=%.2f, цена=%.2f, "
                        + "телефон владельца=%s",
                penType, rodDiameter, getPrice(), getOwnerPhone());
    }
}
