//------------------------------------------------------------------
// author: thomas diewald
// date: 29.08.2011
// encoding barcodes
//------------------------------------------------------------------

import diewald_bardcode.*;
import diewald_bardcode.CONSTANTS.*;

int code_size = 500;
String content = "QRCODE: processing barcode library \nhttp://thomasdiewald.com/blog/";

void setup() {
  size(code_size, code_size);

  EncodingResult result = Barcode.encode(content, ENCODE.QR_CODE, code_size, code_size, CHARACTER_SET.DEFAULT, ERROR_CORRECTION.DEFAULT);
  if( result.encodingFailed() ){
    println( result.getEncodingException() );
  } else {
    result.setBgColor(255, 255, 255, 255);
    result.setCodeColor(0, 0, 100, 255);
  
    PImage barcode = result.asImage(this);
    if ( barcode != null)
      image(barcode, 0, 0);
      
    barcode.save("barcode_"+ENCODE.QR_CODE+".jpg");
  }
}

void draw() {
}
