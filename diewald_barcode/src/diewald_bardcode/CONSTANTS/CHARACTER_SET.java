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

import com.google.zxing.common.CharacterSetECI;

/**
 * this enum contains all the available character sets of the library.<br>
 *
 *
 * @author thomas diewald (c) 2011
 *
 */
public enum CHARACTER_SET {
  Cp437        ( "Cp437"       ), 
  ISO8859_1    ( "ISO8859_1"   ),
  ISO8859_2    ( "ISO8859_2"   ),
  ISO8859_3    ( "ISO8859_3"   ),
  ISO8859_4    ( "ISO8859_4"   ),
  ISO8859_5    ( "ISO8859_5"   ),
  ISO8859_6    ( "ISO8859_6"   ),
  ISO8859_7    ( "ISO8859_7"   ),
  ISO8859_8    ( "ISO8859_8"   ),
  ISO8859_9    ( "ISO8859_9"   ),
  ISO8859_10   ( "ISO8859_10"  ),
  ISO8859_11   ( "ISO8859_11"  ),
  ISO8859_13   ( "ISO8859_13"  ),
  ISO8859_14   ( "ISO8859_14"  ),
  ISO8859_15   ( "ISO8859_15"  ),
  ISO8859_16   ( "ISO8859_16"  ),
  SJIS         ( "SJIS"        ),
  Shift_JIS    ( "Shift_JIS"   ),
  
  DEFAULT      (ISO8859_1.getName()),
  ;
  
  private CharacterSetECI set_;
  private int index_;
  private String name_;
  
  /**
   * private constructor
   * @param by_name name of the set
   */
  private CHARACTER_SET(String by_name) {
    set_ = CharacterSetECI.getCharacterSetECIByName( by_name);
    index_ = set_.getValue();
    name_  = set_.getEncodingName();
  }
  /**
   * get the index of the character set
   * @return index
   */
  public final int getIndex(){
    return index_;
  }
  /**
   * get the name of the character set
   * @return name
   */
  public final String getName(){
    return name_;
  }
  /**
   * get the set of the zxing library
   * @return
   */
  protected final CharacterSetECI getSet(){
    return set_;
  }
}
