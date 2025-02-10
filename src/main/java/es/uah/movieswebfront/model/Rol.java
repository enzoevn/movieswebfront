// package es.uah.movieswebfront.model;

// import lombok.Getter;
// import lombok.Setter;

// @Getter
// @Setter
// public class Rol {

//     private Integer idRol;

//     private String authority;

//     public Rol() {
//     }

//     public Rol(String idRolAndName){
//         if(idRolAndName != null && idRolAndName.length() > 0){
//             String[] fieldPositions = idRolAndName.split("-");
//             this.idRol = Integer.parseInt(fieldPositions[0]);
//             this.authority = fieldPositions[1];
//         }
//     }

// }


package es.uah.movieswebfront.model;

import org.springframework.security.core.GrantedAuthority;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rol implements GrantedAuthority {

    private Integer idRol;
    private String nombre;

    public Rol() {

    }

    public Rol(Integer idRol, String nombre) {
        this.idRol = idRol;
        this.nombre = nombre;
    }

    @Override
    public String getAuthority() {
        return nombre;
    }
}

