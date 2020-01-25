package bigol;
import java.io.File;
import java.util.Scanner;
public class Main{
	private static Cell[][] board = new Cell[9][9];
	
	public static void solve(int x, int y, int number)
	{
		board[x][y].setNumber(number);
		//turning off the potential number for each cell in the column
		for(int i = 0; i < 9; i++)
		{
			if(i != x)
				board[i][y].turnOffPotential(number);
		}
		//turn off the potential number for each cell in the row
		for(int i = 0; i < 9; i++)
		{
			if(i != y)
				board[x][i].turnOffPotential(number);
		}
		//turn off the potential number for all items in the box.
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				if(board[i][j].getBoxID() == board[x][y].getBoxID() && (i!=x && j !=y))//the solved number should have no potentials at all :?
					board[i][j].turnOffPotential(number);
			}
		}
	}
	public static void display()
	{
		for(int x = 0; x < 9; x++)
		{
			for(int y = 0; y < 9; y++)
			{
				if(y == 3 || y == 6)
					System.out.print("| ");
			
				System.out.print(board[x][y].getNumber() + " ");
				
			}
			System.out.println();
			if(x == 2 || x == 5)
				System.out.println("---------------------");
		}
	}
	
	public static void display(Cell[][]board)
	{
		for(int x = 0; x < 9; x++)
		{
			for(int y = 0; y < 9; y++)
			{
				if(y == 3 || y == 6)
					System.out.print("| ");
			
				System.out.print(board[x][y].getNumber() + " ");
				
			}
			System.out.println();
			if(x == 2 || x == 5)
				System.out.println("---------------------");
		}
	}
	
	public static void displayPotentials()
	{
		for(int x = 0; x < 9; x++)
		{
			for(int y = 0; y < 9; y++)
			{
				if(y == 3 || y == 6)
					System.out.print("| ");
				board[x][y].showPotential();
			}
			System.out.println();
			if(x == 2 || x == 5)
				System.out.println("---------------------");
		}
	}
	
	public static void setBoxIDs()
	{
		for(int x = 0; x < 9; x++)
		{
			for(int y = 0; y < 9; y++)
			{
				board[x][y].setBoxID( (x/3)*3 + y/3  +1 );
				//
			}
		}
	}
	
	public static void loadPuzzle(String filename) throws Exception
	{
		File infile = new File(filename);
		Scanner input = new Scanner(infile);
		for(int x = 0; x < 9; x++)
			for(int y = 0; y < 9; y++)
			{
				int data = input.nextInt();
				if(data != 0)
					solve(x, y, data );
			}
		input.close();
	}
	
	public static void loadPuzzle(Cell[][]reversion) throws Exception
	{
		for(int i = 0; i < 9; i++)
			for(int j = 0; j < 9; j++)
			{
				board[i][j].setNumber(0);
				board[i][j].resetPotentials();
			}
		for(int x = 0; x < 9; x++)
			for(int y = 0; y < 9; y++)
			{
				int data = reversion[x][y].getNumber();
				if(data != 0)
					solve(x, y, data);
			}
		//run algorithm 4 again, it will get stuck but the changed potentials is needed to make it potentially faster
		//4c
				for(int y = 0; y < 9; y++)//traverse columns
				{
					for(int x = 0; x < 9; x++)//traverse rows
					{
						if(board[x][y].canBeHowMany() == 2)//if empty and 2 potentials
						{
							for(int j = x+1; j < 9; j++)//search rest of row
							{
								if(board[j][y].canBeHowMany() == 2)
								{
									if(board[j][y].canBeWhat()[0] == board[x][y].canBeWhat()[0] && board[j][y].canBeWhat()[1] == board[x][y].canBeWhat()[1])//arrays of possible potentials the same
									{
										for(int i = 0; i < 9; i++)//go thru row starting at 0 and turn off potentials
										{
											if(i != j && i != x)
											{
												board[i][y].turnOffPotential(board[j][y].canBeWhat()[0]);
												board[i][y].turnOffPotential(board[j][y].canBeWhat()[1]);
											}
										}
									}
								}
							}

						}
					}
				}
				
				//4r
				for(int y = 0; y < 9; y++)//traverse columns
				{
					for(int x = 0; x < 9; x++)//traverse rows
					{
						if(board[y][x].canBeHowMany() == 2)//if empty and 2 potentials
						{
							for(int j = x+1; j < 9; j++)//search rest of row
							{
								if(board[y][j].canBeHowMany() == 2)
								{
									if(board[y][j].canBeWhat()[0] == board[y][x].canBeWhat()[0] && board[y][j].canBeWhat()[1] == board[y][x].canBeWhat()[1])//arrays of possible potentials the same
									{
										for(int i = 0; i < 9; i++)//go thru row starting at 0 and turn off potentials
										{
											if(i != j && i != x)
											{
												board[y][i].turnOffPotential(board[y][j].canBeWhat()[0]);
												board[y][i].turnOffPotential(board[y][j].canBeWhat()[1]);
											}
										}
									}
								}
							}

						}
					}
				}
	}
	
	public static void displayBox(int [] numbers) //display an array in a aesthetically pleasing way
	{
		for(int x = 0; x < numbers.length; x++)
		{
			if(numbers[x] >= 1000)
			{
				System.out.print(numbers[x]);
			}
			else if(numbers[x] >= 100)
			{
				System.out.print(numbers[x] + " ");
			}
			else if(numbers[x] >= 10)
			{
				System.out.print(numbers[x] + "  ");
			}
			else
				System.out.print(numbers[x] + "   ");
			
			if( (x+1)%10 == 0)
				System.out.println();
		}
		System.out.println();
	}
	
	public static int cellSolved()
	{
		int count = 0;
		for(int x = 0; x < 9; x++)
		{
			for(int y = 0; y < 9; y++)
				if(board[x][y].getNumber() != 0)
					count++;
		}
		return count;
	}

	public static int Algorithms()
	{
		int changes = 0;
		
		//1
		int countpotential = 0;
		int cellx = 0;
		int celly = 0;
		for(int d = 0; d < 9; d++)
			for(int i = 1; i < 10; i++)//traverse numbers
			{
					for(int m = 0; m < 9; m++)//traverse row
						if(board[d][m].getNumber() == 0)//if empty
						{
								if(board[d][m].getPotential()[i] == true)
								{
									countpotential++;
									cellx = d;
									celly = m;
								}
							
						}
				if(countpotential == 1)
				{
					solve(cellx, celly, i);
					changes++;
					countpotential = 0;
				}
				else
					countpotential = 0;
			}
		
		//2
		countpotential = 0;
		cellx = 0;
		celly = 0;
		for(int d = 0; d < 9; d++)
			for(int i = 1; i < 10; i++)//traverse numbers
			{
					for(int m = 0; m < 9; m++)//traverse row
						if(board[m][d].getNumber() == 0)//if empty
						{
								if(board[m][d].getPotential()[i] == true)
								{
									countpotential++;
									cellx = m;
									celly = d;
								}
							
						}
				if(countpotential == 1)
				{
					solve(cellx, celly, i);
					changes++;
					countpotential = 0;
				}
				else
					countpotential = 0;
			}
		
		//3
		countpotential = 0;
		cellx = 0;
		celly = 0;
		for(int b = 1; b < 10; b++)//traverse boxIDs
			for(int i = 1; i < 10; i++)//traverse numbers
			{
				int d = 0;
				int limit1 = 0;
				if(b == 1 || b == 2 || b == 3)
				{
					d = 0;
					limit1 = 3;
				}
				else if(b == 4 || b == 5 || b == 6)
				{
					d = 3;
					limit1 = 6;
				}
				else if(b == 7 || b == 8 || b == 9)
				{
					d = 6;
					limit1 = 9;
				}
				while(d < limit1)//traverse columns
				{
					int m = 0;
					int limit2 = 0;
					if(b == 1 || b == 4 || b == 7)
					{
						m = 0;
						limit2 = 3;
					}
					else if(b == 2 || b == 5 || b == 8)
					{
						m = 3;
						limit2 = 6;
					}
					else if(b == 3 || b == 6 || b == 9)
					{
						m = 6;
						limit2 = 9;
					}
					while(m < limit2)//traverse row
					{
						//System.out.println("[" + m + "][" + d + "] boxID:" + b + " " + i);
						if(board[m][d].getNumber() == 0 && board[m][d].getBoxID() == b)//if empty
						{
							if(board[m][d].getPotential()[i] == true)//goes through box with boxID b 9 times for numbers 1-9, then moves on with different parameters to loop
							{
								countpotential++;
								cellx = m;
								celly = d;
							}
									
						}
						m++;
					}
					d++;
				}
				if(countpotential == 1)
				{
					solve(cellx, celly, i);
					changes++;
					countpotential = 0;
				}
				else
				countpotential = 0;
			}
		
		//4c
		for(int y = 0; y < 9; y++)//traverse columns
		{
			for(int x = 0; x < 9; x++)//traverse rows
			{
				if(board[x][y].canBeHowMany() == 2)//if empty and 2 potentials
				{
					for(int j = x+1; j < 9; j++)//search rest of row
					{
						if(board[j][y].canBeHowMany() == 2)
						{
							if(board[j][y].canBeWhat()[0] == board[x][y].canBeWhat()[0] && board[j][y].canBeWhat()[1] == board[x][y].canBeWhat()[1])//arrays of possible potentials the same
							{
								for(int i = 0; i < 9; i++)//go thru row starting at 0 and turn off potentials
								{
									if(i != j && i != x)
									{
										board[i][y].turnOffPotential(board[j][y].canBeWhat()[0]);
										board[i][y].turnOffPotential(board[j][y].canBeWhat()[1]);
									}
								}
							}
						}
					}

				}
			}
		}
		
		//4r
		for(int y = 0; y < 9; y++)//traverse columns
		{
			for(int x = 0; x < 9; x++)//traverse rows
			{
				if(board[y][x].canBeHowMany() == 2)//if empty and 2 potentials
				{
					for(int j = x+1; j < 9; j++)//search rest of row
					{
						if(board[y][j].canBeHowMany() == 2)
						{
							if(board[y][j].canBeWhat()[0] == board[y][x].canBeWhat()[0] && board[y][j].canBeWhat()[1] == board[y][x].canBeWhat()[1])//arrays of possible potentials the same
							{
								for(int i = 0; i < 9; i++)//go thru row starting at 0 and turn off potentials
								{
									if(i != j && i != x)
									{
										board[y][i].turnOffPotential(board[y][j].canBeWhat()[0]);
										board[y][i].turnOffPotential(board[y][j].canBeWhat()[1]);
									}
								}
							}
						}
					}

				}
			}
		}
		
		//5
		for(int y = 0; y < 9; y++)
			for(int x = 0; x < 9; x++)
			{
				if(board[x][y].canBeHowMany() == 1 && board[x][y].getNumber() == 0)
				{
					solve(x, y, board[x][y].canBeWhat()[0]);
					changes++;
				}
			}
		
		return changes;
	}
	
	public static boolean checkContradictions()
	{
		for(int x = 0; x < 9; x++)
			for(int y = 0; y < 9; y++)
			{
				if(board[x][y].canBeHowMany() == 0)
					return true;//no true potentials returns true
			}
		return false;
	}
	
	public static void main(String[] args)throws Exception {
		Guess [] listOfGuesses = new Guess[81];
		int numberOfGuesses = 0;
		for(int x = 0; x < 9; x++)
			for(int y = 0; y < 9; y++)
				board[x][y] = new Cell();
		setBoxIDs();
		//load the puzzle
		//loadPuzzle("easy.txt");
		//loadPuzzle("medium.txt");
		//loadPuzzle("hard.txt");
		loadPuzzle("oni.txt");
		display();
		do
		{
			int changes = 0;
			do
			{
				changes = 0;
				changes += Algorithms();
				System.out.println(changes);
			}
			while(changes > 0);
			//display();
			if(cellSolved() < 81 && !checkContradictions())//if unsolved but not broken (wrong guess somewhere)
			{
				//start guessing
				boolean cellFound = false;
				for(int x = 0; x < 9 && !cellFound; x++)
					for(int y = 0; y < 9 && !cellFound; y++)
					{
						if(board[x][y].getNumber() == 0)
						{
							cellFound = true;
							listOfGuesses[numberOfGuesses] = new Guess(board, board[x][y].getFirstPotential());//maybe experiment with creating guesses first then changing the values here
							
							numberOfGuesses++;
						
							solve(x, y, board[x][y].getFirstPotential());
							//System.out.println("Guessed at:" + x + " " + y + " with " + board[x][y].getFirstPotential());
						}
					}			
			}
			else if(cellSolved() < 81 && checkContradictions())//unsolved and wrong guess somewhere
			{
				//System.out.println("contradict");
				numberOfGuesses--;
				loadPuzzle(listOfGuesses[numberOfGuesses].getDuplicate());
				boolean cellFound = false;
				for(int x = 0; x < 9 && !cellFound; x++)
					for(int y = 0; y < 9 && !cellFound; y++)
					{
						if(board[x][y].getNumber() == 0)
						{
							cellFound = true;
							board[x][y].turnOffPotentialsBefore(listOfGuesses[numberOfGuesses].getThisGuess());
						}
					}
			}
		}
		while(cellSolved() < 81); //it can completely solve sudokus by just guessing
		display();
		
	}

	
	
	
}