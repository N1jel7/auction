package by.n1jel.auction.utils;

import by.n1jel.auction.dto.ReportResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class WordConverter {

    private final static int HEADER_SIZE = 1;

    private String formatDate(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return localDateTime.format(formatter);
    }

    private String formatDate(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(formatter);
    }

    public boolean convert(ReportResponseDto reportResponseDto) {
        try {
            XWPFDocument document = new XWPFDocument();

            XWPFParagraph headerParagraph = document.createParagraph();
            headerParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun paragraphRun = headerParagraph.createRun();
            paragraphRun.setText(
                    "Отчёт о продажах в период с "
                            + formatDate(reportResponseDto.from())
                            + " по "
                            + formatDate(reportResponseDto.to()));
            paragraphRun.setFontFamily("Times New Roman");
            paragraphRun.setFontSize(14);

            XWPFTable table = document.createTable();
            table.setTableAlignment(TableRowAlign.CENTER);
            XWPFTableRow headerRow = table.getRow(0);

            headerRow.getCell(0).setText("Лот №");
            headerRow.addNewTableCell().setText("Тип");
            headerRow.addNewTableCell().setText("Имя");
            headerRow.addNewTableCell().setText("Цена $");
            headerRow.addNewTableCell().setText("Дата продажи");


            for (int row_i = 0; row_i < reportResponseDto.totalLotsAmount(); row_i++) {
                XWPFTableRow tableRow = table.createRow();
                for (int column = 0; column < 5; column++) {
                    switch (column) {
                        case 0:
                            tableRow.getCell(column).setText(reportResponseDto.lots().get(row_i).id().toString());
                            break;
                        case 1:
                            tableRow.getCell(column).setText(reportResponseDto.lots().get(row_i).type());
                            break;
                        case 2:
                            tableRow.getCell(column).setText(reportResponseDto.lots().get(row_i).name());
                            break;
                        case 3:
                            tableRow.getCell(column).setText(reportResponseDto.lots().get(row_i).price().toString());
                            break;
                        case 4:
                            tableRow.getCell(column).setText(formatDate(reportResponseDto.lots().get(row_i).soldAt()));
                            break;
                    }
                }
            }

            XWPFTableRow footerRow = table.createRow();
            footerRow.getCell(3).setText(reportResponseDto.totalLotsPrice().toString());
            footerRow.getCell(4).setText("Итого");

            File file = new File("C:\\dev\\projects\\my-auction\\auction-client\\src\\main\\resources\\assets\\convert\\out\\report_" + LocalDate.now() +".docx");
            file.createNewFile();
            OutputStream outputStream = new FileOutputStream(file, false);
            document.write(outputStream);
            outputStream.close();
            document.close();

        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return true;
    }
}
