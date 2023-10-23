package com.example.testcitycounter.services.analyzer;

import com.example.testcitycounter.dto.CityDictionaryItem;
import com.example.testcitycounter.services.DataFormatter;
import com.example.testcitycounter.services.printer.DataPrinter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Created by fedor.dydykin on 23.09.2023.
 */
public class DuplicatesAnalyzer implements DictionaryAnalyzer {

  private final DataPrinter dataPrinter;
  private final HashMap<CityDictionaryItem, Short> dictionaryWithCounts = new HashMap<>();
  private final DataFormatter dataFormatter = new DataFormatter();

  public DuplicatesAnalyzer(DataPrinter dataPrinter) {
    this.dataPrinter = dataPrinter;
  }

  @Override
  public void addItem(CityDictionaryItem cityDictionaryItem) {
    short count = (short) (dictionaryWithCounts.getOrDefault(cityDictionaryItem, (short) 0) + 1);
    dictionaryWithCounts.put(cityDictionaryItem, count);
  }

  @Override
  public void printResults() {
    final Map<CityDictionaryItem, Short> duplicates = getDuplicates();
    dataPrinter.println("Duplicates found: " + duplicates.size());
    duplicates.forEach(
        (cityDictionaryItem, count) -> dataPrinter.println(
            dataFormatter.prepareDuplicateInfo(cityDictionaryItem, count)
        )
    );
  }

  public Map<CityDictionaryItem, Short> getDuplicates() {
    return dictionaryWithCounts.entrySet()
        .stream()
        .filter(entry -> entry.getValue() > 1)
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
  }
}
