## Challenge final Foro  
Creacion de una api CRUD en java spring boot con validacion y autorizacion  
Se utilizo flyway para la migracion a la base de datos de mySQl, spring web, spring security, spring jpa  
Autenticacion JWT con devolucion de token bearer  
POST /auth
Estructura del body (Ejemplo)  
```
{
    email= "admin@admin.com"
    contrasena= "admin"
}
```
Esto devolvera el token de autorizacion para las siguientes consultas  

### Endpoints
- POST /topicos para registrar un nuevo topico , se responde con el topico adjunto su fecha de creacion
```
Estructura del body
  {
	"idUsuario" : String,
	"mensaje" : String,
	"nombreCurso" : String,
	"titulo": String
}
```
- GET /topicos para listar los topicos  
- GET /topicos/id  para mostrar un topico especifico  
- PUT /topicos para actualizar    
Estructura del body, el id es el id del topico
```
  {
	"id" : String, 
	"mensaje" : String,
	"titulo": String
}
```
- DELETE /topicos/id  para borrado logico del topico, status = false



