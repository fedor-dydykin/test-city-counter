package com.example.testcitycounter.services.reader;

import java.io.InputStream;

/**
 * Created by fedor.dydykin on 12.01.2024.
 */
public abstract class AbstractCityReader implements CityReader{

  protected final InputStream inputStream;

  public AbstractCityReader(InputStream inputStream){
    this.inputStream = inputStream;
  }
}
