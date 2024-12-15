package Laboratorio1BdaGrupo5.BackendLab1.models;

import lombok.Data;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;

import java.util.HashMap;
import java.util.Map;

@Data
public class GeoJSON {
    private final String type;
    private final Object coordinates;

    public GeoJSON(Geometry geometry) {
        if (geometry instanceof Point) {
            this.type = "Point";
            Point point = (Point) geometry;
            this.coordinates = new double[]{point.getX(), point.getY()};
        } else {
            throw new UnsupportedOperationException("Unsupported Geometry type");
        }
    }

    public Map<String, Object> toMap() {
        Map<String, Object> geoJSON = new HashMap<>();
        geoJSON.put("type", type);
        geoJSON.put("coordinates", coordinates);
        return geoJSON;
    }
}