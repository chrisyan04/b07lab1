import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
public class Driver {
    public static void main(String[] args) throws IOException{
        // Create polynomials
        Polynomial poly1 = new Polynomial(new double[]{2, -3, 1}, new int[]{0, 1, 2});
        Polynomial poly2 = new Polynomial(new double[]{1, 2}, new int[]{0, 2});

        // Test addition
		System.out.println("Test add:");
        Polynomial sum =  poly2.add(poly1);
		System.out.println("Received: " + Arrays.toString(sum.polyCoeffs) + "  " + Arrays.toString(sum.exp));
		System.out.println("Expected: [3.0, -3.0, 3.0]  [0, 1, 2]");
		assert Arrays.equals(sum.polyCoeffs, new double[]{3.0, -3.0, 3.0});
        assert Arrays.equals(sum.exp, new int[]{0, 1, 2});
        System.out.println("Test passed!");
		System.out.println();

        // Test evaluation
        System.out.println("Test evaluate:");
		double evaluation = poly1.evaluate(3);
		System.out.println("Received: " +  evaluation);
		System.out.println("Expected: 2.0");
		assert evaluation == 2.0;
        System.out.println("Test passed!");
		System.out.println();

        // Test root detection
        System.out.println("Test hasRoot:");
		boolean hasRoot = poly2.hasRoot(-2);
		System.out.println("Received: " + hasRoot);
		System.out.println("Expected: false");
		assert !hasRoot;
        System.out.println("Test passed!");
		System.out.println();

        // Test multiplication
		System.out.println("Test multiply:");
        Polynomial product =  poly1.multiply(poly2);
		System.out.println("Received: " + Arrays.toString(product.polyCoeffs) + "  " + Arrays.toString(product.exp));
		System.out.println("Expected: [2.0, -3.0, 5.0, -6.0, 2.0]  [0, 1, 2, 3, 4]");
		assert Arrays.equals(product.polyCoeffs, new double[]{2.0, -3.0, 5.0, -6.0, 2.0});
        assert Arrays.equals(product.exp, new int[]{0, 1, 2, 3, 4});
        System.out.println("Test passed!");
		System.out.println();

		// Test reading from file
		System.out.println("Test read from file:");
		File f = new File("poly.txt");
		try {
			Polynomial read = new Polynomial(f);
			System.out.println("Received: " +  Arrays.toString(read.polyCoeffs) + "  " + Arrays.toString(read.exp));
			System.out.println("Expected: [-5.0, 2.0, -3.0, 6.0]  [0, 1, 2, 4]");
			assert Arrays.equals(read.polyCoeffs, new double[]{-5.0, 2.0, -3.0, 6.0});
            assert Arrays.equals(read.exp, new int[]{0, 1, 2, 4});
            System.out.println("Test passed!");
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}

        // Test saving to a file
        System.out.println("Test Save To File:");
		poly1.saveToFile("output.txt");
		System.out.println("Successfully written to output.txt");
		System.out.println();
		System.out.println("Reading from output.txt");
		try (BufferedReader reader = new BufferedReader(new FileReader("output.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println("Received: " + line);
			}
		} catch (IOException e) {
			System.err.println("Error reading the file: " + e.getMessage());
		}
		System.out.println("Expected: 2.0-3.0x1+1.0x2");
		assert readFileContent("output.txt").equals("2.0-3.0x1+1.0x2");
        System.out.println("Test passed!");
		
    }

	private static String readFileContent(String fileName) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }
        return content.toString();
    }
}
