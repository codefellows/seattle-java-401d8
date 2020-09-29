package manufacturing;

public class Bicycle extends Product{
    int gears;

    public Bicycle (String color, int gears){
        super(color);
        this.gears = gears;
    }

    public String toString(){
        return String.format("%s colored %d speed bike", color, gears);
    }
}
