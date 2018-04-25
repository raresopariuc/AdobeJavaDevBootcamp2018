package org.bootcamp.dao;

import org.bootcamp.model.VehicleInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public final class VehicleInfoPlainFileDao implements VehicleInfoDao {

    private static final String SEPARATOR = ";";

    private static final int VEHICLE_TYPE = 1;
    private static final int VEHICLE_AGE = 3;
    private static final int VEHICLE_MILES = 4;
    private static final int VEHICLE_IS_DIESEL = 5;
    private static final int VEHICLE_FORMULA = 2;
    private static final int VEHICLE_ID = 0;

    private final String filePath;

    private final static Map<String, VehicleInfo> map = new HashMap<>();

    public VehicleInfoPlainFileDao(String filePath) {

        this.filePath = filePath;

        final File inputFile = new File(this.filePath);

        try {
            final InputStream inputStream = new FileInputStream(inputFile);
            final Scanner scanner = new Scanner(inputStream);
            final VehicleInfo.Builder builder = VehicleInfo.builder();

            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();
                final String[] tokens = line.split(SEPARATOR);

                final VehicleInfo vehicleInfo = builder.withId(tokens[VEHICLE_ID])
                        .withVehicleTypeName(tokens[VEHICLE_TYPE])
                        .withVehicleTypeFormula(tokens[VEHICLE_FORMULA])
                        .withAge(Integer.parseInt(tokens[VEHICLE_AGE]))
                        .withNumberOfMiles(Long.parseLong(tokens[VEHICLE_MILES]))
                        .withIsDiesel(Boolean.parseBoolean(tokens[VEHICLE_IS_DIESEL]))
                        .build();

                map.put(tokens[VEHICLE_ID], vehicleInfo);
            }

            scanner.close();

        } catch (FileNotFoundException noFileFound) {
            throw new IllegalStateException(String.format("Cannot create instance of %s!",
                    this.getClass().getSimpleName()));
        }
    }

    @Override
    public List<VehicleInfo> getAllVehicles() {
        return new ArrayList<>(map.values());
    }

    @Override
    public VehicleInfo getVehicleInfoById(String id) {
        return map.get(id);
    }
}
