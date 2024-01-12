package com.example.testcitycounter.services.analyzer;

import com.example.testcitycounter.dto.CityDictionaryItem;
import com.example.testcitycounter.services.DataFormatter;
import com.example.testcitycounter.services.printer.DataPrinter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by fedor.dydykin on 23.09.2023.
 */
public class DuplicatesAnalyzer implements DictionaryAnalyzer {

  private final DataPrinter dataPrinter;
  private final Map<String, Short> dictionaryWithCounts = new HashMap<>();
  private final Set<CityDictionaryItem> duplicates = new HashSet<>();
  private final DataFormatter dataFormatter = new DataFormatter();

  public DuplicatesAnalyzer(DataPrinter dataPrinter) {
    this.dataPrinter = dataPrinter;
  }

  @Override
  public void addItem(CityDictionaryItem cityDictionaryItem) {
    short count = (short) (dictionaryWithCounts.getOrDefault(cityDictionaryItem.uniqueHash(), (short) 0) + 1);
    dictionaryWithCounts.put(cityDictionaryItem.uniqueHash(), count);
    if(count == 2) {
      duplicates.add(cityDictionaryItem);
    }
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
    return duplicates.stream()
        .collect(Collectors.toMap(o -> o, o -> dictionaryWithCounts.get(o.uniqueHash())));
  }
}
