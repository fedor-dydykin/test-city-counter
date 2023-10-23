package com.example.testcitycounter.services;

import com.example.testcitycounter.dto.CityDictionaryItem;

/**
 * Created by fedor.dydykin on 23.10.2023.
 */
public class DataFormatter {

  public String prepareDuplicateInfo(CityDictionaryItem item, int times){
    return "city: " + item.getCity()
        + " street: " + item.getStreet()
        + " house: " + item.getHouse()
        + " floors: " + item.getFloor()
        + " duplicated times: " + times;
  }

}
