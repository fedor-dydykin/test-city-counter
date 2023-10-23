package com.example.testcitycounter.services.printer;

/**
 * Created by fedor.dydykin on 23.09.2023.
 */
public class StdoutDataPrinter implements DataPrinter{

  @Override
  public void println(String toPrint) {
    System.out.println(toPrint);
  }

  @Override
  public void println() {
    System.out.println();
  }

  @Override
  public void printError(String toPrint) {
    System.out.println(toPrint);
  }

  @Override
  public void printError(String errorMessage, Exception e) {
    if(e == null) {
      printError(errorMessage);
      return;
    }
    System.out.println(errorMessage + ": " + e.getMessage());
    e.printStackTrace(System.out);
  }
}
