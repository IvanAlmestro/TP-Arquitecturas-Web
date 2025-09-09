package entity;

import lombok.Data;

@Data
public class Producto {
    String nombre;
    Integer idProducto;
    Double valor;
    public Producto(Integer id, String nombre, Double valor) {
        this.idProducto = id;
        this.nombre = nombre;
        this.valor = valor ;
    }
}
