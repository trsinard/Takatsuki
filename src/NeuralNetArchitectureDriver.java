import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import nl.nitori.Takatsuki.ANN.Architecture;
import nl.nitori.Takatsuki.ANN.Examples;
import nl.nitori.Takatsuki.Util.Log;

public class NeuralNetArchitectureDriver {

	private static final int HIDDEN_LAYERS = 2;
	private static final int MAX_NODES = 5;
	private static final int MIN_NODES = 1;

	public static void main(String[] args) {
		Log.registerLogger();
		if (args.length != 1) {
			System.out.println("Invalid argument - Format: <file>");
			System.exit(1);
		}

		ArrayList<String> lines = new ArrayList<String>();
		try {
			FileReader fr = new FileReader(args[0]);
			BufferedReader read = new BufferedReader(fr);
			String line = read.readLine();
			while (line != null) {
				lines.add(line);
				line = read.readLine();
			}
			read.close();
		} catch (IOException e) {
			System.out.println("Error reading file \"" + args[0] + "\".");
			System.exit(1);
		}

		ArrayList<Architecture> archs = new ArrayList<Architecture>();
		double tol = Double.parseDouble(lines.get(3));
		double alpha = Double.parseDouble(lines.get(2));
		int k_bins = Integer.parseInt(lines.get(1));
		Examples examples = null;

		try {
			examples = new Examples(lines.get(0), k_bins);
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find file " + lines.get(0));
			System.exit(1);
		}
		int[] hidden = new int[2];
		for (int j = MIN_NODES; j < MAX_NODES; j++) {

			for (int i = MIN_NODES; i < MAX_NODES; i++) {

				hidden[0] = j;
				hidden[1] = i;
				System.out.println("Creating new architecture");
				archs.add(new Architecture(lines.get(0), examples, alpha, tol,
						HIDDEN_LAYERS, hidden));
			}
		}
		for (int i = MIN_NODES; i < MAX_NODES; i++) {

			hidden[0] = i;
			System.out.println("Creating new architecture");
			archs.add(new Architecture(lines.get(0), examples, alpha, tol,
					HIDDEN_LAYERS, hidden));
		}

		Collections.sort(archs);

		System.out.println("The best architecture is: \n" + archs.get(0).toString());
	}
}
