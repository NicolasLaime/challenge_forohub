# Proyecto de Gestión de Usuarios y Cursos

Este proyecto es una API REST desarrollada con **Spring Boot** para gestionar usuarios, cursos y tópicos. Está diseñado para permitir la creación, lectura y actualización de usuarios, así como la gestión de cursos y sus tópicos relacionados.

## Descripción del Proyecto

El objetivo de este proyecto es proporcionar una API que permita a los administradores gestionar usuarios, asignar roles, y gestionar cursos junto con los tópicos que los componen. La API está construida utilizando **Spring Boot** y **JPA** para la persistencia de datos en una base de datos relacional.

El proyecto está dividido en diferentes módulos que se enfocan en la gestión de usuarios y la gestión de cursos, brindando una plataforma flexible para administrar la información.

## Estructura del Proyecto

El proyecto está estructurado de la siguiente manera:

### 1. **Gestión de Usuarios**
   - **Crear Usuario**: Permite registrar un nuevo usuario con atributos como nombre, apellido, correo electrónico, rol y estado habilitado.
   - **Leer Usuarios**: Permite obtener todos los usuarios registrados o filtrar por los habilitados.
   - **Actualizar Usuario**: Permite actualizar la información de un usuario existente, como la contraseña, el rol, el nombre, el apellido y el correo electrónico.

### 2. **Gestión de Cursos**
   - **Crear Curso**: Permite registrar un nuevo curso, incluyendo detalles como título, descripción y duración en horas.
   - **Leer Cursos**: Permite obtener la lista de todos los cursos registrados en el sistema.
   
### 3. **Gestión de Tópicos**
   - **Crear Tópico**: Permite registrar un tópico relacionado con un curso específico. Cada tópico puede tener un título y una descripción.
   - **Leer Tópicos**: Permite obtener todos los tópicos relacionados con un curso específico.

## Características Clave

- **Autenticación y Autorización**: El sistema gestiona los roles de los usuarios (por ejemplo, "USER", "ADMIN") para permitir acceso a funcionalidades específicas según el rol asignado.
- **Validación de Datos**: Se asegura que los datos proporcionados en las solicitudes sean válidos antes de ser procesados, utilizando anotaciones de validación de **jakarta.validation**.
- **Persistencia de Datos**: Utiliza **JPA (Java Persistence API)** para la gestión de la base de datos, garantizando que los datos se almacenen y recuperen de forma eficiente.
- **Transacciones**: La gestión de usuarios y cursos se realiza dentro de transacciones, lo que asegura que los cambios en la base de datos sean atómicos y consistentes.

## Tecnologías Utilizadas

- **Spring Boot**: Framework utilizado para crear la aplicación de backend.
- **JPA (Java Persistence API)**: Utilizado para la persistencia de datos en una base de datos relacional.
- **H2 Database**: Base de datos embebida utilizada para pruebas y desarrollo (puede ser reemplazada por cualquier otra base de datos en producción).
- **Spring Security**: Para la autenticación y autorización de los usuarios.
- **Spring Validation**: Para la validación de datos de entrada en las solicitudes.

## Funcionamiento de la API

La API permite a los usuarios interactuar con la base de datos para crear, leer y actualizar los registros de usuarios y cursos. El flujo de trabajo típico de la API incluye:

1. **Creación de Usuario**: Un administrador puede registrar un nuevo usuario proporcionando su nombre, apellido, correo electrónico y contraseña.
2. **Gestión de Cursos**: Los administradores pueden crear nuevos cursos con detalles como el título, la descripción y la duración.
3. **Gestión de Tópicos**: Los tópicos pueden ser asignados a cursos existentes para estructurar el contenido del curso en diferentes secciones.
4. **Actualización de Datos**: Los administradores pueden actualizar los datos de un usuario existente, incluidos los roles y estado habilitado.

## Requisitos para Ejecutar el Proyecto

Para ejecutar este proyecto en tu máquina local, necesitas tener los siguientes requisitos:

1. **Java 17 o superior**: Es necesario para ejecutar el proyecto, ya que Spring Boot requiere una versión reciente de Java.
2. **Maven**: El proyecto utiliza Maven para la gestión de dependencias y la construcción del proyecto.
3. **IDE**: Se recomienda usar un IDE como **IntelliJ IDEA** o **Eclipse** para el desarrollo y la ejecución del proyecto.

## Cómo Ejecutar el Proyecto

1. Clona el repositorio del proyecto:
   ```bash
   git clone <URL_DEL_REPOSITORIO>


