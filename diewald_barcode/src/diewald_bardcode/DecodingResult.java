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


import java.util.Locale;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import diewald_bardcode.CONSTANTS.DECODE;


/**
 * this class is the result of a decoded barcode.<br>
 * it contains information about the position of the barcode in the sourceimage, the text and the format of the barcode.<br>
 * 
 * @author thomas diewald (c) 2011
 *
 */
public final class DecodingResult {
  private Exception decoding_exception_;
  private boolean decoding_failed_ = false;
  private   Result result_;
  private   Corner corners_[];
  
//  /**
//   * protected Constructor
//   */
//  protected DecodingResult(){
//  }
  

  /**
   * protected Constructor
   */
  protected DecodingResult(Result result){
    result_ = result;
    decoding_failed_ = false;
    
//    System.out.println( getBarcodeFormat() );
//    System.out.println(result_.getTimestamp() );
//    System.out.println(result_.getRawBytes().length );
    
    ResultPoint[] result_points = result_.getResultPoints();
    corners_ = new Corner[result_points.length];
    for(int i = 0; i < corners_.length; i++){
      corners_[i] = new Corner(result_points[i].getX(), result_points[i].getY());
    }
  }
  
  
  protected DecodingResult(Exception e){
    decoding_exception_ = e;
    decoding_failed_    = true;
  }
  /**
   * get the exception if decoding failed ( decodingFailed() returns true).
   * @return the exception if decoding failed.
   */
  public final Exception getDecodingException(){
    return decoding_exception_;
  }
  /**
   * returns true, if the decoding of the barcode failed.<br>
   * on failure, you can get the exception by calling: getDecodingException().
   * 
   * @return true, if the decoding of the content failed.
   */
  public final boolean decodingFailed(){
    return decoding_failed_;
  }
  
  
  /**
   * 
   * @return the decoded content of the barcode.
   */
  public final String getText(){
    return result_.getText();
  }
  
  
  /**
   * 
   * @return the type of barcode, found in the image.
   */
  public final DECODE getBarcodeFormat(){
    BarcodeFormat b = result_.getBarcodeFormat();
    return DECODE.valueOf(b.getName());
  }
  
  /**
   * 
   * @return the (usually four) corners of the barcode, found in the image.
   */
  public final Corner[] getCorners(){
    return corners_;
  }
  
  /**
   * represents the corners of the barcode, found in the image.<br>
   * coorinates are 'x' and 'y'.
   * 
   * @author thomas
   *
   */
  public class Corner{
    public float x, y;
    
    protected Corner(float x, float y){
      this.x = x;
      this.y = y;
    }
    
    public String toString(){
      String hash_hex = Integer.toHexString(this.hashCode());
      String class_name = this.getClass().getName();
      String coords = "x:"+x+" / y:"+y;
      String representation = String.format(Locale.ENGLISH,"%s@%s - %s",class_name,hash_hex,coords);
      return representation;
    }
  }
  
}
