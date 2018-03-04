#include <Keypad.h>

//Formato de mensaje "A:idAlarma:idDispositivo:torre:apartamento"

//Id del dispositivo
const String ID = "ELAY";

//Número de la torre
const String TORRE = "3";

//Número de apartamento
const String APTO = "704";

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
boolean presencia;

//Alarma si la puerta fue abierta en un horario no permitido;ç
boolean accesoIlegal;

//Indica si la seguridad debe estar activa;
boolean seguridad;

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
  seguridad = true; //SEGURIDAD Activada

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
}

void loop() {

  char customKey;
  //Si alguien entró a la casa en un periodo de tiempo no permitido
  
  //Si la puerta no se encuentra abierta
  if (!open) {
    //Si la seguridad se encuentra desactivada
    if(seguridad){
      securityActive();
      
    }
   
    //Llama al método de abrir la puerta manualmente
    openManually();

    //Si la puerta no está bloqueada
    if (!block) {

      //Solo se permite abrir la puerta con el teclado númerico
      //si y solo sí, la seguridad esta desactivada
      if(!seguridad){
      
      //Selecciona la tecla presionada
      customKey = customKeypad.getKey();

      //Llama al método de abrir la puerta con el teclado númerico
      if(!accesoIlegal){
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

     if(seguridad){
        accesoIlegal = true;
        presencia = false;
        presentTime = 0;
        pirState = LOW;
        restante = 60;
        Serial.println("A:" + AL_4 + ":" + ID + ":" + APTO + ":" + TORRE);
        digitalWrite(ledPin, LOW);
     }
    
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
    checkOpen = false;
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

//Metodo que controla que nadie este en la puerta mientras la seguridad está activada. 
void securityActive(){
  int val = digitalRead(pirPin);
  if(val == HIGH){
     digitalWrite(ledPin, HIGH); 
     if(pirState == LOW){
       Serial.println("¡Movimiento detectado!");
       pirState = HIGH;
       presentTime = millis();
       
     }

     else{
      
      int calculoRestante = 60 - ((millis()-presentTime)/1000);

      if(calculoRestante != restante){
      Serial.print("Tiempo restante de: ");
      Serial.print(restante);
      Serial.println(" segundos.");
      restante = calculoRestante;
      
      }
    
        if(millis()-presentTime >= 60000){
            presencia = true;
            presentTime = 0;
            pirState = LOW;
            restante = 60;
            Serial.println("A:" + AL_3 + ":" + ID + ":" + APTO + ":" + TORRE);
        }
     }
  }

  else{
    digitalWrite(ledPin, LOW);
    
    if(pirState == HIGH){
      
      //Se esperan 10 segundos para verificar que la persona ya no esté en la puerta
      Serial.println("Confirmando presencia...");
      delay(10000);
      val = digitalRead(pirPin);
      if(val == LOW){
        presentTime = 0;
        pirState = LOW;
        restante = 60;
        digitalWrite(ledPin, LOW);
        Serial.println("No se detecta movimiento.");
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

