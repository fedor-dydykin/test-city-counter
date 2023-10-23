package com.example.testcitycounter.services.reader;

import com.example.testcitycounter.enums.FileExtension;
import com.example.testcitycounter.services.printer.DataPrinter;
import com.example.testcitycounter.services.reader.csv.CsvCityReader;
import com.example.testcitycounter.services.reader.xml.XmlCityReader;
import java.io.File;

/**
 * Created by fedor.dydykin on 23.09.2023.
 */
public class CityReaderFactory {

  public static CityReader createCityReader(File file, DataPrinter dataPrinter) {
    if (file.getAbsolutePath().endsWith("." + FileExtension.CSV.name().toLowerCase())) {
      return new TimedCityReader(new CsvCityReader(), dataPrinter);
    } else if (file.getAbsolutePath().endsWith("." + FileExtension.XML.name().toLowerCase())) {
      return new TimedCityReader(new XmlCityReader(), dataPrinter);
    } else {
      throw new UnsupportedOperationException(
          "File " + file.getAbsolutePath() + " has unsupported extension.");
    }
  }

}
