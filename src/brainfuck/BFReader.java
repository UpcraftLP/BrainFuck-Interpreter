package brainfuck;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BFReader {

	/**general init**/
	private static final Set<Character> allowedChars;
	static {
		allowedChars = new HashSet<Character>();
		allowedChars.add('<');
		allowedChars.add('>');
		allowedChars.add('+');
		allowedChars.add('-');
		allowedChars.add('[');
		allowedChars.add(']');
		allowedChars.add('.');
		allowedChars.add(',');
	}
	
	/**instance init**/
	
	private long[] cells;
	private int cellIndex;
	private int openLoops;
	private int computeIndex;
	private char[] toCompute;
	private PrintStream outputStream;
	private InputStream inputStream;
	
	public BFReader(PrintStream output, InputStream input) {
		this.cells = new long[1];
		cellIndex = 0;
		openLoops = 0;
		computeIndex = 0;
		this.outputStream = output;
		this.inputStream = input;
	}
	
	
	
	public String read(String input) {
		String output = new String();
		String computeString = new String();
		for(char c : input.toCharArray()) {
			if(allowedChars.contains(c)) {
				computeString += c;
			}
		}
		toCompute = computeString.toCharArray();
		for(; computeIndex < toCompute.length; computeIndex++) {
			compute(toCompute[computeIndex]);
			for(int i = 0; i < cells.length; i++) {
				outputStream.print(cells[i] + " ");
			}
			outputStream.println(" loops: " + openLoops);
		}
		return output;
	}
	
	public void compute(char input) {
		try {
			switch(input) {
			case '>':
				if(++cellIndex >= cells.length) {
					cells = Arrays.copyOf(cells, cellIndex + 1);
				}
				break;
			case '<':
				this.cellIndex--;
				break;
			case '+':
				cells[cellIndex]++;
				break;
			case '-':
				cells[cellIndex]--;
				break;
			case ',':
				cells[cellIndex] = this.inputStream.read();
				break;
			case '.':
				this.outputStream.print((char) cells[cellIndex]);
				break;
			case '[':
				openLoops++;
				break;
			case ']':
				int openLoops = 0;
				if(cells[cellIndex] != 0) {
					do {
						while(toCompute[--computeIndex] != '[') {
							if(toCompute[computeIndex] == ']') openLoops++;
						};
					}
					while(openLoops > 0);
				}
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
