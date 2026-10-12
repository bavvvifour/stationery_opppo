/**
 * Представляет карандаш.
 */
public final class Pencil extends Stationery {
    /** Твёрдость грифеля. */
    private final int leadHardness;

    /** Цвет карандаша. */
    private final PencilColor color;

    /**
     * Создаёт карандаш.
     *
     * @param leadHardnessValue твёрдость грифеля
     * @param colorValue цвет карандаша
     * @param priceValue цена
     * @param ownerPhoneValue телефон владельца
     */
    public Pencil(
            final int leadHardnessValue,
            final PencilColor colorValue,
            final double priceValue,
            final String ownerPhoneValue) {
        super(priceValue, ownerPhoneValue);

        if (leadHardnessValue <= 0) {
            throw new IllegalArgumentException(
                    "Твёрдость грифеля должна быть положительной");
        }
        if (colorValue == null) {
            throw new IllegalArgumentException(
                    "Цвет не может быть пустым");
        }

        leadHardness = leadHardnessValue;
        color = colorValue;
    }

    /**
     * Возвращает твёрдость грифеля.
     *
     * @return твёрдость грифеля
     */
    public int getLeadHardness() {
        return leadHardness;
    }

    /**
     * Возвращает цвет карандаша.
     *
     * @return цвет карандаша
     */
    public PencilColor getColor() {
        return color;
    }

    /** {@inheritDoc} */
    @Override
    public String getType() {
        return "PENCIL";
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return String.format(
                "Карандаш: твёрдость грифеля=%d, цвет=%s, цена=%.2f, "
                        + "телефон владельца=%s",
                leadHardness, color, getPrice(), getOwnerPhone());
    }
}
