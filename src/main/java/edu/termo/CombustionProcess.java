package edu.termo;

import java.util.HashMap;

/**
 * @author Kamil Sikora
 *         Data: 26.11.13
 */
public abstract class CombustionProcess {
    protected final double V_MOL = 22.42;
    protected final double r0 = 2500;

    protected HashMap<String, Double> elements;

    /**
     * absolute air humidity [(kg of H20)/(kg of fuel)
     */
    protected double x;
    protected double lambda;

    protected Double Qs;
    protected Double Qi;
    protected Double Ot;

    public CombustionProcess(double x, double lambda) {
        this.x = x;
        this.lambda = lambda;
    }

    /**
     * @return composition of fumes.
     */
    public abstract HashMap<String, Double> getFumesVolumes();

    public abstract double getHeatOfCombustion();

    public abstract double getHeatingValue();

    public abstract Double getTheoreticalOxygen();

    public abstract void printInfo();
}
