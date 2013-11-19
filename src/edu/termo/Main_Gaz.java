package edu.termo;

import java.util.HashMap;

import static edu.termo.KeyNames.*;

/**
 * @author Kamil Sikora
 *         Data: 18.11.13
 */
public class Main_Gaz {
    public static void main(String[] args) {

        /*
        final String CO = "CO";
        final String C2H4 = "C2H4";
        final String CH4 = "CH4";
        final String H2 = "H2";
        final String O2 = "O2";
        HashMap<String, Double> WSPOCZYNNIK_UTLENIANIA = new HashMap<String, Double>();
        WSPOCZYNNIK_UTLENIANIA.put(CO, 0.5);
        WSPOCZYNNIK_UTLENIANIA.put(C2H4, 30.0);
        WSPOCZYNNIK_UTLENIANIA.put(CH4, 2.0);
        WSPOCZYNNIK_UTLENIANIA.put(H2, 0.5);
        WSPOCZYNNIK_UTLENIANIA.put(O2, 1.0);

        HashMap<String, Double> PROCENT_CZASTECZEK = new HashMap<String, Double>();
        PROCENT_CZASTECZEK.put(CO, 0.5);
        PROCENT_CZASTECZEK.put(CH4, 0.27);
        */


        final double LAMBDA = 1.0; /* Stosunek nadmiaru powietrza*/
        final double V_MOL = 22.42;

        double fi = 0.1; /* procentowy współczynnik zawilgocenai powietrza*/
        HashMap<String, Double> elements = new HashMap<String, Double>();
        elements.put("CO2", 0.676);
        elements.put("CO", 0.039);
        elements.put("CH4", 0.01);
        elements.put("C2H2", 0.098);
        elements.put("C2H6", 0.01);
        elements.put("H2", 0.098);
        elements.put("H2O", 0.069);
        elements.put("N2", 0.05);

        double Qi = 35897 * elements.get("CH4") + 12629 * elements.get("CO") + 56040 * elements.get("C2H2")
                + 63729 * elements.get("C2H6") + 10749 * elements.get("H2");
        System.out.println("Qi = " + Qi);

        double r0 = 2500;
        double w = elements.get(WATER) + 9 * elements.get(HYDROGEN);

        double Qs = Qi + r0 * w;


        double Ot = V_MOL * (elements.get(CARBON) / 12 + elements.get(HYDROGEN) / 4 + elements.get(SULFUR) / 32 + elements.get(OXYGEN) / 32);/* tlen teoretyczny*/
        double Oc = Ot * LAMBDA; /*tlen całkowity*/
        double V0 = 100 / 21 * Ot; /* zapotrzebowanie na powietrze suche*/
        double V_wilg = (1 + 1.6 * fi) * LAMBDA * V0; /* zapotrzebowanie na powietrze wilgotne*/
        double V = LAMBDA * V0; /* ??? */

        System.out.println(V0);
        System.out.println(V_wilg);

        double V_CO2 = V_MOL / 12 * elements.get(CARBON);
        double V_SO2 = V_MOL / 32 * elements.get(SULFUR);
        double V_H20 = V_MOL * (elements.get(WATER) / 18 + elements.get(HYDROGEN) / 2);
        double V_N = V_MOL / 28 * elements.get(NITROGEN) + 0.79 * LAMBDA;
        double V_O2 = Ot * (1 / LAMBDA);
        double V_spalin = V_CO2 + V_SO2 + V_O2 + V_N;
        double V_spalin_wilg = V_spalin + V_H20;
    }
}
