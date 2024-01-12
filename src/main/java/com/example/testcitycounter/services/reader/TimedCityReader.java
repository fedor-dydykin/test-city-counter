package com.example.testcitycounter.services.reader;

import com.example.testcitycounter.services.analyzer.DictionaryAnalyzer;
import com.example.testcitycounter.services.printer.DataPrinter;
import java.io.File;

/**
 * Created by fedor.dydykin on 13.11.2023.
 */
public class TimedCityReader implements CityReader{

  private final CityReader cityReader;
  private final DataPrinter dataPrinter;

  public TimedCityReader(CityReader cityReader, DataPrinter dataPrinter) {
    this.cityReader = cityReader;
    this.dataPrinter = dataPrinter;
  }

  @Override
  public void read(DictionaryAnalyzer analyzer) {
    long start = System.currentTimeMillis();
    cityReader.read(analyzer);
    dataPrinter.println("Read time: " + (System.currentTimeMillis() - start) + " millis");
  }
}
