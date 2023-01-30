package src.view;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class ClientController {
    private Socket aSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	
	public ClientController (String serverName, int portNumber) {
		
		try {
			aSocket = new Socket (serverName, portNumber);
			socketIn = new BufferedReader (new InputStreamReader (aSocket.getInputStream()));
			socketOut = new PrintWriter((aSocket.getOutputStream()), true);
			System.out.println("Connected Successfully!");
			
			// This first input from server sends the name of the player
			try {
				String response = socketIn.readLine(); // read response from the socket
				System.out.println(response);

				Scanner scanner = new Scanner(System.in); // get user name
				String name = scanner.nextLine();

				socketOut.println(name); // send username to the server
				System.out.println("-Name sent-");
			} catch(IOException e) {
				e.getStackTrace();
			}
			display();
			int i = 0;
			// Keeps getting 
			while (true) {
				try {
					if (i % 2 == 0){
						display();
					}

					String response = socketIn.readLine(); // read response from the socket
					System.out.print(response);

					i++;

					if (response == "THE GAME IS OVER: ") {
						break;
					}

					Scanner scanner = new Scanner(System.in); // get user move from user
					String userMove = scanner.nextLine();
					socketOut.println(userMove); // send username move to the server

					if (i % 2 == 0){
						display();
					}
				} catch (Exception e) {
					e.getStackTrace();
				}
			}
			
			try {
				socketIn.close();
				socketOut.close();
			} catch(IOException ex) {
				ex.getStackTrace();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void display() throws IOException {
		for (int i = 0; i < 14; i++){
			String response = socketIn.readLine(); // read response from the socket
			System.out.println(response);
		}
	}
	
	public static void main (String [] args) throws IOException{
		ClientController myClient = new ClientController ("localhost", 9898);
	}
}

