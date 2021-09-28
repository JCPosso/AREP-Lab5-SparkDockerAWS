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

## Compilación y ejecución

Primero es necesario usar el siguiente comando:
```
mvn clean install
```
Para ejecutar el proyecto usamos Maven en el directorio raiz del proyecto  usando el siguiente comando.
```
mvn package
mvn compile
```

## Despliegue Localhost
Para poder realizar el despliegue en localhost debemos desplegar tanto los contenedores y las imagenes Docker ,
por lo que primero  nos ubucamis en la raiz del directorio y  ejecutamos el siguiente comando:
```
docker-compose up -d
```
Si se ha ejecutado correctamente en consola obtendremos los siguientes resultados en consola.

![docker-compose](/img/docker-compose.img)

Esto significa que nuestras imagenes y contenedores ya han sido creados y ejecutados correctamente , para comprobarlo
ejecutamos los siguientes comandos y obtendremos los siguientes resultados:

![docker-images](/img/docker-images.img)

```
docker images
```

![docker-ps](/img/docker-ps.img)

```
docker ps
```
Finalmente accedemos a la siguiente dirección y comprobamos que se muestre correctamente el aplicativo web en el navegador:
```
http://localhost:35000
```

![localhost](/img/localhost.img)

### Probando servicio en localhost
Al agregar un nombre y un mensaje éste debe aparecer en los logs presentados en la tabla de abajo
entre las diez primeras:

![ingreso-local](/img/ingreso-local.img)

Y efectivamente obtenemos los resultados esperados:

![respuesta-local](/img/respuesta-local.img)

### Docker hub
Docker hub es necesario para poder  desplegar las imagenes en AWS, es por ello que debemos crear un repositorio nuevo
por lo que a las imágenes docker anteriormente creadas le añadimos un tag para poder referenciarlas dentro del repositorio Hub
por lo que fue necesario ejecutar los siguientes comandos en mi caso:

![tags-images.png](/img/tags-images.png)

Para confirmar ejecutamos ```docker images``` y nos debe salir algo parecido a lo que se muestra a continuacion:

![images-with.tag](/img/images-with-tag.png)

Por ultimo enviamos las imágenes a ```docker hub``` con el siguiente comando:

``
docker push juancamiloposso/arepawsroundrobin:[TAG_LOCAL_IMAGE_NAME]
``

Para revisar que las imagenes se encuentren en el repositorio  nos dirigimos a ```docker hub``` y obtendremos:
algo similar a esto :

![docker-hub](/img/docker-hub.png)

## AWS
Para poder hacer el despliegue nos dirigimos a la consola de ```aws``` y la encendemos:

![consola-aws](/img/consola-aws.png)

### Maquina virtual EC2
Una vez encendida la maquina  damos click  al apartado Lance  una maquina virtual con EC2 
Al hacerlos nos saldra un apartado en donde nos pediran escoger el tipo de maquina  y sistema operativo a usar
en este caso se escoge Amazon Linux 2 AMI (HVM), SSD Volume Type" de 64 bits(x86).

![ec2-linux](/img/ec2-linux.png)

Seguimos cada uno de los pasos y damos en lanzar,en este punto se nos pide seleccionar un par de claves
en este caso creamos un nuevo par y descargamos el archivo en el computador

![ec2-crear llaves](/img/ec2-crearllaves.png)


![consola keypar](/img/consola-keypar.png)


una vez seleccionadas le damos en 'Lanzar', si se ha lanzado la instancia correctamente nos saldrá un mensaje
como el siguiente:

![ec2-connect](/img/ec2-connect.png)

Al seleccionar la instancia  vamos al menu acciones y damos  en conectar:

![ec2-acciones-conectar](/img/ec2-acciones-conectar.png)

En el siguiente apartado nos pide la forma en que queremos conectar en estecaso elegimos por  ``ssh``
nos vamos al apartado y copiamos el comando para conectarnos :

![ec2-clientessh](/img/ec2-clientessh.png)

Nos dirigimos a la carpeta donde almacenamos la contraseña y ejecutamos el comando,
si la conexión se realizó correctamente nos debe aparecer un apartado como el siguiente:

![consola-ec2](/img/consola-ec2.png)

Perfecto!, ahora para poder usar de manera correcta la máquina virtual con docker actualizamos paquetes,
descargamos docker y le damos permiso de administrador para ello realizamos los siguientes pasos:

1. Actualizamos repositorio
```
sudo yum update -y
```
2. Instalamos docker
```
sudo yum install docker
```
4. Damos permisos de administrador a docker
```
sudo usermod -a -G docker ec2-user
```
5. reiniciamos la máquina para guardar los cambios
```
exit
ssh -i "roundrobin.pem" ec2-user@ec2-54-163-79-253.compute-1.amazonaws.com
```
6. Iniciamos servicio docker
```
sudo service docker start
```

### Configurando puertos de enlace AWS
Preparada la máquina de AWS procedemos a configurarla para que se conecte a los puertos que vamos a dedicar a docker.

Para ello, vamos a la consola de amazon y en seguridad accedemos a grupos de seguridad:

![ec2-seguridad](/img/ec2-seguridad.png)

Una vez allí nos dirigimos al apartado de editar reglas de entrada

![ec2 editar reglas de entrada](/img/ec2-editar-reglas.png)

En este punto crearemos una regla por cada puerto que vamos a necesitar en cada imagen docker, en nuestro caso
usamos **35000** para roundrobin *35001* service1 *35002* service2 *35003* service 3 y para mongo usamos **27017**

![Ec2 add rules](/img/ec2-add-rules.png)

Terminado esto damos en guardar reglas y configuramos las reglas de salida de la misma forma 
para que así se pueda comunicar docker con la máquina y viceversa.

### Despliegue 

Para el despliegue ejecutamos las imágenes docker en AWS:
```
docker run -d -p [PUERTO]:6000 --name [TAG-IMAGE-AWS] juancamiloposso/arepawsroundrobin:[TAG_DOCKER_IMAGES]
```

y tendremos algo similar a esto en la consola:

![aws docker run](/img/aws-docker-run.png)

Comprobamos que se encuentren las imágenes con `docker images` :

![AWS docker images](/img/aws-docker-images.png)

y revisamos los contenedores con `docker ps` :

![AWS docker ps](/img/aws-dockerps.png)

Finalmente, verificamos que esté el despliegue en nuestra máquina AWS 
siguiendo el siguiente enlace

### Pruebas despliegue AWS
Ejecutamos las mismas pruebas que anteriormente ejecutamos en localhost y probamos que el servicio esté
correctamente funcionando

![ingreso-local](/img/aws-ingreso.img)

Y efectivamente obtenemos los resultados esperados:

![respuesta-local](/img/aws-respuesta.img)


## Documentación

Documentación generada en [docs](docs).

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