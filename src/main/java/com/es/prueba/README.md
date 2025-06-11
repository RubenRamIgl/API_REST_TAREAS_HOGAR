# APP TAREAS HOGAR

## 📌 Idea de la Aplicación
La aplicación consiste en un programa que administrará tareas del hogar asignándolas a usuarios registrados. La función principal es dar de alta tareas con un título y descripción, y el usuario podrá marcarlas como completadas siempre y cuando no haya pasado la fecha límite.

### 🔹 Funcionalidades principales:
- **Registro y gestión de usuarios.**
- **Creación de tareas** con título y descripción.
- **Asignación de tareas** a usuarios.
- **Restricción de fecha límite** para completar las tareas.
- **Gestión de direcciones** asociadas a los usuarios.
- **Borrar tareas.**
- **Gestión del propio perfil** (actualización y borrado de cuenta/dirección).

La base de datos estará compuesta por **tres tablas**: `Usuarios`, `Tareas` y `Direcciones`.

---

## 🗄️ Tablas de la Base de Datos

### 📌 Usuarios
| Campo       | Tipo     | Restricciones                                                                 |
|-------------|----------|-------------------------------------------------------------------------------|
| `username`  | VARCHAR  | `PK`, `NOT NULL`, `UNIQUE`                                                    |
| `nombre`    | VARCHAR  | `NOT NULL`                                                                    |
| `apellidos` | VARCHAR  | -                                                                             |
| `password`  | VARCHAR  | Longitud: 5–20 caracteres                                                     |
| `email`     | VARCHAR  | `NOT NULL`, `UNIQUE`, debe contener `@` y terminar en `.com` o `.es`          |
| `roles`     | VARCHAR  | Valores posibles: `USER` / `ADMIN`                                            |

### 📌 Tareas
| Campo         | Tipo     | Restricciones                                             |
|---------------|----------|-----------------------------------------------------------|
| `idTarea`     | NUMBER   | `PK`, Autoincremental                                     |
| `nombre`      | VARCHAR  | `NOT NULL`, Único                                         |
| `descripcion` | TEXT     | `NOT NULL`                                                |
| `estado`      | BOOLEAN  | Por defecto: `false`                                      |
| `fechaFin`    | DATE     | No puede ser anterior a la fecha actual                   |
| `username`    | VARCHAR  | `FK`                                                      |

### 📌 Direcciones
| Campo         | Tipo     | Restricciones                                             |
|---------------|----------|-----------------------------------------------------------|
| `idDireccion` | NUMBER   | `PK`, Autoincremental                                     |
| `calle`       | VARCHAR  | `NOT NULL`                                                |
| `numero`      | VARCHAR  | `NOT NULL`, debe ser mayor que 0                          |
| `CP`          | NUMBER   | Exactamente 5 dígitos                                     |
| `provincia`   | VARCHAR  | `NOT NULL`, debe ser una provincia de Andalucía           |
| `municipio`   | VARCHAR  | `NOT NULL`                                                |
| `username`    | VARCHAR  | `FK`                                                      |

---

### 📌 Diagrama de la Base de Datos
![Diagrama de Base de Datos](Captura_TareasHogar.png)

---

## 📌 Datos requeridos para las operaciones

### 🔹 Login
- `username`
- `password`

### 🔹 Registro de usuario
- `nombre`
- `apellido`
- `email`
- `password`
- `repetirPassword`
- `username`
- `rol` (por defecto: `USER`)

### 🔹 Ver una tarea
- `nombre`
- `descripcion`
- `fechaFin`
- `estado`
- `idTarea`

### 🔹 Registrar una tarea
- `nombre`
- `descripcion`
- `fechaFin`

### 🔹 Borrar una tarea
- `idTarea`

### 🔹 Actualizar el estado de una tarea
- `idTarea`
- `estado`

### 🔹 Registrar una tarea como admin
- `username` del usuario
- `nombre` de la tarea
- `descripcion`
- `estado`

---

## 📌 Endpoints de la Aplicación

### 🔹 UsuarioController

| Método | Endpoint                    | Descripción                                               | Acceso        |
|--------|-----------------------------|-----------------------------------------------------------|---------------|
| POST   | `/login`                    | Iniciar sesión con username y password                    | Público       |
| POST   | `/register`                 | Registrar un nuevo usuario                                | Público       |
| PUT    | `/usuarioUpdate`            | Actualizar los datos de cualquier usuario                 | Solo ADMIN    |
| DELETE | `/usuarioDelete/{username}` | Eliminar un usuario y sus datos asociados                 | Solo ADMIN    |
| PUT    | `/updateMiPerfil`           | Actualizar los datos del usuario logueado                 | USER y ADMIN  |
| DELETE | `/borrarMiCuenta`           | Eliminar la cuenta propia del usuario logueado            | USER y ADMIN  |

---

### 🔹 DireccionController

| Método | Endpoint                         | Descripción                                        | Acceso        |
|--------|----------------------------------|---------------------------------------------------|---------------|
| POST   | `/direccionRegister`             | Registrar dirección para un usuario específico    | Solo ADMIN    |
| PUT    | `/direccionUpdate`               | Actualizar dirección de un usuario específico     | Solo ADMIN    |
| DELETE | `/eliminarDireccion/{username}`  | Eliminar dirección de un usuario por su username  | Solo ADMIN    |
| POST   | `/registerMiDireccion`           | Registrar dirección propia                        | USER y ADMIN  |
| PUT    | `/updateMiDireccion`             | Actualizar dirección propia                       | USER y ADMIN  |
| DELETE | `/deleteMiDireccion`             | Eliminar dirección propia                         | USER y ADMIN  |

---

### 🔹 TareaController

| Método | Endpoint                              | Descripción                                          | Acceso        |
|--------|----------------------------------------|------------------------------------------------------|---------------|
| POST   | `/tareaRegister`                      | Registrar una tarea para el usuario logueado        | USER y ADMIN  |
| POST   | `/insertarTarea`                      | Registrar tarea para un usuario específico          | Solo ADMIN    |
| GET    | `/mostrarTareas`                      | Mostrar todas las tareas                            | USER y ADMIN  |
| GET    | `/mostrarTareaId/{id}`                | Mostrar tarea por ID                                | USER y ADMIN  |
| GET    | `/buscarTareaNombre/{palabra}`        | Buscar tareas por palabra clave en el nombre        | USER y ADMIN  |
| DELETE | `/eliminarTarea/{id}`                 | Eliminar una tarea por ID                           | USER y ADMIN  |

> 🔒 **Nota**: Para acceder a cualquier endpoint (excepto `/login` y `/register`), es obligatorio haber iniciado sesión previamente mediante `/login`.

---

## ❗ Gestión de Errores

La API devuelve códigos de estado HTTP adecuados para reflejar el resultado de las operaciones:

| Código HTTP        | Nombre                 | Cuándo se utiliza                                                                 |
|--------------------|------------------------|------------------------------------------------------------------------------------|
| `400 Bad Request`  | Solicitud incorrecta   | Datos inválidos, incompletos o con formato erróneo                               |
| `401 Unauthorized` | No autorizado          | El usuario no ha iniciado sesión (`/login` obligatorio para continuar)           |
| `403 Forbidden`    | Prohibido              | El usuario no tiene permisos (por ejemplo, se requiere rol `ADMIN`)              |
| `404 Not Found`    | No encontrado          | Recurso solicitado no existe                                                     |
| `409 Conflict`     | Conflicto              | Duplicidad de datos (por ejemplo, `username` o `email` ya registrados)           |
| `500 Internal Server Error` | Error interno del servidor | Error inesperado en la aplicación                              |


### 📌 Ejemplos

- Registrar un usuario sin email → `400 Bad Request`
- Buscar una tarea con ID inexistente → `404 Not Found`
- Registrar un usuario con `username` ya existente → `409 Conflict`
- Intentar acceder a un endpoint sin estar autenticado → `401 Unauthorized`
- Intentar borrar otro usuario sin ser ADMIN → `403 Forbidden`
- Error inesperado del servidor (por ejemplo, null pointer) → `500 Internal Server Error`


---

## 🧠 Lógica de Negocio

A continuación se describen las reglas de negocio implementadas en la aplicación:

### General

- Para realizar cualquier acción es necesario iniciar sesión antes

### Usuarios

- El username es la **clave primaria** de la tabla de usuarios y también es clave foránea en las tablas **Tareas** y **Direcciones**.
- Solo existen dos tipos de roles: USER y ADMIN.
- Todos los campos del formulario de registro son **obligatorios**.
- El username debe tener entre **5 y 25 caracteres**, ser **único** y no puede repetirse.
- El password debe tener entre **10 y 20 caracteres**, y solo puede contener **letras y números** (sin caracteres especiales).
- El campo repetirPassword debe coincidir exactamente con el password.
- El email debe tener formato válido y pertenecer a los dominios:
  - gmail.com, gmail.es
  - hotmail.com, hotmail.es
  - outlook.com, outlook.es
  - g.educaand.es
- La contraseña quedará hasheada en la base de datos
- Si un usuario es eliminado, también se elimina **su dirección asociada** y sus **tareas** automáticamente (relación fuerte con Direcciones).
- Un ADMIN puede modificar y eliminar cualquier usuario, pero un USER solo puede eliminarse y modificarse él mismo.

---

### Tareas

- El campo idTarea es **autoincremental**.
- Todos los campos son **obligatorios**: nombre, descripción, fecha de finalización, etc.
- El nombre de la tarea debe ser **único**. No puede haber dos tareas con el mismo nombre exacto.
- La fecha de finalización:
  - No puede ser **anterior al día actual**.
  - No puede estar a más de **30 días** en el futuro.
- Una tarea solo puede asignarse a un **usuario existente**.
- Al eliminar un usuario, todas sus **tareas asociadas también pueden ser eliminadas o gestionadas** según lógica futura (relación dependiente).
- Todo lo relacionado con las tareas, solo podrá administrarlo un ADMIN

---

### Direcciones

- El campo idDireccion es **autoincremental**.
- Todos los campos son **obligatorios**: calle, número, código postal, provincia, municipio.
- El número de la calle debe ser **mayor que 0**.
- El código postal debe tener exactamente **5 dígitos**.
- La provincia debe pertenecer exclusivamente a **Andalucía** (validación manual o controlada).
- La dirección está asociada **únicamente a un usuario existente**.
- Al eliminar una dirección, **el usuario NO se elimina** (relación independiente).
- Un ADMIN puede insertar y asignar una dirección a cualquier usuario, además de modificarla y eliminarla
- Los USERS solo pueden asignarse una dirección a sí mismos, además de solo poder modificar y eliminar su dirección

---

⚠️ **Nota**: Todos los datos deben cumplir con las validaciones indicadas. En caso contrario, se devuelven errores con los códigos HTTP apropiados como `400`, `401`, `403`, `404`, `409` o `500`.
