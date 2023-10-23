package com.example.testcitycounter.services.printer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fedor.dydykin on 22.10.2023.
 */
public class MockStdoutDataPrinter extends StdoutDataPrinter{

  private final List<String> printedLines = new ArrayList<>();

  @Override
  public void println(String toPrint) {
    printedLines.add(toPrint);
    super.println(toPrint);
  }

  public List<String> getPrintedLines() {
    return printedLines;
  }
}
