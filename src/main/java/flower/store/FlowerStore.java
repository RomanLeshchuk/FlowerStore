package flower.store;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FlowerStore {
    private List<FlowerBucket> flowerBuckets;

    public FlowerStore() {
        this.flowerBuckets = new ArrayList<>();
    }

    public FlowerStore(List<FlowerBucket> flowerBuckets) {
        if (flowerBuckets == null) {
            this.flowerBuckets = new ArrayList<>();
        } else {
            this.flowerBuckets = flowerBuckets;
        }
    }

    public void addFlowerBucket(FlowerBucket flowerBucket) {
        flowerBuckets.add(flowerBucket);
    }

    public List<FlowerBucket> search(FlowerSpec searchSpec) {
        List<FlowerBucket> sorted = new ArrayList<>(flowerBuckets);

        sorted.sort(
            Comparator.comparingDouble(
                (FlowerBucket b) -> b.getSearchScore(searchSpec)
            ).reversed()
        );

        return sorted;
    }

    public List<FlowerBucket> search(FlowerSpec searchSpec, int count) {
        List<FlowerBucket> sorted = search(searchSpec);

        return sorted.subList(0, Math.min(count, sorted.size()));
    }
}
