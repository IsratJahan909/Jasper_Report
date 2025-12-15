package com.abc.springRestCRUD.service;


import com.abc.springRestCRUD.dto.OrderDetails;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ReportService {

    public JasperPrint generateReport() throws JRException {
        // Load .jasper file (compiled .jrxml)
        JasperReport jasperReport = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/product_report.jrxml")
        );

        // Demo data
        List<OrderDetails> data = new ArrayList<>();
        data.add(new OrderDetails(101, new Date(), "Alice", "Laptop", 2, new BigDecimal("500"), new BigDecimal("1000")));
        data.add(new OrderDetails(101, new Date(), "Alice", "Mouse", 3, new BigDecimal("20"), new BigDecimal("60")));
        data.add(new OrderDetails(102, new Date(), "Bob", "Keyboard", 1, new BigDecimal("50"), new BigDecimal("50")));
        data.add(new OrderDetails(103, new Date(), "Charlie", "Monitor", 2, new BigDecimal("200"), new BigDecimal("400")));

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);

        // Parameters map (if needed)
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ReportTitle", "Demo Order Report");

        // Fill the report
        return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
}
}
