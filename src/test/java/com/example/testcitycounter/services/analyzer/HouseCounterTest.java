package com.example.testcitycounter.services.analyzer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.testcitycounter.dto.CityDictionaryItem;
import com.example.testcitycounter.services.printer.MockStdoutDataPrinter;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class HouseCounterTest {

  @Test
  void printResults(){
    final MockStdoutDataPrinter dataPrinter = new MockStdoutDataPrinter();
    HouseCounter houseCounter = new HouseCounter(dataPrinter);
    houseCounter.addItem(
        new CityDictionaryItem("Братск", "Ближняя улица", (short) 56, (byte) 3)
    );
    houseCounter.addItem(
        new CityDictionaryItem("Братск", "Дальняя улица", (short) 156, (byte) 3)
    );
    houseCounter.addItem(
        new CityDictionaryItem("Братск", "Новая улица", (short) 1, (byte) 1)
    );
    houseCounter.addItem(
        new CityDictionaryItem("Барнаул", "Дальняя улица", (short) 56, (byte) 2)
    );
    houseCounter.printResults();

    final Map<String, Map<Byte, Integer>> housesByCity = houseCounter.getHousesByCity();

    final List<String> printedLines = dataPrinter.getPrintedLines();

    //checking printouts
    assertEquals(7, printedLines.size());
    assertEquals(printedLines.get(2), "city 'Барнаул' has:");
    assertTrue(printedLines.get(3).contains("2-floor houses:1"));
    assertEquals(printedLines.get(4), "city 'Братск' has:");
    assertTrue(printedLines.get(5).contains("1-floor houses:1"));
    assertTrue(printedLines.get(6).contains("3-floor houses:2"));

    //checking results
    assertEquals(2, housesByCity.size());
    assertTrue(housesByCity.containsKey("Братск"));
    final Map<Byte, Integer> bratsk = housesByCity.get("Братск");
    assertTrue(bratsk.containsKey((byte)3));
    assertEquals(2, bratsk.get((byte)3));
    assertEquals(1, bratsk.get((byte)1));
    assertTrue(bratsk.containsKey((byte)1));
    assertTrue(housesByCity.containsKey("Барнаул"));
    final Map<Byte, Integer> barnaul = housesByCity.get("Барнаул");
    assertTrue(barnaul.containsKey((byte)2));
    assertEquals(1, barnaul.get((byte)2));
  }

  @Test
  void printResultsWithDeletedDuplicates(){
    final MockStdoutDataPrinter dataPrinter = new MockStdoutDataPrinter();
    HouseCounter houseCounter = new HouseCounter(dataPrinter);
    final DuplicatesAnalyzer duplicatesAnalyzer = new DuplicatesAnalyzer(dataPrinter);
    houseCounter.setDuplicatesAnalyzer(duplicatesAnalyzer);

    final CityDictionaryItem item1 = new CityDictionaryItem(
        "Братск", "Ближняя улица", (short) 56, (byte) 3
    );
    final CityDictionaryItem item2 = new CityDictionaryItem(
        "Братск", "Ближняя улица", (short) 56, (byte) 3
    );
    final CityDictionaryItem item3 = new CityDictionaryItem(
        "Братск", "Новая улица", (short) 1, (byte) 1
    );
    final CityDictionaryItem item4 = new CityDictionaryItem(
        "Барнаул", "Дальняя улица", (short) 56, (byte) 2
    );

    houseCounter.addItem(item1);
    houseCounter.addItem(item2);
    houseCounter.addItem(item3);
    houseCounter.addItem(item4);

    duplicatesAnalyzer.addItem(item1);
    duplicatesAnalyzer.addItem(item2);
    duplicatesAnalyzer.addItem(item3);
    duplicatesAnalyzer.addItem(item4);

    houseCounter.printResults();

    final Map<String, Map<Byte, Integer>> housesByCity = houseCounter.getHousesByCity();

    final List<String> printedLines = dataPrinter.getPrintedLines();

    //checking printouts
    assertEquals(7, printedLines.size());
    assertEquals(printedLines.get(2), "city 'Барнаул' has:");
    assertTrue(printedLines.get(3).contains("2-floor houses:1"));
    assertEquals(printedLines.get(4), "city 'Братск' has:");
    assertTrue(printedLines.get(5).contains("1-floor houses:1"));
    assertTrue(printedLines.get(6).contains("3-floor houses:1"));

    //checking results
    assertEquals(2, housesByCity.size());
    assertTrue(housesByCity.containsKey("Братск"));
    final Map<Byte, Integer> bratsk = housesByCity.get("Братск");
    assertTrue(bratsk.containsKey((byte)3));
    assertEquals(1, bratsk.get((byte)3));
    assertEquals(1, bratsk.get((byte)1));
    assertTrue(bratsk.containsKey((byte)1));
    assertTrue(housesByCity.containsKey("Барнаул"));
    final Map<Byte, Integer> barnaul = housesByCity.get("Барнаул");
    assertTrue(barnaul.containsKey((byte)2));
    assertEquals(1, barnaul.get((byte)2));
  }

}