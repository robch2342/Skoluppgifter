import java.util.Scanner;

public class UselessCrap {

    public static void main(String[] args) {
        //Skapa en Scanner och döp den till sc. Scannern läser in data från System.in, som i normalfallet läser in data från terminalen.
        Scanner sc = new Scanner(System.in);
        //Skapa en String som heter cmd och sätt den till nästa rad som läses in i Scannern sc.
        String cmd = sc.nextLine();
        //Kör en loop så länge värdet på cmd inte är "exit".
        while (!cmd.equals("exit")) {
            //Om Simpsonsreferens, svara med en referens till 2001.
            if (cmd.equals("Computer, kill Flanders.")) {
                System.out.println("I'm sorry Dave, but I can't do that.");
            //Om Breaking Bad-referens, svara med Javazone Norway-referens.
            } else if (cmd.equals("Say my name.")) {
                System.out.println("Javaberg!");
            //Ja, jag har dålig fantasi och orkade inte hitta på fler referenser.
            } else if (cmd.equals("1 + 1")) {
                System.out.println("2");
            //För alla andra värden, ge ett felmeddelande.
            } else {
                System.out.println("Unrecognized command.");
            }

            //Läs in ett nytt kommando till nästa iteration av while.
            cmd = sc.nextLine();
        }
        //Användaren har skrivit in "exit" och programmet kommer därför avslutas. Säg hej då.
        System.out.println("Bye bye!");
    }
}
