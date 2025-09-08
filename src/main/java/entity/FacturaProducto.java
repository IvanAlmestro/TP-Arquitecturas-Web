package entity;

import lombok.Data;

@Data
public class FacturaProducto {
    Integer idFactura;
    Integer idProducto;
    Integer cantidad;

    public FacturaProducto(Integer idProducto, Integer cantidad, Integer idFactura) {
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.idFactura = idFactura;
    }
}
