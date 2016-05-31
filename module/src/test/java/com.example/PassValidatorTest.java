package com.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 31/5/16.
 */
public class PassValidatorTest {

  private PassValidator passValidator;

  @Before public void prepareSUT(){

  }

  @Test public void shouldValidate() throws Exception {
    //Arrange
    passValidator = new PassValidator("Test1");

    //Act
    boolean result = passValidator.validate();

    //Assert
    assertTrue(result);
  }

  @Test public void shouldNotValidate() throws Exception {
    //Arrange
    passValidator = new PassValidator("T e s t 1");

    //Act
    boolean result = passValidator.validate();

    //Assert
    assertFalse(result);
  }

  @After public void cleanSUT(){
    passValidator = null;
  }
}