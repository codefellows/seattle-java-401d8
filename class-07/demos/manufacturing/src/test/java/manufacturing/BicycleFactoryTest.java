package manufacturing;

import org.junit.Test;

public class BicycleFactoryTest {
    @Test public void testBikes(){
        BicycleFactory schwinn = new BicycleFactory("Schwinn Bikes");
        schwinn.makeBike("Red", 21);
        schwinn.makeBike("Pink", 24);
        schwinn.makeBike("Blue", 8);
        System.out.println(schwinn);
    }
}
