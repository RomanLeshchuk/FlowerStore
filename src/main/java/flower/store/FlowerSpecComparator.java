package flower.store;

public class FlowerSpecComparator {
    private static final double SEPAL_LENGTH_WEIGHT = 0.25;
    private static final double SEPAL_LENGTH_LINEAR_DIFF = 10.0;
    private static final double COLOR_WEIGHT = 0.35;
    private static final double TYPE_WEIGHT = 0.4;

    public double getRelevanceScore(FlowerSpec flowerSpec, FlowerSpec searchSpec) {
        double score = 0.0;

        if (flowerSpec.getFlowerType() == searchSpec.getFlowerType()) {
            score += TYPE_WEIGHT;
        }

        if (flowerSpec.getColor() == searchSpec.getColor()) {
            score += COLOR_WEIGHT;
        }

        double lengthDiff = Math.abs(flowerSpec.getSepalLength() - searchSpec.getSepalLength());
        double lengthScore = 1.0 - Math.min(lengthDiff / SEPAL_LENGTH_LINEAR_DIFF, 1.0);
        score += lengthScore * SEPAL_LENGTH_WEIGHT;

        return score;
    }
}
