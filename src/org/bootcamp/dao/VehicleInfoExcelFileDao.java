package org.bootcamp.dao;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bootcamp.model.VehicleInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class VehicleInfoExcelFileDao implements VehicleInfoDao {

    private final String filePath;
    private static final int EXCEL_INFORMATION_SHEET = 0;

    private static final int VEHICLE_TYPE = 1;
    private static final int VEHICLE_AGE = 3;
    private static final int VEHICLE_MILES = 4;
    private static final int VEHICLE_IS_DIESEL = 5;
    private static final int VEHICLE_FORMULA = 2;
    private static final int VEHICLE_ID = 0;

    private final static Map<String, VehicleInfo> map = new HashMap<>();

    public VehicleInfoExcelFileDao(String filePath) {

        this.filePath = filePath;

        final File inputFile = new File(this.filePath);

        try {

            final InputStream inputStream = new FileInputStream(inputFile);
            Workbook workbook = new XSSFWorkbook(inputStream);

            final Sheet datatypeSheet = workbook.getSheetAt(EXCEL_INFORMATION_SHEET);
            final Iterator<Row> iterator = datatypeSheet.iterator();

            final VehicleInfo.Builder vehicleInfoBuilder = VehicleInfo.builder();

            while (iterator.hasNext()) {

                final Row currentRow = iterator.next();
                final Iterator<Cell> cellIterator = currentRow.iterator();

                while (cellIterator.hasNext()) {

                    final Cell currentCell = cellIterator.next();
                    final Double tmp;

                    switch (currentCell.getAddress().getColumn()) {
                        case VEHICLE_ID:
                            vehicleInfoBuilder.withId(currentCell.getStringCellValue());
                            break;
                        case VEHICLE_TYPE:
                            vehicleInfoBuilder.withVehicleTypeName(currentCell.getStringCellValue());
                            break;
                        case VEHICLE_FORMULA:
                            vehicleInfoBuilder.withVehicleTypeFormula(currentCell.getStringCellValue());
                            break;
                        case VEHICLE_AGE:
                            tmp = currentCell.getNumericCellValue();
                            vehicleInfoBuilder.withAge(tmp.intValue());
                            break;
                        case VEHICLE_MILES:
                            tmp = currentCell.getNumericCellValue();
                            vehicleInfoBuilder.withNumberOfMiles(tmp.longValue());
                            break;
                        case VEHICLE_IS_DIESEL:
                            vehicleInfoBuilder.withIsDiesel(currentCell.getBooleanCellValue());
                            final VehicleInfo vehicleInfo = vehicleInfoBuilder.build();
                            map.put(vehicleInfo.getId(), vehicleInfo);
                            break;
                    }
                }
            }

        } catch (IOException exception) {
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
