# APP TAREAS HOGAR

## 📌 Idea de la Aplicación
La aplicación consiste en un programa que administrará tareas del hogar asignándolas a usuarios registrados. La función principal es dar de alta tareas con un título y descripción y el usuario podrá marcarlas como completadas siempre y cuando no haya pasado la fecha límite. 

### 🔹 Funcionalidades principales:
- **Registro y gestión de usuarios.**
- **Creación de tareas** con título y descripción.
- **Asignación de tareas** a usuarios.
- **Control de estado de las tareas** (pendiente/completada)..
- **Restricción de fecha límite** para completar las tareas.
- **Gestión de direcciones** asociadas a los usuarios.
- **Borrar tareas.**

La base de datos estará compuesta por **tres tablas**: `Usuarios`, `Tareas` y `Direcciones`.

---

## 🗄️ Tablas de la Base de Datos

### 📌 Usuarios
| Campo     | Tipo               | Restricciones |
|-----------|--------------------|---------------|
| `username`  | `VARCHAR` | `PK`, `NOT NULL`, `UNIQUE` |
| `nombre`    | `VARCHAR` | `NOT NULL` |
| `apellidos` | `VARCHAR` |  -  |
| `password`  | `VARCHAR` | `Longitud: 5-20 caracteres` |
| `email`     | `VARCHAR` | `NOT NULL`, `UNIQUE`, `Debe contener @ y terminar en .com o .es` |
| `roles`     | `VARCHAR` | `Valores posibles: USER / ADMIN` |

### 📌 Tareas
| Campo       | Tipo       | Restricciones |
|------------|-----------|---------------|
| `idTarea`   | `NUMBER`  | `PK`, `Autoincremental` |
| `nombre`    | `VARCHAR` | `NOT NULL` |
| `descripcion` | `TEXT`  | `NOT NULL` |
| `estado`    | `BOOLEAN` | `Por defecto: false` |
| `fechaFin`  | `DATE`    | `No puede ser anterior a la fecha actual` |
| `username`  | `VARCHAR` | `FK` |

### 📌 Direcciones
| Campo       | Tipo       | Restricciones |
|------------|-----------|---------------|
| `idDireccion` | `NUMBER`  | `PK`, `Autoincremental` |
| `calle`    | `VARCHAR` | `NOT NULL` |
| `numero`   | `VARCHAR` | `NOT NULL` |
| `CP`       | `NUMBER`  |  -  |
| `provincia`| `VARCHAR` | `NOT NULL` |
| `municipio`| `VARCHAR` | `NOT NULL` |
| `username` | `VARCHAR` | `FK` |

---

### 📌 Diagrama de la Base de Datos
![Diagrama de Base de Datos](Captura_TareasHogar.png)

---

## 📌 Datos requeridos para las operaciones

### 🔹 **Login**
- `username`
- `password`

### 🔹 **Registro de usuario**
- `nombre`
- `apellido`
- `email`
- `password`
- `repetirPassword`
- `username`
- `rol` (por defecto: `USER`)

### 🔹 **Ver una tarea**
- `nombre`
- `descripcion`
- `fechaFin`
- `estado`
- `idTarea`

### 🔹 **Registrar una tarea**
- `nombre`
- `descripcion`
- `fechaFin`

### 🔹 **Borrar una tarea**
- `idTarea`

### 🔹 **Actualizar el estado de una tarea**
- `idTarea`
- `estado`

### 🔹 **Registrar una tarea como admin**
- `username` del usuario
- `nombre` de la tarea
- `descripcion`
- `estado`

---

## 📌 Endpoints de la Aplicación

### UsuarioController

| Método | Endpoint                    | Descripción                                      |
|--------|-----------------------------|--------------------------------------------------|
| POST   | `/login`                    | Iniciar sesión con username y password           |
| POST   | `/register`                 | Registrar un nuevo usuario                       |
| PUT    | `/usuarioUpdate`            | Actualizar los datos del usuario                 |
| DELETE | `/usuarioDelete/{username}` | Eliminar usuario y su dirección asociada         |

---

### DireccionController

| Método | Endpoint                         | Descripción                            |
|--------|----------------------------------|----------------------------------------|
| POST   | `/direccionRegister`             | Registrar una dirección                |
| PUT    | `/direccionUpdate`               | Actualizar dirección del usuario       |
| DELETE | `/eliminarDireccion/{username}`  | Eliminar dirección por username        |

---

### TareaController

| Método | Endpoint                              | Descripción                                              |
|--------|----------------------------------------|----------------------------------------------------------|
| POST   | `/tareaRegister`                      | Registrar una tarea (usuario actual)                     |
| POST   | `/insertarTarea`                      | Registrar tarea como administrador                      |
| GET    | `/mostrarTareas`                      | Mostrar todas las tareas                                 |
| GET    | `/mostrarTareaId/{id}`                | Mostrar tarea específica por ID                          |
| GET    | `/buscarTareaNombre/{palabra}`        | Buscar tareas por palabra clave en el nombre             |
| DELETE | `/eliminarTarea/{id}`                 | Eliminar una tarea por ID                                |

---

## ❗ Gestión de Errores

La API devuelve códigos de estado HTTP adecuados para reflejar el resultado de las operaciones. A continuación se describen los más relevantes utilizados:

| Código HTTP     | Nombre             | Cuándo se utiliza                                                                 |
|-----------------|--------------------|------------------------------------------------------------------------------------|
| `400 Bad Request` | Solicitud incorrecta | Se devuelve cuando los datos enviados no son válidos, están incompletos o no cumplen las condiciones necesarias (por ejemplo, campos vacíos, formato incorrecto, etc.). |
| `404 Not Found`   | No encontrado       | Se devuelve cuando un recurso solicitado no existe. Por ejemplo, si se intenta buscar, actualizar o eliminar un usuario, dirección o tarea que no está registrado. |
| `409 Conflict`    | Conflicto           | Se devuelve cuando se intenta registrar un recurso que ya existe, como un `username` o `email` duplicado. |

### 📌 Ejemplos

- Intentar registrar un usuario sin email → `400 Bad Request`
- Buscar una tarea con un ID inexistente → `404 Not Found`
- Registrar un usuario con un username ya registrado → `409 Conflict`

---

## 🧠 Lógica de Negocio

A continuación se describen las reglas de negocio implementadas en la aplicación:

### Usuarios

- El `username` es la **clave primaria** de la tabla de usuarios y también es clave foránea en las tablas **Tareas** y **Direcciones**.
- Solo existen dos tipos de roles: `USER` y `ADMIN`.
- Todos los campos del formulario de registro son **obligatorios**.
- El `username` debe tener entre **5 y 25 caracteres**, ser **único** y no puede repetirse.
- El `password` debe tener entre **10 y 20 caracteres**, y solo puede contener **letras y números** (sin caracteres especiales).
- El campo `repetirPassword` debe coincidir exactamente con el `password`.
- El `email` debe tener formato válido y pertenecer a los dominios:
    - `gmail.com`, `gmail.es`
    - `hotmail.com`, `hotmail.es`
    - `outlook.com`, `outlook.es`
    - `g.educaand.es`
- Si un usuario es eliminado, también se elimina **su dirección asociada** automáticamente (relación fuerte con `Direcciones`).

---

### Tareas

- El campo `idTarea` es **autoincremental**.
- Todos los campos son **obligatorios**: `nombre`, `descripción`, `fecha de finalización`, etc.
- El `nombre` de la tarea debe ser **único**. No puede haber dos tareas con el mismo nombre exacto.
- La `fecha de finalización`:
    - No puede ser **anterior al día actual**.
    - No puede estar a más de **30 días** en el futuro.
- Una tarea solo puede asignarse a un **usuario existente**.
- Al eliminar un usuario, todas sus **tareas asociadas también pueden ser eliminadas o gestionadas** según lógica futura (relación dependiente).

---

### Direcciones

- El campo `idDireccion` es **autoincremental**.
- Todos los campos son **obligatorios**: `calle`, `número`, `código postal`, `provincia`, `municipio`.
- El `número` de la calle debe ser **mayor que 0**.
- El `código postal` debe tener exactamente **5 dígitos**.
- La `provincia` debe pertenecer exclusivamente a **Andalucía** (validación manual o controlada).
- La dirección está asociada **únicamente a un usuario existente**.
- Al eliminar una dirección, **el usuario NO se elimina** (relación independiente).

---

⚠️ **Nota**: Todos los datos deben cumplir con las validaciones indicadas. En caso contrario, se devuelven errores con los códigos HTTP apropiados como `400 Bad Request`, `404 Not Found`, o `409 Conflict`.