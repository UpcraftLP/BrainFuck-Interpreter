import brainfuck.BFReader;

public class Main {

	public static void main(String[] args) {
		if(args.length == 0) {
			//TODO: open GUI
		}
		else {
			String input = new String();
			for(String part : args) {
				input += part;
			}
			new BFReader(System.out, System.in).read(input);
		}
	}
}
