//------------------------------------------------------------------
// author: thomas diewald
// date: 29.08.2011
// detecting and decoding barcodes via webcam
//------------------------------------------------------------------

import diewald_bardcode.*;
import diewald_bardcode.CONSTANTS.*;
import processing.video.*;

Capture cam;
int cam_size_x = 640;
int cam_size_y = (int)(3.0*cam_size_x/4.0);
PFont font;


void setup() {
  size(cam_size_x + 500, cam_size_y);

  cam = new Capture(this, cam_size_x, cam_size_y, 30);
  
  font = createFont("Calibri", 15);
  textFont(font); textAlign(LEFT, TOP); textLeading(18);
}

void draw() {
  if( cam.available() ){
    background(0);
    cam.read();
    image(cam, 0, 0);
    
    //decodeMulti() can be very slow, ... it tries to find a barcode in the image, of all available barcodes in the library
//    DecodingResult dr = Barcode.decodeMulti(cam, CHARACTER_SET.DEFAULT, true, DECODE.values());
    DecodingResult dr = Barcode.decode(cam, CHARACTER_SET.DEFAULT, true, DECODE.QR_CODE);
//    DecodingResult dr = Barcode.decode(cam, CHARACTER_SET.DEFAULT, true, DECODE.CODE_128);

    if( dr.decodingFailed() ){
      println( dr.getDecodingException() );
      fill(200); text( "... couldnt find any decoded information ...", cam_size_x+40+1, height/2+1);
    } else {
      int x_pos = cam_size_x+40+1;
      int y_pos = 50;
      
      fill(200); 
      text("barcode-format: "+dr.getBarcodeFormat(), x_pos, y_pos);
      y_pos += 40;
      for(int i = 0; i < dr.getCorners().length; i++){
         text(dr.getCorners()[i].toString(),  x_pos, y_pos);
         y_pos += 20;
      }
      y_pos += 20;

      text(dr.getText(), x_pos, y_pos);
    }


  } // end if
}
