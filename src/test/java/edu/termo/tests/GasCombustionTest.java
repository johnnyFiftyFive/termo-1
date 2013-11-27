package edu.termo.tests;

import edu.termo.CombustionProcess;
import edu.termo.GasFuelCombustion;
import org.junit.After;
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
public class GasCombustionTest {

    private CombustionProcess combustion;

    @Before
    public void prepareFumesTest() {
        HashMap<String, Double> elements = new HashMap<String, Double>();

        elements.put(HYDROGEN, 0.95);
        elements.put(CH4, 0.0);
        elements.put(C2H2, 0.0);
        elements.put(C2H4, 0.0);
        elements.put(C2H6, 0.0);
        elements.put(CO, 0.0);
        elements.put(CO2, 0.0);
        elements.put(OXYGEN, 0.0);
        elements.put(NITROGEN, 0.0);
        elements.put(WATER, 0.05);

        double lambda = 1.0;
        double x = 0.05;

        combustion = new GasFuelCombustion(elements, lambda, x);
    }

    @Test
    public void fumesIntegrityTest() {
        HashMap<String, Double> fumes = combustion.getFumesComposition();

        double sum = 0.0;
        for (Map.Entry<String, Double> entry : fumes.entrySet()) {
            if (entry.getKey().equals(DRY_FUMES) || entry.getKey().equals(WET_FUMES))
                continue;

            sum += (entry.getValue() / fumes.get(WET_FUMES) * 100);
        }

        Assert.assertTrue(String.valueOf(sum), 100.0 == Math.floor(sum));
    }

    /* After modification of fuel composition modification of expected values needed. */
    @Test
    public void heatOfCombustionTest() {
        Assert.assertTrue(String.valueOf(combustion.getHeatOfCombustion()), 10211 == Math.floor(combustion.getHeatOfCombustion()));
    }

    @Test
    public void heatingValueTest() {
        Assert.assertTrue(String.valueOf(combustion.getHeatingValue()), 12243 == Math.floor(combustion.getHeatingValue()));
    }

    private void prepareOxygenRequirementTest() {
        HashMap<String, Double> elements = new HashMap<String, Double>();

        elements.put(HYDROGEN, 0.0);
        elements.put(CH4, 1.0);
        elements.put(C2H2, 0.0);
        elements.put(C2H4, 0.0);
        elements.put(C2H6, 0.0);
        elements.put(CO, 0.0);
        elements.put(CO2, 0.0);
        elements.put(OXYGEN, 0.0);
        elements.put(NITROGEN, 0.0);
        elements.put(WATER, 0.0);

        double lambda = 1.0;
        double x = 0.0;

        combustion = new GasFuelCombustion(elements, lambda, x);
    }

    @Test
    public void theoreticalOxygenTest() {
        prepareOxygenRequirementTest();
        Assert.assertTrue(2 == combustion.getTheoreticalOxygen());
    }
}
