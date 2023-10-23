package com.example.testcitycounter.services.printer;

public interface DataPrinter {

  void println(String toPrint);
  void println();
  void printError(String unexpectedError);
  void printError(String unexpectedError, Exception e);
}
