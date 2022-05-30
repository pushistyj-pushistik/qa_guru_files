package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipFile;

import static org.assertj.core.api.Assertions.assertThat;

public class SelenideFilesTest {
    ClassLoader cl = SelenideFilesTest.class.getClassLoader();

    @Test
    void zipParsingPDFTest() throws Exception {
        ZipFile zf = new ZipFile(new File("src/test/resources/files/ziptest.zip"));
        try (ZipInputStream is = new ZipInputStream(cl.getResourceAsStream("files/ziptest.zip"))) {
            ZipEntry entry;
            while ((entry = is.getNextEntry()) != null) {
                if ((entry.getName()).contains("junit-user-guide-5.8.2.pdf")) {
                    try (InputStream inputStream = zf.getInputStream(entry)) {
                        PDF pdf = new PDF(inputStream);
                        Assertions.assertThat(pdf.numberOfPages).isEqualTo(166);

                    }
                }

            }
        }
    }

    @Test
    void zipParsingXLSXTest() throws Exception {
        ZipFile zf = new ZipFile(new File("src/test/resources/files/ziptest.zip"));
        try (ZipInputStream is = new ZipInputStream(Objects.requireNonNull(cl.getResourceAsStream("files/ziptest.zip")))) {
            ZipEntry entry;
            while ((entry = is.getNextEntry()) != null) {
                if ((entry.getName()).contains("sample-xlsx-file.xlsx")) {
                    try (InputStream stream = zf.getInputStream(entry)) {
                        XLS xls = new XLS(stream);
                        String stringCellValue = xls.excel.getSheetAt(0).getRow(21).getCell(1).getStringCellValue();
                        Assertions.assertThat(entry.getName()).isEqualTo("sample-xlsx-file.xlsx");
                        Assertions.assertThat(stringCellValue).contains("Belinda");
                    }
                }
            }
        }
    }
}
