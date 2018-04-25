package org.bootcamp.service;

import org.bootcamp.calculator.InsurancePolicyCalculator;
import org.bootcamp.dao.VehicleInfoDao;
import org.bootcamp.dao.VehicleInfoExcelFileDao;
import org.bootcamp.formula.Formula;
import org.bootcamp.model.VehicleInfo;
import org.bootcamp.vehicle.Vehicle;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.bootcamp.service.ConversionUtils.getVehicle;

public final class InsuranceCalculatorService {
    private final VehicleInfoDao vehicleInfoDao;

    public InsuranceCalculatorService(String filePath) {
        vehicleInfoDao = new VehicleInfoExcelFileDao(filePath);
    }

    public List<InsuranceCalculationResult> calculateAll() {
        final InsurancePolicyCalculator calculator = InsurancePolicyCalculator.INSTANCE;

        final List<VehicleInfo> vehicleInfos = vehicleInfoDao.getAllVehicles();

        final List<InsuranceCalculationResult> resultList = new LinkedList<>();

        if (vehicleInfos.isEmpty()) {
            return Collections.emptyList();
        } else {
            for (VehicleInfo info : vehicleInfos) {
                final Vehicle vehicle = getVehicle(info.getVehicleTypeName(), info.getAge(),
                        info.getNumberOfMiles(), info.isDiesel());

                final Formula formula = Formula.valueOf(info.getVehicleTypeFormula());

                final double totalCost = calculator.calculate(vehicle, formula);

                final InsuranceCalculationResult result = new InsuranceCalculationResult(info.getId(),
                        info.getVehicleTypeName(), totalCost);

                resultList.add(result);
            }

            return resultList;
        }
    }

    public InsuranceCalculationResult calculateById(String id) {
        final InsurancePolicyCalculator calculator = InsurancePolicyCalculator.INSTANCE;

        final VehicleInfo vehicleInfo = vehicleInfoDao.getVehicleInfoById(id);

        final Vehicle vehicle = getVehicle(vehicleInfo.getVehicleTypeName(), vehicleInfo.getAge(),
                vehicleInfo.getNumberOfMiles(), vehicleInfo.isDiesel());

        final Formula formula = Formula.valueOf(vehicleInfo.getVehicleTypeFormula());

        final double totalCost = calculator.calculate(vehicle, formula);

        final InsuranceCalculationResult result = new InsuranceCalculationResult(id, vehicleInfo.getVehicleTypeName(),
                totalCost);

        return result;
    }

    public List<InsuranceCalculationResult> getCostsHigherThan(int cost) {
        final List<InsuranceCalculationResult> resultList = this.calculateAll();

        for (Iterator<InsuranceCalculationResult> iterator = resultList.listIterator(); iterator.hasNext(); ) {
            InsuranceCalculationResult result = iterator.next();
            if (result.getCost() <= cost) {
                iterator.remove();
            }
        }

        return resultList;
    }
}
