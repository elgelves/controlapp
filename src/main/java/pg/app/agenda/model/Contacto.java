package pg.app.agenda.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Contacto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String registro;

    @NotBlank
    private String nombre;

    @NotEmpty
    @Email
    private String email;

    @NotBlank
    private String celular;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @PastOrPresent
    @NotNull
    private LocalDate fechaManualRegistro;

    private LocalDateTime fechaRegistro;

    @PrePersist
    void asignarFechaRegistro(){
        fechaRegistro = LocalDateTime.now();
    }

}
