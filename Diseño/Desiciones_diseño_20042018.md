## Experimento 2
**Actividad**|**Descripción**|**Responsablemente**
:--|:--|:--
Manejo de contraseñas entidad virtual|Almacenamiento de las contraseñas <br>Gestión de las contraseñas|Gabriel Pinto
Implementar seguridad servicios| Implemenatr la seguridad con Auth0 para la protección de los serviciós REST|Juan Trujillo
Gestión contraseñas entidad virtual|Implementar la comunicación con la entidad física.<br>Implementar el servicio REST de cambio de contraseña.<br>Revisar y completar servicios Batch|William Duarte y Hugo Hernandez
Diagrama de despliegue|Actualizar el diagrama de despliegue con el nuevo modelo.|Marlon Forero.

## Manejo del protocolo de comunicación.

Para el manejo de comunicación entre la entidad física y la entidad virtual se reaizara usando el protocolo MQTT.
Para el envio de peticiones a la entidad Física se usa el topico ``` entradasCerradura ```
Para la recepción de peticiones a la entidad Física se usa el topico ``` alarmasCerradura ```

### Protocolo de gestión de contraseñas 

Para la realización de la gestión de las contraseñas, se realiza el envio de mensajes con un formato basado en códigos:

##### Adición de contraseñas 

``` 01:<indice>:<contrasenia> ```, donde ``` <contrasenia> ``` representa la contraseña a adicionar e ``` <indice> ``` es la posición donde estala contraseña.

##### Cambio de contraseña 

``` 02:<indice>:<contrasenia> ```, donde ``` <contrasenia> ``` representa la nueva contraseña e ``` <indice> ``` es la posición donde estala contraseña.

##### Eliminación de una contraseña

``` 03:<indice> ```, donde  ``` <indice> ``` es la posición donde estala contraseña a eliminar.

##### Eliminar todas las contraseñas

``` 04: ``` para la eliminación de todas las contraseñas temporales registradas.

### Protocolo de evio de contraseñas

``` 05: ```  para el envió de todas las contraseñas del arduino.

### Protocolo de respuestas

En caso de que todo salga bien al momento de realizar la solicitud, el Arduino responde con:
``` OK:<codigoProtocolo> ```, donde ``` <codigoProtocolo> ``` puede ser un valor entre 01-05.
En el caso del prótocolo "05", se responde adicionalmente con las alarmas separadas por ":".

### Errores

Si se presenta un error, se responde así:
``` ERROR:<codigoProtocolo> ```, donde ``` <codigoProtocolo> ``` puede ser un valor entre 01-05. 
