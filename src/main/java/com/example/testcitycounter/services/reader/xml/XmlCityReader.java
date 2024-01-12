package com.example.testcitycounter.services.reader.xml;

import com.example.testcitycounter.dto.CityDictionaryItem;
import com.example.testcitycounter.enums.XmlCityAttribute;
import com.example.testcitycounter.services.analyzer.DictionaryAnalyzer;
import com.example.testcitycounter.services.reader.AbstractCityReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by fedor.dydykin on 13.11.2023.
 */
public class XmlCityReader extends AbstractCityReader {

  public XmlCityReader(InputStream inputStream) {
    super(inputStream);
  }

  @Override
  public void read(DictionaryAnalyzer analyzer) {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    try {
      SAXParser saxParser = factory.newSAXParser();

      InputSource source = new InputSource(new InputStreamReader(inputStream));

      // utf-8
      source.setEncoding(StandardCharsets.UTF_8.displayName());

      saxParser.parse(source, new XmlHandler(analyzer));
    } catch (Exception e) {
      throw new RuntimeException("Can't parse file", e);
    }
  }

  private static class XmlHandler extends DefaultHandler {

    private final DictionaryAnalyzer analyzer;

    public XmlHandler(DictionaryAnalyzer analyzer) {
      this.analyzer = analyzer;
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
        this.analyzer.addItem(item);
      }
    }
  }
}
