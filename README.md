# MicroService

## Acerca de el proyecto:
Este es un proyecto APIrest que implementa las API´s de spotify y OpenWeather
para hacer recomendaciones de musica segun la temperatura de una locazion dada.

Creado con java 17 en conjunto con el framework Springboot
Documentacion de arquitectura: https://github.com/LuisOT25/MicroService/blob/master/src/main/java/Documentacion.md
#### Herramientas necesarias para descargar, compilar y ejecutar el programa:
* Maven
* Git
* Java 17.0

## Organizacion:
Este software esta distribuido por paquetes segun su funcion:
servicios, repositorios, entidades, dtos y controladores 


### Como utilizar el programa

* Abra una terminal y dirijase a la carpeta donde guardara el repositorio

Para el siguiente paso debe tener una llave ssh
* Luego ejecute el siguiente comando: git clone git@github.com:LuisOT25/MicroService.git
* una vez descargado el proyecto configure el archivo keys.properties con sus propias llaves de acceso para las API´s
* Luego ejecute el siguiente comando: mvn package
* Luego ejecute el siguiente comando: java -jar target/MicroService-0.0.1-SNAPSHOT.jar

una vez hecho el programa estara inicializado
* despues de inicializarlo puede ver los endpoints en: http://localhost:8080/swagger-ui.html
* tambien puede ver la base de datos en: http://localhost:8080/h2-console

###### by _Luis Eduardo Ocampo de la Torre_
