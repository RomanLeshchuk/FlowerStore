package flower.store;

public class FlowerSpecComparator {
    private static final double SEPAL_LENGTH_WEIGHT = 0.25;
    private static final double SEPAL_LENGTH_LINEAR_DIFF = 10.0;
    private static final double COLOR_WEIGHT = 0.35;
    private static final double TYPE_WEIGHT = 0.4;

    public double getRelevanceScore(FlowerSpec flowerSpec, FlowerSpec searchSpec) {
        double score = 0.0;

        double lengthDiff = Math.abs(flowerSpec.getSepalLength() - searchSpec.getSepalLength());

        double typeScore = flowerSpec.getFlowerType() == searchSpec.getFlowerType() ? 1.0 : 0.0;
        score += typeScore * TYPE_WEIGHT;

        double colorScore = flowerSpec.getColor() == searchSpec.getColor() ? 1.0 : 0.0;
        score += colorScore * COLOR_WEIGHT;

        double lengthScore = 1.0 - Math.min(lengthDiff / SEPAL_LENGTH_LINEAR_DIFF, 1.0);
        score += lengthScore * SEPAL_LENGTH_WEIGHT;

        return score;
    }
}
