package com.example.testcitycounter.services.printer;

/**
 * Created by fedor.dydykin on 23.09.2023.
 */
public class DataPrinterFactory {

  private DataPrinterFactory() {
  }

  private static class DataPrinterFactoryHelper {

    private static final DataPrinter instance = new StdoutDataPrinter();
  }

  public static DataPrinter getInstance() {
    return DataPrinterFactoryHelper.instance;
  }

}
