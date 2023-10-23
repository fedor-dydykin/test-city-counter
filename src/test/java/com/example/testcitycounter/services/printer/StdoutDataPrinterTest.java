package com.example.testcitycounter.services.printer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StdoutDataPrinterTest {

  private final StdoutDataPrinter stdoutDataPrinter = new StdoutDataPrinter();

  @Test
  void println() {
    assertDoesNotThrow(() -> stdoutDataPrinter.println());
  }

  @Test
  void testPrintln() {
    assertDoesNotThrow(() -> stdoutDataPrinter.println("a sting"));
  }

  @Test
  void printError() {
    assertDoesNotThrow(() -> stdoutDataPrinter.printError("a message"));
    assertDoesNotThrow(() -> stdoutDataPrinter.printError("a message", new Exception(new RuntimeException("error message"))));
    assertDoesNotThrow(() -> stdoutDataPrinter.printError(null, new Exception()));
    assertDoesNotThrow(() -> stdoutDataPrinter.printError("a message", null));
  }
}