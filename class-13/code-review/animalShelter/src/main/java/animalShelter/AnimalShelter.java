package animalShelter;

import java.util.HashSet;
import java.util.LinkedList;

public class AnimalShelter {
   LinkedList<Animal> animals= new LinkedList<>();
   LinkedList<Cat> cats = new LinkedList<>();
   LinkedList<Dog> dogs = new LinkedList<>();
   HashSet<Animal> adoptedAnimals = new HashSet<>();

   public void enQueue(Cat cat){
       animals.addLast(cat);
       cats.addLast(cat);
   }
   public void enQueue(Dog dog){
       animals.addLast(dog);
       dogs.addLast(dog);
   }

   public Animal deQueue(){
       Animal animal = animals.removeFirst();
       while(adoptedAnimals.contains(animal)){
           animal = animals.removeFirst();
       }
       return animal;
   }

   public Animal deQueue(Class<?> cl) throws ClassNotFoundException {
       if(cl == Cat.class){
           Cat adoptee = cats.removeFirst();
           adoptedAnimals.add(adoptee);
           return adoptee;
       } else if (cl == Dog.class){
           Dog adoptee = dogs.removeFirst();
           adoptedAnimals.add(adoptee);
           return adoptee;
       } else {
           throw new ClassNotFoundException("We only have Cats and Dogs");
//           return null;
       }
   }
}
