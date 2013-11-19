package edu.termo;

import java.util.HashMap;

import static edu.termo.KeyNames.*;

public class GasFuelCombustion {
    final double V_MOL = 22.42;
    HashMap<String, Double> elements;

    double fi; /* procentowy współczynnik zawilgocenai powietrza*/
    double lambda;

    /**
     * @param elements associative with elements share of solid fuel
     * @param lambda   air fuel ratio
     * @param fi       air humidity percentage
     */
    public GasFuelCombustion(HashMap<String, Double> elements, double lambda, double fi) {
        this.elements = elements;
        this.fi = fi;
        this.lambda = lambda;
    }

    public void printCombustionParameters() {
        double Qi = 12629 * elements.get(CO) + 10749 * elements.get(HYDROGEN) + 35810 * elements.get(CH4)
                + 62974 * elements.get(C2H4) + 56040 * elements.get(C2H2) + 63729 * elements.get(C2H6);

        double r0 = 2500;
        double w = 18.0 / V_MOL * (elements.get(HYDROGEN) + 2 * elements.get(CH4) + 2 * elements.get(C2H4)
                + 3 * elements.get(C2H6) + elements.get(C2H2));

        double Qs = Qi + r0 * w;

        /* Obliczenie Qi i Qs testowane na zadaniu 2.6.2 ze skryptu*/

        double Ot = Ot = 0.5 * elements.get(CO) + 2 * elements.get(CH4) + 3.5 * elements.get(C2H6) +
                3 * elements.get(C2H4) + 2.5 * elements.get(C2H2) + 0.5 * elements.get(HYDROGEN) - elements.get(OXYGEN); /* tlen teoretyczny*/
        double Oc = Ot * lambda;/* tlen całkowity*/

        double V0 = 100.0 / 21.0 * Ot;
        double V_wilg = (1.0 + 1.61 * fi) * lambda * V0; /* zapotrzebowanie na powietrze wilgotne*/
        double V = lambda * V0;  /* powietrze całkowite */
        /* Zapotrzebowanie testowane na zadaniu 2.6.9 */

       /* double V_CO2 = V_MOL / 12.0 * elements.get(CARBON);
        double V_SO2 = V_MOL / 32.0 * elements.get(SULFUR);
        double V_H20 = V_MOL * (elements.get(WATER) / 18 + elements.get(HYDROGEN) / 2);
        double V_N = V_MOL / 28.0 * elements.get(NITROGEN) + 0.79 * lambda * V0;
        double V_O2 = Oc - Ot;
        double V_spalin = V_CO2 + V_SO2 + V_O2 + V_N;
        double V_spalin_wilg = V_spalin + V_H20;*/

        /* Obliczanie składu i objętości paliwa testowane na zadaniu 2.6.11 */

        System.out.printf("Wartość opałowa: %f kJ/m^3\nCiepło spalania: %f kJ/m^3\n", Qi, Qs);
        System.out.printf("Zapotrzebowanie na powietrze:\n\tsuche: %f m^3/kg" +
                "\n\tmokre: %f m^3/kg\n", V0, V);
        /*
        System.out.printf("Udział spalin:\n" +
                "\tCO2 = %f m^3/(kg paliwa)\n" +
                "\tSO2 = %f m^3/(kg paliwa)\n" +
                "\tH20 = %f m^3/(kg paliwa)\n" +
                "\tN   = %f m^3/(kg paliwa)\n" +
                "\tO2  = %f m^3/(kg paliwa)\n", V_CO2, V_SO2, V_H20, V_N, V_O2);
        System.out.printf("Objętość spalin:\n" +
                "\tsuchych: %f m^3/(kg paliwa)\n" +
                "\twilgotnych: %f m^3/(kg paliwa)\n", V_spalin, V_spalin_wilg);*/
    }
}