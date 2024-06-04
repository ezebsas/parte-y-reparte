# TACS parte y reparte

_Trabajo practico de desarrollo de un sistema para comprar productos colaborativamente_

Primero se comienza publicando un producto a comprar y luego los insteresados se suscriben al mismo, cuando estan todos de acuerdo se compra o bien se cancela si no hay acuerdo. Hay secciones de estadísticas para ver como fueron las compras

## Comenzando 🚀

_Descargar repositorio mediante git_


### Pre-requisitos 📋

* Docker
* Archivo de configuración .env y .env.local


## Despliegue 📦

* Es necesario copiar los archivos .env y .env.local que mandamos por privado
El archivo .env hay que copiarlo en la raiz del proyecto
Y en la carpeta /src/main/ui hay que copiar el archivo .env.local 
* En la carpeta raiz del proyecto ejecutar el comando de docker ("docker compose up -d --build")
* Ubicarse en la carpeta /src/main/ui y correr npm run dev




## Construido con 🛠️


* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [Docker](https://docker.com/) - Usado para generar imagenes y containers


## Documentado con Swagger

* Se puede consultar los endpoints al momento de levantar la aplicación en la ruta "/swagger-ui.html"


## Autores ✒️


* **Matías Orieta**   - [matiasorieta](https://github.com/matiasorieta)
* **Federico Herrera**  - [federherrera](https://github.com/federherrera)
* **Alejo Gurfein** - [Alejo-Gurfein](https://github.com/Alejo-Gurfein)
* **Ezequiel Mollinedo** - [ezebsas](https://github.com/ezebsas)
