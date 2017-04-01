package com.overseer.service.impl.report;

import static com.itextpdf.text.FontFactory.HELVETICA_BOLD;
import static com.itextpdf.text.FontFactory.getFont;
import static java.lang.String.format;
import static java.lang.String.valueOf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.overseer.model.Request;
import com.overseer.model.User;
import com.overseer.service.impl.builder.PdfPTableBuilder;
import com.overseer.service.impl.builder.ReportDocumentBuilder;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Class for generating pdf reports for request.
 */
@Service
@RequiredArgsConstructor
@Setter
@PropertySource("classpath:resources.properties")
public class RequestReportPdfBuilder {

    @Value("${reports.image.logo}")
    private String logoImg;

    private Request request;
    private List<Request> subRequests;
    private List<Request> joinedRequests;

    private static final float DEFAULT_TABLE_WIDTH = 100.0f;
    private static final int DEFAULT_TABLE_SPACING = 10;

    /**
     * Method build pdf document.
     *
     * @param document new pdf document.
     * @return return configured Pdf list with data.
     */
    public Document buildPdfDocument(Document document) throws Exception {
        Font font = getFont(HELVETICA_BOLD);
        final float imgLogoX = 370f;
        final float imgLogoY = 760f;
        final int detailsTableColumnNum = 2;
        final int countNewLine = 3;

        return new ReportDocumentBuilder(document)
                .addImage(Image.getInstance(this.logoImg), imgLogoX, imgLogoY)
                .addNewLine(countNewLine)
                .addParagraph(new Paragraph("Details:", font), Paragraph.ALIGN_LEFT)
                .addTable(new PdfPTableBuilder(detailsTableColumnNum, DEFAULT_TABLE_WIDTH, DEFAULT_TABLE_SPACING)
                        .addPdfPCells(BaseColor.LIGHT_GRAY, font, "Column", "Value")
                        .addDoubleCell("Title:", request.getTitle())
                        .addDoubleCell("Reporter:", getUserFullName(request.getReporter()))
                        .addDoubleCell("Assignee:", getUserFullName(request.getAssignee()))
                        .addDoubleCell("Progress:", request.getProgressStatus().name())
                        .addDoubleCell("Priority:", request.getPriorityStatus().getName())
                        .addDoubleCell("Date of creation:", getFormattedDate(request.getDateOfCreation()))
                        .addDoubleCell("Estimate time:", valueOf(request.getEstimateTimeInDays()))
                        .addDoubleCell("Description:", request.getDescription())
                        .build())
                .addParagraphByCondition(!subRequests.isEmpty(), new Paragraph("\nSub requests:", font))
                .addTableByCondition(!subRequests.isEmpty(), generateSubRequestsTable(subRequests))
                .addParagraphByCondition(!joinedRequests.isEmpty(), new Paragraph("\nJoined requests:", font))
                .addTableByCondition(!joinedRequests.isEmpty(), generateJoinedRequestsTable(joinedRequests))
                .buildDocument();

    }

    /**
     * Generates {@link PdfPTable} table for sub requests report section.
     *
     * @param subRequests request sub requests
     * @return sub requests table
     */
    private PdfPTable generateSubRequestsTable(List<Request> subRequests) {
        final int subRequestsTableColumnNum = 3;
        PdfPTable subRequestsTable = new PdfPTableBuilder(subRequestsTableColumnNum, DEFAULT_TABLE_WIDTH, DEFAULT_TABLE_SPACING)
                .addPdfPCells(BaseColor.LIGHT_GRAY, getFont(HELVETICA_BOLD),
                        "Title", "Description", "Date of creation")
                .build();

        subRequests
                .forEach(request -> {
                    subRequestsTable.addCell(request.getTitle());
                    subRequestsTable.addCell(request.getDescription());
                    subRequestsTable.addCell(getFormattedDate(request.getDateOfCreation()));
                });

        return subRequestsTable;
    }

    /**
     * Generates {@link PdfPTable} table for joined requests report section.
     *
     * @param joinedRequests request sub requests
     * @return joined requests table
     */
    private PdfPTable generateJoinedRequestsTable(List<Request> joinedRequests) {
        final int joinedTableColumnNum = 4;
        PdfPTable joinedRequestsTable = new PdfPTableBuilder(joinedTableColumnNum, DEFAULT_TABLE_WIDTH, DEFAULT_TABLE_SPACING)
                .addPdfPCells(BaseColor.LIGHT_GRAY, getFont(HELVETICA_BOLD),
                        "Title", "Reporter", "Priority", "Date of creation")
                .build();

        joinedRequests
                .forEach(request -> {
                    joinedRequestsTable.addCell(request.getTitle());
                    joinedRequestsTable.addCell(getUserFullName(request.getReporter()));
                    joinedRequestsTable.addCell(request.getPriorityStatus().getName());
                    joinedRequestsTable.addCell(getFormattedDate(request.getDateOfCreation()));
                });

        return joinedRequestsTable;
    }

    /**
     * Concat full user name into string. Also check if user or last name is absent and substitute empty string.
     * Request assignee can be null and last name is not required.
     *
     * @param user specified user
     * @return full name string
     */
    private String getUserFullName(User user) {
        if (user == null) {
            return "";
        }
        if (user.getSecondName() == null) {
            user.setSecondName("");
        }
        return format("%s %s %s", user.getLastName(), user.getFirstName(), user.getSecondName());
    }

    /**
     * Format {@link LocalDateTime} instance by pattern.
     * By default it contain 'T' symbol.
     *
     * @param dateTime selected date and time
     * @return formatted date and time string
     */
    private String getFormattedDate(LocalDateTime dateTime) {
        return DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss")
                .format(dateTime);
    }
}
