package com.example.testcitycounter.services;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by fedor.dydykin on 12.11.2023.
 */
public class FileService {
  public static final String FILE_NAME_CSV = "default_files/address.csv";
  public static final String FILE_NAME_XML = "default_files/address.xml";

  private final FileValidationService fileValidationService;

  public FileService(FileValidationService fileValidationService) {
    this.fileValidationService = fileValidationService;
  }


  public File getDefaultCsvFile(){
    return getFile(FILE_NAME_CSV);
  }

  public File getDefaultXmlFile(){
    return getFile(FILE_NAME_XML);
  }

  private File getFile(String fileName) {
    URL resource = getClass().getClassLoader().getResource(fileName);
    if (resource == null) {
      throw new IllegalArgumentException("file not found!");
    } else {
      try {
        return new File(resource.toURI());
      } catch (URISyntaxException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public File getFileFromInput(String input) {
    if (input == null) {
      return null;
    }
    return switch (input) {
      case "1" -> getDefaultCsvFile();
      case "2" -> getDefaultXmlFile();
      default -> {
        final File file = new File(input);
        boolean fileIsValid = fileValidationService.validateFile(file);
        yield fileIsValid ? file : null;
      }
    };
  }
}
