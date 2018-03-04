#include <Keypad.h>

//Id del dispositivo
const String ID = "ELAY";

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

//Attribute that defines the button state
boolean buttonState;

boolean checkOpen;

//Current time when the button is tapped
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

  if (!open) {

    openManually();

    if (!block) {
      //Selected key parsed;
      customKey = customKeypad.getKey();

      openWithKeypad(customKey);
    }
    else {
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

    if (buttonState) {
      checkDoorOpened();
    }
    else {
      //Selected key parsed;
      customKey = customKeypad.getKey();

      if (customKey == '*') {
        open = false;
        currentKey = "";
        setColor(0, 255, 255);
        checkOpen = false;
        Serial.println("Door closed");
      }
    }
    
    checkTimeOpened(checkOpen);
  }

  delay(100);
}

void openManually() {

  if (!buttonState) {
    if (digitalRead(CONTACT_PIN)) {
      currTime = millis();
      buttonState = true;
      setColor(0, 255, 0);
      open = true;
      attempts = 0;
      currentKey = "";
      Serial.println("Door opened!!");
      block = false;
      checkOpen = true;
    }
  }

}

void checkDoorOpened() {
  if (!digitalRead(CONTACT_PIN)) {
    setColor(0, 0, 255);
    open = false;
    checkOpen = false;
    buttonState = false;
    Serial.println("Door closed!!");
  } 
}

void checkTimeOpened(boolean check){
  if (check && (millis() - currTime) >= 10000) {
      setColor(255, 0, 0);
    }
}

void openWithKeypad(char customKey) {

  //Verification of input and appended value
  if (customKey) {
    currentKey += String(customKey);
    Serial.println(currentKey);
  }

  //If the current key contains '#' reset attempt
  if (currentKey.endsWith("#") && currentKey.length() <= KEY.length()) {
    currentKey = "";
    Serial.println("Attempt deleted");
  }

  //If current key matches the key length
  if (currentKey.length() == KEY.length()) {
    if (currentKey == KEY) {
      open = true;
      Serial.println("Door opened!!");
      attempts = 0;
      setColor(0, 255, 0);
      currTime = millis();
      checkOpen = true;
    }
    else {
      attempts++;
      currentKey = "";
      Serial.println("Number of attempts: " + String(attempts));
    }
  }

  if (attempts >= maxAttempts) {
    block = true;
  }

}

//Method that outputs the RGB specified color
void setColor(int redValue, int greenValue, int blueValue) {
  analogWrite(R_LED_PIN, redValue);
  analogWrite(G_LED_PIN, greenValue);
  analogWrite(B_LED_PIN, blueValue);
}

