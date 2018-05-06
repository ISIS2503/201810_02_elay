#include <Keypad.h>
#include <string.h>
#include <EEPROM.h>


//Formato de mensaje "A:idAlarma:idDispositivo:unidadResidencialtorre:apartamento"

//Incoming message
String serialResponse = "";

//Arreglo de comandos
int comandos[3] = {0};

//Contraseñas no permitidas
String noPerms = "";

//Comando que indica que se va a enviar la lista de contraseñas no permitidas
const int NO_PERMITIDAS = 0;

//Comando que pide que se cree una contraseña
const int CREAR_CONTRA = 1;

//Comando que pide que se actualice una contraseña
const int CAMBIAR_CONTRA = 2;

//Comando que pide que se elimine una contraseña
const int ELIMINAR_CONTRA = 3;

//Comando que pide que se eliminen todas las contraseñas
const int ELIMINAR_TODAS = 4;

//Comando que pide que se envien todas las contraseñas
const int ENVIAR_TODAS = 5;

//Comando que indica que se pide un health check
const int HC = 6;

//Indica que la operación salió bien
const String OPERACION_CORRECTA = "OK";

//Indica que hubo un error en la operación
const String OPERACION_ERROR = "ERROR";

//El health check a enviar
const String RESPUESTA = "HC";

//Id del dispositivo
const String ID = "333";

//Número de la torre
const String TORRE = "3";

//Número de apartamento
const String APTO = "704";

//Unidad residencial
const String UNIDAD = "123";

//Pin en el que se encuentra el sensor de movimiento PIR
byte pirPin = 2;

//Pin digital en el que está el LED rojo
int ledPin = A0;

//Indica el estado del sensor de movimiento
int pirState = LOW;

//Constante que indica que la puerta ha estado abierta por más de 10 segundos.
const String AL_1 = "1";

//Constante que indica que se ha excedido el límite permitido de intentos de apertura.
const String AL_2 = "2";

//Constante que indica una persona se acercó a la cerradura, en un horario no permitido.
const String AL_3 = "3";

//Constante que indica que una persona entró a la casa, en un horario no permitido.
const String AL_4 = "4";

//Constante que indica que la batería se encuentra en estado crítico
const String AL_5 = "5";

//Button pin
const int CONTACT_PIN = 11;

//R LED pin
const int R_LED_PIN = 13;

//G LED pin
const int G_LED_PIN = 12;

//B LED pin
const int B_LED_PIN = 10;

//Indica que la puerta ha sido abierta
const String ABIERTA = "La puerta ha sido abierta";

//Indica que la puerta ha sido cerrada
const String CERRADA = "La puerta ha sido cerrada";

//Attribute that defines the button state
boolean buttonState;

//Boolean que indica si es necesario verificar el tiempo que lleva abierta la puerta
boolean checkOpen;

//Current time when the door is opened
long currTime;

//Current time when someone is in front of the door
long presentTime;

//Number of current attempts
byte attempts;

//Tiempo restante de presencia de alguna persona
int restante = 60;

//Keypad rows
const byte ROWS = 4;

//Keypad columns
const byte COLS = 3;

//Maximum number of attempts allowed
const byte maxAttempts = 3;

//Current key variable
String currentKey;

//Door state
boolean open;

//Indica si alguien sospechoso esta detras de la puerta más del tiempo limite
boolean confirmandoPresencia = true;
long temporizador;
//Alarma si la puerta fue abierta en un horario no permitido
boolean accesoIlegal;

//Indica si la seguridad debe estar activa;
boolean seguridad;

//If the number of current attempts exceeds the maximum allowed
boolean block;

//La alarma que se imprime
boolean alarma;

//Minimum voltage required for an alert
const double MIN_VOLTAGE = 1.2;

//Battery indicator
const int BATTERY_LED = A1;

//Battery measure pin
const int BATTERY_PIN = A2;

//Current battery charge
double batteryCharge;

//Indicates that the battery is low
boolean lowBattery;

//Counter of time that indicates when the buzzer must sound
long lowBatFrec;

//Indicates that the buzzer is making a sound
boolean buzzerSound;

const char *A[3];

//Keypad mapping matrix
char hexaKeys[ROWS][COLS] = {
  {
    '1', '2', '3'
  }
  ,
  {
    '4', '5', '6'
  }
  ,
  {
    '7', '8', '9'
  }
  ,
  {
    '*', '0', '#'
  }
};

//Keypad row pins definition
byte rowPins[ROWS] = {
  9, 8, 7, 6
};

//Keypad column pins definition
byte colPins[COLS] = {
  5, 4, 3
};

//Keypad library initialization
Keypad customKeypad = Keypad(makeKeymap(hexaKeys), rowPins, colPins, ROWS, COLS);

void setup() {
  Serial.begin(9600);
  checkOpen = false;
  seguridad = false; //SEGURIDAD Activada
  alarma = false;

  //Setup Al_1
  buttonState = false;
  pinMode(R_LED_PIN, OUTPUT);
  pinMode(G_LED_PIN, OUTPUT);
  pinMode(B_LED_PIN, OUTPUT);
  pinMode(CONTACT_PIN, INPUT);
  setColor(0, 255, 255);


  //Setup AL_2
  currentKey = "";
  open = false;
  attempts = 0;
  block = false;

  //Setup Sensor de movimiento
  pinMode(ledPin, OUTPUT);
  pinMode(pirPin, INPUT);

  //Setup batería crítica
  // Ouput pin definition for BATTERY_LED
  pinMode(BATTERY_LED, OUTPUT);
  //Input pin definition for battery measure
  pinMode(BATTERY_PIN, INPUT);

  //Indica que empieza la conexión
  Serial.println("START:" + ID);
  
}

void loop() {

  while (Serial.available()) {
    if ( Serial.available()) {
      serialResponse = Serial.readStringUntil('\r\n');
      comandos[3] = { 0 };
      processCommand(serialResponse);
    }
  }

  char customKey;
  //Si alguien entró a la casa en un periodo de tiempo no permitido

  //Value conversion from digital to voltage
  batteryCharge = (analogRead(BATTERY_PIN) * 5.4) / 1024;

  //Measured value comparison with min voltage required
  if (batteryCharge <= MIN_VOLTAGE) {
    digitalWrite(BATTERY_LED, HIGH);
    if (!lowBattery) {
      Serial.println("A:" + AL_5 + ":" + ID + ":" + UNIDAD + ":" + APTO + ":" + TORRE);
      lowBatFrec = millis();
      setColor(255, 0, 0);
      buzzerSound = true;
    }
    lowBattery = true;

  }
  else {
    digitalWrite(BATTERY_LED, LOW);
    lowBattery = false;
    stopBuzzer();
  }

  if (lowBattery) {
    long actual = millis() - lowBatFrec;
    if (buzzerSound && actual >= 2000) {
      stopBuzzer();
    }
    else if (!buzzerSound && actual >= 30000) {
      lowBatFrec = millis();
      setColor(255, 0, 0);
      buzzerSound = true;
    }
  }

  //Si la puerta no se encuentra abierta
  if (!open) {
    //Si la seguridad se encuentra desactivada
    if (seguridad) {
      securityActive();

    }

    //Llama al método de abrir la puerta manualmente
    openManually();

    //Si la puerta no está bloqueada
    if (!block) {

      //Solo se permite abrir la puerta con el teclado númerico
      //si y solo sí, la seguridad esta desactivada
      if (!seguridad) {

        //Selecciona la tecla presionada
        customKey = customKeypad.getKey();

        //Llama al método de abrir la puerta con el teclado númerico
        if (!accesoIlegal) {
          openWithKeypad(customKey);
        }
      }
    }
    else {

      //Si la puerta está bloqueda
      if (block) {
        setColor(255, 0, 0);
        //Espera a que la puerta se abra manualmente
        while (!open) {
          openManually();
        }
      }
    }

  }
  else {

    //Si el botón ha sido presionado, llama al método de la pulsación
    if (buttonState) {
      checkDoorOpened();
    }
    else {

      //Selecciona la tecla presionada
      customKey = customKeypad.getKey();

      //Si la tecla presionada es "*", la puerta se cierra
      if (customKey == '*') {
        open = false;
        currentKey = "";
        setColor(0, 255, 255);
        checkOpen = false;
        alarma = false;
        Serial.println(CERRADA);
      }
    }

    //Verifica el tiempo que la puerta lleva abierta
    checkTimeOpened(checkOpen);
  }

  delay(100);
}

//Método que permite abrir la puerta por medio del pulsador
void openManually() {
  if (!buttonState) {
    if (digitalRead(CONTACT_PIN)) {
      currTime = millis();
      buttonState = true;
      if (!alarma) {
        setColor(0, 255, 0);
      }
      open = true;
      attempts = 0;
      currentKey = "";
      Serial.println(ABIERTA);
      block = false;
      checkOpen = true;
      alarma = false;

      if (seguridad) {
        accesoIlegal = true;
        presentTime = 0;
        pirState = LOW;
        restante = 60;
        Serial.print("A:" + AL_4 + ":" + ID + ":" + UNIDAD + ":" + APTO + ":" + TORRE);
        setColor(255, 0, 0);
        alarma = true;
        digitalWrite(ledPin, LOW);
      }

    }
  }
}

//Método que verifica si la puerta está abierta por medio de la pulsación del pulsador
void checkDoorOpened() {
  if (!digitalRead(CONTACT_PIN)) {
    if (!alarma) {
      setColor(0, 0, 255);
    }

    open = false;
    checkOpen = false;
    alarma = false;
    buttonState = false;
    Serial.println(CERRADA);
  }
}

//Método que verifica si la puerta lleva abierta más de 30 segundos
void checkTimeOpened(boolean check) {
  if (check && (millis() - currTime) >= 30000) {
    Serial.print("A:" + AL_1 + ":" + ID + ":" + UNIDAD + ":" + APTO + ":" + TORRE);
    checkOpen = false;
    alarma = true;
    setColor(255, 0, 0);
  }
}

//Método que permite que la puerta sea abierta por medio del teclado númerico
void openWithKeypad(char customKey) {

  //Verification of input and appended value
  if (customKey) {
    currentKey += String(customKey);
    Serial.println(currentKey);
  }

  //If the current key contains '#' reset attempt
  if (currentKey.endsWith("#") && currentKey.length() <= 4) {
    currentKey = "";
    Serial.println("Intento borrado");
  }

  //If current key matches the key length
  if (currentKey.length() == 4) {
    if (compareKey(currentKey) && permitido(currentKey)) {
      open = true;
      Serial.println(ABIERTA);
      attempts = 0;
      if (!alarma) {
        setColor(0, 255, 0);
      }
      currTime = millis();
      checkOpen = true;
    }
    else {
      attempts++;
      currentKey = "";
      Serial.println("Número de intentos: " + String(attempts));
    }
  }

  if (attempts >= maxAttempts) {
    block = true;
    Serial.println("A:" + AL_2 + ":" + ID + ":" + UNIDAD + ":"  + APTO + ":" + TORRE);
    alarma = true;
  }

}

//Metodo que controla que nadie este en la puerta mientras la seguridad está activada.
void securityActive() {
  int val = digitalRead(pirPin);
  if (val == HIGH) {
    digitalWrite(ledPin, HIGH);
    if (pirState == LOW) {
      Serial.println("¡Movimiento detectado!");
      pirState = HIGH;
      presentTime = millis();

    }

    else {

      int calculoRestante = 60 - ((millis() - presentTime) / 1000);

      if (calculoRestante != restante) {
        Serial.print("Tiempo restante de: ");
        Serial.print(restante);
        Serial.println(" segundos.");
        restante = calculoRestante;
        temporizador = 0;
        confirmandoPresencia = true;

      }

      if (millis() - presentTime >= 60000) {
        presentTime = 0;
        pirState = LOW;
        restante = 60;
        Serial.println("A:" + AL_3 + ":" + ID + ":" + UNIDAD + ":" + APTO + ":" + TORRE);
        setColor(255, 0, 0);
        alarma = true;
      }
    }
  }

  else {
    digitalWrite(ledPin, LOW);

    if (pirState == HIGH) {

      //Se esperan 10 segundos para verificar que la persona ya no esté en la puerta
      if (confirmandoPresencia) {
        Serial.println("Confirmando presencia...");
        temporizador = millis();
        confirmandoPresencia = false;
      }
      val = digitalRead(pirPin);
      if (val == LOW && millis() - temporizador >= 10000 && !confirmandoPresencia) {
        presentTime = 0;
        pirState = LOW;
        restante = 60;
        digitalWrite(ledPin, LOW);
        Serial.println("No se detecta movimiento.");
        temporizador = 0;
        confirmandoPresencia = true;
      }
    }
  }
}

//Method that outputs the RGB specified color
void setColor(int redValue, int greenValue, int blueValue) {
  analogWrite(R_LED_PIN, redValue);
  analogWrite(G_LED_PIN, greenValue);
  analogWrite(B_LED_PIN, blueValue);
}

void stopBuzzer() {
  if (open && !alarma) {
    setColor(0, 255, 0);
  }
  else if (!alarma) {
    setColor(0, 0, 255);
  }
  buzzerSound = false;
}


// Methods that divides the command by parameters
void processCommand(String command) {
  String com = command;
  const char *coms = command.c_str();
  char *pt;
  int i = 0;
  pt = strtok (coms, ":");
  int code;
  while (pt != NULL) {
    code = atoi(pt);
    comandos[i] = code;
    i++;
    pt = strtok (NULL, ":");
  }

  code = comandos[0];

  if (code == NO_PERMITIDAS) {
    noPerms = com;
    Serial.println(OPERACION_CORRECTA + ":0" + code);
  }
  else if (code == CREAR_CONTRA) {
    addPassword(comandos[2], comandos[1]);
  } else if (code == CAMBIAR_CONTRA ) {
    updatePassword(comandos[2], comandos[1]);
  } else if (code == ELIMINAR_CONTRA ) {
    deletePassword(comandos[1]);
  } else if (code == ELIMINAR_TODAS ) {
    deleteAllPasswords();
  } else if (code == ENVIAR_TODAS ) {
    getAllPasswords();
  } else if (code == HC){
    Serial.println(RESPUESTA); 
  }
  else {
    Serial.println(OPERACION_ERROR + ":" + code);
  }

}

void deleteAllPasswords() {
  //Password reference to inactive
  EEPROM.write(0, 0);
  EEPROM.write(1, 0);
  EEPROM.write(2, 0);
  Serial.println(OPERACION_CORRECTA + ":0" + ELIMINAR_TODAS);
}

void deletePassword(int index) {
  byte i = 1;
  byte location = index / 8;
  byte position = index % 8;
  i <<= position;
  byte j = EEPROM.read(location);
  j ^= i;
  EEPROM.write(location, j);
  Serial.println(OPERACION_CORRECTA + ":0" + ELIMINAR_CONTRA);
}

void addPassword(int val, int index) {
  if (index < 0 || index > 19 || compareKey(String(val))) {
    Serial.println(OPERACION_ERROR + ":0" + CREAR_CONTRA);
  } else {
    byte arg0 = val % 256;
    byte arg1 = val / 256;
    EEPROM.write((index * 2) + 3, arg0);
    EEPROM.write((index * 2) + 4, arg1);
    byte i = 1;
    byte location = index / 8;
    byte position = index % 8;
    i <<= position;
    byte j = EEPROM.read(location);
    j |= i;
    EEPROM.write(location, j);
    Serial.println(OPERACION_CORRECTA + ":0" + CREAR_CONTRA);
  }

}

boolean compareKey(String key) {
  int acc = 3;
  int codif, arg0, arg1;
  for (int i = 0; i < 3; i++) {
    codif = EEPROM.read(i);
    while (codif != 0) {
      if (codif % 2 == 1) {
        arg0 = EEPROM.read(acc);
        arg1 = EEPROM.read(acc + 1) * 256;
        arg1 += arg0;
        if (String(arg1) == key) {
          return true;
        }
      }
      acc += 2;
      codif >>= 1;
    }
    acc = (i + 1) * 16 + 3;
  }
  return false;
}

//Mira si la contraseña ingresada esta autorizada en ese horario
boolean permitido(String key){
  if(noPerms.indexOf(key) > 0){
    return false;
  }else{
    return true;
  }
}


boolean getAllPasswords() {
  int acc = 3;
  int j = 20;
  int codif, arg0, arg1;
  String claves = "";
  for (int i = 0; i < 3; i++) {
    codif = EEPROM.read(i);

    while (codif != 0) {
      if (codif % 2 == 1) {
        arg0 = EEPROM.read(acc);
        arg1 = EEPROM.read(acc + 1) * 256;
        arg1 += arg0;
        claves += String(arg1) + ":";
        j++;
      }
      acc += 2;
      codif >>= 1;

    }
    acc = (i + 1) * 16 + 3;
  }

  if (claves != "") {
    Serial.println(OPERACION_CORRECTA + ":0" + ENVIAR_TODAS + ":" + claves + "FIN");
  } else {
    Serial.println(OPERACION_ERROR + ":0" + ENVIAR_TODAS);
  }
}

//Method that updates a password in the specified index
void updatePassword(int val, int index) {
  if (index < 0 || index > 19 || compareKey(String(val))) {
    Serial.println(OPERACION_ERROR + ":0" + CAMBIAR_CONTRA);
  } else {
    byte arg0 = val % 256;
    byte arg1 = val / 256;
    EEPROM.write((index * 2) + 3, arg0);
    EEPROM.write((index * 2) + 4, arg1);
    Serial.println(OPERACION_CORRECTA + ":0" + CAMBIAR_CONTRA);
  }
}
