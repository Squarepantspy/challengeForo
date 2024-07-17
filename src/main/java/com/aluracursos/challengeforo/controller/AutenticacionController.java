package com.aluracursos.challengeforo.controller;

import com.aluracursos.challengeforo.domain.usuarios.DatosAutenticacionUsuario;
import com.aluracursos.challengeforo.domain.usuarios.Usuario;
import com.aluracursos.challengeforo.infra.security.DatosJWTToken;
import com.aluracursos.challengeforo.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario){
        //System.out.println(datosAutenticacionUsuario.email());
        //System.out.println(datosAutenticacionUsuario.contrasena());
        Authentication authtoken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.email(),datosAutenticacionUsuario.contrasena());
        var usuarioAutenticado = authenticationManager.authenticate(authtoken);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        //System.out.println(JWTtoken);
        return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
    }

}
