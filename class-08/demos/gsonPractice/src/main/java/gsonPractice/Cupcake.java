package gsonPractice;

public class Cupcake {
    public String glitter;
    public String name;
    public String color;
    public int age;

    public Cupcake(String glitter, String name, String color, int age) {
        this.glitter = glitter;
        this.name = name;
        this.color = color;
        this.age = age;
    }

    public String toString(){
        return this.name + " is a cupcake";
    }

    public void getEaten(){
        System.out.println("aaaaaaaaaaah");
    }
}
