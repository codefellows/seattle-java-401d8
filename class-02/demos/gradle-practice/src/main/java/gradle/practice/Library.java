/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package gradle.practice;

public class Library {
    public boolean someLibraryMethod() {
        System.out.println("This is being run by my test");
        return true;
    }

    public String giveIceCream(String name){ // Garhett
        return  "Give " + name + " chocolate cookie dough ice cream"; // Give Garhett chocolate cookie dough ice cream
    }

    public int oneOrTwo(){ //return a 1 or a 2
        double random = Math.random();
        if(random < 0.5){
            return 1;
        } else {
            return 2;
        }
    }
}
