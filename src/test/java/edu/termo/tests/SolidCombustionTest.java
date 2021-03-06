package edu.termo.tests;

import edu.termo.CombustionProcess;
import edu.termo.SolidFuelCombustion;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static edu.termo.KeyNames.*;

/**
 * @author Kamil Sikora
 *         Data: 27.11.13
 */
public class SolidCombustionTest {

    private CombustionProcess combustion;

    @Before
    public void prepareSolidCombustionTest() {
        HashMap<String, Double> elements = new HashMap<String, Double>();

        elements.put(CARBON, 0.7);
        elements.put(HYDROGEN, 0.1);
        elements.put(SULFUR, 0.0);
        elements.put(OXYGEN, 0.1);
        elements.put(NITROGEN, 0.0);
        elements.put(ASH, 0.0);
        elements.put(WATER, 0.1);
        double lambda = 1.4;
        double x = 0.0;

        combustion = new SolidFuelCombustion(elements, lambda, x);
    }

    @Test
    public void fumesIntegrityTest() {
        HashMap<String, Double> fumes = combustion.getFumesVolumes();

        double sum = 0.0;
        for (Map.Entry<String, Double> entry : fumes.entrySet()) {
            if (entry.getKey().equals(DRY_FUMES) || entry.getKey().equals(WET_FUMES))
                continue;

            sum += (entry.getValue() / fumes.get(WET_FUMES) * 100.0);
        }

        Assert.assertTrue("Sum of fumes volume: " + sum, 100.0 == sum);
    }

    /* After modification of fuel composition modification of expected values needed. */
    @Test
    public void heatOfCombustionTest() {
        Assert.assertTrue(String.valueOf(combustion.getHeatOfCombustion()), 33848.375 == combustion.getHeatOfCombustion());
    }

    @Test
    public void heatingValueTest() {
        Assert.assertTrue(String.valueOf(combustion.getHeatingValue()), 36348.375 == combustion.getHeatingValue());
    }
}
