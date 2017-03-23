package com.overseer.service.impl.report.view;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.util.Arrays;

/**
 * Implementation of Builder pattern for {@link PdfPTable} instance construct.
 */
public class PdfPTableBuilder {

    private PdfPTable pdfPTable;

    public PdfPTableBuilder(int columns, float widthPercentage, int spacing) {
        this.pdfPTable = new PdfPTable(columns);
        this.pdfPTable.setWidthPercentage(widthPercentage);
        this.pdfPTable.setSpacingBefore(spacing);
    }

    /**
     * Added {@link PdfPCell} to {@link PdfPTable} with selected background color and font.
     *
     * @param title cell title
     * @param bgColor background color
     * @param font cell text font
     * @return this builder instance
     */
    public PdfPTableBuilder addPdfPCell(String title, BaseColor bgColor, Font font) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(bgColor);
        cell.setPhrase(new Phrase(title, font));
        pdfPTable.addCell(cell);
        return this;
    }

    /**
     * Added multiple {@link PdfPCell} to {@link PdfPTable} with selected background color and font.
     *
     * @param titles cells titles
     * @param bgColor background color
     * @param font cell text font
     * @return this builder instance
     */
    public PdfPTableBuilder addPdfPCells(BaseColor bgColor, Font font, String... titles) {
        Arrays.stream(titles).forEach(t -> addPdfPCell(t, bgColor, font));
        return this;
    }

    /**
     * Added single {@link PdfPCell} to {@link PdfPTable} with selected title.
     *
     * @param title cell title
     * @return this builder instance
     */
    public PdfPTableBuilder addCell(String title) {
        pdfPTable.addCell(title);
        return this;
    }

    /**
     * Added double {@link PdfPCell} to {@link PdfPTable} with selected titles.
     *
     * @param leftTitle left cell title
     * @param rightTitle right cell title
     * @return this builder instance
     */
    public PdfPTableBuilder addDoubleCell(String leftTitle, String rightTitle) {
        pdfPTable.addCell(leftTitle);
        pdfPTable.addCell(rightTitle);
        return this;
    }

    /**
     * Build final {@link PdfPCell} instance.
     * @return generated table.
     */
    public PdfPTable build() {
        return pdfPTable;
    }

}
