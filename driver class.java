import java.util.Scanner;
public class Driver{
    private Q1List[];
    private Q2List[];
    private MLQList[];
    
    public static void main(String args[]){
        Scanner input = new Scanner(System.in);
      System.out.println("welcome to our program!");
       
        menu();
        int option=input.nextInt();
        while(option != 3){
        switch(option){
            case 1:

            break;
            case 2:
            
            break;
            
            default:
                system.out.println("invalid input!please ented a valid input.")
                break;
        }}}
    
    public menu(){
    System.out.println("choose one of the following options:");
    System.out.println("1. Enter process’ information.");
    System.out.println("2. Report detailed information about each process and different scheduling criteria.");
    System.out.println("3. Exit the program.");
    }
}