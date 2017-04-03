package com.overseer.service.impl.report;

import static com.itextpdf.text.FontFactory.HELVETICA;
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
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Class for generating pdf reports for Office Manager.
 */
@Service
@RequiredArgsConstructor
@Getter
@PropertySource("classpath:resources.properties")
@SuppressWarnings("PMD.UnusedPrivateField")
public class ManagerReportBuilder {

    @Value("${reports.image.logo}")
    private String logoImg;

    @Value("${reports.gravatar.link}")
    private String gravatarLink;

    private String start;
    private String end;
    private String encryptedEmail;
    private int managerId;

    private static final float DEFAULT_TABLE_WIDTH = 100.0f;
    private static final int DEFAULT_TABLE_SPACING = 10;
    private final RequestService requestService;

    public void setDatePeriod(String start, String end, int id, String encryptedEmail) {
        this.start = start;
        this.end = end;
        this.managerId = id;
        this.encryptedEmail = encryptedEmail;
    }

    /**
     * Gets period and load PdfPTable with data for adding in manager's report.
     *
     * @param beginDate date from.
     * @param endDate   date to.
     * @param id        manager id.
     * @return return configured PdfPTable with data.
     */
    private PdfPTable generateClosedRequestsTable(String beginDate, String endDate, int id) {
        LocalDate start = LocalDate.parse(beginDate, LocalDateFormatter.FORMATTER);
        LocalDate end = LocalDate.parse(endDate, LocalDateFormatter.FORMATTER);
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
     * @param id manager id.
     * @return return configured PdfPTable with data.
     */
    private PdfPTable generateNeededCloseRequestsTable(int id) {
        final int size = 20;
        final int pageNum = 1;
        val topInProgressRequests = requestService.findRequestsWithGivenProgressByAssignee(
                (long) id, ProgressStatus.IN_PROGRESS, size, pageNum);
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

    /**
     * Gets period and load it to PDF list.
     *
     * @param document new pdf document.
     * @return return configured Pdf list with data.
     */
    public Document buildPdfDocument(Document document) throws Exception {
        final int countNewLine = 6;
        final float imgLogoX = 370f;
        final float imgLogoY = 760f;
        final float imgAvatarX = 40f;
        final float imgAvatarY = 720f;
        final int fontSize = 8;
        Font font = getFont(HELVETICA, fontSize);
        val dateNow = LocalDateTime.now();

        return new ReportDocumentBuilder(document)
                .addImage(Image.getInstance(new URL(this.gravatarLink + this.encryptedEmail + "?s=100")), imgAvatarX, imgAvatarY)
                .addImage(Image.getInstance(this.logoImg), imgLogoX, imgLogoY)
                .addNewLine(countNewLine)
                .addParagraph(new Paragraph(dateNow.toLocalDate().toString() + ": " + dateNow.toLocalTime().toString()), Element.ALIGN_LEFT)
                .addParagraph(new Paragraph("OFFICE MANAGER REPORTS"), Element.ALIGN_TOP)
                .addParagraph(new Paragraph("For period: " + this.start + " : " + this.end, getFont(HELVETICA_BOLD)), Paragraph.ALIGN_LEFT)
                .addLineSeparator(new LineSeparator())
                .addLineSeparator(new LineSeparator())
                .addParagraph(new Paragraph("Statistic for closed request by period:", getFont(HELVETICA_BOLD)), Paragraph.ALIGN_LEFT)
                .addParagraph(new Paragraph("Node: Data are presented with rounding up to a near month", font), Element.ALIGN_LEFT)
                .addTable(generateClosedRequestsTable(this.start, this.end, this.managerId))
                .addParagraph(new Paragraph("You need close this request in the near future:", getFont(HELVETICA_BOLD)), Paragraph.ALIGN_LEFT)
                .addTable(generateNeededCloseRequestsTable(this.managerId))
                .buildDocument();
    }
}
