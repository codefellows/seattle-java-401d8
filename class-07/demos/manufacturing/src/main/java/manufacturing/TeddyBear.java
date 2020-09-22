package manufacturing;

public class TeddyBear extends Product{
    int sizeInCubicMeters;
    int handFullsOfStuffing;

    public TeddyBear(String color, int sizeInCubicMeters, int handFullsOfStuffing) {
        super(color);
        this.sizeInCubicMeters = sizeInCubicMeters;
        this.handFullsOfStuffing = handFullsOfStuffing;
    }

    public String toString(){
        return String.format(
                "I am a %s Teddy bear, I am %d m^3 and contain %d handfulls of Stuffing",
                color, sizeInCubicMeters, handFullsOfStuffing);
    }
}
