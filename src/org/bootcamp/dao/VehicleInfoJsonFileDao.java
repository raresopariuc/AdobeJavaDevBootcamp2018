package org.bootcamp.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bootcamp.model.VehicleInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class VehicleInfoJsonFileDao implements VehicleInfoDao {

    private final String filePath;

    private final static Map<String, VehicleInfo> map = new HashMap<>();

    public VehicleInfoJsonFileDao(String filePath) {
        this.filePath = filePath;

        final File inputFile = new File(this.filePath);

        try {
            final InputStream inputStream = new FileInputStream(inputFile);
            final ObjectMapper objectMapper = new ObjectMapper();
            List<VehicleInfo> vehicleInfos;

            vehicleInfos = objectMapper.readValue(inputStream, objectMapper.getTypeFactory().constructCollectionType(List.class, VehicleInfo.class));

            for (VehicleInfo vehicleInfo : vehicleInfos) {
                map.put(vehicleInfo.getId(), vehicleInfo);
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
