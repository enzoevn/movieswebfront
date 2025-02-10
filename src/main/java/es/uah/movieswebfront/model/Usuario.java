// package es.uah.movieswebfront.model;

// import java.util.List;

// import lombok.Getter;
// import lombok.Setter;

// @Getter
// @Setter
// public class Usuario {

//     private Integer idUsuario;
//     private String nombre;
//     private String clave;
//     private String correo;
//     private boolean enable;
//     private List<Rol> roles;

//     public Usuario(Integer idUsuario, String nombre, String clave, String correo, boolean enable, List<Rol> roles) {
//         this.idUsuario = idUsuario;
//         this.nombre = nombre;
//         this.clave = clave;
//         this.correo = correo;
//         this.enable = enable;
//         this.roles = roles;
//     }

//     public Usuario() {

//     }

//     @Override
//     public String toString() {
//         return "Usuario{" +
//                 "idUsuario=" + idUsuario +
//                 ", nombre='" + nombre + '\'' +
//                 ", clave='" + clave + '\'' +
//                 ", correo='" + correo + '\'' +
//                 ", enable=" + enable +
//                 ", roles=" + roles +
//                 '}';
//     }
// }


package es.uah.movieswebfront.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Usuario {

    private Integer idUsuario;
    private String nombre;
    private String clave;
    private String correo;
    private boolean enable;
    private List<Rol> roles;

    public Usuario(Integer idUsuario, String nombre, String clave, String correo, boolean enable, List<Rol> roles) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.clave = clave;
        this.correo = correo;
        this.enable = enable;
        this.roles = roles != null ? roles : new ArrayList<>();
    }

    public Usuario() {
        this.roles = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", clave='" + clave + '\'' +
                ", correo='" + correo + '\'' +
                ", enable=" + enable +
                ", roles=" + roles +
                '}';
    }

    // public static List<Rol> crearRolesDePrueba() {
    //     List<Rol> rolesDePrueba = new ArrayList<>();
    //     rolesDePrueba.add(new Rol(1, "ADMIN"));
    //     rolesDePrueba.add(new Rol(2, "USER"));
    //     return rolesDePrueba;
    // }
}
