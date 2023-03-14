# MicroService

## Acerca de el proyecto:
Este es un proyecto APIrest que implementa las API´s de spotify y OpenWeather
para hacer recomendaciones de musica segun la temperatura de una locazion dada.


#### Herramientas necesarias para descargar, compilar y ejecutar el programa:
* Maven
* Git
* Java 17.0
* Postgresql 15

## Organizacion:
Este software esta distribuido por paquetes segun su funcion: servicios, repositorios, entidades, dtos y controladores 

Creado con java 17 en conjunto con el framework Springboot

Documentacion de arquitectura: https://github.com/LuisOT25/MicroService/blob/master/src/main/java/Documentacion.md


### Como utilizar el programa

* Necesitara tener postgresql instalado y crear una base de datos llamada "apiconsultas" (utilizar el puerto 5432)
* dentro de la base de datos crear las siguientes tablas con las siguientes estructuras
* >CREATE TABLE public.consultas( id serial NOT NULL, ciudad varchar(50) NOT NULL,temperatura decimal NOT NULL,descripcion varchar(50) NOT NULL,recomendacion varchar(20) NOT NULL,fecha timestamp without time zone NOT NULL,latitud decimal NULL,longitud decimal NULL,CONSTRAINT consultas_pk PRIMARY KEY (id));

* >CREATE TABLE public.usuarios (
  usuario varchar(50) NOT NULL,
  contraseña varchar(50) NOT NULL,
  "token" text NOT NULL,
  expiracion timestamp without time zone NOT NULL,
  CONSTRAINT usuarios_pk PRIMARY KEY (usuario)
  );


* Abra una terminal y dirijase a la carpeta donde guardara el repositorio

Para el siguiente paso debe tener una llave ssh
* Luego ejecute el siguiente comando: git clone git@github.com:LuisOT25/MicroService.git

* una vez descargado el proyecto configure el archivo keys.properties con sus propias llaves de acceso para las API´s
* Luego ejecute el siguiente comando: mvn package
* Luego ejecute el siguiente comando: java -jar target/MicroService-0.0.1-SNAPSHOT.jar

una vez hecho el programa estara inicializado
* despues de inicializarlo puede ver los endpoints en: http://localhost:8080/swagger-ui/index.html

###### by _Luis Eduardo Ocampo de la Torre_
