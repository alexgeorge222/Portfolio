import java.util.Scanner;
import java.io.*;


public class Airline {

	public static void main(String[] args) throws IOException {

		Scanner kb = new Scanner(System.in);

		String filename;

		System.out.println("INPUT FILE: ");
		filename = kb.nextLine();
		File inputFile = new File(filename);

		if (!inputFile.exists()) {

			System.out.println("The file does not exist");
			System.exit(0);
		}
		In file = new In(inputFile);
		int cityCount = file.readInt();
		String[] cities = new String[cityCount];
		for (int i = 0; i < cityCount; i++){

			cities[i] = file.readLine().toLowerCase();
		}
		file.close();
		file = new In(inputFile);
		EdgeWeightedGraph airline = new EdgeWeightedGraph(file);
		file.close();
		file = new In(inputFile);
		EdgeWeightedDigraph airline2 = new EdgeWeightedDigraph(file);
		file.close();
		int check = 0;



		while (check == 0){
			System.out.println("What would you like to do?(Type a Number): ");
			System.out.print("1.Show Entire List of Direct Routes\n2.Show a Minimum Spanning Tree\n3.Find the Shortest Path\n4.Trips Less Than a Given Amount\n5.Add a New Route\n6.Remove a route\n7.Quit\n");

			int uInput = kb.nextInt();
			if(uInput == 1){


				System.out.print(airline.toString());


			}

			else if(uInput == 2){

				KruskalMST mst = new KruskalMST(airline);
				Iterable<Edge> queue = mst.edges();
				for(Edge e : queue){

					System.out.println(airline.gCities(e.either()) + "," + airline.gCities(e.or()) + ": " + e.weight());
				}
				System.out.print("\n");


			}

			else if(uInput == 3){
				int sCheck = 0;
				while(sCheck == 0){
					System.out.print("What would you like your search to be based on?\n1.Shortest Path Based on Total Miles\n2.Cheapest Path\n3.Least Number of Stops\n");
					uInput = kb.nextInt();

					if(uInput == 1){
						kb.nextLine();
						int cityCheck =0;
						int src = 0;
						int dest = 0;
						while(cityCheck == 0){
							System.out.print("Please Type in the Name of your Source: ");
							String uSource = kb.nextLine().toLowerCase();
							System.out.print("Please Type in the Name of your Destination: ");
							String uDest = kb.nextLine().toLowerCase();
							int s = 0;
							int d = 0;
							for(int i = 0; i < cityCount; i++){

								if(cities[i].equals(uSource)){

									s = 1;
									src = i;
								}
								if(cities[i].equals(uDest)){

									d = 1;
									dest = i;
								}

								if(s == 1 && d == 1){

									cityCheck = 1;
									break;
								}
							}
							if(s == 0 || d == 0){

								System.out.println("One of your loactions is unavailable ");
							}

						}

						DijkstraSP mSaver = new DijkstraSP(airline2, src);
						if(mSaver.hasPathTo(dest) == false){

							System.out.println("No options available for your trip");
							break;
						}
						Iterable<DirectedEdge> path = mSaver.pathTo(dest);
						for(DirectedEdge e : path){

							System.out.println(airline.gCities(e.from()) + "," +airline.gCities(e.to()) + ":" + e.weight());
						}
						sCheck = 1;
					}

					else if (uInput == 2){

						kb.nextLine();
						int cityCheck =0;
						int src = 0;
						int dest = 0;
						while(cityCheck == 0){
							System.out.print("Please Type in the Name of your Source: ");
							String uSource = kb.nextLine().toLowerCase();
							System.out.print("Please Type in the Name of your Destination: ");
							String uDest = kb.nextLine().toLowerCase();
							int s = 0;
							int d = 0;
							for(int i = 0; i < cityCount; i++){

								if(cities[i].equals(uSource)){

									s = 1;
									src = i;
								}
								if(cities[i].equals(uDest)){

									d = 1;
									dest = i;
								}

								if(s == 1 && d == 1){

									cityCheck = 1;
									break;
								}
							}
							if(s == 0 || d == 0){

								System.out.println("One of your loactions is unavailable ");
							}

						}

						DijkstraSP mSaver = new DijkstraSP(airline2, src);
						if(mSaver.hasPathToP(dest) == false){

							System.out.println("No options available for your trip");
							break;
						}
						Iterable<DirectedEdge> path = mSaver.pathToP(dest);
						for(DirectedEdge e : path){

							System.out.println(airline.gCities(e.from()) + "," +airline.gCities(e.to()) + ":" + e.weight());
						}
						sCheck = 1;


					}

					else if (uInput == 3){

						kb.nextLine();
						int cityCheck =0;
						int src = 0;
						int dest = 0;
						while(cityCheck == 0){
							System.out.print("Please Type in the Name of your Source: ");
							String uSource = kb.nextLine().toLowerCase();
							System.out.print("Please Type in the Name of your Destination: ");
							String uDest = kb.nextLine().toLowerCase();
							int s = 0;
							int d = 0;
							for(int i = 0; i < cityCount; i++){

								if(cities[i].equals(uSource)){

									s = 1;
									src = i;
								}
								if(cities[i].equals(uDest)){

									d = 1;
									dest = i;
								}

								if(s == 1 && d == 1){

									cityCheck = 1;
									break;
								}
							}
							if(s == 0 || d == 0){

								System.out.println("One of your loactions is unavailable ");
							}

						}
						file = new In(inputFile);
						Graph airline3 = new Graph(file);
						file.close();
						BreadthFirstPaths path = new BreadthFirstPaths(airline3, src);	
						if (path.hasPathTo(dest)){

							Iterable<Integer> pathS = path.pathTo(dest);
							System.out.println("The fewest hops from " + airline.gCities(src) + " to " + airline.gCities(dest) + " is " + path.distTo(dest));
							for(Integer e : pathS){

								System.out.println(airline.gCities(e));
							}
							sCheck = 1;
						}

					}

					else{

						System.out.println("Please enter a valid choice: ");

					}

				}
			}

			else if(uInput == 4){
				kb.nextLine();
				System.out.println("What is the maximum amount of money you are willing to spend?: ");
				double max = kb.nextDouble();
				for(int i = 0; i < cityCount; i++){

					for(int x = 0;x < cityCount; x++){

						DijkstraSP mSaver = new DijkstraSP(airline2, i);
						if(mSaver.hasPathToP(x)){

							if(mSaver.distToP(x) < max){

								Iterable<DirectedEdge> path = mSaver.pathToP(x);
								double totes = 0;
								for(DirectedEdge e : path){

									System.out.println(airline.gCities(e.from()) + "," +airline.gCities(e.to()) + ":" + e.weight());
									totes = totes + e.weight();
								}
								if(totes != 0){
									System.out.println("Total Cost of Trip: " + totes);
									System.out.print("\n");
								}
							}
						}
					}
				}

			}

			else if(uInput == 5){
				kb.nextLine();


				int cityCheck =0;
				int src = 0;
				int dest = 0;
				int mile = 0;
				double price = 0;
				while(cityCheck == 0){
					System.out.println("Please type in the first city the route visits");
					String c1= kb.nextLine().toLowerCase();
					System.out.println("Please type in the second city the route visits");
					String c2= kb.nextLine().toLowerCase();
					System.out.println("Please type in the mileage of the trip");
					mile= kb.nextInt();
					System.out.println("Please type in the price of the trip");
					price= kb.nextDouble();
					int s = 0;
					int d = 0;
					for(int i = 0; i < cityCount; i++){

						if(cities[i].equals(c1)){

							s = 1;
							src = i;
						}
						if(cities[i].equals(c2)){

							d = 1;
							dest = i;
						}

						if(s == 1 && d == 1){

							cityCheck = 1;
							break;
						}
					}
					if(s == 0 || d == 0){

						System.out.println("One of your loactions is unavailable ");
					}

				}
				StringBuilder s = new StringBuilder();
				s.append("\n");
				s.append( + src + " " + dest + " " + mile + " " + price);
				String added= s.toString();
				BufferedWriter output;
				output = new BufferedWriter(new FileWriter(filename, true));
				output.newLine();
				output.write(added);
				output.close();
				file = new In(inputFile);
				airline = new EdgeWeightedGraph(file);
				file.close();
				file = new In(inputFile);
				airline2 = new EdgeWeightedDigraph(file);
				file.close();
			}

			else if(uInput == 6){
				kb.nextLine();
				int cityCheck =0;
				int src = 0;
				int dest = 0;
				while(cityCheck == 0){
					System.out.print("Please Type in the Name of your Source: ");
					String uSource = kb.nextLine().toLowerCase();
					System.out.print("Please Type in the Name of your Destination: ");
					String uDest = kb.nextLine().toLowerCase();
					int s = 0;
					int d = 0;
					for(int i = 0; i < cityCount; i++){

						if(cities[i].equals(uSource)){

							s = 1;
							src = i;
						}
						if(cities[i].equals(uDest)){

							d = 1;
							dest = i;
						}

						if(s == 1 && d == 1){

							cityCheck = 1;
							break;
						}
					}
					if(s == 0 || d == 0){

						System.out.println("One of your loactions is unavailable ");
						kb.nextLine();
					}

				}
				file.close();
				PrintWriter write = new PrintWriter("tempFile.txt", "UTF-8");
				file = new In(inputFile);
				int start = file.readInt();
				write.println(start);
				for (int i = 1; i < cityCount + 1 ; i++){

					String loc = file.readString();
					write.println(loc);
				}
				while (file.hasNextLine()) {
					int v = file.readInt();
					int w = file.readInt();
					double miles = file.readDouble();
					double price = file.readDouble();
					if(v == src || v == dest){

						if(w == src || w == dest){


						}
						else{

							write.println(v + " " + w + " " + miles + " " + price);
						}

					}
					else{

						write.println(v + " " + w + " " + miles + " " + price);
					}
				}
				write.close();
				File newFile = new File("tempFile.txt");
				newFile.renameTo(inputFile);
				file = new In(inputFile);
				airline = new EdgeWeightedGraph(file);
				file.close();
				file = new In(inputFile);
				airline2 = new EdgeWeightedDigraph(file);
				file.close();



			}

			else if(uInput == 7){

				check = 1;
			}



			else{

				System.out.println("Please enter a valid choice: ");
			}

		}
		System.out.println("Have a Nice Day");
	}

}
