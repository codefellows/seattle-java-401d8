package animalShelter;

public class Cat {
    String howToLeap = "I coil into a feline spring and release into the air";
    private String name; // accessible outside the class with getters, inside i can just use them
    private String color;
    private int age;

    public Cat(String name, String color, int age) {
        this.name = name;
        this.color = color;
        this.age = age;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String newName){
        // if to check if this is a valid name
        this.name = newName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void sleep(){
        System.out.println("Snowdrop is dreaming of catnip");
    }

    public String toString(){
        return String.format("Cat : %s, Color: %s, Age: %d", name, color, age);
    }
}
