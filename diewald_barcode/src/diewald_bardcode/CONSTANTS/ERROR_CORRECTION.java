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

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * this enum contains all the available error-correction levels of the library.<br>
 * they typically are used for encoding QRcodes.<br><br>
 * 
 * ERROR_CORRECTION.DEFAULT is the same as ERROR_CORRECTION.DEFAULT.LEVEL1.
 * <br><br>
 * 
 * ERROR_CORRECTION.DEFAULT.LEVEL1 is the lowest level.
 *
 *
 * @author thomas diewald (c) 2011
 *
 */
public enum ERROR_CORRECTION {
  LEVEL1  (ErrorCorrectionLevel.L),
  LEVEL2  (ErrorCorrectionLevel.M),
  LEVEL3  (ErrorCorrectionLevel.Q),
  LEVEL4  (ErrorCorrectionLevel.H),
  DEFAULT (LEVEL1.getECL()),
  ;
  
  private ErrorCorrectionLevel value;
  
  private ERROR_CORRECTION(ErrorCorrectionLevel value) {
    this.value = value;
  }
  public final ErrorCorrectionLevel getECL(){
    return value;
  }
}
