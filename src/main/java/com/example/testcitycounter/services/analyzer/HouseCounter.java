package com.example.testcitycounter.services.analyzer;

import com.example.testcitycounter.dto.CityDictionaryItem;
import com.example.testcitycounter.services.printer.DataPrinter;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by fedor.dydykin on 21.11.2023.
 */
public class HouseCounter implements DictionaryAnalyzer {

  private final DataPrinter dataPrinter;
  private final Map<String, Map<Byte, Integer>> housesByCity = new TreeMap<>();

  private DuplicatesAnalyzer duplicatesAnalyzer;

  public HouseCounter(DataPrinter dataPrinter) {
    this.dataPrinter = dataPrinter;
  }

  @Override
  public void addItem(CityDictionaryItem item) {
    final Map<Byte, Integer> houses = housesByCity.computeIfAbsent(
        item.getCity(),
        cityName -> new TreeMap<>()
    );
    final Byte floor = item.getFloor();
    Integer count = houses.computeIfAbsent(floor, floorNumber -> 0);
    count++;
    houses.put(floor, count);
  }

  @Override
  public void printResults() {
    deleteDuplicates();
    dataPrinter.println("");
    dataPrinter.println("city dictionary has " + housesByCity.size() + " cities");
    housesByCity.forEach((cityName, houses) -> {
      dataPrinter.println("city '" + cityName + "' has:");
      houses.forEach(
          (floor, count) -> dataPrinter.println("     " + floor + "-floor houses:" + count));
    });
  }

  public void deleteDuplicates() {
    if (duplicatesAnalyzer == null) {
      return;
    }
    duplicatesAnalyzer.getDuplicates().forEach(
        (item, duplicateCount) -> housesByCity.get(item.getCity()).compute(
            item.getFloor(),
            (floor, count) -> count - (duplicateCount - 1)
        )
    );
  }

  public void setDuplicatesAnalyzer(
      DuplicatesAnalyzer duplicatesAnalyzer) {
    this.duplicatesAnalyzer = duplicatesAnalyzer;
  }

  public Map<String, Map<Byte, Integer>> getHousesByCity() {
    return housesByCity;
  }
}
