/**
 * diewald_bardcode - Processing Library.
 * 
 * this processing-library is for encoding/decoding barcodes.
 * dependencies: com.google.zxing
 * 
 * 
 * Copyright (c) 2011 Thomas Diewald
 *
 *
 * This source is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This code is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 * 
 * A copy of the GNU General Public License is available on the World
 * Wide Web at <http://www.gnu.org/copyleft/gpl.html>. You can also
 * obtain it by writing to the Free Software Foundation,
 * Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */



package diewald_bardcode;


import com.google.zxing.common.BitMatrix;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * this class is the result of an encoded barcode.<br>
 * the result can be a rendered PImage by using asImage(), or just the raw data (getBoolMatrix(), getBitMatrix()).<br>
 * for quick control of the result, print() does the job well too.<br>
 * 
 * further you can set the code /background-color of the rendered image.
 * 
 * 
 * @author thomas diewald (c) 2011
 *
 */
public final class EncodingResult {
  private Exception encoding_exception_;
  private boolean encoding_failed_ = false;
  private BitMatrix bit_matrix_;
  private int width_ = 0;
  private int height_ = 0;
  
  private int code_color_ = 0;
  private int bg_color_ = 0;
  
//  /**
//   *  protected constructor
//   */
//  protected EncodingResult(){
//  }

  /**
   *  protected constructor
   */
  protected EncodingResult(BitMatrix bit_matrix){
    bit_matrix_ = bit_matrix;
    encoding_failed_ = false;
    if( bit_matrix_ != null){
      width_  = bit_matrix_.getWidth();
      height_ = bit_matrix_.getHeight();
    }
    setCodeColor(0,0,0, 255);
    setBgColor(255, 255, 255, 255);
  }
  
  /**
   *  protected constructor
   */
  protected EncodingResult(Exception e){
    encoding_exception_ = e;
    encoding_failed_ = true;
  }
  
  /**
   * get the exception if encoding failed ( encodingFailed() returns true).
   * @return the exception if encoding failed.
   */
  public final Exception getEncodingException(){
    return encoding_exception_;
  }
  /**
   * returns true, if the encoding of the content failed.<br>
   * on failure, you can get the exception by calling: getEncodingException().
   * 
   * @return true, if the encoding of the content failed.
   */
  public final boolean encodingFailed(){
    return encoding_failed_;
  }
  

  /**
   * to define the color of the barcode.
   *   
   * @param red
   * @param green
   * @param blue
   * @param alpha
   * @return this
   */
  public final EncodingResult setCodeColor(int red, int green, int blue, int alpha){
    code_color_ = (alpha&0xFF)<<24 | (red&0xFF)<<16 | (green&0xFF)<<8 | (blue&0xFF)<<0 ;
    return this;
  }
  /**
   * to define the color of the barcode.
   * 
   * @param rgba
   * @return this
   */
  public final EncodingResult setCodeColor(int rgba){
    code_color_ = rgba;
    return this;
  }
  /**
   *  to define the color of the background.
   *  
   * @param red
   * @param green
   * @param blue
   * @param alpha
   * @return this
   */
  public final EncodingResult setBgColor(int red, int green, int blue, int alpha){
    bg_color_ = (alpha&0xFF)<<24 | (red&0xFF)<<16 | (green&0xFF)<<8 | (blue&0xFF)<<0 ;
    return this;
  }
  /**
   * to define the color of the background.
   *  
   * @param rgba
   * @return this
   */
  public final EncodingResult setBgColor(int rgba){
    bg_color_ = rgba;
    return this;
  }
  

  /**
   * print the barcode to the console, as an ASCII-Image.
   */
  public final void print(){
    if( bit_matrix_ != null ){
//      System.out.println(bit_matrix_.toString());
      for(int y = 0; y < height_; y++){
        for(int x = 0; x < width_; x++){
          System.out.print( bit_matrix_.get(x, y) ? "X ":"  ");
        }   
        System.out.println("");
      }
    }
  }
  /**
   * generate a PImage of the barcode.
   * 
   * @param papplet
   * @return PImage
   */
  public final PImage asImage(PApplet papplet){
    PImage img = papplet.createImage(width_, height_, PApplet.ARGB);
    if( bit_matrix_ != null ){
      img.loadPixels();
      for(int y = 0; y < height_; y++){
        for(int x = 0; x < width_; x++){
          int idx = y*width_+x;
          img.pixels[idx] = bit_matrix_.get(x, y) ? code_color_ : bg_color_;
        }   
      }
      img.updatePixels();
    }
    return img;
  }
  
  /**
   * get the BitMatrix (com.google.zxing.common.BitMatrix) of the barcode.
   * 
   * @return BitMatrix
   */
  public final BitMatrix getBitMatrix(){
    return bit_matrix_;
  }
  /**
   *  get an 2d array of boolean values, representing the barcode.
   * @return a 2d array 
   */
  public final boolean[][] getBoolMatrix(){
    boolean[][] matrix = new boolean[width_][height_];
    for(int y = 0; y < height_; y++){
      for(int x = 0; x < width_; x++){
        matrix[x][y] =  bit_matrix_.get(x, y);
      }   
    }
    return matrix;
  }
  /**
   *  get width of the barcode (in pixels).
   * @return width
   */
  public final int getWidth(){
    return width_;
  }
  /**
   *  get height of the barcode (in pixels).
   * @return height
   */
  public final int getHeight(){
    return height_;
  }
  /**
   * of the top left position of the barcode.
   * @return int[2]
   */
  public final int[] getTopLeftOnBit(){
    if( bit_matrix_ != null )
      return bit_matrix_.getTopLeftOnBit();
    else
      return new int[0];
  }
  
  
  
  
  
  
//  
//  
//  protected void setBitMatrix_temp(BitMatrix bit_matrix){
//    bit_matrix_ = bit_matrix;
//    if( bit_matrix_ != null){
//      
//      System.out.println(bit_matrix_.toString());
//      System.out.println("width: = "+bit_matrix_.width);
//      System.out.println("width: = "+bit_matrix_.getWidth());
//      System.out.println("height: = "+bit_matrix_.height);
//      System.out.println("height: = "+bit_matrix_.getHeight());
//      System.out.println("rowSize: = "+bit_matrix_.rowSize);
//      System.out.println("bits.length: = "+bit_matrix_.bits.length);
//      System.out.println("getTopLeftOnBit() : = "+bit_matrix_.getTopLeftOnBit()[0] +", "+ bit_matrix_.getTopLeftOnBit()[1]);
//      
//      System.out.println("");
//      for(int i = 0; i < bit_matrix_.bits.length; i++)
//        System.out.print(bit_matrix_.bits[i]+" ");
//      System.out.println("");
//      
//      BitArray ba =  bit_matrix_.getRow(0, null);
//      System.out.println(ba.toString());
//      System.out.println("ba.size = " + ba.size);
//      System.out.println("ba.getSize() = " + ba.getSize());
//      System.out.println("ba.getSizeInBytes() = " + ba.getSizeInBytes());
//      System.out.println("ba.bits.length = " + ba.bits.length);
//      System.out.println("");
//      for(int i = 0; i < ba.bits.length; i++)
//        System.out.print(ba.bits[i]+" ");
//      System.out.println("");
//      System.out.println("ba.get(3) = " + ba.get(3));
//      //isnon(ba.bits, 3);
//      System.out.println("--------------------------------------------------");
//      
//      System.out.println(ba.toString());
//      //System.out.println(ba.bits[0]+" ");
//      System.out.println("");
//      for(int i = 0; i < 32; i++){
//        if( i%8==0 )System.out.print( " ");
//        System.out.print( (ba.bits[0]>>i & 1) == 1 ? 'X':'.');
//      }
//      System.out.println("");
//      for(int i = 0; i < 180; i++){
//        if( i%8==0 )System.out.print( " ");
//        System.out.print( ba.get(i) ? 'X':'.');
//      }
//      System.out.println("");
//    }
//  }
//  
//  int bits_pos[] ={
//      0x01,
//      0x02,
//      0x04,
//      0x08,
//      0x10,
//      0x20,
//      0x40,
//      0x80,   
//      0x80,  
//  };
//  
//  public void isnon(int bit[], int index){
//    System.out.println("--------------------------------------------------");
//    System.out.println( bit[index] & 0x04);
//    System.out.println( bit[index]>>index & 1);
//    System.out.println("--------------------------------------------------");
//    System.out.println( 0x001);
//    System.out.println( 0x002);
//    System.out.println( 0x004);
//    System.out.println( 0x008);
//    System.out.println( 0x010);
//    System.out.println( 0x020);
//    System.out.println( 0x040);
//    System.out.println( 0x080);
//    System.out.println( 0x100);
//    System.out.println( 0x1F);
//
//    
////    System.out.println( 0B10111011 ); // 187
//  }
  
 

  
  
  
  
}
