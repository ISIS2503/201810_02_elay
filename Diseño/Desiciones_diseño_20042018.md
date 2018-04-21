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

``` 00:<indice>:<contrasenia> ```, donde ``` <contrasenia> ``` representa la contraseña a adicionar e ``` <indice> ``` es la posición donde estala contraseña.

##### Cambio de contraseña 

``` 01:<indice>:<contraseniaAntigua>:<contrseniaNueva> ```, donde ``` <contraseniaAntigua> ``` representa la contaseña a modificar, ``` <contrseniaNueva> ``` representa la nueva contraseña e ``` <indice> ``` es la posición donde estala contraseña..

##### Eliminación de una contraseña

``` 02:<indice>:<contrasenia>  ```, donde ``` <contrasenia>  ``` es la contraseña a eliminar e ``` <indice> ``` es la posición donde estala contraseña..

##### Eliminar todas las contraseñas

``` 03: ``` para la eliminación de todas las contraseñas temporales registradas.

### Protocolo de evio de contraseñas

``` 04: ```  para el envió de todas las contraseñas del arduino.
