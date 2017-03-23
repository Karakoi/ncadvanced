package com.overseer.service.impl.report;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.overseer.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


/**
 * Implementation of <code>ReportBuilderImpl</code> interface, that specifies how
 * to generate reports for Office Manager.
 */
@Service
@RequiredArgsConstructor
@SuppressWarnings("PMD.UnusedPrivateField")
public class ManagerReportBuilderImpl extends ReportBuilderImpl {

    @Autowired
    private RequestService requestService;

    /**
     * {@inheritDoc}.
     */
    @Override
    public Document generateReport(Document document, LocalDate start, LocalDate end) {
        try {
            return createPDF(document)
                    .buildParagraph(new Paragraph("OFFICE MANAGER REPORTS"), Element.ALIGN_TOP)
                    .buildLineSeparator(new LineSeparator())
                    .buildLineSeparator(new LineSeparator())
//                     Implements manager report in pdf
                    .getDocument();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

}
