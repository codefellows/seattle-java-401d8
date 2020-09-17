package demos;


import java.util.ArrayList;

public class Bakery {
    public ArrayList<Pastry> pastries = new ArrayList<Pastry>();

    public void makePastry(String fruit, boolean nuts, boolean isSavory, String shape ){
        // add a new pastry to the pastries arraylistoo
        Pastry newPastry = new Pastry(fruit, nuts, isSavory, shape);
        pastries.add(newPastry);
        System.out.println("One new pastry, coming right up!");
    }

    public void displayPastries(){
        for(Pastry p : pastries){
            System.out.println(p);
        }
    }

    public void sellPastry(int index){
        System.out.println(String.format("Here is your %s pastry", pastries.get(index).fruit));
        pastries.remove(index);
    }
}
