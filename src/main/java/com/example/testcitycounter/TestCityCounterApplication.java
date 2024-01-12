package com.example.testcitycounter;

import com.example.testcitycounter.dto.SourceFile;
import com.example.testcitycounter.services.analyzer.DuplicatesAnalyzer;
import com.example.testcitycounter.services.FileService;
import com.example.testcitycounter.services.analyzer.HouseCounter;
import com.example.testcitycounter.services.analyzer.NestedAnalyzer;
import com.example.testcitycounter.services.printer.DataPrinter;
import com.example.testcitycounter.services.printer.DataPrinterFactory;
import com.example.testcitycounter.services.reader.CityReader;
import com.example.testcitycounter.services.reader.CityReaderFactory;
import com.example.testcitycounter.services.FileValidationService;
import java.io.File;
import java.util.List;
import java.util.Scanner;

public class TestCityCounterApplication {

  public static void main(String[] args) {
    DataPrinter dataPrinter = DataPrinterFactory.getInstance();
    FileValidationService fileValidationService = new FileValidationService(dataPrinter);
    FileService fileService = new FileService(fileValidationService);

    Scanner scanner = new Scanner(System.in);
    printWelcomeMessage(dataPrinter);
    while (true) {
      dataPrinter.println("Please enter the path to csv/xml file with cities:");
      String input;
      do {
        input = scanner.nextLine();
      } while (input == null || input.trim().isEmpty());
      if ("exit".equals(input)) {
        break;
      }
      try {
        final SourceFile sourceFile = fileService.getFileFromInput(input);
        if (sourceFile != null) {
          dataPrinter.println("Reading file: " + sourceFile.fileName() + "....");
          NestedAnalyzer nestedAnalyzer = new NestedAnalyzer(
              List.of(new DuplicatesAnalyzer(dataPrinter), new HouseCounter(dataPrinter))
          );
          final CityReader cityReader = CityReaderFactory.createCityReader(sourceFile, dataPrinter);
          cityReader.read(nestedAnalyzer);
          nestedAnalyzer.printResults();
        }
      } catch (Exception e) {
        dataPrinter.printError("Unexpected error", e);
      }
      printIterationBreaker(dataPrinter);
    }
    dataPrinter.println("Exiting, bye!");
  }

  private static void printIterationBreaker(DataPrinter dataPrinter) {
    dataPrinter.println();
    dataPrinter.println("------------------------------------------------------");
    dataPrinter.println();
    dataPrinter.println();
  }

  private static void printWelcomeMessage(DataPrinter dataPrinter) {
    dataPrinter.println("Welcome to the Test City Counter application!");
    dataPrinter.println("Type 'exit' to stop the application.");
    dataPrinter.println("Type '1' to load default csv file.");
    dataPrinter.println("Type '2' to load default xml file.");
    dataPrinter.println();
    dataPrinter.println();
  }

}
