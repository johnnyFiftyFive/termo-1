import edu.termo.GasFuelCombustion;
import edu.termo.SolidFuelCombustion;

import java.util.HashMap;

import static edu.termo.KeyNames.*;

/**
 * @author Kamil Sikora
 *         Data: 19.11.13
 */
public class Main {
    public static void main(String[] args) {
        HashMap<String, Double> elements = new HashMap<String, Double>();
        elements.put(CARBON, 0.5);
        elements.put(HYDROGEN, 0.03);
        elements.put(SULFUR, 0.008);
        elements.put(OXYGEN, 0.168);
        elements.put(NITROGEN, 0.006);
        elements.put(ASH, 0.09);
        elements.put(WATER, 0.198);

        SolidFuelCombustion solid = new SolidFuelCombustion(elements, 0.0, 1.1);
//        solid.printCombustionParameters();

        HashMap<String, Double> gasElements = new HashMap<String, Double>();
        gasElements.put(HYDROGEN, 0.50);
        gasElements.put(CH4, 0.27);
        gasElements.put(C2H2, 0.0);
        gasElements.put(C2H4, 0.03);
        gasElements.put(C2H6, 0.0);
        gasElements.put(CO, 0.05);
        gasElements.put(CO2, 0.02);
        gasElements.put(OXYGEN, 0.01);
        gasElements.put(NITROGEN, 0.12);

        GasFuelCombustion gas = new GasFuelCombustion(gasElements, 1.1, 1.1);
        gas.printCombustionParameters();
    }
}
