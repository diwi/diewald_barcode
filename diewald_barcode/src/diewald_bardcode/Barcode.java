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

import java.awt.image.BufferedImage;
import java.util.Hashtable;
import java.util.Vector;

import processing.core.PImage;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;

import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.Writer;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.MultipleBarcodeReader;
import com.google.zxing.multi.qrcode.QRCodeMultiReader;

import diewald_bardcode.CONSTANTS.CHARACTER_SET;
import diewald_bardcode.CONSTANTS.DECODE;
import diewald_bardcode.CONSTANTS.ENCODE;
import diewald_bardcode.CONSTANTS.ERROR_CORRECTION;

/**
 * this class is the main entry point for the library.<br>
 * it contains methods for encoding and decoding barcodes.<br>
 * 
 * <br><br>
 * basic encoding example:
 * <p>
 *  void draw(){<br>
 *  &nbsp;&nbsp;  EncodingResult result = Barcode.encode("hallo moon", ENCODE.QR_CODE, 200, 200, CHARACTER_SET.DEFAULT, ERROR_CORRECTION.DEFAULT);<br>
 *  &nbsp;&nbsp;  PImage img = result.asImage(this);<br>
 *  &nbsp;&nbsp;  image(img, 0, 0);<br>
 *  }<br>
 *  </p>
 * 
 * @author thomas diewald (c) 2011
 *
 */
public final class Barcode {
  protected static final String NAME_    = "diewald_barcode";
  protected static final String VERSION_ = "v1.00";
  
  
  static{
    System.out.println("\nprocessing library: "+NAME_ +" - "+ VERSION_+"\n");
  }
  
  /**
   * private Constructor
   */
  private Barcode(){
  }
  
  
  /**
   * generate a barcode from a given content.
   * 
   * @param content                 the content to encode.
   * @param type                    the barcode-format the content should be encoded to.
   * @param width                   width of the barcode (if '0', with is determined  by the library).
   * @param height                  height of the barcode (if '0', with is determined  by the library).
   * @param character_set           Specifies what character encoding to use when encoding.
   * @param error_correction_level  Specifies what degree of error correction to use, for example in QR Codes.
   * 
   * @return the generated barcode result.
   */
  public final static EncodingResult encode(
      String content, 
      ENCODE type, 
      int width, 
      int height, 
      CHARACTER_SET character_set,
      ERROR_CORRECTION error_correction_level)
  {
    if( type == null )                   return null;
    if( character_set == null )          character_set = CHARACTER_SET.DEFAULT;
    if( error_correction_level == null ) error_correction_level = ERROR_CORRECTION.DEFAULT;

    Writer writer = type.getWriter();
    BarcodeFormat barcode_format = type.getFormat();
    
    Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
    hints.put(EncodeHintType.ERROR_CORRECTION, error_correction_level.getECL());
    hints.put(EncodeHintType.CHARACTER_SET, character_set.getName());
    
    try {      
      BitMatrix bit_matrix = writer.encode(content, barcode_format, width, height, hints);
      return new EncodingResult(bit_matrix);
    }
    catch(Exception e){
//      System.err.println("ERROR : while encoding \""+content + "\" with \""+ type+"\"");
//      System.err.println(e);
//    e.printStackTrace();
      return new EncodingResult(e);
    }
    
    
  }
  
  
  /**
   * convert the given processing PImage to an encodable BinaryBitmap.
   * 
   * checkout: http://code.google.com/p/zxing/wiki/DeveloperNotes.
   * 
   * @param Processing's PImage
   * 
   * @return An Ready-to-Encode BinaryBitmap.
   */
  private static final BinaryBitmap getBinaryBitmap(PImage image){
    BufferedImage code_image = (BufferedImage) image.getImage();
    LuminanceSource source   = new BufferedImageLuminanceSource( code_image );
    BinaryBitmap bitmap      = new BinaryBitmap( new HybridBinarizer( source ) );
    return bitmap;
  }
  
  
  /**
   * 
   * decode() is used for decoding a barcode (defined by the parameter 'type')
   * in an image. <br>
   * Use this function if you already know, what kind of barcode, the image contains.
   * otherwise, use decodeMulti() for decoding.<br>
   * 
   * Optionally, you can provide hints (character_set, try_harder) to request different behavior. 
   * 
   * @param image             Processing's PImage.
   * @param character_set     Specifies what character encoding to use when decoding.
   * @param try_harder        Spend more time to try to find a barcode; optimize for accuracy, not speed.
   * @param type              type of the Barcode, the image contains.
   * 
   * @return barcode-result found in the given image
   */
  public final static DecodingResult decode(
      PImage image,
      CHARACTER_SET character_set,
      boolean try_harder,
      DECODE type)
  {
    Exception decoding_exception = null; 
    try{
      if (image == null )          return null;
      if( type  == null )          return null;
      if( character_set == null )  character_set = CHARACTER_SET.DEFAULT;
      
      Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
      hints.put( DecodeHintType.TRY_HARDER,    new Boolean(try_harder));
      hints.put( DecodeHintType.CHARACTER_SET, character_set.getName());

      Reader reader = type.getReader();
      BinaryBitmap bitmap = getBinaryBitmap(image);
      Result result = reader.decode(bitmap, hints);
 
      return new DecodingResult(result);
    } catch ( NotFoundException e  ) {
      decoding_exception = e;
    } catch ( ChecksumException e  ) {
      decoding_exception = e;
    } catch ( Exception e  ) {
//      System.err.println("ERROR : while decoding \""+image + "\" with \""+ type+"\"");
//      System.err.println(e);
//      e.printStackTrace();
      decoding_exception = e;
    }
    return new DecodingResult(decoding_exception);
  }
  

  /**
   * 
   * decodeMulti() by default attempts to decode all barcode-formats that the 
   * library supports.<br>
   * Optionally, you can provide hints (character_set, try_harder, possible_types)
   * to request different behavior, for example only decoding QR codes. 
   * 
   * @param image             Processing's PImage.
   * @param character_set     Specifies what character encoding to use when decoding.
   * @param try_harder        Spend more time to try to find a barcode; optimize for accuracy, not speed.
   * @param possible_types    Image is known to be of one of a few possible formats.
   * 
   * @return                  barcode-result found in the given image.
   */
  public final static DecodingResult decodeMulti(
      PImage image,
      CHARACTER_SET character_set,
      boolean try_harder,
      DECODE ... possible_types)
  {
    Exception decoding_exception = null; 
    try{
      if (image == null )         return null;
      if( character_set == null ) character_set = CHARACTER_SET.DEFAULT;
      
      Vector<BarcodeFormat> vector = new Vector<BarcodeFormat>();
      for(DECODE type : possible_types)
        vector.add(type.getFormat());
      
      Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
      hints.put( DecodeHintType.TRY_HARDER,    new Boolean(try_harder));
      hints.put( DecodeHintType.CHARACTER_SET, character_set.getName());
      hints.put( DecodeHintType.POSSIBLE_FORMATS,  vector);
     
      Reader reader = new MultiFormatReader();
      BinaryBitmap bitmap = getBinaryBitmap(image);
      Result result = reader.decode(bitmap, hints);
      return new DecodingResult(result);
    } catch ( NotFoundException e  ) {
      decoding_exception = e;
    } catch ( ChecksumException e  ) {
      decoding_exception = e;
    } catch ( Exception e  ) {
//      System.err.println("ERROR : while decoding \""+image + "\" with \"MultiFormatReader()");
//      System.err.println(e);
//      e.printStackTrace();
      decoding_exception = e;
    }
    return new DecodingResult(decoding_exception);
  }
  

  /** 
   * decodeMultiQRcode() attempts to decode all QRcodes in a given image.
   * <br>
   * Optionally, you can provide hints (character_set, try_harder) to request different behavior. 
   * 
   * @param image             Processing's PImage.
   * @param character_set     Specifies what character encoding to use when decoding.
   * @param try_harder        Spend more time to try to find a barcode; optimize for accuracy, not speed.
   * 
   * @return and array of all QRcode-results found in the given image
   */
  public final static DecodingResult[] decodeMultiQRcode(
      PImage image,
      CHARACTER_SET character_set,
      boolean try_harder )
  {
    Exception decoding_exception = null; 
    try{
      if (image == null )          return null;
      if( character_set == null )          character_set = CHARACTER_SET.DEFAULT;
      
      Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
      hints.put( DecodeHintType.TRY_HARDER,    new Boolean(try_harder));
      hints.put( DecodeHintType.CHARACTER_SET, character_set.getName());

      MultipleBarcodeReader reader = new QRCodeMultiReader();
      BinaryBitmap bitmap = getBinaryBitmap(image);
      Result result[] = reader.decodeMultiple(bitmap, hints);
      
      DecodingResult[] decoding_results = new DecodingResult[result.length];
      for(int i = 0; i < decoding_results.length; i++){
        decoding_results[i] = new DecodingResult(result[i]); 
      }
      
      return decoding_results;
    } catch ( NotFoundException e  ) {
      decoding_exception = e;
    } catch ( Exception e  ) {
//      System.err.println("ERROR : while decoding \""+image + "\" with \"MultiFormatReader()");
//      System.err.println(e);
//      e.printStackTrace();
      decoding_exception = e;
    }
    return new DecodingResult[]{new DecodingResult(decoding_exception) };
  }
  

}
