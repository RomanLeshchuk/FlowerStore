package flower.store;

public class Main {
    public static void main(String[] args) {
        Flower flower = new Flower(new FlowerSpec(FlowerType.ROSE, FlowerColor.RED, 100.0), 100.0);
        FlowerPack bucket = new FlowerPack(flower, 12);
        System.out.println(bucket.getPrice());
        flower.setPrice(110);
        System.out.println(bucket.getPrice());
    }
}
