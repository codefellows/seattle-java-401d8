package gsonPractice;

public class Unicorn {
    String glitter;
    String color;
    int age;
    String name;

    public Unicorn(String glitter, String color, int age, String name) {
        this.glitter = glitter;
        this.color = color;
        this.age = age;
        this.name = name;
    }

    public void prance(){
        System.out.println(String.format("I am %s the Unicorn and I am prancing", name));
    }

    public String toString(){
        return String.format("This is %s, a %s unicorn with %s glitter who is %d years old", name, color, glitter, age);
    }
}
