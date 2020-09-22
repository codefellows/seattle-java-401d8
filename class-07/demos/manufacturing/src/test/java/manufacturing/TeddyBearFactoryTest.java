package manufacturing;

import org.junit.Test;

public class TeddyBearFactoryTest {
    @Test public void testFactory(){
        TeddyBearFactory buildABear = new TeddyBearFactory("build a bear");
        buildABear.makeBear(50, 3, "brown");
        buildABear.makeBear(300, 7, "black");
        buildABear.makeBear(20, 1, "rainbow");

        System.out.println(buildABear);
        System.out.println(buildABear.finishedBears.get(0));
    }
}
