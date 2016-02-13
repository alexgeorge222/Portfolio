/*************************************************************************
 *  Compilation:  javac LZW.java
 *  Execution:    java LZW - < input.txt   (compress)
 *  Execution:    java LZW + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *
 *  Compress or expand binary input from standard input using LZW.
 *
 *  WARNING: STARTING WITH ORACLE JAVA 6, UPDATE 7 the SUBSTRING
 *  METHOD TAKES TIME AND SPACE LINEAR IN THE SIZE OF THE EXTRACTED
 *  SUBSTRING (INSTEAD OF CONSTANT SPACE AND TIME AS IN EARLIER
 *  IMPLEMENTATIONS).
 *
 *  See <a href = "http://java-performance.info/changes-to-string-java-1-7-0_06/">this article</a>
 *  for more details.
 *
 *************************************************************************/

public class myLZW {
	private static final int R = 256;        // number of input chars
	private static int w = 9;         // smallest codeword width
	private static final int wLARGE = 16; // largest codeword width

	public static void compress() { 
		String input = BinaryStdIn.readString();
		TST<Integer> st = new TST<Integer>();
		for (int i = 0; i < R; i++)
			st.put("" + (char) i, i);
		int code = R+1;  // R is codeword for EOF
		int nCode = (int)Math.pow(2, w); //number of codewords


		while (input.length() > 0) {

			String s = st.longestPrefixOf(input);  // Find max prefix match s.
			BinaryStdOut.write(st.get(s), w);      // Print s's encoding.
			int t = s.length();

			if (t < input.length() && code < nCode)    // Add s to symbol table.
				st.put(input.substring(0, t + 1), code++);

			else if(code >= nCode && w < wLARGE){

				w++;//Increases codeword width, raises max codewords
				nCode = (int)Math.pow(2, w);
				st.put(input.substring(0, t + 1), code++);

			}


			input = input.substring(t);            // Scan past s in input.

		}
		BinaryStdOut.write(R, w);
		BinaryStdOut.close();
	} 

	public static void compressR() { 
		String input = BinaryStdIn.readString();
		TST<Integer> st = new TST<Integer>();
		for (int i = 0; i < R; i++)
			st.put("" + (char) i, i);
		int code = R+1;  // R is codeword for EOF
		int nCode = (int)Math.pow(2, w); //number of codewords


		while (input.length() > 0) {

			String s = st.longestPrefixOf(input);  // Find max prefix match s.
			BinaryStdOut.write(st.get(s), w);      // Print s's encoding.
			int t = s.length();

			if (t < input.length() && code < nCode)    // Add s to symbol table.
				st.put(input.substring(0, t + 1), code++);

			else if(code >= nCode && w < wLARGE){

				w++;
				nCode = (int)Math.pow(2, w);
				st.put(input.substring(0, t + 1), code++);

			}

			else if(code >= nCode && w == wLARGE){//clears dictionary but retains basic 256 characters

				st = new TST<Integer>();
				for (int i = 0; i < R; i++)
					st.put("" + (char) i, i);
				code = R+1;
				st.put(input.substring(0, t + 1), code++);

			}


			input = input.substring(t);            // Scan past s in input.

		}
		BinaryStdOut.write(R, w);
		BinaryStdOut.close();
	} 

	public static void compressM() { 
		String input = BinaryStdIn.readString();
		int bCount = 0;
		int cCount = 0;
		int compCount = 0;
		double comRat = 0;
		double comRatN = 0;
		TST<Integer> st = new TST<Integer>();
		for (int i = 0; i < R; i++)
			st.put("" + (char) i, i);
		int code = R+1;  // R is codeword for EOF
		int nCode = (int)Math.pow(2, w); //number of codewords


		while (input.length() > 0) {

			String s = st.longestPrefixOf(input);  // Find max prefix match s.
			BinaryStdOut.write(st.get(s), w);      // Print s's encoding.
			int t = s.length();
			bCount = bCount + (s.length() * 16);//checks value of read in
			cCount = cCount + w;//checks value of compression
			if (t < input.length() && code < nCode){    // Add s to symbol table.

				st.put(input.substring(0, t + 1), code++);

			}
			else if(code >= nCode && w < wLARGE){

				w++;
				nCode = (int)Math.pow(2, w);
				st.put(input.substring(0, t + 1), code++);
			}

			else if(code >= nCode && w == wLARGE){
				if (compCount == 0){//first time through initializes a compression ration 

					comRat = bCount/cCount;
					compCount = 1;
				}

				else if (compCount == 1){//after this a new compression ratio is activated

					comRatN = bCount/cCount;
				}

				else if (compCount == 1 && (comRat/comRatN) <= 1.1){
					st = new TST<Integer>();
					for (int i = 0; i < R; i++)
						st.put("" + (char) i, i);
					code = R+1;
					st.put(input.substring(0, t + 1), code++);
					compCount = 0;
				}

			}


			input = input.substring(t);            // Scan past s in input.

		}
		BinaryStdOut.write(R, w);
		BinaryStdOut.close();
	} 


	public static void expand() {

		int nCode = (int)Math.pow(2, w);
		String[] st = new String[(int)Math.pow(2, 16)];
		int i; // next available codeword value

		// initialize symbol table with all 1-character strings
		for (i = 0; i < R; i++)
			st[i] = "" + (char) i;
		st[i++] = "";                        // (unused) lookahead for EOF

		int codeword = BinaryStdIn.readInt(w);
		if (codeword == R) return;           // expanded message is empty string
		String val = st[codeword];

		while (true) {
			BinaryStdOut.write(val);
			codeword = BinaryStdIn.readInt(w);
			if (codeword == R) break;
			String s = st[codeword];
			if (i == codeword) s = val + val.charAt(0);   // special case hack
			if (i < nCode - 1) {

				st[i] = val + s.charAt(0);
				i++;
			}

			else if(i >= nCode - 1 && w < wLARGE){

				w++;
				nCode = (int)Math.pow(2, w);
				st[i] = val + s.charAt(0);
				i ++;
			}

			val = s;
		}
		BinaryStdOut.close();
	}



	public static void main(String[] args) {
		if(args[0].equals("-")) {

			if(args[1].equals("n")){
				compress();
			}

			else if(args[1].equals("r")){

				compressR();
			}

			else if(args[1].equals("m")){

				compressM();
			}
		}
		else if (args[0].equals("+")) expand();

		else throw new IllegalArgumentException("Illegal command line argument");
	}

}
