package edu.termo;

import java.util.HashMap;

import static edu.termo.KeyNames.*;

/**
 * @author Kamil Sikora
 *         Data: 18.11.13
 */
public class SolidFuelCombustion {
    final double LAMBDA = 1.6;
    final double V_MOL = 22.42;

    HashMap<String, Double> elements;
    double fi; /* procentowy współczynnik zawilgocenai powietrza*/

    /**
     * @param elements - associative with elements share of solid fuel
     */
    public SolidFuelCombustion(HashMap<String, Double> elements, double fi) {
        this.elements = elements;
        this.fi = fi;
    }

    public void printCombustionParameters() {
        double Qi = 34080 * elements.get(CARBON) + 142770 * (elements.get(HYDROGEN) - elements.get(OXYGEN) / 8)
                + 9290 * elements.get(SULFUR) - 2500 * (elements.get(WATER) + 9 * elements.get(HYDROGEN));

        double r0 = 2500;
        double w = elements.get(WATER) + 9 * elements.get(HYDROGEN);

        double Qs = Qi + r0 * w;
        /* Obliczenie Qi i Qs testowane na zadaniu 2.6.1 ze skryptu*/

        double Ot = V_MOL * (elements.get(CARBON) / 12 + elements.get(HYDROGEN) / 4 + elements.get(SULFUR) / 32 - elements.get(OXYGEN) / 32); /*tlen teoretyczny*/
        double Oc = Ot * LAMBDA; /*tlen całkowity*/

        double V0 = 100.0 / 21.0 * Ot;
        double V_wilg = (1.0 + 1.61 * fi) * LAMBDA * V0; /* zapotrzebowanie na powietrze wilgotne*/
        double V = LAMBDA * V0;  /* powietrze całkowite */

        double V_CO2 = V_MOL / 12.0 * elements.get(CARBON);
        double V_SO2 = V_MOL / 32.0 * elements.get(SULFUR);
        double V_H20 = V_MOL * (elements.get(WATER) / 18 + elements.get(HYDROGEN) / 2);
        double V_N = V_MOL / 28.0 * elements.get(NITROGEN) + 0.79 * LAMBDA * V0;
        double V_O2 = Oc - Ot;
        double V_spalin = V_CO2 + V_SO2 + V_O2 + V_N;
        double V_spalin_wilg = V_spalin + V_H20;

        /* Obliczanie składu i objętości paliwa testowane na zadaniu 2.6.11 */

        System.out.printf("Wartość opałowa: %f kJ/kg\nCiepło spalania: %f kJ/kg\n", Qi, Qs);
        System.out.printf("Zapotrzebowanie na powietrze:\n\tsuche: %f m^3/kg" +
                "\n\tmokre: %f m^3/kg\n", V0, V);
        System.out.printf("Udział spalin:\n" +
                "\tCO2 = %f m^3/(kg paliwa)\n" +
                "\tSO2 = %f m^3/(kg paliwa)\n" +
                "\tH20 = %f m^3/(kg paliwa)\n" +
                "\tN   = %f m^3/(kg paliwa)\n" +
                "\tO2  = %f m^3/(kg paliwa)\n", V_CO2, V_SO2, V_H20, V_N, V_O2);
        System.out.printf("Objętość spalin:\n" +
                "\tsuchych: %f m^3/(kg paliwa)\n" +
                "\twilgotnych: %f m^3/(kg paliwa)\n", V_spalin, V_spalin_wilg);
    }
}
