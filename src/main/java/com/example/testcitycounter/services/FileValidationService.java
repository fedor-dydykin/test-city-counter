package com.example.testcitycounter.services;

import com.example.testcitycounter.enums.FileExtension;
import com.example.testcitycounter.services.printer.DataPrinter;
import java.io.File;
import java.util.Arrays;

/**
 * Created by fedor.dydykin on 23.09.2023.
 */
public class FileValidationService {


  private final DataPrinter dataPrinter;

  public FileValidationService(DataPrinter dataPrinter) {
    this.dataPrinter = dataPrinter;
  }

  public boolean validateFile(File file) {
    System.out.println("++++++++++++++++ " + new File(".").getAbsolutePath());
    final boolean exists = file.exists();
    if (!exists) {
      dataPrinter.printError("File ");
      dataPrinter.printError("     " + file.getAbsolutePath());
      dataPrinter.printError("doesn't exist. Please, correct the file path.");
      return false;
    }
    if (!fileHasValidExtension(file)) {
      System.err.println("File should be csv or xml only.");
      return false;
    }
    return true;
  }

  private boolean fileHasValidExtension(File file) {
    return Arrays.stream(FileExtension.values())
        .anyMatch(allowedExtension -> file.getAbsolutePath()
            .endsWith("." + allowedExtension.name().toLowerCase())
        );
  }

}
