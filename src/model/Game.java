package src.model;
import java.io.*;

//STUDENTS SHOULD ADD CLASS COMMENT, METHOD COMMENTS, FIELD COMMENTS 


public class Game implements Constants, Runnable {

    private BufferedReader input1, input2;
	private PrintWriter output1, output2;

	private Board theBoard;
	private Referee theRef;
	
    public Game(BufferedReader input1, BufferedReader input2, PrintWriter output1, PrintWriter output2) {
        theBoard  = new Board(output1, output2);
        this.input1 = input1;
		this.input2 = input2;
		this.output1 = output1;
		this.output1 = output2;
	}
    
    public void appointReferee(Referee r) throws IOException {
        theRef = r;
    	theRef.runTheGame();
    }
	
    @Override
	public void run() {
		Referee theRef;
		Player xPlayer, oPlayer;

        String name = "";

		output1.print("\nPlease enter the name of the \'X\' player: ");
        try {
            name = input1.readLine();
		    while (name == null) {
			    System.out.print("Please try again: ");
			    name = input1.readLine();
		    }
        } catch (Exception e) {
            e.printStackTrace();
        }
		

		xPlayer = new Player(name, LETTER_X, input1, output1);
		xPlayer.setBoard(this.theBoard);
		
		output2.print("\nPlease enter the name of the \'O\' player: ");
        try {
            name = input2.readLine();
		    while (name == null) {
			    System.out.print("Please try again: ");
			    name = input2.readLine();
		}
        } catch (Exception e) {
            e.printStackTrace();
        }

		oPlayer = new Player(name, LETTER_O, input2, output2);
		oPlayer.setBoard(this.theBoard);
		
		theRef = new Referee();
		theRef.setBoard(this.theBoard);
		theRef.setoPlayer(oPlayer);
		theRef.setxPlayer(xPlayer);
        
        try {
            this.appointReferee(theRef);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}