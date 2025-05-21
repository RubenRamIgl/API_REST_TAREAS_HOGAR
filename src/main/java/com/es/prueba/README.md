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
