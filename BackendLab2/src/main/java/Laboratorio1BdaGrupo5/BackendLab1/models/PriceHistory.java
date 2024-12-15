package Laboratorio1BdaGrupo5.BackendLab1.models;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PriceHistory {
    BigDecimal precio;
    Date fecha;
}
