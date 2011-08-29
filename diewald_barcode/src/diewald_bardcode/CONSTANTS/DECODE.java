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
import com.google.zxing.Reader;

import com.google.zxing.datamatrix.DataMatrixReader;
import com.google.zxing.oned.Code39Reader;
import com.google.zxing.oned.Code93Reader;
import com.google.zxing.oned.EAN13Reader;
import com.google.zxing.oned.EAN8Reader;
import com.google.zxing.oned.ITFReader;
import com.google.zxing.oned.UPCAReader;
import com.google.zxing.oned.UPCEReader;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.oned.CodaBarReader;
import com.google.zxing.oned.Code128Reader;
import com.google.zxing.pdf417.PDF417Reader;
import com.google.zxing.aztec.*;
//import com.google.zxing.aztec.AztecReader;
/**
 * this enum contains all the available barcodes of the library, which can be decoded.<br>
 *
 *
 * @author thomas diewald (c) 2011
 *
 */
public enum DECODE {

  AZTEC        (new AztecReader(),       BarcodeFormat.AZTEC,       2),
  CODABAR      (new CodaBarReader(),     BarcodeFormat.CODABAR,     1),
  CODE_128     (new Code128Reader(),     BarcodeFormat.CODE_128,    1),
  CODE_39      (new Code39Reader(),      BarcodeFormat.CODE_39,     1),
  CODE_93      (new Code93Reader(),      BarcodeFormat.CODE_93,     1),
  DATA_MATRIX  (new DataMatrixReader(),  BarcodeFormat.DATA_MATRIX, 2),
  EAN_13       (new EAN13Reader(),       BarcodeFormat.EAN_13,      1),
  EAN_8        (new EAN8Reader(),        BarcodeFormat.EAN_8,       1),
  ITF          (new ITFReader(),         BarcodeFormat.ITF,         1),
  PDF_417      (new PDF417Reader(),      BarcodeFormat.PDF_417,     2),
  QR_CODE      (new QRCodeReader(),      BarcodeFormat.QR_CODE,     2),
  UPC_A        (new UPCAReader(),        BarcodeFormat.UPC_A,       1),
  UPC_E        (new UPCEReader(),        BarcodeFormat.UPC_E,       1),
  ;

  private Reader reader_;
  private BarcodeFormat barcode_format_;
  private int dimensions_ = 0;
  
  
  /**
   * 
   * @param reader
   * @param barcode_format
   * @param dimensions
   */
  private DECODE(Reader reader, BarcodeFormat barcode_format, int dimensions) {
    reader_ = reader;
    barcode_format_ = barcode_format;
    dimensions_ = dimensions;
  }
 
  public final Reader getReader(){
    return reader_;
  }
  public final BarcodeFormat getFormat(){
    return barcode_format_;
  }
  public final int getDimensions(){
    return dimensions_;
  }
}


/*
CodaBarReader, 
Code128Reader, 
Code39Reader, 
Code93Reader, 
DataMatrixReader, 
EAN13Reader, 
EAN8Reader, 
ITFReader, 
MultiFormatReader,
OneDReader, 
PDF417Reader, 
QRCodeMultiReader, 
QRCodeReader,
UPCAReader,  
UPCEReader
*/
