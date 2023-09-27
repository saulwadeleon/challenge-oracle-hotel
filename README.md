# challenge-oracle-hotel

challengeonehotelaluralatam5

# Hotel Alura - Sistema de Reservas

El Hotel Alura, conocido por sus espectaculares instalaciones y paquetes promocionales para desarrolladores de software, ha desarrollado un sistema de reservas para llevar un mejor control de las reservas de sus clientes. Este sistema cuenta con diversas funcionalidades, incluyendo autenticación de usuarios, gestión de reservas y registro de huéspedes. Además, permite calcular automáticamente el valor de una reserva en base a la duración y la tasa diaria configurada.

## Características Principales

- **Autenticación de Usuario**: El sistema permite que solo los usuarios pertenecientes al hotel accedan a las funciones de gestión de reservas y huéspedes.

- **Gestión de Reservas**: Los usuarios pueden crear, editar y eliminar reservas para los clientes. La información detallada de cada reserva se almacena en la base de datos.

- **Gestión de Huéspedes**: Se puede registrar, editar y eliminar datos de los huéspedes. Los detalles de los huéspedes se asocian automáticamente con las reservas correspondientes.

- **Cálculo Automático del Valor de Reserva**: El sistema calcula automáticamente el valor de una reserva en función de la duración de la estadía y la tasa diaria configurada. El valor se muestra al usuario antes de confirmar la reserva.

- **Base de Datos**: Se utiliza una base de datos para almacenar todos los datos relacionados con las reservas, los huéspedes y otros detalles.

## Tablas Adicionales

El proyecto también incluye tablas adicionales en la base de datos para agregar más funcionalidades a la aplicación del Hotel Alura:

- **Formas de Pago**: Se registran las formas de pago disponibles para las reservas.

- **Países**: Se almacenan los países de origen de los huéspedes.

- **Tipos de Habitaciones**: Se incluyen tipos de habitaciones con sus descripciones y precios asociados.

- **Usuarios**: Se gestionan los usuarios del sistema, con autenticación mediante hash para contraseñas.

## Funcionalidades Adicionales

Además de las características principales, el sistema cuenta con funcionalidades adicionales:

- Se creo un metodo para generar un número de reservacion automatico y aleatorio en lugar de utilizar el id de la reserva.

- Doble clic para editar registros en la pantalla de búsqueda de huéspedes y reservas.

- Búsqueda de huéspedes antes de realizar una reserva, con la opción de agregar uno nuevo si no está registrado.

- Validaciones exhaustivas en los campos de registro, incluyendo fechas de entrada y salida.

- Autenticación de usuarios directamente en la base de datos utilizando un hash para contraseñas.

## Capturas de Pantalla
![image](https://github.com/saulwadeleon/challenge-oracle-hotel/assets/128748724/b0f8047f-74a1-401c-8ef8-9b2affad5921)
![image](https://github.com/saulwadeleon/challenge-oracle-hotel/assets/128748724/0fc6e210-0eda-423c-9219-9d70be187749)
![image](https://github.com/saulwadeleon/challenge-oracle-hotel/assets/128748724/8dbf61d2-417b-4b1f-bddf-ef156b9b3ce6)
![image](https://github.com/saulwadeleon/challenge-oracle-hotel/assets/128748724/cd534a18-d316-46b7-87b7-1aac6b870bf6)



## Cómo Ejecutar

1. Clona o descarga este repositorio.
2. Asegúrate de tener configurado el JDK en tu entorno.
3. Importa el proyecto en tu entorno de desarrollo (por ejemplo, Eclipse, VSCode, IntelliJ, etc.).
4. Configura la base de datos según las especificaciones proporcionadas en el archivo `hotelalura.sql`.
5. Configura la conexión a la base de datos en la clase `ConnectionFactory`.
6. Usuario y Password de la App: `admin`.
7. Ejecuta la clase `MenuPrincipal` para iniciar la aplicación.

## Contribuciones

Las contribuciones son bienvenidas. Si deseas contribuir a esta aplicación, puedes hacer lo siguiente:

1. Realiza un fork del repositorio.
2. Crea una rama para tu contribución: `git checkout -b mi-contribucion`
3. Realiza tus cambios y commitea: `git commit -m "Agrega mi contribución"`
4. Envía tus cambios: `git push origin mi-contribucion`
5. Abre una solicitud de extracción en GitHub.

## Créditos

Esta aplicación fue desarrollada por [Saúl Wade León](https://github.com/saulwadeleon) como parte de Challenges Back End ONE - G5.

## Licencia

Este proyecto está bajo la Licencia [SWL](LICENSE).

**Hotel Alura - Tu destino de ensueño para desarrolladores de software.**
