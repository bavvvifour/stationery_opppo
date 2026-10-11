public abstract class Stationery {
    private double price;
    private String ownerPhone;

    public Stationery(double price, String ownerPhone) {
        if (price < 0) {
            throw new IllegalArgumentException("Цена не может быть отрицательной");
        }

        if (ownerPhone == null || ownerPhone.isEmpty()) {
            throw new IllegalArgumentException("Номер телефона не может быть пустым");
        }

        this.price = price;
        this.ownerPhone = ownerPhone;
    }

    public double getPrice() {
        return price;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public abstract String getType();

    @Override
    public abstract String toString();
}