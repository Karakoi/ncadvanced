package com.overseer.service.impl.report.view;

import static com.itextpdf.text.FontFactory.HELVETICA_BOLD;
import static com.itextpdf.text.FontFactory.getFont;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.overseer.model.enums.ProgressStatus;
import com.overseer.service.RequestService;
import com.overseer.service.impl.report.builder.PdfPTableBuilder;
import com.overseer.service.impl.report.builder.ReportDocumentBuilder;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Implementation of <code>ReportDocumentBuilder</code> interface, that specifies how
 * to generate reports for Administrator.
 */
@Service
@RequiredArgsConstructor
public class AdminReportView extends AbstractPdfView {

    private LocalDate start;
    private LocalDate end;
    private int countTop;

    private static final float DEFAULT_TABLE_WIDTH = 100.0f;
    private static final int DEFAULT_TABLE_SPACING = 10;

    private final RequestService requestService;

    public void setDatePeriod(LocalDate start, LocalDate end, int countTop) {
        this.start = start;
        this.end = end;
        this.countTop = countTop;
    }

    /**
     * Gets period and load PdfPTable with data for adding in report.
     *
     * @param start date from.
     * @param end   date to.
     * @return return configured PdfPTable with data.
     */
    private PdfPTable getTableWithCountRequestsByPeriod(LocalDate start, LocalDate end) throws DocumentException {

        val collection = requestService.findListCountRequestsByPeriod(start, end, ProgressStatus.FREE.getId());
        final int tableColumnNum = 3;
        final int colorR = 185;
        final int colorG = 247;
        final int colorB = 166;
        PdfPTable table = new PdfPTableBuilder(tableColumnNum, DEFAULT_TABLE_WIDTH, DEFAULT_TABLE_SPACING)
                .addPdfPCells(new BaseColor(colorR, colorG, colorB), getFont(HELVETICA_BOLD),
                        "Count", "Start Date", "End Date")
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
    private List getListWithBestManagers(LocalDate start, LocalDate end, int countTop) {
        List list = new List();
        val collection = requestService.findBestManagersByPeriod(start, end, ProgressStatus.CLOSED.getId(), countTop);

        System.out.println(collection);
        for (int i = 0; i < collection.size(); i++) {
            list.add(new ListItem("Position " + (i + 1)) + " Closed requests: " + collection.get(i).getCount() + ", Name: "
                    + new ListItem(collection.get(i).getManagerFirstName() + " " + collection.get(i).getManagerLastName()));
        }
        return list;
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        val dateNow = LocalDateTime.now();
        String logoFilepath = "backend\\src\\main\\resources\\img\\overseer_logo.jpg";
        new ReportDocumentBuilder(document)
                .addParagraph(new Paragraph(dateNow.toLocalDate().toString() + ": " + dateNow.toLocalTime().toString()), Element.ALIGN_LEFT)
                .addImage(Image.getInstance(logoFilepath), Image.RIGHT)
                .addParagraph(new Paragraph("ADMIN REPORTS"), Element.ALIGN_TOP)
                .addParagraph(new Paragraph("For period: " + this.start + " : " + this.end, getFont(HELVETICA_BOLD)), Paragraph.ALIGN_LEFT)
                .addLineSeparator(new LineSeparator())
                .addLineSeparator(new LineSeparator())
                .addParagraph(new Paragraph("Count created requests in period from "
                        + start.toString() + " to " + end.toString()), Element.ALIGN_CENTER)
                .addTable(getTableWithCountRequestsByPeriod(start, end))
                .addLineSeparator(new LineSeparator())
                .addParagraph(new Paragraph("Best managers: "), Element.ALIGN_CENTER)
                .addList(getListWithBestManagers(start, end, countTop))
                .buildDocument();
    }
}
