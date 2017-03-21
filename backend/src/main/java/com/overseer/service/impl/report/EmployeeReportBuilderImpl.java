package com.overseer.service.impl.report;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Implementation of <code>ReportBuilderImpl</code> interface, that specifies how
 * to generate reports for Employee.
 */
@Service
@AllArgsConstructor
public class EmployeeReportBuilderImpl extends ReportBuilderImpl {

    @Override
    public Paragraph createParagraph(String title) {
        Paragraph paragraph = super.createParagraph(title);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        return paragraph;
    }


    @Override
    public Document generatePDFReport() throws IOException, DocumentException {
        Document document = buildPDFTemplate();
        document.add(createParagraph("List of own requests"));
        return document;
    }

    @Override
    public void generateExcelReport() {
        buildExcelTemplate();
    }

}
