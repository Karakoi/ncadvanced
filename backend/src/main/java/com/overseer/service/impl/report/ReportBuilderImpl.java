package com.overseer.service.impl.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.overseer.model.AbstractEntity;
import com.overseer.service.ReportBuilder;

import java.io.IOException;
import java.util.List;

/**
 * Basic implementation of {@link ReportBuilder} interface.
 *
 * @param <T> entity type.
 */
public abstract class ReportBuilderImpl<T extends AbstractEntity> implements ReportBuilder {

    private static final float WIDTH = 100f;
    private static final float SPACE = 10f;

    /**
     * {@inheritDoc}.
     */
    @Override
    public Document buildPDFTemplate() throws DocumentException, IOException {
        Document document = new Document();
        document.open();
        Image img = Image.getInstance("../resources/img/NetCracker_logo.jpg");
        document.add(img);
        Paragraph p = new Paragraph("REPORT:");
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        LineSeparator ls = new LineSeparator();
        document.add(new Chunk(ls));
        return document;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void buildExcelTemplate() {
    }

    /**
     * Method generate and initializate PdfPTable.
     *
     * @return complete {@link PdfPTable}.
     */
    public PdfPTable createTable(List collection) throws DocumentException {
        PdfPTable table = new PdfPTable(collection.size());
        table.setWidthPercentage(WIDTH);
        table.setSpacingBefore(SPACE);
        table.setSpacingAfter(SPACE);
        float[] columnWidths = null;
        for (int i = 0; i < collection.size(); i++) {
            columnWidths[1] = 2f;
        }
        table.setWidths(columnWidths);
        return table;
    }

    public Paragraph createParagraph(String title) {
        return new Paragraph(title);
    }

    public abstract Document generatePDFReport() throws IOException, DocumentException;

    public abstract void generateExcelReport();
}
