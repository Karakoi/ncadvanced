package com.overseer.service.impl.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.overseer.dto.RequestDTO;
import com.overseer.service.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
//import java.util.List;


/**
 * Implementation of <code>ReportBuilderImpl</code> interface, that specifies how
 * to generate reports for Administrator.
 */
@Service
@RequiredArgsConstructor
public class AdminReportBuilderImpl extends ReportBuilderImpl {

    private static final int TABLE_SIZE = 3;
    @Autowired
    private RequestService requestService;


    /**
     * {@inheritDoc}.
     */
    @Override
    public Document generateReport(Document document, LocalDate start, LocalDate end) {
        try {
            return createPDF(document)
//                    .buildImage(getImageForAdmin())
                    .buildParagraph(new Paragraph("ADMIN REPORTS"), Element.ALIGN_TOP)
                    .buildLineSeparator(new LineSeparator())
                    .buildLineSeparator(new LineSeparator())
                    .buildParagraph(new Paragraph("Count created requests in period from "
                            + start.toString() + " to " + end.toString()), Element.ALIGN_CENTER)
                    .buildTable(getTableWithCountRequestsByPeriod(start, end))
                    .buildLineSeparator(new LineSeparator())
                    .buildParagraph(new Paragraph("Best managers: "), Element.ALIGN_CENTER)
                    .buildList(getListWithBestManagers(start, end))
                    .getDocument();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

//    /**
//     * Add image to document.
//     *
//     * @return image.
//     */
//    private Image getImageForAdmin() {
//        try {
//            return Image.getInstance("../../main/resources/img/NetCracker_logo.jpg");
//        } catch (BadElementException | IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    /**
     * Gets period and load PdfPTable with data for adding in report.
     *
     * @param start date from.
     * @param end   date to.
     * @return return configured PdfPTable with data.
     */
    private PdfPTable getTableWithCountRequestsByPeriod(LocalDate start, LocalDate end) throws DocumentException {

        PdfPTable table = new PdfPTable(TABLE_SIZE);
        table.setWidthPercentage(TABLE_WIDTH_PERCENTAGE);
        table.setSpacingBefore(TABLE_SPACING);
        table.setSpacingAfter(TABLE_SPACING);
        float[] columnWidths = {COLOMN_WIDTH, COLOMN_WIDTH, COLOMN_WIDTH};
        table.setWidths(columnWidths);
        val collection = requestService.findListCountRequestsByPeriod(start, end, "Free");
        for (RequestDTO r : collection) {

            PdfPCell firstCell = new PdfPCell(new Paragraph("Count: " + r.getCount()));
            firstCell.setBorderColor(BaseColor.BLACK);
            firstCell.setPaddingLeft(CELL_PADDING);
            firstCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            firstCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell secondCell = new PdfPCell(new Paragraph("Start Date: " + r.getStartDateLimit()));
            secondCell.setBorderColor(BaseColor.BLACK);
            secondCell.setPaddingLeft(CELL_PADDING);
            secondCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            secondCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell thirtCell = new PdfPCell(new Paragraph("End Date: " + r.getEndDateLimit()));
            thirtCell.setBorderColor(BaseColor.BLACK);
            thirtCell.setPaddingLeft(CELL_PADDING);
            thirtCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            thirtCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

            table.addCell(firstCell);
            table.addCell(secondCell);
            table.addCell(thirtCell);
        }
        return table;
    }

    /**
     * Gets period and load it to PDF list.
     *
     * @param start date from.
     * @param end   date to.
     * @return return configured Pdf list with data.
     */
    private List getListWithBestManagers(LocalDate start, LocalDate end) {
        List list = new List();
        val collection = requestService.findBestManagersByPeriod(start, end, "Closed");

        System.out.println(collection);
        for (int i = 0; i < collection.size(); i++) {
            list.add(new ListItem("Position " + (i + 1)) + " Closed requests: " + collection.get(i).getCount() + ", Name: "
                    + new ListItem(collection.get(i).getManagerFirstName() + " " + collection.get(i).getManagerLastName()));
        }
        return list;
    }

}
