package src.model;
import java.io.*;

class Player implements Constants {
    private BufferedReader input;
    private PrintWriter output;

	private String name;
	private Board board;
	private char mark;
	Player opponent;

	public Player(String nameA, char markA, BufferedReader input, PrintWriter output) {
		name = nameA;
		mark = markA;
        this.input = input;
		this.output = output;
	}

	public void setOpponent(Player other) {
		opponent = other;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public String name() {
		return name;
	}

	public char mark() {
		return mark;
	}

	public void play() throws IOException {
	
		board.clear();

		while ((board.xWins() == false) && (board.oWins() == false) && (board.isFull() == false)) {
			makeMove();
			board.display();
			if (board.xWins() == false && board.isFull() == false){
				opponent.makeMove();
				board.display();
			}
		}

        // When the game is over send the following to both players.
		output.print("\nTHE GAME IS OVER: ");
        opponent.output.print("\nTHE GAME IS OVER: ");
		if (board.xWins() != false) {
            output.print(name() + " is the winner!\n\n");
            opponent.output.print(name() + " is the winner!\n\n");
        }
		else if (board.oWins() != false) {
            output.print(opponent.name() + " is the winner!\n\n");
            opponent.output.print(opponent.name() + " is the winner!\n\n");
        }
		else {
			output.print("the game is a tie.\n\n");
            opponent.output.print("the game is a tie.\n\n");
        }
	}

	private void makeMove() throws IOException {
		String regex = "[0-9]+"; 
		output.print(name + ", what row should your next " + mark() + " be placed in?    ");
		int row, col;
		String rStr, cStr;

		rStr = input.readLine();

		while(!rStr.matches(regex)){
			output.print(name + ", what row should your next " + mark() + " be placed in?    ");
			rStr = input.readLine();
		}
			
		row =  Integer.parseInt(rStr);

		output.flush();
		output.print(name + ", what column should your next " + mark() + " be placed in? ");

		cStr = input.readLine();
		
		while(!cStr.matches(regex)){
			output.print(name + ", what column should your next " + mark() + " be placed in?    ");
			cStr = input.readLine();
		}
		
		col = Integer.parseInt(cStr);

		while (true) {
            boolean acceptableInput = isAcceptableUserInput(row, col, rStr, cStr); 
			if (acceptableInput == true) break;

			output.print("Please enter the row again:    ");
			rStr = input.readLine();
			row = Integer.parseInt(rStr);
			output.print("Please enter the column again: ");
			cStr = input.readLine();
			col = Integer.parseInt(cStr);
		}
		
		board.addMark(row, col, mark());
	}
	


      private boolean isAcceptableUserInput(int row, int col, String rStr, String cStr) {
		boolean acceptableInput = true;
		if (rStr == null || cStr == null) {
			output.print("Sorry, " + name()
					+ ", I couldn't understand your input.\n");
			acceptableInput = false;
		} else if (row < 0 || row >= 3 || col < 0 || col >= 3) {
			output.print("Sorry, " + name
					+ ", but there is no square with coordinates (row="
					+ row + ", col=" + col + ").\n");
			acceptableInput = false;
		} else if (board.getMark(row, col) != SPACE_CHAR) {
			output.print("Sorry, " + name
					+ ", but the square with coordinates (row=" + row
					+ ", col=" + col + ") is marked.\n");
			acceptableInput = false;
		}
		
		return acceptableInput;
	}
}
