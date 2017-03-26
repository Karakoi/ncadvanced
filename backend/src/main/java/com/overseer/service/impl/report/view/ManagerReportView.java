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
import lombok.Getter;
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
@Getter
@SuppressWarnings("PMD.UnusedPrivateField")
public class ManagerReportView extends AbstractPdfView {

    private LocalDate start;
    private LocalDate end;
    private int managerId;

    private static final float DEFAULT_TABLE_WIDTH = 100.0f;
    private static final int DEFAULT_TABLE_SPACING = 10;
    private final RequestService requestService;

    public void setDatePeriod(LocalDate start, LocalDate end, int id) {
        this.start = start;
        this.end = end;
        this.managerId = id;
    }

    /**
     * Gets period and load PdfPTable with data for adding in manager's report.
     *
     * @param start date from.
     * @param end   date to.
     * @param id    manager id.
     * @return return configured PdfPTable with data.
     */
    private PdfPTable generateClosedRequestsTable(LocalDate start, LocalDate end, int id) {
        val collection = requestService.findListCountRequestsByManagerAndPeriod(start, end, ProgressStatus.CLOSED.getId(), id);
        final int tableColumnNum = 3;
        final int colorR = 185;
        final int colorG = 247;
        final int colorB = 166;
        PdfPTable table = new PdfPTableBuilder(tableColumnNum, DEFAULT_TABLE_WIDTH, DEFAULT_TABLE_SPACING)
                .addPdfPCells(new BaseColor(colorR, colorG, colorB), getFont(HELVETICA_BOLD),
                        "Count", "From", "To")
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
     * Gets period and load PdfPTable with data for adding in manager's report.
     *
     * @param id    manager id.
     * @return return configured PdfPTable with data.
     */
    private PdfPTable generateNeededCloseRequestsTable(int id) {
        val topInProgressRequests = requestService.findInProgressRequestsByAssignee((long) id, 1);
        final int tableColumnNum = 4;
        final int colorR = 253;
        final int colorG = 166;
        final int colorB = 149;
        PdfPTable table = new PdfPTableBuilder(tableColumnNum, DEFAULT_TABLE_WIDTH, DEFAULT_TABLE_SPACING)
                .addPdfPCells(new BaseColor(colorR, colorG, colorB), getFont(HELVETICA_BOLD),
                        "Title", "Priority", "Date of creation", "Description")
                .build();
        topInProgressRequests
                .forEach(request -> {
                    table.addCell(request.getTitle());
                    table.addCell(request.getPriorityStatus().getName());
                    table.addCell(request.getDateOfCreation().toString());
                    table.addCell(request.getDescription());
                });
        return table;
    }


    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String logoFilepath = "backend\\src\\main\\resources\\img\\overseer_logo.jpg";
        val dateNow = LocalDateTime.now();
        new ReportDocumentBuilder(document)
                .addParagraph(new Paragraph(dateNow.toLocalDate().toString() + ": " + dateNow.toLocalTime().toString()), Element.ALIGN_LEFT)
                .addImage(Image.getInstance(logoFilepath), Image.RIGHT)
                .addParagraph(new Paragraph("OFFICE MANAGER REPORTS"), Element.ALIGN_TOP)
                .addParagraph(new Paragraph("For period: " + this.start + " : " + this.end, getFont(HELVETICA_BOLD)), Paragraph.ALIGN_LEFT)
                .addLineSeparator(new LineSeparator())
                .addLineSeparator(new LineSeparator())
                .addParagraph(new Paragraph("Statistic for closed request by peroid:", getFont(HELVETICA_BOLD)), Paragraph.ALIGN_LEFT)
                .addTable(generateClosedRequestsTable(this.start, this.end, this.managerId))
                .addParagraph(new Paragraph("You need close this request in the near future:", getFont(HELVETICA_BOLD)), Paragraph.ALIGN_LEFT)
                .addTable(generateNeededCloseRequestsTable(this.managerId))
                .buildDocument();
    }
}
