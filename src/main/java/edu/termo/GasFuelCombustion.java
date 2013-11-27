package edu.termo;

import java.util.HashMap;

import static edu.termo.KeyNames.*;

/**
 * Class computes heat of combustion, caloric value, required oxygen and exhaust composition from combustion of gas fuel.
 */
public class GasFuelCombustion extends CombustionProcess {

    /**
     * @param elements associative with elements share of solid fuel
     * @param lambda   air fuel ratio
     * @param x        air absolute humidity [(kg of H20)/(kg of fuel)
     */
    public GasFuelCombustion(HashMap<String, Double> elements, double lambda, double x) {
        super(x, lambda);
        this.elements = elements;
    }


    public HashMap<String, Double> getFumesVolumes() {
        getHeatingValue();

        /* Obliczenie Qi i Qs testowane na zadaniu 2.6.2 ze skryptu*/

        Ot = getTheoreticalOxygen(); /* tlen teoretyczny*/
        double Oc = Ot * lambda;/* tlen całkowity*/

        double V0 = 100.0 / 21.0 * Ot;
        double V_wilg = (1.0 + 1.61 * x) * lambda * V0; /* zapotrzebowanie na powietrze wilgotne*/
        double V = lambda * V0;  /* powietrze całkowite */
        /* Zapotrzebowanie testowane na zadaniu 2.6.14 */

        HashMap<String, Double> fumes = new HashMap<String, Double>();

        double V_CO2 = elements.get(CO2) + elements.get(CO) + elements.get(CH4) + 2 * elements.get(C2H2) + 2 * elements.get(C2H4) + 2 * elements.get(C2H6);
        double V_H20 = 2 * elements.get(CH4) + 2 * elements.get(C2H4) + elements.get(C2H2) + 3 * elements.get(C2H6) + elements.get(HYDROGEN)
                + V_MOL / 18 * elements.get(WATER) + 1.61 * x * V;
        double V_N = elements.get(NITROGEN) + 0.79 * lambda * V0;
        double V_O2 = Oc - Ot;
        double V_spalin = V_CO2 + V_O2 + V_N;
        double V_spalin_wilg = V_spalin + V_H20;

        fumes.put(CO2, V_CO2);
        fumes.put(WATER, V_H20);
        fumes.put(NITROGEN, V_N);
        fumes.put(OXYGEN, V_O2);
        fumes.put(KeyNames.DRY_FUMES, V_spalin);
        fumes.put(KeyNames.WET_FUMES, V_spalin_wilg);

        /* Obliczanie składu i objętości paliwa testowane na zadaniu 2.6.14 */


        return fumes;
    }

    @Override
    public double getHeatOfCombustion() {
        return Qi == null ? Qi = 12629 * elements.get(CO) + 10749 * elements.get(HYDROGEN) + 35810 * elements.get(CH4)
                + 62974 * elements.get(C2H4) + 56040 * elements.get(C2H2) + 63729 * elements.get(C2H6)
                : Qi;
    }

    @Override
    public double getHeatingValue() {
        if (Qs == null) {
            double ww = 18.0 / V_MOL * (elements.get(HYDROGEN) + 2 * elements.get(CH4) + 2 * elements.get(C2H4)
                    + 3 * elements.get(C2H6) + elements.get(C2H2)) + elements.get(WATER);

            Qs = (Qi == null ? getHeatOfCombustion() : Qi) + r0 * ww;
        }

        return Qs;
    }

    @Override
    public Double getTheoreticalOxygen() {
        return Ot == null ? Ot = 0.5 * elements.get(CO) + 2 * elements.get(CH4) + 3.5 * elements.get(C2H6) +
                3 * elements.get(C2H4) + 2.5 * elements.get(C2H2) + 0.5 * elements.get(HYDROGEN) - elements.get(OXYGEN) : Ot;
    }

    @Override
    public void printInfo() {
        HashMap<String, Double> fumesVolumes = getFumesVolumes();
        double V_spalin_wilg = elements.get(WET_FUMES);
        double V0 = 100.0 / 21.0 * getTheoreticalOxygen();

        System.out.printf("\nWartość opałowa: %f kJ/m^3\nCiepło spalania: %f kJ/m^3\n", Qi, Qs);
        System.out.printf("Tlen teoretyczny: %f m^3\nTlen całkowity: %f m^3\n", Ot, Ot * lambda);
        System.out.printf("Zapotrzebowanie na powietrze:\n\tsuche: %f m^3/kg" +
                "\n\tmokre: %f m^3/kg\n", V0, V0 * lambda);
        System.out.printf("Udział spalin:\n" +
                "\tCO2 = %f %%\n" +
                "\tH20 = %f %%\n" +
                "\tN   = %f %%\n" +
                "\tO2  = %f %%\n",
                fumesVolumes.get(CO2) / V_spalin_wilg * 100, fumesVolumes.get(WATER) / V_spalin_wilg * 100.0, fumesVolumes.get(NITROGEN) / V_spalin_wilg * 100.0, fumesVolumes.get(OXYGEN) / V_spalin_wilg * 100.0);
        System.out.printf("Objętość spalin:\n" +
                "\tsuchych: %f m^3/(m^3 paliwa)\n" +
                "\twilgotnych: %f m^3/(m^3 paliwa)\n", fumesVolumes.get(DRY_FUMES), V_spalin_wilg);

    }
}