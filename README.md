# grouptalk-project
API GroupTalk

Aplicación que gestiona un foro con 3 roles de usuario (admin, registrado y no registrado) en el que se contemplan
operaciones CRUD en los diferentes recursos con las siguientes restricciones y servicios:

- Se debe permitir el registro de usuarios. Cualquier usuario no registrado lo único que podrá ver son los grupos que hay en el sistema. 

- Los usuarios registrados pueden hacer login en la aplicación recibiendo un token de acceso que utilizarán para pedir la autorización para que se procesen sus peticiones. También podrán hacer logout que eliminará el token de acceso concedido en el login.

- Los grupos sólo los puede crear el usuario administrador.

- Los usuarios registrados pueden ingresar en cualquier grupo. También pueden abandonar el grupo aunque sus mensajes no serán borrados.

- Los usuarios registrados pueden consultar cualquier tema de cualquier grupo a los que estén suscritos.

- Los usuarios registrados pueden escribir un tema en cualquiera de los grupos en los que hayan ingresado.

- Cada tema tiene un título y un contenido que desarrolla el tema. 
- Cualquier usuario registrado puede participar en el tema mediante respuestas al tema inicial. Una respuesta sólo tiene contenido.

- El contenido de un tema o una respuesta puede ser editado por el usuario que lo creó. El título no se puede editar.

- El usuario que creó un tema puede eliminarlo por completo y también cualquier respuesta asociada al tema. El administrador puede eliminar cualquier tema y cualquier respuesta.
