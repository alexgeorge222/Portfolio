import java.util.ArrayList;
import javax.swing.JFrame;

public class MazeFrame
{
	public static void main(String[] args) throws InterruptedException
	{
		int width = 100;
		int height = 100;
		JFrame frame = new JFrame();
		Maze maze = new Maze(width, height);
		ArrayList<Pair<Integer,Integer>> solution = new ArrayList<Pair<Integer,Integer>>();
		MazeComponent mc = new MazeComponent(maze, solution);
		frame.setSize(800,800);
		frame.setTitle("Maze");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(mc);
		frame.setVisible(true);

		solution.add(new Pair<Integer,Integer>(0,0));
		Thread.sleep(1000);
		solveMaze(solution, mc, maze,0,0,0,0, width, height);
		mc.repaint();
	}

	/** Solve Maze: recursively solve the maze
	 * 
	 * @param solution   : The array list solution is needed so that every recursive call,
	 *                     a new (or more) next position can be added or removed.
	 * @param mc         : This is the MazeComponent. We need that only for the purpose of
	 *                     animation. We need to call mc.repaint() every time a new position
	 *                     is added or removed. For example,
	 *                       :
	 *                     solution.add(...);
	 *                     mc.repaint();
	 *                     Thread.sleep(sleepTime);
	 *                       :
	 *                     solution.remove(...);
	 *                     mc.repaint();
	 *                     Thread.sleep(sleepTime);
	 *                       :
	 * @param maze       : The maze data structure to be solved. 
	 * @return a boolean value to previous call to tell the previous call whether a solution is
	 *         found.
	 * @throws InterruptedException: We need this because of our Thread.sleep(50);
	 */
	public static boolean solveMaze(ArrayList<Pair<Integer,Integer>> solution, MazeComponent mc, Maze maze, int row, int col, int preRow, int preCol, int width, int height) throws InterruptedException
	{
		if((row == height - 1) && (col == width - 1)){

			return true;
		}
		else
		{
			int sleepTime = 100;
			if(maze.isNorthWall(row,col) == false && row - 1 != preRow) {

				solution.add(new Pair<Integer,Integer>(row - 1,col));
				mc.repaint();
				Thread.sleep(sleepTime);
				boolean go = solveMaze(solution, mc, maze,row - 1, col, row, col, width, height);
				if(go == true){

					return true;
				}

				else{

					solution.remove(solution.size() - 1);
					mc.repaint();
					Thread.sleep(sleepTime);
				}
			}
			if(maze.isEastWall(row,col) == false && col + 1 != preCol) {
				solution.add(new Pair<Integer,Integer>(row,col + 1));
				mc.repaint();
				Thread.sleep(sleepTime);
				boolean go = solveMaze(solution, mc, maze,row, col + 1, row, col, width, height);
				if(go == true){

					return true;
				}
				else{
					
					solution.remove(solution.size() - 1);
					mc.repaint();
					Thread.sleep(sleepTime);
				}
			}

			if(maze.isSouthWall(row,col) == false && row + 1 != preRow) {
				solution.add(new Pair<Integer,Integer>(row + 1,col));
				mc.repaint();
				Thread.sleep(sleepTime);
				boolean go = solveMaze(solution, mc, maze,row + 1, col, row, col, width, height);
				if(go == true){
					
					return true;
				}
				else{
					
					solution.remove(solution.size() - 1);
					mc.repaint();
					Thread.sleep(sleepTime);
				}
					
			}
			if(maze.isWestWall(row,col) == false && col - 1 != preCol) {
				solution.add(new Pair<Integer,Integer>(row,col - 1));
				mc.repaint();
				Thread.sleep(sleepTime);
				boolean go = solveMaze(solution, mc, maze,row, col - 1, row, col, width, height);
				if(go == true){
					
					return true;
				}
			
					
				else{
					
					solution.remove(solution.size() - 1);
					mc.repaint();
					Thread.sleep(sleepTime);
				}
					
			}
			return false;
		}
	}
}
