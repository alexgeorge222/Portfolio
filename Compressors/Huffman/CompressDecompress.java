/**
 * It is okay to use ArrayList class but you are not allowed to use any other
 * predefined class supplied by Java.
 */
import java.util.ArrayList;

public class CompressDecompress
{
	/**
	 * Get a string representing a Huffman tree where its root node is root
	 * @param root the root node of a Huffman tree
	 * @return a string representing a Huffman tree
	 */
	public static String getTreeString(final BinaryNodeInterface<Character> root)
	{
		String msg = new String();
		if(root == null){

			msg = "";
			return msg;
		}

		else{


			msg = getTreeString(root, msg);
			return msg;	

		}
	}

	/**
	 * Get a string representing a Huffman tree where its root node is root
	 * @param root the root node of a Huffman tree
	 * @param message 
	 * @return a string representing a Huffman tree
	 */
	private static String getTreeString(final BinaryNodeInterface<Character> root, String msg){

		if (root == null){

			return msg;
		}

		else{

			if(root.isLeaf() == true){

				msg = msg + "L" + root.getData();
				return msg;
			}

			else{

				msg = msg + "I";
				msg = getTreeString(root.getLeftChild(), msg);
				msg = getTreeString(root.getRightChild(), msg);

				return msg;
			}
		}

	}

	/**
	 * Compress the message using Huffman tree represented by treeString
	 * @param root the root node of a Huffman tree
	 * @param message the message to be compressed
	 * @return a string representing compressed message.
	 */
	public static String compress(final BinaryNodeInterface<Character> root, final String message)
	{



		if(root == null){

			return "";	
		}
		else{

			String numb = new String();
			String nChar = new String();
			ArrayList<String> table = new ArrayList<>();
			ArrayList<Character> cTable = new ArrayList<>(); 
			table = compress(root, nChar, table);
			cTable = compress(root, cTable);
			int ind = 0;

			for(int i = 0; i < message.length(); i++){

				char conv = message.charAt(i);
				ind = cTable.indexOf(conv);
				numb = numb + table.get(ind);

			}
			return numb;
		}
	}

	/**
	 * Compress the message using Huffman tree represented by treeString
	 * @param root the root node of a Huffman tree
	 * @param table the table of reference variables
	 * @param nChar the reference variable
	 * @return a string representing compressed message.
	 */
	private static ArrayList<String> compress(final BinaryNodeInterface<Character> root, String nChar, ArrayList<String> table){

		if(root == null){

			return table;
		}

		else{

			if(root.isLeaf() == true){

				table.add(nChar);
				return table;
			}

			else{

				table = compress(root.getLeftChild(), nChar + "0" , table);
				table = compress(root.getRightChild(), nChar + "1" , table);
			}

			return table;
		}
	}

	/**
	 * Compress the message using Huffman tree represented by treeString
	 * @param root the root node of a Huffman tree
	 * @param Ctable the table of reference characters
	 * @param nChar the reference variable
	 * @return a string representing compressed message.
	 */
	private static ArrayList<Character> compress(final BinaryNodeInterface<Character> root, ArrayList<Character> cTable){

		if(root == null){

			return cTable;
		}

		else{

			if(root.isLeaf() == true){

				cTable.add(root.getData());
				return cTable;
			}

			else{

				cTable = compress(root.getLeftChild(), cTable);
				cTable = compress(root.getRightChild(), cTable);
			}

			return cTable;
		}

	}


	/**
	 * Decompress the message using Huffman tree represented by treeString
	 * @param treeString the string represents the Huffman tree of the
	 * compressed message
	 * @param message the compressed message to be decompressed
	 * @return a string representing decompressed message
	 */
	public static String decompress(final String treeString, final String message)
	{
		String msg = new String();

		if(treeString.isEmpty() == true){

			return "";
		}

		else{

			BinaryNodeInterface<Character> root = new BinaryNode<Character>();

			msg = decompress(root, treeString);
			
			String nMsg = message;

			String nChar = new String();
			ArrayList<String> table = new ArrayList<>();
			ArrayList<Character> cTable = new ArrayList<>(); 
			table = compress(root, nChar, table);
			cTable = compress(root, cTable);

			while(nMsg.isEmpty() == false){
				
				msg = decompress(root,msg, nMsg);
				StringBuilder sb = new StringBuilder(msg);
				sb.deleteCharAt(sb.length() - 1);
				msg= sb.toString();
				char tCheck = msg.charAt(msg.length() - 1);
				int ind = cTable.indexOf(tCheck);
				String sub = table.get(ind) ;
				sb = new StringBuilder(nMsg);
				sb.delete(0, sub.length());
				nMsg= sb.toString();
			}

		}


		return msg;
	}

	/**
	 *Build a Huffman tree
	 * @param treeString the string represents the Huffman tree of the
	 * compressed message
	 * @param message the compressed message to be decompressed
	 * @return a string representing decompressed message
	 */

	private static String decompress(final BinaryNodeInterface<Character> root, String treeString ){

		if(treeString.isEmpty() == true){

			return treeString;
		}

		else{

			if(treeString.charAt(0) == 'L'){

				root.setData(treeString.charAt(1));
				StringBuilder sb = new StringBuilder(treeString);
				sb.deleteCharAt(0);
				sb.deleteCharAt(0);
				treeString = sb.toString();
				return treeString;
			}

			else{

				StringBuilder sb = new StringBuilder(treeString);
				sb.deleteCharAt(0);
				treeString = sb.toString();
				root.setLeftChild(new BinaryNode<Character>());
				treeString = decompress(root.getLeftChild(), treeString);
				root.setRightChild(new BinaryNode<Character>());
				treeString = decompress(root.getRightChild(), treeString);
			}

			return treeString;
		}

	}

	private static String decompress(final BinaryNodeInterface<Character> root, String msg, String nMsg ){

		if (root == null){

			return msg;
		}

		else{

			if(root.isLeaf() == true){

				msg = msg + root.getData() + '~';
				return msg;
			}

			else{

				if(nMsg.charAt(0) == '0'){

					StringBuilder sb = new StringBuilder(nMsg);
					sb.deleteCharAt(0);
					nMsg = sb.toString();
					msg = decompress(root.getLeftChild(),msg, nMsg);
					if(msg.charAt(msg.length() - 1) == '~'){

						return msg;
					}
				}

				if(nMsg.charAt(0) == '1'){

					StringBuilder sb = new StringBuilder(nMsg);
					sb.deleteCharAt(0);
					nMsg = sb.toString();
					msg = decompress(root.getRightChild(),msg, nMsg);
					if(msg.charAt(msg.length() - 1) == '~'){

						return msg;
					}
				}
			}



			return msg;
		}
	}

}
