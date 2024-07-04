package org.example.queingmodel;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MultichannelQueuingModel {
    private int s; // number of servers
    private double lambda; // arrival rate
    private double mu; // service rate

    public MultichannelQueuingModel(int s, double lambda, double mu) {
        if (s <= 0 || lambda <= 0 || mu <= 0) {
            throw new IllegalArgumentException("Servers, arrival rate, and service rate must be greater than zero.");
        }
        this.s = s;
        this.lambda = lambda;
        this.mu = mu;
    }

//    Traffic Intensity / Average Utilization (ρ)
    public double getRho() {
        return round(lambda / (s * mu));
    }

//    Probability of no customers in the system (Po)
    public double getP0() {
        double sum = 0.0;
        for (int n = 0; n < s; n++) {
            sum += Math.pow(lambda / mu, n) / factorial(n);
        }
        double p0Part = Math.pow(lambda / mu, s) / (factorial(s) * (1 - getRho()));
        return round (1 / (sum + p0Part));
    }

//    Probability of n customers in the system (Pn)
    public double getPn(int n) {
        double p0 = getP0();
        if (n < s) {
            return round(Math.pow(lambda / mu, n) / factorial(n) * p0);
        } else {
            return round(Math.pow(lambda / mu, n) / (factorial(s) * Math.pow(s, n - s)) * p0);
        }
    }

//    Average number of customers waiting (Lq)
    public double getLq() {
        double p0 = getP0();
        return round((p0 * Math.pow(lambda / mu, s) * getRho()) / (factorial(s) * Math.pow(1 - getRho(), 2)));
    }

//    Average waiting time (Wq)
    public double getWq() {
        return round(getLq() / lambda);
    }

//    Average time in system (Ws)
    public double getWs() {
        return round(getWq() + 1 / mu);
    }

//  Average number of customers in the system (Ls)
    public double getLs() {
        return round(lambda * getWs());
    }

//    Factorial (!)
    private long factorial(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public void printModelResults() {
        var wq = BigDecimal.valueOf(getWq()).toPlainString();
        System.out.println("Multichannel Queuing Model (M/M/S) Results:");
        System.out.println("Number of Servers (s): " + s);
        System.out.println("Arrival Rate (λ): " + lambda);
        System.out.println("Service Rate (μ): " + mu);
        System.out.println("Traffic Intensity (ρ): " + getRho());
        System.out.println("Probability of 0 customers in the system (P₀): " + getP0());
        System.out.println("Average number of customers in the queue (Lq): " + getLq());
        System.out.println("Average waiting time in the queue (Wq): " + wq);
        System.out.println("Average time spent in the system (Ws): " + getWs());
        System.out.println("Average number of customers in the system (Ls): " + getLs());
    }

    private static double round(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(5, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
