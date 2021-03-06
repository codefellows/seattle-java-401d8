/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package animalShelter;

import java.util.ArrayList;

public class App {
    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {
        Cat cat = new Cat("Snowdrop", "White", 12);
        System.out.println(cat.getName() + " " + cat.getAge() + " " + cat.getColor());
        System.out.println(cat);
        Lion simba = new Lion("Simba", "yellow", 2);
        Lion nala = new Lion("Nala", "yellow", 3);
        Lion nairobi = new Lion("Nairobi", "yellow", 10);
        simba.addToPride(nala);
        simba.addToPride(nairobi);
        nala.addToPride(nairobi);
        System.out.println(simba);
        System.out.println(nala);
        System.out.println(nairobi);



        ArrayList<Cat> catKingdom = new ArrayList<>();
        catKingdom.add(cat);
        catKingdom.add(simba); // simba is a Lion and a Cat, it can be stored as a Cat
        catKingdom.add(nala);
        catKingdom.add(nairobi);
        // system.out.println always calls toString on objects

        for(Cat kitty : catKingdom){
            System.out.println(kitty);
        }

        Cat simbaCat = simba;
        System.out.println("this is simba as a cat :" + simbaCat );
//        System.out.println(simbaCat.rawr()); // because this is a cat, it cannot rawr right now
        System.out.println( ( (Lion) simbaCat).rawr() );
//        System.out.println( ( (Lion) cat).rawr() ); // because snowdrop was only ever a `Cat` she cannot be a Lion

        FeedAble cat1 = simba;
        FeedAble cat2 = nala;
        System.out.println(cat1.eat("Antelope"));
        System.out.println(cat2.eatMedicine("Hippo", "benadryl"));

    }
}
