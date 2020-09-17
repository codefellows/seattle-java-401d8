package demos;

public class Pastry {
    // class vars
    public static boolean yummy = true; // class variable, available without an instance
    // with STATIC, `yummy` is available before I ever call new Pastry
    public static boolean baked = true;

    // instance vars
    public String madeOf = "Dough"; // this is an instance variable because it is only available once an instance has been made
    public boolean hot = false;
    public String fruit;
    public boolean nuts;
    public boolean savory;
    public String shape;

    public Pastry(String fruit, boolean nuts, boolean isSavory, String shape){ // having the same name as the class is how java knows this is the constructor
        this.fruit = fruit;
        this.nuts = nuts;
        this.savory = isSavory;
        this.shape = shape;
    }

    public String toString(){
        return String.format(
                "I am a pastry filled with %s filling and I am %s, I am this good : %f",
                this.fruit,
                this.shape,
                9.9);
    }

    public String bake(){
        this.hot = true;
        return "This pastry is now hot";
    }



//    public String toString(){
//        return "I am a pastry with " + this.fruit +" filling and I am " + this.shape;
//    }
}
