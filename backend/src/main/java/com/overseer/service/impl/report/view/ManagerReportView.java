package com.overseer.service.impl.report.view;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.overseer.service.RequestService;
import com.overseer.service.impl.report.builder.ReportDocumentBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

//    private static final float TABLE_SPACING = 10f;
//    private static final float COLOMN_WIDTH = 2f;
//    private static final float CELL_PADDING = 10;
//    private static final int TABLE_WIDTH_PERCENTAGE = 100;
//    private static final int TABLE_SIZE = 3;

    private final RequestService requestService;

    public void setDatePeriod(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

//    /**
//     * Gets period and load PdfPTable with data for adding in report.
//     *
//     * @param start date from.
//     * @param end   date to.
//     * @return return configured PdfPTable with data.
//     */
//    private PdfPTable getTableWithCountRequestsByPeriod(LocalDate start, LocalDate end) throws DocumentException {
//
//        PdfPTable table = new PdfPTable(TABLE_SIZE);
//        table.setWidthPercentage(TABLE_WIDTH_PERCENTAGE);
//        table.setSpacingBefore(TABLE_SPACING);
//        table.setSpacingAfter(TABLE_SPACING);
//        float[] columnWidths = {COLOMN_WIDTH, COLOMN_WIDTH, COLOMN_WIDTH};
//        table.setWidths(columnWidths);
//        val collection = requestService.findListCountRequestsByPeriod(start, end, ProgressStatus.FREE.getId());
//        for (RequestDTO r : collection) {
//
//            PdfPCell firstCell = new PdfPCell(new Paragraph("Count: " + r.getCount()));
//            firstCell.setBorderColor(BaseColor.BLACK);
//            firstCell.setPaddingLeft(CELL_PADDING);
//            firstCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            firstCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//
//            PdfPCell secondCell = new PdfPCell(new Paragraph("Start Date: " + r.getStartDateLimit()));
//            secondCell.setBorderColor(BaseColor.BLACK);
//            secondCell.setPaddingLeft(CELL_PADDING);
//            secondCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            secondCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//
//            PdfPCell thirtCell = new PdfPCell(new Paragraph("End Date: " + r.getEndDateLimit()));
//            thirtCell.setBorderColor(BaseColor.BLACK);
//            thirtCell.setPaddingLeft(CELL_PADDING);
//            thirtCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            thirtCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//
//            table.addCell(firstCell);
//            table.addCell(secondCell);
//            table.addCell(thirtCell);
//        }
//        return table;
//    }
//
//    /**
//     * Gets period and load it to PDF list.
//     *
//     * @param start date from.
//     * @param end   date to.
//     * @return return configured Pdf list with data.
//     */
//    private List getListWithBestManagers(LocalDate start, LocalDate end) {
//        List list = new List();
//        val collection = requestService.findBestManagersByPeriod(start, end, ProgressStatus.CLOSED.getId());
//
//        System.out.println(collection);
//        for (int i = 0; i < collection.size(); i++) {
//            list.add(new ListItem("Position " + (i + 1)) + " Closed requests: " + collection.get(i).getCount() + ", Name: "
//                    + new ListItem(collection.get(i).getManagerFirstName() + " " + collection.get(i).getManagerLastName()));
//        }
//        return list;
//    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String logoFilepath = "backend\\src\\main\\resources\\img\\overseer_logo.jpg";
        new ReportDocumentBuilder(document)
                .addImage(Image.getInstance(logoFilepath), Image.RIGHT)
                .addParagraph(new Paragraph("OFFICE MANAGER REPORTS"), Element.ALIGN_TOP)
                .addLineSeparator(new LineSeparator())
                .addLineSeparator(new LineSeparator())
//                .addParagraph(new Paragraph("Count created requests in period from "
//                        + start.toString() + " to " + end.toString()), Element.ALIGN_CENTER)
//                .addTable(getTableWithCountRequestsByPeriod(start, end))
//                .addLineSeparator(new LineSeparator())
//                .addParagraph(new Paragraph("Best managers: "), Element.ALIGN_CENTER)
//                .addList(getListWithBestManagers(start, end))
                .buildDocument();
    }
}
