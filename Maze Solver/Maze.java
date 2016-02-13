import java.util.Random;
// No other import statement is allowed

public class Maze
{
	private int cols;
	private int rows;
	private int[][] north;
	private int[][] east;
	private int[][] south;
	private int[][] west;
	

	/**
	 * Constructor
	 * @param aWidth the number of chambers in each row
	 * @param aHeight the number of chamber in each column
	 */
	public Maze(int aWidth, int aHeight)
	{
		cols = aWidth;
		rows = aHeight;

		north = new int[cols][rows];
		south = new int[cols][rows];
		east = new int[cols][rows];
		west = new int[cols][rows];
		
		
		for(int z = 0; z< cols; z++){
			
			for(int k = 0; k < rows; k++){
				
				north[z][k] = 0;
				east[z][k] = 0;
				west[z][k] = 0;
				south[z][k] = 0;
			}
		}

		for(int x = 0; x < cols; x ++){

			north[x][0] = 1;
			south[x][rows - 1] = 1;

		}

		for(int y = 0; y < rows; y++){

			west[0][y] =  1;
			east[cols - 1][y] = 1;

		}
		Random rand = new Random();
		buildMaze(0, cols - 1, 0, rows - 1, rand);



	}

	/**
	 * buildMaze
	 * @param rWall the row where the wall is located
	 * @param cWall the column where the wall is located
	 */

	public void buildMaze(int lBord, int rBord, int tBord, int bBord, Random rand){

		int height = bBord - tBord;
		int width = rBord - lBord;
	

		
		
		
		if(height <= 0 || width <= 0){
			
			return;
		}

		else{
			
			int cWall = rand.nextInt(rBord - lBord )+ 1 + lBord;
			int rWall = rand.nextInt(bBord- tBord) + 1 + tBord;
			int wall = rand.nextInt(4);
			int wWall = rand.nextInt(cWall - lBord) + lBord;
			int nWall = rand.nextInt(rWall - tBord) + tBord;
			int eWall = rand.nextInt(rBord- cWall + 1 ) + cWall ;
			int sWall = rand.nextInt(bBord - rWall + 1) + rWall ;
			System.out.println(nWall);
			System.out.println(sWall);
			System.out.println(eWall);
			System.out.println(wWall);
			System.out.println(wall);
			System.out.println("pause");

			for(int i = lBord; i < cWall ; i++){

				if(wall != 0 && i == wWall){

				}
				else{
					north[i][rWall] = 1;
					south[i][rWall - 1] = 1;
				}

			}

			for(int k = cWall ; k < rBord + 1; k++){

				if(wall != 1 && k == eWall){

				}
				else{
					north[k][rWall] = 1;
					south[k][rWall - 1] = 1;

				}
			}

			for(int z = tBord; z < rWall ; z++){

				if(wall != 2 && z == nWall){

				}
				else{
					
					west[cWall][z] = 1;
					east[cWall - 1][z] = 1;
				}

			}
			
			for(int v = rWall  ; v < bBord +1 ; v++){

				if(wall != 3 && v == sWall){

				}
				else{
					west[cWall][v] = 1;
					east[cWall - 1][v] = 1;
				}

			}

			buildMaze(lBord, cWall - 1 , tBord, rWall - 1, rand );
			buildMaze(cWall , rBord , tBord, rWall - 1, rand);
			buildMaze(cWall , rBord , rWall , bBord, rand);
			buildMaze(lBord, cWall - 1, rWall , bBord,rand );

		}


	}


	/**
	 * getWidth
	 * @return the width of this maze
	 */
	public int getWidth()
	{
		return cols;
	}

	/**
	 * getHeight
	 * @return the height of this maze
	 */
	public int getHeight()
	{
		return rows;
	}
	

	/**
	 * isNorthWall
	 * @param row the row identifier of a chamber
	 * @param column the column identifier of a chamber
	 * @return true if the chamber at row row and column column
	 * contain a north wall. Otherwise, return false
	 */
	
	
	public boolean isNorthWall(int row, int column)
	{
		
		if(north[column][row] == 1){

			return true;
		}
		else{

			return false;
		}
	}

	/**
	 * isEastWall
	 * @param row the row identifier of a chamber
	 * @param column the column identifier of a chamber
	 * @return true if the chamber at row row and column column
	 * contain an east wall. Otherwise, return false
	 */
	public boolean isEastWall(int row, int column)
	{

		if(east[column][row] == 1){

			return true;
		}
		else{

			return false;
		}
	}

	/**
	 * isSouthWall
	 * @param row the row identifier of a chamber
	 * @param column the column identifier of a chamber
	 * @return true if the chamber at row row and column column
	 * contain a south wall. Otherwise, return false
	 */
	public boolean isSouthWall(int row, int column)
	{
		if(south[column][row] == 1){

			return true;
		}
		else{

			return false;
		}
	}

	/**
	 * isWestWall
	 * @param row the row identifier of a chamber
	 * @param column the column identifier of a chamber
	 * @return true if the chamber at row row and column column
	 * contain a west wall. Otherwise, return false
	 */
	public boolean isWestWall(int row, int column)
	{

		if(west[column][row] == 1){

			return true;
		}
		else{

			return false;
		}
	}
}
