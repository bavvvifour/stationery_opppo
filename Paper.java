/**
 * Представляет лист бумаги.
 */
public final class Paper extends Stationery {
    /** Плотность бумаги. */
    private final int density;

    /** Ширина листа. */
    private final int width;

    /** Высота листа. */
    private final int height;

    /**
     * Создаёт лист бумаги.
     *
     * @param densityValue плотность бумаги
     * @param widthValue ширина листа
     * @param heightValue высота листа
     * @param priceValue цена
     * @param ownerPhoneValue телефон владельца
     */
    public Paper(
            final int densityValue,
            final int widthValue,
            final int heightValue,
            final double priceValue,
            final String ownerPhoneValue) {
        super(priceValue, ownerPhoneValue);

        if (densityValue <= 0) {
            throw new IllegalArgumentException(
                    "Плотность бумаги должна быть положительной");
        }
        if (widthValue <= 0 || heightValue <= 0) {
            throw new IllegalArgumentException(
                    "Ширина и высота должны быть положительными");
        }

        density = densityValue;
        width = widthValue;
        height = heightValue;
    }

    /**
     * Возвращает плотность бумаги.
     *
     * @return плотность бумаги
     */
    public int getDensity() {
        return density;
    }

    /**
     * Возвращает ширину листа.
     *
     * @return ширина листа
     */
    public int getWidth() {
        return width;
    }

    /**
     * Возвращает высоту листа.
     *
     * @return высота листа
     */
    public int getHeight() {
        return height;
    }

    /** {@inheritDoc} */
    @Override
    public String getType() {
        return "PAPER";
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return String.format(
                "Бумага: плотность=%d, ширина=%d, высота=%d, "
                        + "цена=%.2f, телефон владельца=%s",
                density, width, height, getPrice(), getOwnerPhone());
    }
}
