package sim;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Generates random inputs to simulate actual acquired Colossus data.
 */
public class random {

	/**
	 * Continues to generate random inputs indefinitely.
	 *
	 * @param args
	 * 		the command-line arguments
	 */
	public static void main(String args[]) {
		String directory = System.getProperty("user.dir");
		String sep = File.separator;
		int dirIndex = directory.indexOf("build" + File.separator + "classes" + File.separator + "java");
		directory = directory.substring(0, dirIndex);

		// total number of each item
		final int ERRORS = 10;
		final int WARNINGS = 16;
		final int PT = 14;

		while (true) {

			// number of digits in quantity of item (used to standardize numbers in naming convention)
			int digits = 0;

			// random readings for the errors and warnings
			for (int i = 1; i <= ERRORS; i++) {
				int emptyN = (int) Math.ceil(Math.log10(ERRORS)) - (int) Math.ceil(Math.log10(i));
				emptyN += Math.log10(ERRORS) % 1 == 0 ? 1 : 0;
				emptyN -= Math.log10(i) % 1 == 0 ? 1 : 0;
				String empty = new String(new char[emptyN]).replace('\0', '0');
				String pathname = directory + "E" + empty + i;
				writeFile(pathname, 2, true);
			}
			for (int i = 1; i <= WARNINGS; i++) {
				int emptyN = (int) Math.ceil(Math.log10(WARNINGS)) - (int) Math.ceil(Math.log10(i));
				emptyN += Math.log10(WARNINGS) % 1 == 0 ? 1 : 0;
				emptyN -= Math.log10(i) % 1 == 0 ? 1 : 0;
				String empty = new String(new char[emptyN]).replace('\0', '0');
				String pathname = directory + "W" + empty + i;
				writeFile(pathname, 2, true);
			}

			// random readings for the pressure transducers
			for (int i = 1; i <= PT; i++) {
				writeFile(directory + "PT" + i, 4, false);
			}

			// random readings for the valve states
			String[] valvePaths = {"Val150", "Val151", "Val250", "Val251", "Val252", "Val253", "Val350", "Val351",
					"Val352", "Val353"};
			String[] tempPaths = {"T290", "T291", "T292", "T293", "T390", "T391", "T392", "T393"};
			for (int i = 0; i < valvePaths.length; i++) {
				writeFile(directory + valvePaths[i], 2, true);
			}
			for (int i = 0; i < tempPaths.length; i++) {
				writeFile(directory + tempPaths[i], 1000, false);
			}

			// random readings for the other gauges
			writeFile(directory + "Tank_Fuel_1", 100, false);
			writeFile(directory + "Tank_Fuel_2", 100, false);
			writeFile(directory + "Tank_Temp_1", 1000, false);
			writeFile(directory + "Tank_Temp_2", 1000, false);
			writeFile(directory + "Battery", 100, false);
			writeFile(directory + "Thrust", 1000, false);

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("Simulation was interrupted.");
			}
		}
	}

	/**
	 * Writes inputs to files that the GUI can read as "Colossus" data.
	 *
	 * @param path
	 * 		the file path to write to
	 * @param range
	 * 		the range of possible positive values
	 * @param integer
	 * 		true to write values as integers, false to write values as doubles
	 * @return true if the file was successfully written, otherwise false
	 */
	private static boolean writeFile(String path, int range, boolean integer) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			double random = Math.random() * range;
			String value = integer ? Integer.toString((int) random) : Double.toString(random);
			writer.write(value);
			writer.close();
		} catch (IOException e) {
			System.out.println("NOOOOOO");
			return false;
		}
		return true;
	}
}
