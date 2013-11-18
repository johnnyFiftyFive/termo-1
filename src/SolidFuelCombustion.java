import java.util.HashMap;

/**
 * @author Kamil Sikora
 *         Data: 18.11.13
 */
public class SolidFuelCombustion {
    final double LAMBDA = 1.6;
    final double V_MOL = 22.42;

    HashMap<String, Double> elements;

    public SolidFuelCombustion(HashMap<String, Double> elements){
        this.elements = elements;
    }

    public static void main(String[] args) {
        final double LAMBDA = 1.6; /* Stosunek nadmiaru powietrza*/
        final double V_MOL = 22.42;

        double fi = 0.0; /* procentowy współczynnik zawilgocenai powietrza*/
        HashMap<String, Double> elements = new HashMap<String, Double>();
        elements.put("C", 0.5);
        elements.put("H", 0.03);
        elements.put("S", 0.008);
        elements.put("O", 0.168);
        elements.put("N", 0.006);
        elements.put("A", 0.09);
        elements.put("W", 0.198);

        double Qi = 34080 * elements.get("C") + 142770 * (elements.get("H") - elements.get("O") / 8)
                + 9290 * elements.get("S") - 2500 * (elements.get("W") + 9 * elements.get("H"));

        double r0 = 2500;
        double w = elements.get("W") + 9 * elements.get("H");

        double Qs = Qi + r0 * w;
        /* Obliczenie Qi i Qs testowane na zadaniu 2.6.1 ze skryptu*/

        double Ot = V_MOL * (elements.get("C") / 12 + elements.get("H") / 4 + elements.get("S") / 32 - elements.get("O") / 32); /*tlen teoretyczny*/
        double Oc = Ot * LAMBDA; /*tlen całkowity*/

        System.out.println(Ot + " " + Oc);

        double V0 = 100.0 / 21.0 * Ot;
        double V_wilg = (1.0 + 1.61 * fi) * LAMBDA * V0; /* zapotrzebowanie na powietrze wilgotne*/
        double V = LAMBDA * V0;  /* powietrze całkowite */

        double V_CO2 = V_MOL / 12.0 * elements.get("C");
        double V_SO2 = V_MOL / 32.0 * elements.get("S");
        double V_H20 = V_MOL * (elements.get("W") / 18 + elements.get("H") / 2);
        double V_N = V_MOL / 28.0 * elements.get("N") + 0.79 * LAMBDA * V0;
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

    public void printCombustionParameters() {
        //To change body of created methods use File | Settings | File Templates.
    }
}
