import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;

// The process for running a file will be compiling `javac Basics.java` and then running it `java Basics` this step must be repeated each time you update the file

// The file name and the class name need to match, they both need a capital first letter
public class Basics {
  // Java is NOT a scripting language, we cannot just write code and have it run top to bottom. Java does allow us to run files though
  // If java finds a method with the name `main` and a very specific function signature, it will run that single method
  // the `main` method is considered the `entry point` of the app
  public static void main(String[] args){
    // console logging happens through System.out.print and System.out.println
    System.out.println("cool beans, (this does not break)"); // strings have double quotes
    helloWorld();
    System.out.println("2 + 4 is " + add(2, 4));

    for(int i = 0; i < 10; i++){
      System.out.print(i + " + 100 is " + add(i, 100));
    }

    while(true){
      System.out.println("Stop");
      break;
    }

  // Arrays must have a defined length
    String[] favoriteBooks = new String[4]; // string array with 3 indexes
    favoriteBooks[0] = "Speaker of the Mind";
    favoriteBooks[1] = "Harry Potter";
    favoriteBooks[2] = "East of Eden";
    // favoriteBooks[3] = "Lord of the Rings";
    
    int[] bookRatings = new int[]{9, 9, 10, 9};

    System.out.println(favoriteBooks);

    for(int i = 0; i < favoriteBooks.length; i++){
      System.out.println(favoriteBooks[i]);
      // the default value for favoriteBooks[3] is null
      // all objects have the default value of null
    }

    if(favoriteBooks[3] != null){
      System.out.println("it exists");
    }

    if(isGingerCold()){
      System.out.println("lets put a jacket on Ginger");
    } else {
      System.out.println("Lets go swimming");
    }
  }

  public static void helloWorld(){
    System.out.println("hey there world");
  }

  public static int add(int a, int b){
    return a + b;
  }

  public static boolean isGingerCold(){
    // Check the current month, if it is not august, she is cold
    Date date = new Date();
    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    int monthNumber = localDate.getMonthValue();
    System.out.println("month : " + monthNumber);

    if(monthNumber == 8){
      return false;
    }
    return true;
  }

}