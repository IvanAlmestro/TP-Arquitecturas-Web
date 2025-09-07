package entity;
import lombok.*;
@Data
public class Cliente {
    String nombre;
    Integer idCliente;
    String email;
    public Cliente(Integer id, String nombre, String email) {
        this.idCliente = id;
        this.nombre = nombre;
        this.email = email ;
    }
}
