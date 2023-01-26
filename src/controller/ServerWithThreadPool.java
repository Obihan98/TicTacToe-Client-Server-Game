package src.controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

import src.model.Game;

public class ServerWithThreadPool {
    
    private Socket player1, player2;
	private ServerSocket serverSocket;
	private BufferedReader input1, input2;
	private PrintWriter output1, output2;
	
	private ExecutorService pool;

	public ServerWithThreadPool(int port) {
		try {
			serverSocket = new ServerSocket(port);
			pool = Executors.newCachedThreadPool();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    public void runServer () {
		try {
			while (true) {
				player1 = serverSocket.accept();
				System.out.println("Player 1 Connected!");

				player2 = serverSocket.accept();
				System.out.println("Player 2 Connected!");

				// Paramaters are input and output sockets for the 2 players
				Game currentGame = new Game(input1, input2, output1, output2);
				pool.execute(currentGame);
			}
			
		}catch (IOException e) {}
		pool.shutdown();
		
		try {
			input1.close();
			input2.close();
			output1.close();
			output2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void main (String [] args){
		ServerWithThreadPool myServer = new ServerWithThreadPool (9898);
		myServer.runServer();
	}
}
