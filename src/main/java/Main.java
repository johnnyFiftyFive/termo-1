import edu.termo.CombustionProcess;
import edu.termo.GasFuelCombustion;
import edu.termo.SolidFuelCombustion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import static edu.termo.KeyNames.*;

/**
 * @author Kamil Sikora
 *         Data: 19.11.13
 */
public class Main {
    public static void main(String[] args) {
        System.out.printf("Program do obliczania wartości opałowej i składu spalin paliwa stałego oraz gazowego.\nWybierz opcję:\n" +
                "1 - paliwo stałe\n2 - paliwo gazowe\n");
        boolean flag = true;
        int option = -1;
        while (flag) {
            String input = readInput();
            try {
                option = Integer.parseInt(input);
                if (option != 1 && option != 2)
                    throw new Exception();
            } catch (Exception e) {
                System.err.println("Niepoprawny wybór!");
                continue;
            }
            flag = false;
        }

        flag = true;
        HashMap<String, Double> elements = new HashMap<String, Double>();
        CombustionProcess combustionProcess = null;

        double lambda = 0, x = 0, w = 0;
        switch (option) {
            case 1:
                System.out.println("Wybrano paliwo stałe.\nPodaj robocze udziały procentowe składników:");
                while (flag) {
                    try {
                        System.out.print("\tWęgiel: ");
                        elements.put(CARBON, Double.parseDouble(readInput()) / 100);
                        System.out.print("\tWodór: ");
                        elements.put(HYDROGEN, Double.parseDouble(readInput()) / 100);
                        System.out.print("\tSiarka: ");
                        elements.put(SULFUR, Double.parseDouble(readInput()) / 100);
                        System.out.print("\tTlen: ");
                        elements.put(OXYGEN, Double.parseDouble(readInput()) / 100);
                        System.out.print("\tAzot: ");
                        elements.put(NITROGEN, Double.parseDouble(readInput()) / 100);
                        System.out.print("\tPopiół: ");
                        elements.put(ASH, Double.parseDouble(readInput()) / 100);
                        System.out.print("\tWilgotność: ");
                        elements.put(WATER, Double.parseDouble(readInput()) / 100);
                        System.out.print("\tWspółczynnik nadmiaru powietrza: ");
                        lambda = Double.parseDouble(readInput());
                        System.out.print("\tWilgotność powietrza [(kg H20)/(kg paliwa): ");
                        x = Double.parseDouble(readInput());
                    } catch (Exception e) {
                        System.err.println("Podana wartość nie jest wartością liczbową!");
                        continue;
                    }
                    flag = false;
                }

                combustionProcess = new SolidFuelCombustion(elements, lambda, x);
                break;

            case 2:
                System.out.println("Wybrano paliwo stałe.\nPodaj robocze udziały procentowe składników:");
                while (flag) {
                    try {
                        System.out.print("\tWodór: ");
                        elements.put(HYDROGEN, Double.parseDouble(readInput()) / 100);
                        System.out.print("\tCH4: ");
                        elements.put(CH4, Double.parseDouble(readInput()) / 100);
                        System.out.print("\tC2H2: ");
                        elements.put(C2H2, Double.parseDouble(readInput()) / 100);
                        System.out.print("\tC2H4: ");
                        elements.put(C2H4, Double.parseDouble(readInput()) / 100);
                        System.out.print("\tC2H6: ");
                        elements.put(C2H6, Double.parseDouble(readInput()) / 100);
                        System.out.print("\tCO: ");
                        elements.put(CO, Double.parseDouble(readInput()) / 100);
                        System.out.print("\tCO2: ");
                        elements.put(CO2, Double.parseDouble(readInput()) / 100);
                        System.out.print("\tTlen: ");
                        elements.put(OXYGEN, Double.parseDouble(readInput()) / 100);
                        System.out.print("\tAzot: ");
                        elements.put(NITROGEN, Double.parseDouble(readInput()) / 100);
                        System.out.print("\tWoda: ");
                        elements.put(WATER, Double.parseDouble(readInput()) / 100);
                        System.out.print("\tWspółczynnik nadmiaru powietrza: ");
                        lambda = Double.parseDouble(readInput());
                        System.out.print("\tWilgotność powietrza [(kg H20)/(kg paliwa): ");
                        x = Double.parseDouble(readInput());
                    } catch (Exception e) {
                        System.err.println("Podana wartość nie jest wartością liczbową!");
                        continue;
                    }
                    flag = false;
                }

                combustionProcess = new GasFuelCombustion(elements, lambda, x);
                break;
        }

        combustionProcess.printInfo();
    }

    private static String readInput() {
        String input = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            input = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return input;
    }
}
