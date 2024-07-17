package com.aluracursos.challengeforo.domain.topico;


import com.aluracursos.challengeforo.domain.curso.Curso;
import com.aluracursos.challengeforo.domain.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name="topicos")
@Entity(name="Topico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    private boolean status;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;


    public Topico(DatosRegistroTopico datosRegistroTopico, Curso curso, Usuario usuario) {
        this.titulo = datosRegistroTopico.titulo();
        this.mensaje = datosRegistroTopico.mensaje();
        this.fechaCreacion = LocalDateTime.now();
        this.status = true;
        this.usuario = usuario;
        this.curso = curso;
    }

    public void actualizarDatos(DatosActualizarTopico datosActualizarTopico) {
        if(datosActualizarTopico.titulo() !=null){
        this.titulo = datosActualizarTopico.titulo();}
        if (datosActualizarTopico.mensaje() !=null){
        this.mensaje = datosActualizarTopico.mensaje();}
    }

    public void desactivarTopico() {
        this.status= false;
    }
}
