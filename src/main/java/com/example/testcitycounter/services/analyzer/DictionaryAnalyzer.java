package com.example.testcitycounter.services.analyzer;

import com.example.testcitycounter.dto.CityDictionaryItem;

public interface DictionaryAnalyzer {

  void addItem(CityDictionaryItem cityDictionaryItem);
  void printResults();

}
