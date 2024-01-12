package com.example.testcitycounter.services.reader;

import com.example.testcitycounter.dto.SourceFile;
import com.example.testcitycounter.enums.FileExtension;
import com.example.testcitycounter.services.printer.DataPrinter;
import com.example.testcitycounter.services.reader.csv.CsvCityReader;
import com.example.testcitycounter.services.reader.xml.XmlCityReader;
import java.io.File;

/**
 * Created by fedor.dydykin on 23.09.2023.
 */
public class CityReaderFactory {

  public static CityReader createCityReader(SourceFile sourceFile, DataPrinter dataPrinter) {
    if (sourceFile.fileName().endsWith("." + FileExtension.CSV.name().toLowerCase())) {
      return new TimedCityReader(new CsvCityReader(sourceFile.inputStream()), dataPrinter);
    } else if (sourceFile.fileName().endsWith("." + FileExtension.XML.name().toLowerCase())) {
      return new TimedCityReader(new XmlCityReader(sourceFile.inputStream()), dataPrinter);
    } else {
      throw new UnsupportedOperationException(
          "File " + sourceFile.fileName() + " has unsupported extension.");
    }
  }

}
