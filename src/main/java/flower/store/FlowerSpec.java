package flower.store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class FlowerSpec {
    private FlowerType flowerType;
    private FlowerColor color;
    private double sepalLength;

    public FlowerSpec(FlowerSpec flowerSpec) {
        flowerType = flowerSpec.flowerType;
        color = flowerSpec.color;
        sepalLength = flowerSpec.sepalLength;
    }

    public String getColor() {
        return color.toString();
    }
}
