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


package diewald_bardcode.CONSTANTS;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.oned.Code39Writer;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.oned.EAN8Writer;
import com.google.zxing.oned.ITFWriter;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * this enum contains all the available barcodes of the library, which can be encoded.<br>
 *
 *
 * @author thomas diewald (c) 2011
 *
 */
public enum ENCODE {
  CODE_128    (new Code128Writer(), BarcodeFormat.CODE_128,  1),
  CODE_39     (new Code39Writer(),  BarcodeFormat.CODE_39,   1),
  EAN_13      (new EAN13Writer(),   BarcodeFormat.EAN_13,    1),
  EAN_8       (new EAN8Writer(),    BarcodeFormat.EAN_8,     1),
  ITF         (new ITFWriter(),     BarcodeFormat.ITF,       1),
  QR_CODE     (new QRCodeWriter(),  BarcodeFormat.QR_CODE,   2),
  ;
  
  private Writer writer_;
  private BarcodeFormat barcode_format_;
  private int dimensions_ = 0;
  
  
  
  private ENCODE(Writer writer, BarcodeFormat barcode_format, int dimensions) {
    writer_ = writer;
    barcode_format_ = barcode_format;
    dimensions_ = dimensions;
  }
  
  public final Writer getWriter(){
    return writer_;
  }
  public final BarcodeFormat getFormat(){
    return barcode_format_;
  }
  public final int getDimensions(){
    return dimensions_;
  }
}
//Code128Writer, Code39Writer, EAN13Writer, EAN8Writer, ITFWriter, MultiFormatWriter, QRCodeWriter