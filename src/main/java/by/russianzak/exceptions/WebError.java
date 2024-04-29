package by.russianzak.exceptions;

import java.util.Date;

public class WebError {

  private final int status;
  private final String message;
  private final Date timestamp;

  public WebError(int status, String message) {
    this.status = status;
    this.message = message;
    this.timestamp = new Date();
  }

  public int getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public Date getTimestamp() {
    return timestamp;
  }
}