package flower.store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class Flower {
    private FlowerSpec spec;

    @Getter
    @Setter
    private double price;

    public Flower(Flower flower) {
        spec = new FlowerSpec(flower.spec);
        price = flower.price;
    }

    public double getSearchScore(FlowerSpec searchSpec) {
        return new FlowerSpecComparator().getRelevanceScore(spec, searchSpec);
    }

    public FlowerType getFlowerType() {
        return spec.getFlowerType();
    }

    public String getColor() {
        return spec.getColor();
    }

    public double getSepalLength() {
        return spec.getSepalLength();
    }

    public void setFlowerType(FlowerType flowerType) {
        spec.setFlowerType(flowerType);
    }

    public void setColor(FlowerColor color) {
        spec.setColor(color);
    }

    public void setSepalLength(double sepalLength) {
        spec.setSepalLength(sepalLength);
    }
}
