package entity;

import lombok.Data;

@Data
public class Factura {
    Integer idCliente;
    Integer idFactura;
    public Factura(Integer idCliente, Integer idFactura) {
        this.idCliente = idCliente;
        this.idFactura = idFactura;
    }
}
