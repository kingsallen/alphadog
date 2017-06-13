/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.common.struct;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum ValueOp implements TEnum {
  EQ(0),
  NEQ(1),
  GT(2),
  GE(3),
  LT(4),
  LE(5),
  IN(6),
  NIN(7),
  BT(8),
  NBT(9),
  LIKE(10),
  NLIKE(11);

  private final int value;

  private ValueOp(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static ValueOp findByValue(int value) { 
    switch (value) {
      case 0:
        return EQ;
      case 1:
        return NEQ;
      case 2:
        return GT;
      case 3:
        return GE;
      case 4:
        return LT;
      case 5:
        return LE;
      case 6:
        return IN;
      case 7:
        return NIN;
      case 8:
        return BT;
      case 9:
        return NBT;
      case 10:
        return LIKE;
      case 11:
        return NLIKE;
      default:
        return null;
    }
  }
}