package animalShelter;

import java.util.ArrayList;

public class Lion extends Cat implements FeedAble {
    public ArrayList<Lion> pride = new ArrayList<>();

    public Lion(String name, String color, int age) {
        super(name, color, age); // super === Cat
        // while creating Lion, also call the Cat constructor,
        // by doing it through super, it does't create a new class Cat and Lion separately
        // it joins the two classes into one instance
    }

    public void addToPride(Lion friend){
        this.pride.add(friend);
        friend.pride.add(this);
    }

    // OVERRIDE: when I replace a method that I inherited

    public String toString(){
        String result = "LION rawr: " + this.getName() + " my pride :";
        for(Lion friend :  this.pride){
            result += friend.getName() +", ";
        }
        return result;
    }

    public String rawr(){
        return "RAWR!!!";
    }

    @Override
    public String eat(String food) {
        return "I am eating " + food;
    }

    @Override
    public String eatMedicine(String food, String hiddenMeds) {
        return "I am eating " + food + " there is gross " +hiddenMeds +" inside";
    }
}
