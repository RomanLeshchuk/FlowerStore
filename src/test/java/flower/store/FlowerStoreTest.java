package flower.store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class FlowerStoreTest {
    private Flower roseRed;
    private Flower tulipBlue;
    private Flower chamomileYellow;

    private FlowerPack rosePack;
    private FlowerPack tulipPack;
    private FlowerPack chamomilePack;

    private FlowerBucket bucket1;
    private FlowerBucket bucket2;
    private FlowerBucket bucket3;

    private FlowerStore store;

    @BeforeEach
    public void setUp() {
        roseRed = new Flower(new FlowerSpec(FlowerType.ROSE, FlowerColor.RED, 15.0), 50.0);
        tulipBlue = new Flower(new FlowerSpec(FlowerType.TULIP, FlowerColor.BLUE, 10.0), 30.0);
        chamomileYellow = new Flower(new FlowerSpec(FlowerType.CHAMOMILE, FlowerColor.YELLOW, 12.0), 25.0);

        rosePack = new FlowerPack(roseRed, 50);
        tulipPack = new FlowerPack(tulipBlue, 10);
        chamomilePack = new FlowerPack(chamomileYellow, 20);

        bucket1 = new FlowerBucket();
        bucket1.addFlowerPack(rosePack);
        bucket1.addFlowerPack(tulipPack);

        bucket2 = new FlowerBucket();
        bucket2.addFlowerPack(chamomilePack);

        bucket3 = new FlowerBucket();
        bucket3.addFlowerPack(rosePack);
        bucket3.addFlowerPack(chamomilePack);

        store = new FlowerStore();
        store.addFlowerBucket(bucket1);
        store.addFlowerBucket(bucket2);
        store.addFlowerBucket(bucket3);
    }

    @Test
    public void testFlowerCopyConstructorCreatesIndependentObject() {
        Flower original = new Flower(new FlowerSpec(FlowerType.ROSE, FlowerColor.RED, 15.0), 50.0);
        Flower copy = new Flower(original);

        Assertions.assertEquals(original.getFlowerType(), copy.getFlowerType());
        Assertions.assertEquals(original.getColor(), copy.getColor());
        Assertions.assertEquals(original.getSepalLength(), copy.getSepalLength());
        Assertions.assertEquals(original.getPrice(), copy.getPrice());

        copy.setPrice(99.9);
        copy.setSepalLength(5.0);
        copy.setColor(FlowerColor.BLUE);

        Assertions.assertNotEquals(original.getPrice(), copy.getPrice(), 
                "Price change should not affect original");
        Assertions.assertNotEquals(original.getSepalLength(), copy.getSepalLength(), 
                "Sepal length change should not affect original");
        Assertions.assertNotEquals(original.getColor(), copy.getColor(), 
                "Color change should not affect original");
    }

    @Test
    public void testFlowerPackCopyContainsIndependentFlower() {
        Flower originalFlower = new Flower(new FlowerSpec(FlowerType.TULIP, FlowerColor.BLUE, 10.0), 30.0);
        FlowerPack originalPack = new FlowerPack(originalFlower, 10);
        FlowerPack copyPack = new FlowerPack(originalPack.getFlower(), originalPack.getQuantity());

        Assertions.assertEquals(originalPack.getFlower().getFlowerType(), copyPack.getFlower().getFlowerType());
        Assertions.assertEquals(originalPack.getQuantity(), copyPack.getQuantity());

        copyPack.getFlower().setColor(FlowerColor.RED);
        copyPack.getFlower().setPrice(200.0);

        Assertions.assertNotEquals(originalPack.getFlower().getColor(), copyPack.getFlower().getColor(),
                "Changing flower color in copy should not affect original");
        Assertions.assertNotEquals(originalPack.getFlower().getPrice(), copyPack.getFlower().getPrice(),
                "Changing flower price in copy should not affect original");
    }

    @Test
    public void testFlowerPackGetPrice() {
        Assertions.assertEquals(roseRed.getPrice() * rosePack.getQuantity(), rosePack.getPrice());
    }

    @Test
    public void testBucketGetPrice() {
        double expected = rosePack.getPrice() + tulipPack.getPrice();
        Assertions.assertEquals(expected, bucket1.getPrice());
    }

    @Test
    public void testBucketSearchScoreHigherForRelevant() {
        FlowerSpec tulipSearch = new FlowerSpec(FlowerType.TULIP, FlowerColor.BLUE, 10.0);

        double score1 = bucket1.getSearchScore(tulipSearch);
        double score2 = bucket2.getSearchScore(tulipSearch);
        Assertions.assertTrue(score1 > score2, 
                "Bucket with matching tulip should have higher score");
    }

    @Test
    public void testSearchReturnsAtLeastOneResult() {
        FlowerSpec tulipSearch = new FlowerSpec(FlowerType.TULIP, FlowerColor.BLUE, 10.0);
        List<FlowerBucket> results = store.search(tulipSearch);
        Assertions.assertFalse(results.isEmpty(), "Search should return non-empty list");
    }

    @Test
    public void testSearchResultCountLimit() {
        FlowerSpec anySpec = new FlowerSpec(FlowerType.TULIP, FlowerColor.BLUE, 10.0);
        List<FlowerBucket> results = store.search(anySpec, 1);
        Assertions.assertEquals(1, results.size(), 
                "Search with count limit should return exactly 1 result");
    }

    @Test
    public void testSearchReturnsEmptyForNoMatch() {
        FlowerSpec rareSpec = new FlowerSpec(FlowerType.CHAMOMILE, FlowerColor.BLACK, 999.0);
        List<FlowerBucket> results = store.search(rareSpec);
        Assertions.assertTrue(results.get(0).getSearchScore(rareSpec) < 0.5);
    }

    @Test
    public void testSearchPerfectMatchRanksFirst() {
        FlowerSpec tulipSearch = new FlowerSpec(FlowerType.TULIP, FlowerColor.BLUE, 10.0);
        List<FlowerBucket> results = store.search(tulipSearch);

        Assertions.assertFalse(results.isEmpty());

        double topScore = results.get(0).getSearchScore(tulipSearch);
        double secondScore = results.size() > 1 ? results.get(1).getSearchScore(tulipSearch) : 0.0;

        Assertions.assertTrue(topScore >= secondScore, "Bucket with exact tulip match must rank first");
        Assertions.assertEquals(bucket1, results.get(0), 
                "Bucket1 should be first because it has exact tulip match");
    }

    @Test
    public void testSearchExactChamomileMatchRanksFirst() {
        FlowerSpec chamomileSearch = new FlowerSpec(FlowerType.CHAMOMILE, FlowerColor.YELLOW, 12.0);
        List<FlowerBucket> results = store.search(chamomileSearch);

        Assertions.assertFalse(results.isEmpty(), "Search should return non-empty result");

        FlowerBucket topBucket = results.get(0);
        double topScore = topBucket.getSearchScore(chamomileSearch);

        Assertions.assertEquals(bucket2, topBucket, 
                "Bucket2 (contains chamomile pack) should be top for chamomile search");
        Assertions.assertTrue(topScore > 0.9, "Exact match should produce near-maximum score");
    }

    @Test
    public void testSearchExactRoseMatchRanksFirst() {
        FlowerSpec roseSearch = new FlowerSpec(FlowerType.ROSE, FlowerColor.RED, 15.0);
        List<FlowerBucket> results = store.search(roseSearch);

        Assertions.assertFalse(results.isEmpty());

        FlowerBucket top = results.get(0);
        double score = top.getSearchScore(roseSearch);

        Assertions.assertEquals(bucket1, top, 
                "Bucket1 should rank first because it contains red roses");
        Assertions.assertTrue(score > 0.8, "Almost exact match should produce high score");
    }
}
