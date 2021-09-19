# AREP - MODULARIZACIÓN CON VIRTUALIZACIÓN E INTRODUCCIÓN A DOCKER Y A AWS
La aplicación web APP-LB-RoundRobin está compuesta por un cliente web y al menos un servicio REST. El cliente web tiene un campo y un botón y cada vez que el usuario envía un mensaje, este se lo envía al servicio REST y actualiza la pantalla con la información que este le regresa en formato JSON.
El servicio REST recibe la cadena e implementa un algoritmo de balanceo de cargas de Round Robin, delegando el procesamiento del mensaje y el retorno de la respuesta a cada una de las tres instancias del servicio LogService.

## Comenzando
Genere copia local del repositorio puede ejecutar la siguiente línea en la consola de comandos.

    git clone https://github.com/JCPosso/AREP-Lab5-SparkDockerAWS.git

## Pre-requisitos

Antes de ejecutar el proyecto es necesario instalar los siguientes programas:

* [GIT](https://git-scm.com/book/es/v2/Inicio---Sobre-el-Control-de-Versiones-Instalación-de-Git)
* [JAVA 8](https://www.java.com/es/download/)
* [MAVEN](https://maven.apache.org)
* [DOCKER](https://www.docker.com/)
* [DOCKER COMPOSE](https://docs.docker.com/compose/install/).

## Otros
* [MongoDB](www.mongodb.com) - MongoDB es una base de datos de documentos que ofrece una gran escalabilidad y flexibilidad, y un modelo de consultas e indexación avanzado.

## Compilación
Para ejecutar el proyecto usamos Maven en el directorio raiz del proyecto  usando el siguiente comando.
```
mvn compile
mvn package
```

## Ejecución

#### SparkWebServer
Primero es necesario usar el siguiente comando:
```
mvn clean install
```

Para ejecutar la aplicacion use los siguientes comandos segun el SO.

**JAVA Linux**

        java -cp target/classes/:target/dependency/* co.edu.escuelaing.sparkdockeraws.app

**JAVA Windows**

        java -cp target/classes/;target/dependency/* co.edu.escuelaing.sparkdockeraws.app

## Documentación

Documentacion generada en [docs](docs).

### Javadoc
Para generar javadoc ejecute los siguientes comandos.

*        mvn javadoc:javadoc
*        mvn javadoc:jar

## Autor
[Juan Camilo Posso Guevara](https://github.com/JCPosso)
## Derechos de Autor
**©** _Juan Camilo Posso G., Escuela Colombiana de Ingeniería Julio Garavito._
## Licencia
Licencia bajo  [GNU General Public License](https://github.com/JCPosso/AREP-Laboratorio-1/blob/master/LICENSE).