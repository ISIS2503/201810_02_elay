#include <Keypad.h>

//Formato de mensaje "A:idAlarma:idDispositivo:torre:apartamento"

//Id del dispositivo
const String ID = "ELAY";

//Número de la torre
const String TORRE = "3";

//Número de apartamento
const String APTO = "704";

//Constante que indica que la puerta ha estado abierta por más de 10 segundos.
const String AL_1 = "1";

//Constante que indica que se ha excedido el límite permitido de intentos de apertura.
const String AL_2 = "2";

//Constante que indica una persona se acercó a la cerradura, en un horario no permitido.
const String AL_3 = "3";

//Specified password
const String KEY = "1234";

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

//Number of current attempts
byte attempts;

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

//If the number of current attempts exceeds the maximum allowed
boolean block;

//La alarma que se imprime
String alarma = "";

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
}

void loop() {

  char customKey;

  //Si la puerta no se encuentra abierta
  if (!open) {

    //Llama al método de abrir la puerta manualmente
    openManually();

    //Si la puerta no está bloqueada
    if (!block) {

      //Selecciona la tecla presionada
      customKey = customKeypad.getKey();

      //Llama al método de abrir la puerta con el teclado númerico
      openWithKeypad(customKey);
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
      setColor(0, 255, 0);
      open = true;
      attempts = 0;
      currentKey = "";
      Serial.println(ABIERTA);
      block = false;
      checkOpen = true;
    }
  }
}

//Método que verifica si la puerta está abierta por medio de la pulsación del pulsador
void checkDoorOpened() {
  if (!digitalRead(CONTACT_PIN)) {
    setColor(0, 0, 255);
    open = false;
    checkOpen = false;
    buttonState = false;
    Serial.println(CERRADA);
  }
}

//Método que verifica si la puerta lleva abierta más de 10 segundos
void checkTimeOpened(boolean check) {
  if (check && (millis() - currTime) >= 10000) {
    setColor(255, 0, 0);
    Serial.println("A:" + AL_1 + ":" + ID + ":" + APTO + ":" + TORRE);
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
  if (currentKey.endsWith("#") && currentKey.length() <= KEY.length()) {
    currentKey = "";
    Serial.println("Intento borrado");
  }

  //If current key matches the key length
  if (currentKey.length() == KEY.length()) {
    if (currentKey == KEY) {
      open = true;
      Serial.println(ABIERTA);
      attempts = 0;
      setColor(0, 255, 0);
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
    Serial.println("A:" + AL_2 + ":" + ID + ":" + APTO + ":" + TORRE);
  }

}

//Method that outputs the RGB specified color
void setColor(int redValue, int greenValue, int blueValue) {
  analogWrite(R_LED_PIN, redValue);
  analogWrite(G_LED_PIN, greenValue);
  analogWrite(B_LED_PIN, blueValue);
}

