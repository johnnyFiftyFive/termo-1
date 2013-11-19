import edu.termo.SolidFuelCombustion;
import static edu.termo.KeyNames.*;
import java.util.HashMap;

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

        SolidFuelCombustion solid = new SolidFuelCombustion(elements, 0.0);
        solid.printCombustionParameters();
    }
}
