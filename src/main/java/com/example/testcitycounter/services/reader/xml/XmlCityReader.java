package com.example.testcitycounter.services.reader.xml;

import com.example.testcitycounter.dto.CityDictionaryItem;
import com.example.testcitycounter.enums.XmlCityAttribute;
import com.example.testcitycounter.services.analyzer.DictionaryAnalyzer;
import com.example.testcitycounter.services.reader.CityReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by fedor.dydykin on 13.11.2023.
 */
public class XmlCityReader extends DefaultHandler implements CityReader {

  private DictionaryAnalyzer analyzer;

  @Override
  public void read(DictionaryAnalyzer analyzer, File file) {
    this.analyzer = analyzer;
    SAXParserFactory factory = SAXParserFactory.newInstance();
    try {
      SAXParser saxParser = factory.newSAXParser();

      InputSource source = new InputSource(new FileReader(file));

      // utf-8
      source.setEncoding(StandardCharsets.UTF_8.displayName());

      saxParser.parse(source, this);
    } catch (Exception e) {
      throw new RuntimeException("Can't parse file " + file.getAbsolutePath(), e);
    }
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) {
    int attributeLength = attributes.getLength();
    if ("item".equals(qName)) {
      final CityDictionaryItem item = new CityDictionaryItem();
      for (int i = 0; i < attributeLength; i++) {
        String attrName = attributes.getQName(i);

        String attrVal = attributes.getValue(i);
        switch (XmlCityAttribute.valueOf(attrName)) {
          case city -> item.setCity(attrVal);
          case street -> item.setStreet(attrVal);
          case floor -> item.setFloor(Byte.valueOf(attrVal));
          case house -> item.setHouse(Short.valueOf(attrVal));
        }
      }
      analyzer.addItem(item);
    }
  }
}
