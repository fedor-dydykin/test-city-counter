package com.example.testcitycounter.services;

import com.example.testcitycounter.dto.SourceFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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


  public SourceFile getDefaultCsvFile() {
    return new SourceFile(FILE_NAME_CSV, getFileInputStream(FILE_NAME_CSV));
  }

  public SourceFile getDefaultXmlFile() {
    return new SourceFile(FILE_NAME_XML, getFileInputStream(FILE_NAME_XML));
  }

  private InputStream getFileInputStream(String fileName){
    return getClass().getResourceAsStream("/" + fileName);
  }

  public SourceFile getFileFromInput(String input) {
    if (input == null) {
      return null;
    }
    return switch (input) {
      case "1" -> getDefaultCsvFile();
      case "2" -> getDefaultXmlFile();
      default -> {
        final File file = new File(input);
        boolean fileIsValid = fileValidationService.validateFile(file);
        final FileInputStream inputStream;
        try {
          inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
          throw new RuntimeException(e);
        }
        yield fileIsValid ? new SourceFile(input, inputStream) : null;
      }
    };
  }
}
