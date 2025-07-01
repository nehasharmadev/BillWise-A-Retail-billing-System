package com.example.Billing.System.Util;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.Billing.System.io.OrderResponse;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
 
@Component
public class MailUtility {

	public File generateSalesReport(List<OrderResponse> orders) throws Exception {
	    String filePath = "sales-report.pdf";

	    PdfWriter writer = new PdfWriter(filePath);
	    PdfDocument pdf = new PdfDocument(writer);
	    Document document = new Document(pdf);

	    document.add(new Paragraph("Sales Report").setFontSize(18).setBold());
	    for (OrderResponse order : orders) {
	        document.add(new Paragraph("Order ID: " + order.getOrderId()));
	        document.add(new Paragraph("Customer: " + order.getCustomerName()));
	        document.add(new Paragraph("Amount: Rs " + order.getGrandTotal()));
	        document.add(new Paragraph("Date: " + order.getCreatedAt()));
	        document.add(new Paragraph("--------"));
	    }

	    document.close();
	    return new File(filePath);
	}

}
