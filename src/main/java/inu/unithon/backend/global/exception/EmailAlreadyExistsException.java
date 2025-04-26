package inu.unithon.backend.global.exception;

public class EmailAlreadyExistsException extends RuntimeException {

  public EmailAlreadyExistsException(String message) {
    super(message);
  }
}

