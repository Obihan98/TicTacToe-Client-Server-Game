package src.client.view;
import java.util.Scanner;

public class ClientView  {

    public void showResponses(String response)
    {
        System.out.print(response);
    }

    public String askForInput()
    {
        Scanner scanner = new Scanner(System.in); // get user move from user
		String input = scanner.nextLine();
        return input;
    }
    
}