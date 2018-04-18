package org.bootcamp;

public final class BusBasicFormula implements Formula {
    @Override
    public int calculate(Vehicle vehicle) {
        int cost = 200 * vehicle.getAge();
        cost += vehicle.isDiesel() ? 1000 : 0;
        if (vehicle.getNumberOfMiles() > 200000) {
            cost += 1000;
        } else if (vehicle.getNumberOfMiles() > 100000) {
            cost += 500;
        }

        return cost;
    }
}
