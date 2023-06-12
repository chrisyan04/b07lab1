import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Arrays;

public class Polynomial {
    // i)
    double[] polyCoeffs;
    int[] exp;

    // ii)
    public Polynomial() {
        this.polyCoeffs = new double[] { 0 };
        this.exp = new int[] { 0 };
    }
    
    // iii)
    public Polynomial(double[] polyCoeffs, int[] exp) {
        this.polyCoeffs = polyCoeffs;
        this.exp = exp;
    }

    public Polynomial(File file) throws IOException{
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            String[] elements = line.split("(?=[\\+|-])");
            int[] resultExp = new int[elements.length];
            double[] resultCoeff = new double[elements.length];
            int index = 0;

            for (int i=0; i<elements.length; i++) {
                index = elements[i].indexOf('x');
                if (index != -1) {
                    // non-constant terms
                    resultExp[i] = Integer.parseInt(elements[i].substring(index+1));
                    resultCoeff[i] = Double.parseDouble(elements[i].substring(0, index));
                }
                else {
                    // constant terms
                    resultExp[i] = 0;
                    resultCoeff[i] = Double.parseDouble(elements[i]);
                }
            }
            reader.close();
            this.exp = resultExp;
            this.polyCoeffs = resultCoeff;
        }
        catch (IOException e) {
            System.err.println("Error with reading file " + e.getMessage());
            e.printStackTrace();
        }
    }

    // iv) method named add that takes one argument of type Polynomial and returns
    // the polynomial resulting from adding the calling object and the argument.
    public Polynomial add(Polynomial addOn) {
        int newLen = this.polyCoeffs.length + addOn.polyCoeffs.length;
        int[] tempExp = new int[newLen];
        double[] tempCoeff = new double[newLen];
        int k=0;

        int i=0;
        int j=0;

        while (i < this.polyCoeffs.length && j < addOn.polyCoeffs.length) {
            if (this.exp[i] > addOn.exp[j]) {
                tempExp[k] = addOn.exp[j];
                tempCoeff[k] = addOn.polyCoeffs[j];
                j++;
            }
            else if (this.exp[i] < addOn.exp[j]) {
                tempExp[k] = this.exp[i];
                tempCoeff[k] = this.polyCoeffs[i];
                i++;
            }
            else {
                double sum = this.polyCoeffs[i] + addOn.polyCoeffs[j];

                if (sum != 0) {
                    tempExp[k] = this.exp[i];
                    tempCoeff[k] = sum;
                    i++;
                    j++;
                }
                else {
                    i++;
                    j++;
                    continue;
                }
                
            }
            k++;
        }
        while (i < this.polyCoeffs.length) {
            tempCoeff[k] = this.polyCoeffs[i];
            tempExp[k] = this.exp[i];
            i++;
            k++;
        }

        while (j < addOn.polyCoeffs.length) {
            tempCoeff[k] = addOn.polyCoeffs[j];
            tempExp[k] = addOn.exp[j];
            j++;
            k++;
        }

        return new Polynomial(Arrays.copyOf(tempCoeff, k), Arrays.copyOf(tempExp, k));
    }
    
    

    // v) method named evaluate that takes one argument of type double representing
    // a value of x and evaluates the polynomial accordingly.
    public double evaluate(double x) {
        double ans = 0;
        for (int i=0; i<polyCoeffs.length; i++) {
            ans += polyCoeffs[i] * Math.pow(x, this.exp[i]);
        }
        return ans;
    }

    // vi) method named hasRoot that takes one arg of type double and determines
    // whether this value is a root of polynomical or not.
    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    // method named multiply that takes one argument of type Polynomial and returns
    // the polynomial resulting from multiplying the calling object and arg.
    // Note: resulting polynomical should not contain redundant exponents.
    public Polynomial multiply(Polynomial multIn) {
        int resultLen = this.exp.length * multIn.exp.length;
        Polynomial[] newArr = new Polynomial[resultLen];

        int count = 0;
        for (int i = 0; i < this.exp.length; i++) {
            for (int j = 0; j < multIn.exp.length; j++) {
                double product = polyCoeffs[i] * multIn.polyCoeffs[j];
                int power = this.exp[i] + multIn.exp[j];
                newArr[count] = new Polynomial(new double[]{product}, new int[]{power});
                count++;
            }
        }

        Polynomial newPoly = newArr[0];
        for (int i = 1; i < newArr.length; i++) {
            if (newArr[i].polyCoeffs[0] != 0.0) {
                newPoly = newPoly.add(newArr[i]);
            }
        }

        return newPoly;
    }
    

    public void saveToFile(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        int constant = 1;

        for (int i=0; i<this.polyCoeffs.length; i++) {
            if (this.polyCoeffs[i] != 0) {
                if (constant != 1) {
                    if (this.polyCoeffs[i] > 0) {
                        writer.write("+");
                    }
                    else {
                        writer.write("-");
                    }
                }
                else {
                    constant = 0;
                }

                writer.write(Double.toString(Math.abs(this.polyCoeffs[i])));

                if (this.exp[i] > 0) {
                    writer.write("x" + this.exp[i]);
                }
            }
        }
        writer.close();
    }
}