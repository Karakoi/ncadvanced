package com.overseer.service.impl.report.view;

import static com.itextpdf.text.FontFactory.HELVETICA_BOLD;
import static com.itextpdf.text.FontFactory.getFont;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.overseer.model.enums.ProgressStatus;
import com.overseer.service.RequestService;
import com.overseer.service.impl.builder.PdfPTableBuilder;
import com.overseer.service.impl.builder.ReportDocumentBuilder;
import com.overseer.util.LocalDateFormatter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Implementation of <code>ReportDocumentBuilder</code> interface, that specifies how
 * to generate reports for Administrator.
 */
@Service
@RequiredArgsConstructor
public class AdminReportBuilder{

    private String start;
    private String end;
    private int countTop;

    private static final float DEFAULT_TABLE_WIDTH = 100.0f;
    private static final int DEFAULT_TABLE_SPACING = 10;

    private final RequestService requestService;

    public void setDatePeriod(String start, String end, int countTop) {
        this.start = start;
        this.end = end;
        this.countTop = countTop;
    }

    /**
     * Gets period and load PdfPTable with data for adding in report.
     *
     * @param beginDate date from.
     * @param endDate   date to.
     * @return return configured PdfPTable with data.
     */
    private PdfPTable getTableWithCountRequestsByPeriod(String beginDate, String endDate, ProgressStatus progressStatus) throws DocumentException {
        LocalDate start = LocalDate.parse(beginDate, LocalDateFormatter.FORMATTER);
        LocalDate end = LocalDate.parse(endDate, LocalDateFormatter.FORMATTER);
        val collection = requestService.findListCountRequestsByPeriod(start, end, progressStatus.getId());
        String name = progressStatus.getName();
        name = name.toLowerCase();
        name = name.replaceAll("_", " ");
        name = name.toUpperCase().charAt(0) + name.substring(1);
        final int tableColumnNum = 3;
        final int colorR = 185;
        final int colorG = 247;
        final int colorB = 166;
        PdfPTable table = new PdfPTableBuilder(tableColumnNum, DEFAULT_TABLE_WIDTH, DEFAULT_TABLE_SPACING)
                .addPdfPCells(new BaseColor(colorR, colorG, colorB), getFont(HELVETICA_BOLD),
                        "Count of " + name + " requests", "Start Date", "End Date")
                .build();

        collection
                .forEach(request -> {
                    table.addCell(request.getCount().toString());
                    table.addCell(request.getStartDateLimit().toString());
                    table.addCell(request.getEndDateLimit().toString());
                });

        return table;
    }

    /**
     * Gets period and load it to PDF list.
     *
     * @param start date from.
     * @param end   date to.
     * @return return configured Pdf list with data.
     */
    private List getListWithBestManagers(String start, String end, int countTop) {
        List list = new List();
        val collection = requestService.findBestManagersByPeriod(start, end, ProgressStatus.CLOSED.getId(), countTop);
        for (int i = 0; i < collection.size(); i++) {
            list.add(new ListItem(String.valueOf(i + 1)) + " " + new ListItem("Closed requests") + " " + collection.get(i).getCount()
                    + " " + new ListItem("Name") + " " + collection.get(i).getManagerFirstName() + " " + collection.get(i).getManagerLastName());
        }
        return list;
    }


    /**
     * Gets period and load it to PDF list.
     *
     * @param document new pdf document.
     * @return return configured Pdf list with data.
     */
    public Document buildPdfDocument(Document document) throws Exception {
        val dateNow = LocalDateTime.now();
        String logoFilepath = "src\\main\\resources\\img\\overseer_logo.jpg";
       return new ReportDocumentBuilder(document)
                .addParagraph(new Paragraph(dateNow.toLocalDate().toString() + ": " + dateNow.toLocalTime().toString()), Element.ALIGN_LEFT)
                .addImage(Image.getInstance(logoFilepath), Image.RIGHT)
                .addParagraph(new Paragraph("ADMIN REPORTS"), Element.ALIGN_TOP)
                .addParagraph(new Paragraph("For period: " + this.start + " : " + this.end, getFont(HELVETICA_BOLD)), Paragraph.ALIGN_LEFT)
                .addLineSeparator(new LineSeparator())
                .addLineSeparator(new LineSeparator())
                .addParagraph(new Paragraph("Count created requests in period from "
                        + start + " to " + end), Element.ALIGN_CENTER)
                .addTable(getTableWithCountRequestsByPeriod(start, end, ProgressStatus.FREE))
                .addTable(getTableWithCountRequestsByPeriod(start, end, ProgressStatus.IN_PROGRESS))
                .addLineSeparator(new LineSeparator())
                .addParagraph(new Paragraph("Best managers: "), Element.ALIGN_CENTER)
                .addList(getListWithBestManagers(start, end, countTop))
                .buildDocument();
    }
}
