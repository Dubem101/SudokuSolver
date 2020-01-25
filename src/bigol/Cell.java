package bigol;
public class Cell {
	private int number;
	private int boxID;
	
	private boolean[] potential = {false,true,true,true,true,true,true,true,true,true};
	public Cell()
	{
		number = 0;
		boxID = 0;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
		for(int x = 0; x<9; x++)
		{
			if(x != number)
				potential[x] = false;
		}
	}
	
	public int getBoxID() {
		return boxID;
	}
	
	public void setBoxID(int boxID) {
		this.boxID = boxID;
	}
	
	public boolean[] getPotential() {
		return potential;
	}
	
	public void setPotential(boolean[] potential) {
		this.potential = potential;
	}
	
	public void turnOffPotential(int number)
	{
		potential[number] = false;
	}
	
	public void showPotential()
	{
		for(int x = 1; x < 10; x++)
			System.out.print(x + ":" +potential[x] + " ");
	}
	
	public int getFirstPotential()
	{
		for(int x = 1; x < 10; x++)
		{
			if(potential[x] == true)
				return x;
		}
		return -1; //if cell is contradictory return -1
		
	}
	
	public void resetPotentials()
	{
		for(int x = 1; x < 10; x++)
			potential[x] = true;
	}
	
	public void turnOffPotentialsBefore(int number)
	{
		for(int x = 1; x <= number; x++)
		{
			potential[x] = false;
		}
	}
	public int canBeHowMany()
	{
		int trues = 0;
		for(int i = 1; i < 10; i++)
			if(potential[i] == true)
				trues++;
		return trues;
	}
	
	public int [] canBeWhat()
	{
		int [] numbersCanBe = new int [canBeHowMany()];
		int i = 1;
		for(int n = 0; n < numbersCanBe.length; n++)
		{
			while(i < 10 && potential[i] != true)
			{
				i++;
			}
			numbersCanBe[n] = i;
			i++;
		}
				
		return numbersCanBe;
	}
}