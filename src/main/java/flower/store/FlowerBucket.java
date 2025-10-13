package flower.store;

import java.util.ArrayList;
import java.util.List;

public class FlowerBucket {
    private List<FlowerPack> flowerPacks;

    public FlowerBucket() {
        this.flowerPacks = new ArrayList<>();
    }

    public FlowerBucket(List<FlowerPack> flowerPacks) {
        if (flowerPacks == null) {
            this.flowerPacks = new ArrayList<>();
        } else {
            this.flowerPacks = flowerPacks;
        }
    }

    public double getSearchScore(FlowerSpec searchSpec) {
        double totalScore = 0.0;
        for (FlowerPack flowerPack : flowerPacks) {
            // here I do not multiply search score by quantiny inside FlowerPack getSearchScore method,
            // because FlowerPack search score itself does not depend on quantity,
            // but for score in bucket we need count of flowers in each pack with their scores,
            // Example: if we are searching for tulip, Bucket(FlowerPack(5 tulips), FlowerPack(1 rose))
            // must be better then Bucket(FlowerPack(1 tulip), FlowerPack(1 rose)),
            // but FlowerPack(5 tulips) and FlowerPack(1 tulip) are equally relevant
            totalScore += flowerPack.getSearchScore(searchSpec)
                        * flowerPack.getQuantity();
        }
        return totalScore / getQuantity();
    }

    public void addFlowerPack(FlowerPack flowerPack) {
        flowerPacks.add(flowerPack);
    }
    
    public double getPrice() {
        double totalPrice = 0.0;
        for (FlowerPack flowerPack : flowerPacks) {
            totalPrice += flowerPack.getPrice();
        }
        return totalPrice;
    }

    public int getQuantity() {
        int totalQuantiny = 0;
        for (FlowerPack flowerPack : flowerPacks) {
            totalQuantiny += flowerPack.getQuantity();
        }
        return totalQuantiny;
    }
}
