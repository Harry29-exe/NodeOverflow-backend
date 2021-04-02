package com.kw.nodeimageeditorbackend;

public class mainTest {
    public static double N = 100_000;

    public static void main(String[] args) {
        double f;
        for(f = 0; f < 33; f += 0.1) {
            double xk = 0;
            double t = 0;
            while (t < N) {
                double val = function(t,f);
                double tempXK = val * Math.cos(2d*Math.PI*f*t/N);
                xk += tempXK;
                t += 0.01;
            }
            System.out.println("f(" + f*2d*3.14d + ") = " + xk);
        }
    }

    public static double calc(double f, double t) {
        double t1 = -(Math.PI * 2 * f * t);
        double temp = Math.pow(Math.E, t1);
        return temp;
    }

    public static double function(double t, double f) {
        return Math.sin(2*Math.PI*f*t) + 0.4*Math.pow(Math.sin(2d*Math.PI*f*t),2);
    }
}
