package com.aluracursos.challengeforo.controller;



import com.aluracursos.challengeforo.domain.curso.CursoRepository;
import com.aluracursos.challengeforo.domain.topico.*;
import com.aluracursos.challengeforo.domain.usuarios.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico) {
        var nombreCurso = datosRegistroTopico.nombreCurso();
        var idUsuario= datosRegistroTopico.idUsuario();
        var OptionalCurso = cursoRepository.findByNombre(nombreCurso);
        var OptionalUsuario = usuarioRepository.findById(idUsuario);
        if (OptionalCurso.isPresent() || OptionalUsuario.isPresent()) {
            //get curso y usuario
            var Curso = OptionalCurso.get();
            var Usuario = OptionalUsuario.get();
            Topico topico = new Topico(datosRegistroTopico,Curso,Usuario);
            //se actualiza la relacion bidireccional
            Curso.addTopico(topico);
            Usuario.addTopico(topico);
            topicoRepository.save(topico);
            DatosRespuestaTopico datosRespuestaTopico= new DatosRespuestaTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion());
            URI url = UriComponentsBuilder.fromPath("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
            return ResponseEntity.created(url).body(datosRespuestaTopico);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<DatosRespuestaTopico>> listadoTopico(Pageable paginacion){
        return ResponseEntity.ok(topicoRepository.findByStatusTrue(paginacion).map(topico -> new DatosRespuestaTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion())));
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> retornarDatosTopico(@PathVariable Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        var datosTopico = new DatosRespuestaTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion());
        return ResponseEntity.ok(datosTopico);
    }

    //actualizar topico
    //por regla de negocio no se podra actualizar el id del autor del topico ni el curso, tampoco su fecha de creacion ya que esto es una modificacion
    @PutMapping
    @Transactional
    public ResponseEntity actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico){
        Topico topico = topicoRepository.getReferenceById(datosActualizarTopico.id());
        topico.actualizarDatos(datosActualizarTopico);
        return ResponseEntity.ok(new DatosRespuestaTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion()));
    }
    //delete nivel logico
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        topico.desactivarTopico();
        return ResponseEntity.noContent().build();
    }






}

