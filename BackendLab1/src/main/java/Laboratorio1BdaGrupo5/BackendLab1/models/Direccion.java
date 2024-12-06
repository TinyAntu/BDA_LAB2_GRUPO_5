package Laboratorio1BdaGrupo5.BackendLab1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Geometry;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {
    private Integer id_direccion;

    private String tipo;
    @JsonIgnore
    private Geometry geom;

    @JsonProperty("geom")
    public Object getGeomAsGeoJSON() {
        if (geom == null) {
            return null;
        }
        return new GeoJSON(geom); // Convert Geometry to GeoJSON
    }
}
