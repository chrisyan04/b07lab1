public class Polynomial {
    // i)
    double[] polyCoeffs;

    // ii)
    public Polynomial() {
        double[] zero = {0.0};
        polyCoeffs = zero;
    }
    
    // iii)
    public Polynomial(double[] in) {
        polyCoeffs = in;
    }

    // iv)
    public Polynomial add(Polynomial addOn) {
        for (int i=0; i<polyCoeffs.length; i++) {
            addOn.polyCoeffs[i] += polyCoeffs[i];
        }
        return addOn;
    }

    // v)
    public double evaluate(double x) {
        double ans = 0;
        for (int i=0; i<polyCoeffs.length; i++) {
            ans += polyCoeffs[i] * Math.pow(x, i);
        }
        return ans;
    }

    // vi)
    public boolean hasRoot(double x) {
        if (evaluate(x) == 0) {
            return true;
        }
        return false;

    }
}