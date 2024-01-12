package com.example.testcitycounter.services.reader.csv;

import com.example.testcitycounter.dto.CityDictionaryItem;
import com.example.testcitycounter.enums.CsvCityColumn;
import com.example.testcitycounter.services.analyzer.DictionaryAnalyzer;
import com.example.testcitycounter.services.reader.AbstractCityReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by fedor.dydykin on 23.09.2023.
 */
public class CsvCityReader extends AbstractCityReader {

  private int indexColumnCity;
  private int indexColumnStreet;
  private int indexColumnHouse;
  private int indexColumnFloor;

  private int count = 0;

  public CsvCityReader(InputStream inputStream) {
    super(inputStream);
  }

  @Override
  public void read(DictionaryAnalyzer analyzer) {
    BufferedReader reader;

    try {
      reader = new BufferedReader(new InputStreamReader(this.inputStream));
      String header = reader.readLine();
      header = header.replace("\"", "");
      String[] split = header.split(";");
      for (int i = 0, splitLength = split.length; i < splitLength; i++) {
        String columnName = split[i];
        CsvCityColumn csvColumn = CsvCityColumn.valueOf(columnName);
        switch (csvColumn) {
          case city -> indexColumnCity = i;
          case street -> indexColumnStreet = i;
          case house -> indexColumnHouse = i;
          case floor -> indexColumnFloor = i;
        }
      }

      String line = reader.readLine();
      while (line != null) {
        final String[] values = line.split(";");
        if(values.length != 4) {
          throw new RuntimeException("Wrong columns count in csv line: " + Arrays.asList(values));
        }
        final CityDictionaryItem item = getCityDictionaryItem(values);
        analyzer.addItem(item);
        if (count < 10) {
          System.out.println(item);
          count++;
        }
        line = reader.readLine();
      }

      reader.close();
    } catch (IOException e) {
      throw new RuntimeException("Can't read csv file", e);
    }
  }

  private CityDictionaryItem getCityDictionaryItem(String[] values) {
    try {
      String city = values[indexColumnCity].replace("\"", "");
      String street = values[indexColumnStreet].replace("\"", "");
      Short house = Short.valueOf(values[indexColumnHouse]);
      Byte floor = Byte.valueOf(values[indexColumnFloor]);
      return new CityDictionaryItem(city, street, house, floor);
    } catch (Exception e) {
      throw new RuntimeException("Cant read csv line: " + Arrays.asList(values));
    }
  }
}
