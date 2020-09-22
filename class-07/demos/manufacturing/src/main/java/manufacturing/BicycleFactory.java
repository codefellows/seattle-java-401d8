package manufacturing;

import java.util.LinkedList;

public class BicycleFactory extends Factory implements FactorizeAble{
    String name;
    LinkedList<Product> bicyclesIMade = new LinkedList<>();

    public BicycleFactory(String name){
        this.name = name;
    }

    public int makeThing(String color){
        Bicycle bike = new Bicycle(color, 7);
        bicyclesIMade.add(bike);
        return bicyclesIMade.size();
    };

    public int makeBike(String color, int gears){
        Bicycle bike = new Bicycle(color, gears);
        bicyclesIMade.add(bike);
        return bicyclesIMade.size();
    }

    public Bicycle sellFirstBike(){
        return (Bicycle) bicyclesIMade.get(0);
    }

    public String toString(){
        String result = name + " : available bike colors :";
        for(Product bike : bicyclesIMade){
            result += bike.color + ", ";
        }
        return result;
    }
}
