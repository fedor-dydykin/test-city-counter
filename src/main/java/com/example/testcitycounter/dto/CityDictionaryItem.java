package com.example.testcitycounter.dto;

import java.util.Objects;

/**
 * Created by fedor.dydykin on 23.09.2023.
 */
public class CityDictionaryItem {

  private String city;
  private String street;
  private Short house;
  private Byte floor;

  public CityDictionaryItem() {
  }

  public CityDictionaryItem(String city, String street, Short house, Byte floor) {
    this.city = city;
    this.street = street;
    this.house = house;
    this.floor = floor;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public Short getHouse() {
    return house;
  }

  public void setHouse(Short house) {
    this.house = house;
  }

  public Byte getFloor() {
    return floor;
  }

  public void setFloor(Byte floor) {
    this.floor = floor;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CityDictionaryItem cityDictionaryItem1 = (CityDictionaryItem) o;
    return Objects.equals(city, cityDictionaryItem1.city)
        && Objects.equals(street, cityDictionaryItem1.street)
        && Objects.equals(house, cityDictionaryItem1.house)
        && Objects.equals(floor, cityDictionaryItem1.floor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(city, street, house, floor);
  }

  @Override
  public String toString() {
    return "CityDictionaryItem{" +
        "city='" + city + '\'' +
        ", street='" + street + '\'' +
        ", house=" + house +
        ", floor=" + floor +
        '}';
  }
}