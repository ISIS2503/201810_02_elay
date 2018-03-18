
### Estructura de mensajes 

El *Arduino* se encarga de enviar por el puerto serial el identificador del tipo de alarma que se presenta. El procesamiento de esto se realizara por *Node-Red* para asignar el mensaje de la alerta respectivo y enviarlo a la cola de mensajes.

Alerta | Nombre | Descripción
:--:|:--|:--
1|Puerta abierta| Si se deja la puerta abierta por mas de 10 segundos (para efectos practicos del experiento).
2|Intento de apertura sospechoso | Si se ingresa 3 veces una contraseña incorrecta.
3|Detección de persona sospechosa| Si se registra una persona por el sensor de proximidad en un horario no permitido.
4|Apertura no permitida | Se activa si se abre la puerta en un horario no permitido.
5|Nivel de batería crítico| Indica que el nivel de la batería de la cerradura es bajo y requiere ser recargada.

#### Estructura del envió del mensaje 

El Arduino envía un mensaje con la información de la alerta en el siguiente formato.

    A:<idAlerta>:<idDispositivo>:<torre>:<apartamento>
donde:
<br>
``` <idAlerta> ``` Es el identificador del la alrta  que posteríomente se le asignará el mensaje correspondiente. <br>
``` <idDispositivo> ``` Es el identificador de la cerradura donde ocurrió el evento. <br>
``` <torre> ``` Es la torre donde ocurrió el incidente. Esto es con el fin de ubicar más facíl la cerradura.<br>
``` <apartamento> ``` Es el aprtamento donde ocurrió el incidente. Esto es con el fin de ubicar más facíl la cerradura.
<br>

### Descripción de los topicos y filtros
Los filtros se realizarán orientado al facíl acceso por los roles que interactuan con la aplicación.

	<rol>/<torre>/<apto>/<alertaId>
donde:<br>
``` <rol> ``` Son los diferentes stakeholder que interactuan con el sistema. <br>
``` <torre> ``` Torre donde se encuentra la cerradura. <br>
``` <apto> ``` Apartamento donde se encuentra la cerradura. <br>
``` <alertaId> ``` El identificador de la alerta que se envian. <br>
<br>
