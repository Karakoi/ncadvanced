package com.overseer.service.impl.report.builder;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.LineSeparator;

/**
 * The <code>ReportDocumentBuilder</code> abstract class represents Builder pattern for creating report in PDF format.
 */
@SuppressWarnings("PMD.UnusedPrivateField")
public class ReportDocumentBuilder {

    private final Document document;

    public ReportDocumentBuilder(Document document) {
        this.document = document;
        this.document.open();
    }

    /**
     * Gets new image, add it to document and returns ReportDocumentBuilder for next usages.
     *
     * @param img pdf document.
     * @return ReportDocumentBuilder.
     */
    public ReportDocumentBuilder addImage(Image img, int alignment) throws DocumentException {
        img.setAlignment(alignment);
        document.add(img);
        return this;
    }

    /**
     * Gets new list, add it to document and returns ReportDocumentBuilder for next usages.
     *
     * @param list pdf document.
     * @return ReportDocumentBuilder.
     */
    public ReportDocumentBuilder addList(List list) throws DocumentException {
        document.add(list);
        return this;
    }

    /**
     * Gets new line separator, add it to document and returns ReportDocumentBuilder for next usages.
     *
     * @param ls line separator.
     * @return ReportDocumentBuilder.
     */
    public ReportDocumentBuilder addLineSeparator(LineSeparator ls) throws DocumentException {
        document.add(new Chunk(ls));
        return this;
    }

    /**
     * Gets new PdfPTable, add it to document and returns ReportDocumentBuilder for next usages.
     *
     * @param table PdfPTable.
     * @return ReportDocumentBuilder.
     */
    public ReportDocumentBuilder addTable(PdfPTable table) throws DocumentException {
        document.add(table);
        return this;
    }

    /**
     * Gets paragraph, add it to document and returns ReportDocumentBuilder for next usages.
     *
     * @param paragraph PdfPTable paragraph.
     * @return ReportDocumentBuilder.
     */
    public ReportDocumentBuilder addParagraph(Paragraph paragraph, int alignment) throws DocumentException {
        paragraph.setAlignment(alignment);
        document.add(paragraph);
        return this;
    }

    /**
     * Close document and returns Document for next usages.
     *
     * @return Document.
     */
    public Document buildDocument() {
        return this.document;
    }
}
