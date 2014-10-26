import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

/*
* Funktionen assertEquals som vi använt tidigare jämför den data en funktion returnerar med ett värde vi tycker är rätt svar.
* Som exempel kan vi titta på funktionen verify från Rasmus program. Den tar en String som argument och returnerar
* en boolean. Denna funktion är enkel att testa med assertEquals. T.ex. kan vi skriva "assertEquals(true, Main.verify("8210230234"))".
* Mitt problem är att mina funktioner läser indata från System.in och skriver utdata direkt till System.out. Mina funktioner
* returnerar ingen data som assertEquals kan jämföra mot, och all in/utmatning sker via terminalen. Detta är egentligen dålig kodning
* och borde helst undvikas, men ibland kanske man vill ha det så. I de fallen vore det förstås trevlgt att fortfarande
* kunna köra JUnit-tester. Lyckligtvis visste google hur man löser detta, och här kommer ett dåligt försök till förklaring.
*/

public class UselessCrapTest {
    /*
    * System.out och System.in är s.k. Streams. De tar emot/skickar data som en ström av bytes. Default-inställningen
    * är att de skickar/tar emot data till/från terminalen. Lösningen på mitt problem är att styra om System.out
    * och System.in till två strömmar jag själv skapat. Det är viktigt att komma ihåg att återställa till orginalinställningarna
    * när testet är färdigt. Detta kan lösas med taggarna @Before och @After.
    *
    * Det första jag gör är att skappa tre Streams av rätt typ. Den första använder jag för att fånga all data som skickas till
    * System.out, dvs det som normalt skulle skrivas ut i terminalen. De två andra använder jag för att lagra de befintliga
    * strömmarna System.in och System.out.
    */
    private ByteArrayOutputStream outStream;
    private InputStream originalIn;
    private PrintStream originalOut;


    //@Before innebär att funktionen setUp kommer köras innan varje test (= funktioner märkta med @Test).
    @Before
    public void setUp() throws Exception {
        //Spara System.out i variabeln originalOut.
        originalOut = System.out;
        //Sätt variabeln outStream till en ny ByteArrayOutputStream så den är tömd inför testet.
        outStream = new ByteArrayOutputStream();
        //Sätt System.out till en ny PrintStream som skapas utifrån strömmen ovan.
        System.setOut(new PrintStream(outStream));
        //Spara System.in i varisabeln originalIn.
        originalIn = System.in;
    }

    //@After innebär att funktionen tearDown kommer köras efter varje test.
    @After
    public void tearDown() throws Exception {
        //Återställ System.out till dess orginalvärde som jag sparade i originalOut.
        System.setOut(originalOut);
        //Återställ System.in till dess orginalvärde som jag sparade i originalIn.
        System.setIn(originalIn);
    }

    //@Test identifierar testMain som en test-funktion.
    @Test
    public void testMain() throws Exception {
        //Skapa en String med datan jag vill skicka till min simulerade terminal.
        String cmd = "exit";
        //Funktionen getBytes() returnerar innehållet i strängen som en ström av bytes. Jag sätter System.in till
        //denna ström. För funktionen jag ska testa kommer det se ut som att användaren matade in "exit" i terminalen.
        System.setIn(new ByteArrayInputStream(cmd.getBytes()));
        //Jag kör nu funktionen jag ska testa, i det här fallet UselessCrap.main(). main() kräver alltid en array av
        // typen String som argument, så jag skapar en tom sådan och skickar med den.
        UselessCrap.main(new String[0]);
        /*Funktionen har nu körts. Den skickade en sträng till System.out m.h.a. funktionen System.out.println().
        * Eftersom jag styrde om System.out tidigare så landade den datan i variabeln outStream, som en ström av bytes.
        * Funktionen toString() returnerar innehållet i outStream i from av en String, som jag kan jämföra mot det svar
        * jag förväntade mig, m.h.a. assertEquals().
        * Som synes har jag lagt till "\n" efter "Bye bye!", vilket inte finns med i strängen som skickas till println().
        * Detta beror på att println() lägger till en radbrytning efter varje sträng den printar ut. "\" är en så kallad
        * escape char. Den betyder att tecknet efter är ett specialtecken. "\n" ger en radbrytning. "\t" blir en tab.
        * "\\" blir "\". Det finns fler, men jag har aldrig behövt använda dem.
        */
        assertEquals("Bye bye!\n", outStream.toString());
        //I de exempel vi fick på lektionen fanns det flera assertEquals i testfunktionen. Det går att göra så här med
        //men man måste då tömma outStream mellan varje test. Detta görs automatiskt så fort den här funktionen tar slut,
        //tack vare @After. Det känns därför enklare att lägga varje test i en egen funktion.
    }

    @Test
    public void testMain2() throws Exception {
        /*
        * Här kommer ett litet mer avancerat exempel. Den här gången skickar jag två kommandon till funktionen. De
        * separeras med "\n" så ser det ut som att användaren tryckt på enter mellan dem.
         */
        String cmd = "Say my name.\nexit";
        System.setIn(new ByteArrayInputStream(cmd.getBytes()));
        UselessCrap.main(new String[0]);
        assertEquals("Javaberg!\nBye bye!\n", outStream.toString());
    }

    @Test
    public void testMain3() throws Exception {
        String cmd = "Computer, kill Flanders.\nEkkiwekkifnatang!\n1 + 1\nSay my name.\nexit";
        System.setIn(new ByteArrayInputStream(cmd.getBytes()));
        UselessCrap.main(new String[0]);
        assertEquals("I'm sorry Dave, but I can't do that.\nUnrecognized command.\n2\nJavaberg!\nBye bye!\n", outStream.toString());
    }
}
