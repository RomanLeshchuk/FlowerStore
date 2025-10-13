package flower.store;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlowerPack {
    private Flower flower;
    private int quantity;

    public FlowerPack(Flower flower, int quantity) {
        this.flower = new Flower(flower);
        this.quantity = quantity;
    }

    public double getSearchScore(FlowerSpec searchSpec) {
        return flower.getSearchScore(searchSpec);
    }

    public double getPrice() {
        return flower.getPrice() * quantity;
    }
}
