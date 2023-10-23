package com.example.testcitycounter.services.reader;

import com.example.testcitycounter.services.analyzer.DictionaryAnalyzer;
import java.io.File;

public interface CityReader {

  void read(DictionaryAnalyzer analyzer, File file);
}
