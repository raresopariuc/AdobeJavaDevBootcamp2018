package org.bootcamp;

import org.bootcamp.service.InsuranceCalculationResult;
import org.bootcamp.service.InsuranceCalculatorService;

import java.text.DecimalFormat;
import java.util.List;

public class MainApp {

    private final static String OUTPUT_FORMAT = "Vehicle with id \"%s\", type %s, has a total cost of $%s.";
    private final static String pattern = "###,###.##";

    public static void main(String[] args) {

        DecimalFormat doubleFormatter = new DecimalFormat(pattern);

        if(args.length >= 1) {
            final InsuranceCalculatorService service = new InsuranceCalculatorService(args[0]);
            final List<InsuranceCalculationResult> resultList = service.calculateAll();

            for (InsuranceCalculationResult result : resultList) {

                final String output = String.format(OUTPUT_FORMAT, result.getId(), result.getVehicleTypeName(),
                        doubleFormatter.format(result.getCost()));
                System.out.println(output);
            }
        } else {
            System.out.println("No arguments!");
        }
    }
}
