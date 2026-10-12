/**
 * Базовый класс канцелярской принадлежности.
 */
public abstract class Stationery {
    /** Цена принадлежности. */
    private final double price;

    /** Телефон владельца. */
    private final String ownerPhone;

    /**
     * Создаёт канцелярскую принадлежность.
     *
     * @param priceValue цена
     * @param ownerPhoneValue телефон владельца
     */
    protected Stationery(
            final double priceValue,
            final String ownerPhoneValue) {
        if (!Double.isFinite(priceValue) || priceValue < 0) {
            throw new IllegalArgumentException(
                    "Цена должна быть конечным неотрицательным числом");
        }
        if (ownerPhoneValue == null || ownerPhoneValue.isEmpty()) {
            throw new IllegalArgumentException(
                    "Номер телефона не может быть пустым");
        }

        price = priceValue;
        ownerPhone = ownerPhoneValue;
    }

    /**
     * Возвращает цену принадлежности.
     *
     * @return цена
     */
    public final double getPrice() {
        return price;
    }

    /**
     * Возвращает телефон владельца.
     *
     * @return телефон владельца
     */
    public final String getOwnerPhone() {
        return ownerPhone;
    }

    /**
     * Возвращает тип принадлежности.
     *
     * @return строковый код типа
     */
    public abstract String getType();

    /**
     * Возвращает текстовое представление принадлежности.
     *
     * @return описание принадлежности
     */
    @Override
    public abstract String toString();
}
