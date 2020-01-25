package bigol;

public class Guess {
	
	Cell [][] duplicate = new Cell [9][9];
	int thisGuess = 0;
	
	public Guess(Cell[][] board, int thisGuess) 
	{
		for(int x = 0; x < 9; x++)
			for(int y = 0; y < 9; y++)
			{
				duplicate[x][y] = new Cell();
				duplicate[x][y].setNumber(board[x][y].getNumber());
				//saves board
			}
		this.thisGuess = thisGuess;//saves what was guessed
		
	}
	public Cell[][] getDuplicate() {
		return duplicate;
	}
	public void setDuplicate(Cell[][] duplicate) {
		this.duplicate = duplicate;
	}
	public int getThisGuess() {
		return thisGuess;
	}
	public void setThisGuess(int thisGuess) {
		this.thisGuess = thisGuess;
	}

}
