package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipFile;


public class ZipParsingTest {
   static ClassLoader cl = ZipParsingTest.class.getClassLoader();
    String pdfName = "zipTest/junit-user-guide-5.8.2.pdf";
    String xlsName = "zipTest/sample-xlsx-file.xlsx";
    String csvName = "zipTest/sample-csv-file.csv";

    @Test
    @DisplayName("One test for parsing all files in zip")
    void zipTest() throws Exception {
        ZipFile zf = new ZipFile(new File("src/test/resources/files/zipTest.zip"));
         ZipInputStream is = new ZipInputStream(cl.getResourceAsStream("files/zipTest.zip"));
            ZipEntry entry;
            while ((entry = is.getNextEntry()) != null) {
                    try (InputStream inputStream = zf.getInputStream(entry)) {
                        if (entry.getName().equals(pdfName)) {
                        PDF pdf = new PDF(inputStream);
                        Assertions.assertThat(pdf.text).contains("Overview");
                        Assertions.assertThat(entry.getName()).isEqualTo(pdfName);
                        Assertions.assertThat(pdf.numberOfPages).isEqualTo(166);
                        }
                        else if ((entry.getName()).contains(xlsName)) {
                                XLS xls = new XLS(inputStream);
                                String stringCellValue = xls.excel.getSheetAt(0).getRow(21).getCell(1).getStringCellValue();
                                Assertions.assertThat(stringCellValue).contains("Belinda");
                        }
                        else if (entry.getName().equals(csvName)) {
                            CSVReader reader = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                            List<String[]> content = reader.readAll();
                            Assertions.assertThat(content).contains(
                                    new String[]{"0;First Name;Last Name;Gender;Country;Age;Date;Id"},
                                    new String[]{"1;Dulce;Abril;Female;United States;32;15/10/2017;1562"}
                            );
                        }
                    }
            }
    }

    @Test
    @DisplayName("PDF parsing test")
    void pdfZipParsingTest() throws Exception {
        ZipFile zf = new ZipFile(new File("src/test/resources/files/zipTest.zip"));
        ZipInputStream is = new ZipInputStream(cl.getResourceAsStream("files/zipTest.zip"));
        ZipEntry entry;
        while ((entry = is.getNextEntry()) != null) {
            try (InputStream inputStream = zf.getInputStream(entry)) {
                if (entry.getName().equals(pdfName)) {
                    PDF pdf = new PDF(inputStream);
                    Assertions.assertThat(pdf.text).contains("Overview");
                    Assertions.assertThat(entry.getName()).isEqualTo(pdfName);
                    Assertions.assertThat(pdf.numberOfPages).isEqualTo(166);

                }
            }
        }
    }

    @Test
    @DisplayName("XLS parsing test")
    void xlsZipParsingTest() throws Exception {
        ZipFile zf = new ZipFile(new File("src/test/resources/files/zipTest.zip"));
        ZipInputStream is = new ZipInputStream(cl.getResourceAsStream("files/zipTest.zip"));
        ZipEntry entry;
            while ((entry = is.getNextEntry()) != null) {
                try (InputStream inputStream = zf.getInputStream(entry)) {
                    if ((entry.getName()).contains(xlsName)) {
                        XLS xls = new XLS(inputStream);
                        String stringCellValue = xls.excel.getSheetAt(0).getRow(21).getCell(1).getStringCellValue();
                        Assertions.assertThat(stringCellValue).contains("Belinda");
                    }
                }
            }
    }

    @Test
    @DisplayName("CSV parsing test")
    void csvZipParsingTest() throws Exception {
        ZipFile zf = new ZipFile(new File("src/test/resources/files/zipTest.zip"));
        ZipInputStream is = new ZipInputStream(cl.getResourceAsStream("files/zipTest.zip"));
        ZipEntry entry;
        while ((entry = is.getNextEntry()) != null) {
            try (InputStream inputStream = zf.getInputStream(entry)) {
                if (entry.getName().equals(csvName)) {
                    CSVReader reader = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                    List<String[]> content = reader.readAll();
                    Assertions.assertThat(content).contains(
                            new String[]{"0;First Name;Last Name;Gender;Country;Age;Date;Id"},
                            new String[]{"1;Dulce;Abril;Female;United States;32;15/10/2017;1562"}
                    );
                }

            }
        }
    }
}
