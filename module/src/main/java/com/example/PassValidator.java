package com.example;

public class PassValidator implements Validator<String> {

  private String textToValidate ;

  public PassValidator(String textToValidate) {
    this.textToValidate = textToValidate;
  }

  @Override public boolean validate() {
    return isValid(textToValidate);
  }

  private boolean isValid(String textToValidate) {
    String textWithOutSpaces = textToValidate.replace(" ", "");
    return textToValidate.equals(textWithOutSpaces);
  }
}
