package com.example.testcitycounter.services.analyzer;

import com.example.testcitycounter.dto.CityDictionaryItem;
import java.util.List;

/**
 * Created by fedor.dydykin on 22.11.2023.
 */
public class NestedAnalazer implements DictionaryAnalyzer{

  private final List<DictionaryAnalyzer> analyzers;

  public NestedAnalazer(List<DictionaryAnalyzer> analyzers) {
    if(analyzers == null || analyzers.isEmpty()){
      throw new RuntimeException("Analyzers can't be empty");
    }
    this.analyzers = analyzers;
  }

  @Override
  public void addItem(CityDictionaryItem cityDictionaryItem) {
    this.analyzers.forEach(dictionaryAnalyzer -> dictionaryAnalyzer.addItem(cityDictionaryItem));
  }

  @Override
  public void printResults() {
    this.analyzers.forEach(DictionaryAnalyzer::printResults);
  }
}
