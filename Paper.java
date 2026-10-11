public class Paper extends Stationery {
    private int density;
    private int width;
    private int height;

    public Paper(int density, int width, int height,
                 double price, String ownerPhone) {

        super(price, ownerPhone);

        if (density <= 0) {
            throw new IllegalArgumentException(
                    "Плотность бумаги должна быть положительной"
            );
        }

        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException(
                    "Ширина и высота должны быть положительными"
            );
        }

        this.density = density;
        this.width = width;
        this.height = height;
    }

    public int getDensity() {
        return density;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String getType() {
        return "PAPER";
    }

    @Override
    public String toString() {
        return String.format(
                "Бумага: плотность=%d, ширина=%d, высота=%d, цена=%.2f, телефон владельца=%s",
                density,
                width,
                height,
                getPrice(),
                getOwnerPhone()
        );
    }
}