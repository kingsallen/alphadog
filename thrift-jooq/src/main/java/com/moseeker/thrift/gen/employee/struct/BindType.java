/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.moseeker.thrift.gen.employee.struct;


public enum BindType implements org.apache.thrift.TEnum {
  EMAIL(0),
  CUSTOMFIELD(1),
  QUESTIONS(2),
  WORKWX(3);


  private final int value;

  private BindType(int value) {
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
  public static BindType findByValue(int value) {
    for(BindType bindType : values()){
      if(bindType.value == value){
        return bindType;
      }
    }
    return null ;
  }
}
