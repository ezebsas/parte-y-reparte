# TACS parte y reparte

_Trabajo practico de desarrollo de un sistema para comprar productos colaborativamente_

Primero se comienza publicando un producto a comprar y luego los insteresados se suscriben al mismo, cuando estan todos de acuerdo se compra o bien se cancela si no hay acuerdo. Hay secciones de estadísticas para ver como fueron las compras

## Comenzando 🚀

_Descargar repositorio mediante git_


### Pre-requisitos 📋

* Docker
* Archivo de configuración .env y .env.local


## Despliegue 📦

* Es necesario copiar los archivos .env y .env.local que mandamos por privado.
El archivo .env hay que copiarlo en la raiz del proyecto
Y en la carpeta /src/main/ui hay que copiar el archivo .env.local. O pueden configurarlo ustedes mismos.
* En la carpeta raiz del proyecto ejecutar el comando de docker ("docker compose up -d --build")
* Ubicarse en la carpeta /src/main/ui y correr npm run dev

#### Configuración de .env (Ruta: "/src/main/resources")

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `SECURITY_KEY` | `string` | **Requerido**. Clave en base64 de 32 Bytes |
| `DB_MONGO_HOST` | `string` | **Requerido**. Ruta de la base de datos |
| `DB_MONGO_USERNAME` | `string` | **Requerido**. Usuario en la base de datos |
| `DB_MONGO_PASSWORD` | `string` | **Requerido**. Contraseña de la base de datos |

Puedes generar tu base64 key de 32 Bytes [Acá](https://generate.plus/en/base64)

#### Configuración de .env.local (Ruta: "src/main/ui")


| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `NEXTAUTH_SECRET` | `string` | **Requerido**. Clave para que utilice Next |
| `NEXTAUTH_URL` | `string` | **Requerido**. Ruta del dominio donde se encuentra el servidor frontend |
| `NEXT_PUBLIC_BACKEND_INTERNAL_URL` | `string` | **Requerido**. Ruta de la base de datos 



## Construido con 🛠️


* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [Docker](https://docker.com/) - Usado para generar imagenes y containers


## Documentado con Swagger

* Se puede consultar los endpoints al momento de levantar la aplicación en la ruta "/swagger-ui.html"


## Tecnologías utilizadas

**Contenedores** : Docker

**Cliente:** React, NextJs, TailwindCSS

**Server:** Java, Spring(Spring boot, Spring security, Spring core, Spring data)


## Autores ✒️


* **Matías Orieta**   - [matiasorieta](https://github.com/matiasorieta)
* **Federico Herrera**  - [federherrera](https://github.com/federherrera)
* **Alejo Gurfein** - [Alejo-Gurfein](https://github.com/Alejo-Gurfein)
* **Ezequiel Mollinedo** - [ezebsas](https://github.com/ezebsas)
