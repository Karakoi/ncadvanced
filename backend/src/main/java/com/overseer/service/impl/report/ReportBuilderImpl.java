package com.overseer.service.impl.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.IOException;
import java.time.LocalDate;

/**
 * The <code>ReportBuilderImpl</code> abstract class represents Builder pattern for creating report in PDF format.
 */
@SuppressWarnings("PMD.UnusedPrivateField")
public abstract class ReportBuilderImpl {

    protected static final float TABLE_WIDTH = 100f;
    protected static final float TABLE_SPACING = 10f;
    protected static final float COLOMN_WIDTH = 2f;
    protected static final float CELL_PADDING = 10;
    protected static final int TABLE_WIDTH_PERCENTAGE = 100;
    protected static final float SPACE = 10f;

    private Document document;
    private PdfPTable pdfTable;
    private Paragraph paragraph = new Paragraph();
    private Image image;
    private LineSeparator lineSeparator;
    private List list;

    /**
     * Creates new document and returns ReportBuilderImpl for next usages.
     *
     * @param document pdf document.
     * @return ReportBuilderImpl.
     */
    public ReportBuilderImpl createPDF(Document document) {
        this.document = document;
        this.document.open();
        return this;
    }

    /**
     * Gets new image, add it to document and returns ReportBuilderImpl for next usages.
     *
     * @param img pdf document.
     * @return ReportBuilderImpl.
     */
    public ReportBuilderImpl buildImage(Image img) throws DocumentException {
        this.image = img;
        document.add(image);
        return this;
    }

    /**
     * Gets new list, add it to document and returns ReportBuilderImpl for next usages.
     *
     * @param list pdf document.
     * @return ReportBuilderImpl.
     */
    public ReportBuilderImpl buildList(List list) throws DocumentException {
        this.list = list;
        document.add(list);
        return this;
    }

    /**
     * Gets new line separator, add it to document and returns ReportBuilderImpl for next usages.
     *
     * @param ls line separator.
     * @return ReportBuilderImpl.
     */
    public ReportBuilderImpl buildLineSeparator(LineSeparator ls) throws DocumentException {
        this.lineSeparator = ls;
        document.add(new Chunk(ls));
        return this;
    }

    /**
     * Gets new PdfPTable, add it to document and returns ReportBuilderImpl for next usages.
     *
     * @param table PdfPTable.
     * @return ReportBuilderImpl.
     */
    public ReportBuilderImpl buildTable(PdfPTable table) throws DocumentException {
        this.pdfTable = table;
        document.add(pdfTable);
        return this;
    }

    /**
     * Gets paragraph, add it to document and returns ReportBuilderImpl for next usages.
     *
     * @param p PdfPTable.
     * @return ReportBuilderImpl.
     */
    public ReportBuilderImpl buildParagraph(Paragraph p, int alignment) throws DocumentException {
        this.paragraph = p;
        p.setAlignment(alignment);
        document.add(paragraph);
        return this;
    }

    /**
     * Close document and returns Document for next usages.
     *
     * @return Document.
     */
    public Document getDocument() {
        this.document.close();
        return this.document;
    }

    /**
     * Gets clear document, period for reporting and returns configured doc.
     *
     * @param document document.
     * @param start    start date.
     * @param end      end date.
     * @return Document.
     */
    public abstract Document generateReport(Document document, LocalDate start, LocalDate end) throws IOException, DocumentException;

}
