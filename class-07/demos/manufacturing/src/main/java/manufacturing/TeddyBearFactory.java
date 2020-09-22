package manufacturing;

import java.util.LinkedList;

public class TeddyBearFactory extends Factory implements FactorizeAble{
    public String name;
    public LinkedList<Product> finishedBears = new LinkedList<>();

    public TeddyBearFactory(String name){
        this.name = name;
    }

    public int makeThing(String color){
        TeddyBear bear = new TeddyBear(color, 1, 30);
        finishedBears.add(bear);
        return finishedBears.size();
    }

    public int makeBear(int handFullsOfStuffing, int sizeInCubicMeters, String color){
        TeddyBear bear = new TeddyBear(color, sizeInCubicMeters, handFullsOfStuffing);
        finishedBears.add(bear);
        return finishedBears.size();
    }

    public String toString(){
        String result = name + " : available bear colors :";
        for(Product bear : finishedBears){
            result += bear.color + ", ";
        }
        return result;
    }
}
