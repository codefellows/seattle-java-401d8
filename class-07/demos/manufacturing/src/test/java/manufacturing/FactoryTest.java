package manufacturing;

import org.junit.Test;

import java.util.ArrayList;

public class FactoryTest {
    @Test
    public void testingFactories(){
        BicycleFactory schwinn = new BicycleFactory("Schwinn Bikes");
        schwinn.makeBike("Red", 21);
        schwinn.makeBike("Pink", 24);
        schwinn.makeBike("Blue", 8);
        System.out.println(schwinn);

        TeddyBearFactory buildABear = new TeddyBearFactory("build a bear");
        buildABear.makeBear(50, 3, "brown");
        buildABear.makeBear(300, 7, "black");
        buildABear.makeBear(20, 1, "rainbow");
        System.out.println(buildABear);

        ArrayList<Factory> factories = new ArrayList<>();
        factories.add(buildABear);
        factories.add(schwinn);

        ArrayList<FactorizeAble> factoriesThatFactorize = new ArrayList<>(){};
        factoriesThatFactorize.add(buildABear);
        factoriesThatFactorize.add(schwinn);
        for(FactorizeAble factory : factoriesThatFactorize){
            factory.makeThing("Silver");
            factory.makeThing("Gold");
            factory.makeThing("Purple");
            factory.makeThing("Neon");
        }
        System.out.println(schwinn);
        System.out.println(buildABear);

        schwinn.makeThing("SkyBlue");
        buildABear.makeThing("Orange");

        System.out.println(schwinn);
        System.out.println(buildABear);

        Bicycle bike = schwinn.sellFirstBike();
        System.out.println(bike);



    }
}
