package com.example.testcitycounter.services.analyzer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.testcitycounter.dto.CityDictionaryItem;
import com.example.testcitycounter.services.DataFormatter;
import com.example.testcitycounter.services.printer.MockStdoutDataPrinter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.jupiter.api.Test;

class DuplicatesAnalyzerTest {

  private final DataFormatter dataFormatter = new DataFormatter();

  @Test
  void printDuplicates() {
    final MockStdoutDataPrinter dataPrinter = new MockStdoutDataPrinter();
    DuplicatesAnalyzer duplicatesAnalyzer = new DuplicatesAnalyzer(dataPrinter);
    duplicatesAnalyzer.addItem(
        new CityDictionaryItem("Братск", "Ближняя улица", (short) 56, (byte) 1)
    );
    final CityDictionaryItem duplicate = new CityDictionaryItem(
        "Барнаул",
        "Дальняя улица",
        (short) 56,
        (byte) 2
    );
    duplicatesAnalyzer.addItem(duplicate);
    duplicatesAnalyzer.addItem(
        new CityDictionaryItem("Братск", "Дальняя улица", (short) 56, (byte) 1)
    );
    duplicatesAnalyzer.addItem(
        new CityDictionaryItem("Барнаул", "Дальняя улица", (short) 56, (byte) 2)
    );
    duplicatesAnalyzer.printResults();
    final Map<CityDictionaryItem, Short> duplicates = duplicatesAnalyzer.getDuplicates();

    //cheking printouts
    final List<String> onlyDuplicatesPrinted = dataPrinter.getPrintedLines()
        .stream()
        .filter(line -> line.contains(" times")).toList();
    assertEquals(1, onlyDuplicatesPrinted.size());
    assertEquals(
        dataFormatter.prepareDuplicateInfo(duplicate, 2),
        onlyDuplicatesPrinted.get(0)
    );

    //checking results
    assertEquals(1, duplicates.size());
    final Entry<CityDictionaryItem, Short> entry = duplicates.entrySet().iterator().next();
    assertEquals("Барнаул", entry.getKey().getCity());
    assertEquals("Дальняя улица", entry.getKey().getStreet());
    assertEquals((short) 56, entry.getKey().getHouse());
    assertEquals((byte) 2, entry.getKey().getFloor());
    assertEquals((short) 2, entry.getValue());
  }
}