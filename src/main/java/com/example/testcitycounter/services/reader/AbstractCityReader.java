package com.example.testcitycounter.services.reader;

import java.io.File;

/**
 * Created by fedor.dydykin on 12.01.2024.
 */
public abstract class AbstractCityReader implements CityReader{

  protected final File file;

  public AbstractCityReader(File file){
    this.file = file;
  }
}
