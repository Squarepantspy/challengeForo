package com.aluracursos.challengeforo.controller;



import com.aluracursos.challengeforo.domain.curso.CursoRepository;
import com.aluracursos.challengeforo.domain.topico.DatosRegistroTopico;
import com.aluracursos.challengeforo.domain.topico.DatosRespuestaTopico;
import com.aluracursos.challengeforo.domain.topico.Topico;
import com.aluracursos.challengeforo.domain.topico.TopicoRepository;
import com.aluracursos.challengeforo.domain.usuarios.UsuarioRepository;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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




}

