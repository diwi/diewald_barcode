//------------------------------------------------------------------
// author: thomas diewald
// date: 29.08.2011
// detecting and decoding QR-codes
//------------------------------------------------------------------

import diewald_bardcode.*;
import diewald_bardcode.CONSTANTS.*;

int code_size = 500;
String content = "processing barcode library\nhttp://thomasdiewald.com/blog/";
String filename ="barcode_QR_CODE.jpg";
void setup() {
  size(code_size, code_size);




  PImage code = loadImage(filename);
  image(code, 0, 0);
  DecodingResult dr = Barcode.decode(code, CHARACTER_SET.DEFAULT, true, DECODE.QR_CODE);
  if( dr.decodingFailed() ){
    println( dr.getDecodingException() );
  } else {
    DecodingResult.Corner corners[] = dr.getCorners();
    println("\n----- barcode content -------------");
    println(dr.getText());
    
    println("\n----- barcode format -------------");
    println(dr.getBarcodeFormat());
    
    println("\n----- barcode corners -------------");
    println(dr.getCorners());
  }
}

void draw() {
}
