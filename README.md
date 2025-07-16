# 🩺 Clinic-Server

Este servicio proporciona una API REST para gestionar las operaciones de una clínica. Incluye funcionalidades para la gestión de usuarios, citas, historiales médicos y recetas.

---

## 📋 Tecnologías
*   **Java 17:** Lenguaje de programación utilizado para desarrollar el servidor.
*   **Spring Boot 3.3.0:** Framework utilizado para la aplicación, facilitando la creación de aplicaciones Java basadas en Spring.
*   **Spring Data JPA:** Para la gestión de la persistencia de datos y la interacción con la base de datos.
*   **Spring Web:** Para crear la API RESTful.
*   **Spring Security:** Para la seguridad de la aplicación, incluyendo autenticación y autorización.
*   **PostgreSQL:** Base de datos relacional utilizada para almacenar los datos de la clínica.
*   **Lombok:** Para reducir el boilerplate code en las clases de modelo.
*   **JWT (JSON Web Tokens):** Para la autenticación y autorización de usuarios.
*   **ModelMapper:** Para mapear objetos entre diferentes capas de la aplicación.

---

## 📦 Requisitos
*   **Java 17** o superior.
[Instrucciones de instalación](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html).
*  **postgreSQL** instalado y configurado.
[Instrucciones de instalación](https://www.postgresql.org/download/).

---

## 🚀 Instalación

Para instalar y ejecutar el servidor, sigue estos pasos:

1. Clona el repositorio:
   ```bash
   git clone https://github.com/tu_usuario/clinic-server.git
   cd clinic-server
   ```

2. Asegúrate de tener [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) o superior instalado.

3. Compila y ejecuta la aplicación:
   ```bash
   ./mvnw spring-boot:run
   ```

4. La API estará disponible en `http://localhost:8080/api`.

---

## ▶️ Ejecutar servidor
Para ejecutar el servidor, asegúrate de que PostgreSQL esté en funcionamiento y que la base de datos esté configurada correctamente. Luego, utiliza el siguiente comando:

```bash
./mvnw spring-boot:run
```
El servidor se iniciará en el puerto `8080` por defecto. Puedes cambiar el puerto editando el archivo `application.properties` en la carpeta `src/main/resources`.

---

## 📌 Endpoints

### Authentication

| Method | Endpoint         | Description            |
| ------ | ---------------- | ---------------------- |
| POST   | `/api/auth/login`  | Autenticacion de usuario y devuelve un token JWT. |
| POST   | `/api/auth/register` | Registra un nuevo usuario. |

---

### Users
| Method | Endpoint         | Description            |
| ------ | ---------------- | ---------------------- |
| GET    | `/api/users/all`     | Obtiene todos los usuarios.   |
| GET    | `/api/users/data`     | Obtiene los datos de un usuario autenticado. |
| POST   | `/api/users/config/user-role`     | Este endpoint permite configurar el rol de un usuario, solo accesible para administradores. |
| POST   | `/api/users/record` | Crea un nuevo registro en el historial médico del usuario autenticado. |
| GET    | `/api/users/record/get?start={fecha}&end={fecha}` | Obtiene el historial médico del usuario autenticado, pero permite filtrar por fechas. |

---

### Appointments
| Method | Endpoint         | Description            |
| ------ | ---------------- | ---------------------- |
| POST   | `/api/appointment/request`     | Permite a un usuario autenticado solicitar una cita. |
| GET    | `/api/appointment/pending`     | Obtiene todas las citas de un usuario autenticado que están pendientes. |
| GET    | `/api/appointment/all`   | Permite obtener todas las citas registradas, solo para administradores y asistentes. |
| POST   | `/api/appointment/finish`   | Permite a un usuario autenticado finalizar una cita, solamente para doctores. |
| POST   | `/api/appointment/clinic/prescription`   | Permite a un usuario autenticado crear una receta para un paciente, solamente para doctores. |
| GET    | `/api/appointment/own`   | Permite a un usuario autenticado obtener sus propias citas. |
| GET    | `/api/appointment/clinic/denied`   | Permite a un usuario autenticado obtener las citas denegadas de un paciente. |
| GET    | `/api/appointment/clinic/schedule`   | Permite a un usuario autenticado obtener el horario de un paciente, solamente para doctores. |
| POST   | `/api/appointment/approve`   | Permite a un usuario autenticado aprobar una cita, solamente para asistentes. |
| GET    | `/api/appointment/finished`   | Permite a un usuario autenticado obtener las citas finalizadas de un paciente, solamente para doctores. |
| GET    | `/api/appointment/approved`   | Permite a un usuario autenticado obtener las citas aprobadas de un paciente, solamente para doctores. |

---

### History
| Method | Endpoint         | Description            |
| ------ | ---------------- | ---------------------- |
| GET    | `/api/history/all`     | Obtiene el historial médico de un paciente, solamente para doctores. |

---

### Prescription
| Method | Endpoint         | Description            |
| ------ | ---------------- | ---------------------- |
| POST   | `/api/prescription/save`     | Guarda una nueva receta para un paciente, solamente para doctores. |
| GET    | `/api/prescription/all`     | Obtiene todas las recetas de un paciente, solamente para doctores. |
| GET    | `/api/prescription/clinic/prescriptions`     | Obtiene todas las recetas de un paciente en la clínica, solamente para doctores. |

---

### Role

| Method | Endpoint         | Description            |
| ------ | ---------------- | ---------------------- |
| GET    | `/api/role/all`     | Obtiene todos los roles disponibles. |
| GET    | `/api/role/{id}`     | Obtiene un rol por su ID. |

---

## 📝 Colaboradores
Si deseas contribuir a este proyecto, por favor revisa las [contribuciones](CONTRIBUTING.md) y sigue las pautas establecidas.
- [Tu Nombre](https://github.com/Fabio-Alfredo)
- [Colaborador 1](https://github.com/Nietoof6)
- [Colaborador 2](https://github.com/aroblesdev)

---

## 📄 Notas 
- Este proyecto es un ejemplo de una API RESTful para la gestión de una clínica. Está diseñado para ser utilizado como base para aplicaciones más complejas y puede ser extendido con nuevas funcionalidades según sea necesario.
- Asegúrate de configurar correctamente la base de datos PostgreSQL antes de ejecutar el servidor
- Puedes personalizar la configuración de la aplicación editando el archivo `application.properties` en la carpeta `src/main/resources`.


