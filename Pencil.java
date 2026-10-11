public class Pencil extends Stationery {
    private int leadHardness;
    private PencilColor color;

    public Pencil(int leadHardness, PencilColor color,
                  double price, String ownerPhone) {

        super(price, ownerPhone);

        if (leadHardness <= 0) {
            throw new IllegalArgumentException(
                    "Плотность грифеля должна быть положительной"
            );
        }

        if (color == null) {
            throw new IllegalArgumentException("Цвет не может быть пустым");
        }

        this.leadHardness = leadHardness;
        this.color = color;
    }

    public int getLeadHardness() {
        return leadHardness;
    }

    public PencilColor getColor() {
        return color;
    }

    @Override
    public String getType() {
        return "PENCIL";
    }

    @Override
    public String toString() {
        return String.format(
                "Карандаш: плотность грифеля=%d, цвет=%s, цена=%.2f, телефон владельца=%s",
                leadHardness,
                color,
                getPrice(),
                getOwnerPhone()
        );
    }
}