## Reunión 27 de febrero del 2018

### Estructura de mensajes 

El *Arduino* se encarga de enviar por el puerto serial el identificador del tipo de alarma que se presenta. El procesamiento de esto se realizara por *Node-Red* para asignar el mensaje de la alerta respectivo y enviarlo a la cola de mensajes.

Alerta | Nombre | Descripción
:--:|:--|:--
1|Puerta abierta| Si se deja la puerta abierta por mas de 5 segundos (para efectos practicos del experiento).
2|Intento de apertura sospechoso | Si se ingresa 3 veces una contraseña incorrecta.
3|Apertura no permitida | Si se registra una persona por el sensor de proximidad.

### URI de acceso 
Para el caso se asume que el usuario ya se encuentra registrado.
La raíz de acceso principal se define como:

    */dispositivo
Para el acceso de un dspositivo especifico de ingresa el *id* del dispositivo

    */dispositivo/{id}

    */dispositivo/{id}/alertas/{id}


### Filtros 
Los filtros se realizarán orientado al facíl acceso por los roles que interactuan con la aplicación.

    <rol> / <dispositivoId> / <alertas>
donde:
``` <rol> ``` Son los diferentes stakeholderque interactuan con el sistema.
``` <dispositivoId> ``` El identificador único de cada cerradura
``` <alerta> ``` El identificador de la alerta que se envian.
