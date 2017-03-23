package com.overseer.service.impl.report.resolver;

import com.overseer.service.impl.report.view.RequestReportPdfView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

/**
 * Implementation of {@link ViewResolver} for PDF resolver.
 */
public class PdfViewResolver implements ViewResolver {

    @Override
    public View resolveViewName(String s, Locale locale) throws Exception {
        RequestReportPdfView view = new RequestReportPdfView();
        return view;
    }
}
