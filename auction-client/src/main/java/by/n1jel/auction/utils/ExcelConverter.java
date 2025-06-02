package by.n1jel.auction.utils;

import by.n1jel.auction.dto.ReportResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class ExcelConverter {

    private final static int HEADER_SIZE = 2;

    private String formatDate(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm");
        return localDateTime.format(formatter);
    }

    private String formatDate(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(formatter);
    }

    public boolean convert(ReportResponseDto reportResponseDto) {
        try {
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet main = wb.createSheet("Отчёт");
            main.setColumnWidth(0, 1800);
            main.setColumnWidth(1, 3000);
            main.setColumnWidth(2, 7000);
            main.setColumnWidth(3, 4000);
            main.setColumnWidth(4, 6000);

            Row rowMainHeader = main.createRow(0);
            Cell cellHeader = rowMainHeader.createCell(0);
            cellHeader.setCellValue(
                    "Отчёт о продажах в период с "
                            + formatDate(reportResponseDto.from())
                            + " по "
                            + formatDate(reportResponseDto.to()));

            Row rowSecondHeader = main.createRow(1);
            Cell head1 = rowSecondHeader.createCell(0);
            head1.setCellValue("Лот №");

            Cell head2 = rowSecondHeader.createCell(1);
            head2.setCellValue("Тип   ");

            Cell head3 = rowSecondHeader.createCell(2);
            head3.setCellValue("Имя   ");

            Cell head4 = rowSecondHeader.createCell(3);
            head4.setCellValue("Цена $   ");

            Cell head5 = rowSecondHeader.createCell(4);
            head5.setCellValue("Дата продажи  ");


            for (int row_i = 0; row_i < reportResponseDto.totalLotsAmount(); row_i++) {
                Row row = main.createRow(row_i + HEADER_SIZE);
                for (int column = 0; column < 5; column++) {
                    Cell cell = row.createCell(column);
                    switch (column) {
                        case 0:
                            cell.setCellValue(reportResponseDto.lots().get(row_i).id());
                            break;
                        case 1:
                            cell.setCellValue(reportResponseDto.lots().get(row_i).type());
                            break;
                        case 2:
                            cell.setCellValue(reportResponseDto.lots().get(row_i).name());
                            break;
                        case 3:
                            cell.setCellValue(reportResponseDto.lots().get(row_i).price().toString());
                            break;
                        case 4:
                            cell.setCellValue(formatDate(reportResponseDto.lots().get(row_i).soldAt()));
                            break;
                    }
                }
            }

            Row rowFooter = main.createRow(reportResponseDto.totalLotsAmount() + HEADER_SIZE);
            for (int column = 3; column <= 4; column++) {
                Cell cell = rowFooter.createCell(column);
                switch (column) {
                    case 3:
                        cell.setCellValue(reportResponseDto.totalLotsPrice().toString());
                        break;
                    case 4:
                        cell.setCellValue("ИТОГО");
                        break;
                }
            }

            File file = new File("C:\\dev\\projects\\my-auction\\auction-client\\src\\main\\resources\\assets\\convert\\out\\report_" + LocalDate.now() +".xlsx");
            file.createNewFile();
            OutputStream outputStream = new FileOutputStream(file, false);
            wb.write(outputStream);
            outputStream.close();
            wb.close();

        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return true;
    }
}
