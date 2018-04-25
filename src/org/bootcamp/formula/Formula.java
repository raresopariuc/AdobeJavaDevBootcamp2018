package org.bootcamp.formula;

import org.bootcamp.vehicle.Vehicle;

public enum Formula {
    CAR_BASIC_FORMULA {
        @Override
        public double calculate(Vehicle vehicle) {
            int cost = 100 * vehicle.getAge();
            cost += vehicle.isDiesel() ? 500 : 0;
            cost += vehicle.getNumberOfMiles() > 200000 ? 500 : 0;

            return cost;
        }
    },

    BUS_BASIC_FORMULA {
        @Override
        public double calculate(Vehicle vehicle) {
            int cost = 200 * vehicle.getAge();
            cost += vehicle.isDiesel() ? 1000 : 0;
            if (vehicle.getNumberOfMiles() > 200000) {
                cost += 1000;
            } else if (vehicle.getNumberOfMiles() > 100000) {
                cost += 500;
            }

            return cost;
        }
    },

    TIPPER_BASIC_FORMULA {
        @Override
        public double calculate(Vehicle vehicle) {
            int cost = 300 * vehicle.getAge();
            cost += vehicle.getNumberOfMiles() > 80000 ? 700 : 0;

            return cost;
        }
    },

    CAR_CHRISTMAS_FORMULA {
        @Override
        public double calculate(Vehicle vehicle) {
            return 0.95 * CAR_BASIC_FORMULA.calculate(vehicle);
        }
    },

    BUS_CHRISTMAS_FORMULA {
        @Override
        public double calculate(Vehicle vehicle) {
            return 0.90 * BUS_BASIC_FORMULA.calculate(vehicle);
        }
    },

    TIPPER_CHRISTMAS_FORMULA {
        @Override
        public double calculate(Vehicle vehicle) {
            return 0.85 * TIPPER_BASIC_FORMULA.calculate(vehicle);
        }
    };

    public abstract double calculate(Vehicle vehicle);
}
