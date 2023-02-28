
![Untitled](https://user-images.githubusercontent.com/123667079/221988809-b8145713-5350-4a35-96c9-3bc1a1ebfe05.png)


# Servicios:

## AutenticadorSpotify:
Este servicio cuenta con 4 atributos:
>String authUrl: este toma su valor de archivo application.properties

>String clientId:  este toma su valor de archivo key.properties

>String clientSecret:  este toma su valor de archivo key.properties

>Token authToken: este guardara el token con que se haran llamadas a spotify

y cuenta con 2 metodos:

>### Token validarToken():
> este metodo verifica si el valor del atributo authToken no nes null
> o si no ha expirado llamando a un metodo que esta en la clase Token llamado doStillWork
> en caso de que alguna de esas dos condiciones se cumpla se le asignara como valor a authToken
> lo que regreso el metodo llamado obtenerNuevoAccessToken y al final regresa authToken.

>### Token obtenerNuevoAccessToken():
> este metodo genera un token de acceso haciendo una llamada a spotify pasandole en los encabezados
> el clientId y clientSecret y al regresa el token generado.

## SpotifyService:
Este servicio cuenta con 5 atributos:

>String spotifyBaseUrl: este toma su valor de archivo application.properties

> ListString classicalList.

> ListString popList.

> ListString rockList.

> ListString partyList.

y cuenta con 2 metodos:

>### ListString getTracks():
> este metodo recibe dos argumentos un genero musical y un accessToken
> el metodo realiza una llamada a la api de spotify con el genero y token recibidos
> se toma el objeto json recibido y se itera para extraer solamente los nombres de los tracks
> y se almacenan en una lista que finalmente es regresada.  

>### ListString getList():
> este metodo recibe dos argumentos un genero musical y un Token
> el metodo verifica el genero recibido y en base a eso verifica uno de los atributos de tipo ListString
> si la lista esta vacia o el Token ya ha expirado si una de esas es verdadera se le asigna un nuevo
> valor al atributo haciendo una llamada al metodo getTracks y al final regresa ese atributo.

## WeatherService:
Este servicio cuenta con 2 atributos:
>String openWeatherBaseUrl: este toma su valor del archivo application.properties

>String opiKey: este toma su valor del archivo key.properties

y cuenta con 3 metodos

>### Consulta findByName():
> este metodo recibe como parametro un string con el nombre de una ciudad
> y hace una llamada a la api de OpenWeather con el nombre de esa ciudad
> luego extrae la temperatura y descripcion del objeto JSON obtenido y los guarda en variables,
> tambien se crean dos variables mas, una con la fecha y otra que con el valor obtenido de el metodo
> tempToGender con el parametro de la temperatura y al final regresa un nuevo objeto de tipo Consulta 
> que se crea con todas la variables antes creadas.

>### Consulta findByCoordinates():
> este metodo recibe dos parametros un string con una latitud y otro con una longitud
> y hace una llamada a la api de OpenWeather con las coordenadas
> luego extrae la temperatura, nombre y descripcion del objeto JSON obtenido y los guarda en variables,
> tambien se crean dos variables mas, una con la fecha y otra que con el valor obtenido de el metodo
> tempToGender con el parametro de la temperatura y al final regresa un nuevo objeto de tipo Consulta
> que se crea con todas la variables antes creadas.

>### String tempToGender():
> este metodo recibe como parametro un double y depende su valor regresa un string con 
> una de las siguientes opciones classical, rock, pop o party.


# Repositorios:

## RepoConsultas:
Este es una interface que extiende de JpaRepository creada para comunicarse con la base de 
datos.


# Entidades:

## Consulta:

Esta entidad se relaciona con una tabla llamada consultas tiene los siguientes atributos
> Integer id

> String cityName

> double temp

> String descripcion

> LocalDateTime fecha

> String genero

Cada atributo corresponde a su respectiva columna en la tabla de la base de datos

Esta clase tiene un metodo constructor e implementa sus respectivos metodos getters
y setters por cada atributo.


# Data Transfer Object (dto):
## Token:
Este clase fue creada para mapear la respuesta de la llamada a la api en el metodo obtenerNuevoAccessToken
de la clase AutenticadorSpotify

este clase contiene 4 atributo:

> String accessToken: este obtiene su valor mediante la notacion JsonProperty

> String tokenType: este obtiene su valor mediante la notacion JsonProperty

> Integer expiresIn: este obtiene su valor mediante la notacion JsonProperty

> Instant momentoExpiracion.

y contiene 5 metodos:

>### Token registrarToken():
> este metodo recibe como parametro un objeto Token y agsina un valor a cada uno de sus atributos extrayendo
> el valor de los atributos del objeto pasado en los argumentos excepto el del atributo
> momentoExpiracion.

>### void calcularMomentoExpiracion():
> este metodo calcula el momento el momento de expiracion tomando en cuenta en momento de su
> creacion y sumandole 3600 segundos y se le asigna ese valor al atributo momentoExpiracion.

>### String getAccessToken():
> regresa el valor del atributo accessToken

>### String getTokenType():
> regresa el valor del atributo tokenType

>### boolean doStillWork():
> verifica si el token todavia puede ser usado regresa un TRUE de lo contrario regresa un
> FALSE.

# Controladores:
## Controller:
Este clase de es la encargada de controlar el proceso de ejecucion

contiene 4 atributos:

> WeatherService weatherServive

> RepoConsultas repoConsultas

> SpotifyService spotifyService

> AutenticadorSpotify autenticadorSpotify

y cuenta con 4 metodos:

un metodo constructor

>### Object getListTracks():
> este metodo recibe como parametro un objeto de tipo Consulta 
> luego genera un objeto de tipo Token llamando al metodo validarToken de su atributo 
> autenticadorSpotify luego manda a llamar el metodo save de su atributo repoConsultas 
> con el objeto Consulta como argumento y finalmente regresa la respuesta del metodo getList
> de el atributo spotifyService con el atributo genero del objeto Consulta y el objeto Token
> como argumentos

>### ResponseEntityObject requestByName():
> este metodo toma como argumento el valor ingresado en el parametro ingresado al hacer la llamada
> luego crea un objeto Consulta utilizando el metodo findByName de su atributo weatherService
> pasando como argumentos el propio argumento del metodo y regresa un ResponseEntity.ok con
> el metodo getListTracks con el objeto Consulta como argumento.

>### ResponseEntityObject requestByCoordinates():
> este metodo toma como argumentos los valores ingresados en los parametros ingresados al hacer la llamada
> luego crea un objeto Consulta utilizando el metodo findByCoordinates de su atributo weatherService
> pasando como argumentos los propios argumentos del metodo y regresa un ResponseEntity.ok con
> el metodo getListTracks con el objeto Consulta como argumento.
