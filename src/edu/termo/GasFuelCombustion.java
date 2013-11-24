package edu.termo;

import java.util.HashMap;

import static edu.termo.KeyNames.*;

/**
 * Class computes heat of combustion, caloric value, required oxygen and exhaust composition from combustion of gas fuel.
 */
public class GasFuelCombustion {
    final double V_MOL = 22.42;
    HashMap<String, Double> elements;

    /**
     * absolute air humidity [(kg of H20)/(kg of fuel)
     */
    double x;
    double lambda;

    /**
     * @param elements associative with elements share of solid fuel
     * @param lambda   air fuel ratio
     * @param x        air absolute humidity [(kg of H20)/(kg of fuel)
     */
    public GasFuelCombustion(HashMap<String, Double> elements, double lambda, double x) {
        this.elements = elements;
        this.lambda = lambda;
        this.x = x;
    }

    public void printCombustionParameters() {
        double r0 = 2500;
        double Qi = 12629 * elements.get(CO) + 10749 * elements.get(HYDROGEN) + 35810 * elements.get(CH4)
                + 62974 * elements.get(C2H4) + 56040 * elements.get(C2H2) + 63729 * elements.get(C2H6)
                - r0 * elements.get(MATERIAL_WATER);

        double ww = 18.0 / V_MOL * (elements.get(HYDROGEN) + 2 * elements.get(CH4) + 2 * elements.get(C2H4)
                + 3 * elements.get(C2H6) + elements.get(C2H2)) + elements.get(MATERIAL_WATER);

        double Qs = Qi + r0 * ww;

        /* Obliczenie Qi i Qs testowane na zadaniu 2.6.2 ze skryptu*/

        double Ot = 0.5 * elements.get(CO) + 2 * elements.get(CH4) + 3.5 * elements.get(C2H6) +
                3 * elements.get(C2H4) + 2.5 * elements.get(C2H2) + 0.5 * elements.get(HYDROGEN) - elements.get(OXYGEN); /* tlen teoretyczny*/
        double Oc = Ot * lambda;/* tlen całkowity*/

        double V0 = 100.0 / 21.0 * Ot;
        double V_wilg = (1.0 + 1.61 * x) * lambda * V0; /* zapotrzebowanie na powietrze wilgotne*/
        double V = lambda * V0;  /* powietrze całkowite */
        /* Zapotrzebowanie testowane na zadaniu 2.6.14 */

        double V_CO2 = elements.get(CO2) + elements.get(CO) + elements.get(CH4) + 2 * elements.get(C2H2) + 2 * elements.get(C2H4) + 2 * elements.get(C2H6);
        double V_H20 = 2 * elements.get(CH4) + 2 * elements.get(C2H4) + elements.get(C2H2) + 3 * elements.get(C2H6) + elements.get(HYDROGEN)
                + V_MOL / 18 * elements.get(MATERIAL_WATER) + 1.61 * x * V;
        double V_N = elements.get(NITROGEN) + 0.79 * lambda * V0;
        double V_O2 = Oc - Ot;
        double V_spalin = V_CO2 + V_O2 + V_N;
        double V_spalin_wilg = V_spalin + V_H20;

        /* Obliczanie składu i objętości paliwa testowane na zadaniu 2.6.14 */

        System.out.printf("Wartość opałowa: %f kJ/m^3\nCiepło spalania: %f kJ/m^3\n", Qi, Qs);
        System.out.printf("Zapotrzebowanie na powietrze:\n\tsuche: %f m^3/kg" +
                "\n\tmokre: %f m^3/kg\n", V0, V);
        System.out.printf("Udział spalin:\n" +
                "\tCO2 = %f %%\n" +
                "\tH20 = %f %%\n" +
                "\tN   = %f %%\n" +
                "\tO2  = %f %%\n",
                V_CO2 / V_spalin_wilg * 100, V_H20 / V_spalin_wilg * 100.0, V_N / V_spalin_wilg * 100.0, V_O2 / V_spalin_wilg * 100.0);
        System.out.printf("Objętość spalin:\n" +
                "\tsuchych: %f m^3/(kg paliwa)\n" +
                "\twilgotnych: %f m^3/(kg paliwa)\n", V_spalin, V_spalin_wilg);
    }
}