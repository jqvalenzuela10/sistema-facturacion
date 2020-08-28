package com.proyect.main.view.pdf;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;


import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import com.proyect.main.models.entity.Factura;

@Component("factura/pdf")
public class ClientesPdf extends AbstractPdfView{
	String colorCeldaHeader="#b8daff";
	String colorTitle="#1e8fa1";
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		float fntSize=10f;
		String fechaInicio=request.getParameter("fecha1");
		String fechaFin=request.getParameter("fecha2");
		
		@SuppressWarnings("unchecked")
		List<Factura> lista=(List<Factura>) model.get("facturasBetweenDate");
		
		
		PdfPTable tablaHeader=new PdfPTable(1);
		
		PdfPCell cellHeader=new PdfPCell(new Phrase("Listado De Facturas                                         "+fechaInicio+"   |   "+fechaFin,FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
		cellHeader.setBackgroundColor(Color.decode(colorTitle));
		cellHeader.setPadding(8f);
		cellHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cellHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
		tablaHeader.addCell(cellHeader);
		
		
		PdfPTable tabla=new PdfPTable(5);
		tabla.setSpacingAfter(20);
		
		Stream.of("ID", "Fecha", "Descripcion","Cliente","Mesa")
        .forEach(headerTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(Color.LIGHT_GRAY);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setPhrase(new Phrase(headerTitle));
            header.setPadding(5f);
            tabla.addCell(header);
        });
		
		
		
		
		
		float[] columnWidths = new float[]{10f, 20f, 40f, 15f,15f};
		tabla.setWidths(columnWidths);
		
		lista.forEach(factura->{
			
			String idFactura=factura.getId().toString();
			
			String fechaFactura=factura.getCreateAt().toString();
			
			String descripcionFactura=factura.getDescripcion();
			
			String nombreClienteFactura=factura.getCliente()!=null?factura.getCliente().getNombre():"no hay cliente";
			
			String nombreMesa=factura.getMesa().getDescripcion();
			
			
			PdfPCell cell=new PdfPCell(new Phrase(idFactura,FontFactory.getFont(FontFactory.COURIER, fntSize)));
			StylesCellFactura(cell,tabla);
		
			PdfPCell cell1=new PdfPCell(new Phrase(fechaFactura,FontFactory.getFont(FontFactory.COURIER, fntSize)));
			StylesCellFactura(cell1,tabla);
			
			PdfPCell cell2=new PdfPCell(new Phrase(descripcionFactura,FontFactory.getFont(FontFactory.COURIER, fntSize)));
			StylesCellFactura(cell2,tabla);
			
			
			PdfPCell cell3=new PdfPCell(new Phrase(nombreClienteFactura,FontFactory.getFont(FontFactory.COURIER, fntSize)));
			StylesCellFactura(cell3,tabla);
			
				
			PdfPCell cell4=new PdfPCell(new Phrase(nombreMesa,FontFactory.getFont(FontFactory.COURIER, fntSize)));
			StylesCellFactura(cell4,tabla);
			
			
			/*detalle Factura*/
			Stream.of("","Producto","Cantidad","Precio","Importe")
	        .forEach(headerTitlee -> {
	            PdfPCell headerDetalle = new PdfPCell();
	            setStylesHeaderDetails(headerDetalle,tabla,headerTitlee);
	        });
			
			
			factura.getItems().forEach(e->{
				
				PdfPCell cellDetalle0=new PdfPCell();
				setStylesCellDetails(cellDetalle0,tabla);
				
				PdfPCell cellDetalle1=new PdfPCell(new Phrase(e.getProducto().getNombre(),FontFactory.getFont(FontFactory.COURIER, fntSize)));
				setStylesCellDetails(cellDetalle1,tabla);
				
				PdfPCell cellDetalle2=new PdfPCell(new Phrase(e.getCantidad().toString(),FontFactory.getFont(FontFactory.COURIER, fntSize)));
				setStylesCellDetails(cellDetalle2,tabla);
				
				PdfPCell cellDetalle3=new PdfPCell(new Phrase(e.getProducto().getPrecio().toString(),FontFactory.getFont(FontFactory.COURIER, fntSize)));
				setStylesCellDetails(cellDetalle3,tabla);
				
				PdfPCell cellDetalle4=new PdfPCell(new Phrase(e.calcularImpote().toString(),FontFactory.getFont(FontFactory.COURIER, fntSize)));
				setStylesCellDetails(cellDetalle4,tabla);
				
			});
			
			/*-------------*/
		});
		
		
		document.add(tablaHeader);
		document.add(tabla);
	}

	void StylesCellFactura(PdfPCell celda,PdfPTable tabla) {
		celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setBackgroundColor(Color.decode(colorCeldaHeader));
		celda.setPadding(4f);
		tabla.addCell(celda);
	}
	
	void setStylesCellDetails(PdfPCell celda,PdfPTable tabla) {
		celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setPadding(4f);
		tabla.addCell(celda);
	}
	
	void setStylesHeaderDetails(PdfPCell celda,PdfPTable tabla,String headerTitle) {
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);

		celda.setPhrase(new Phrase(headerTitle,FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10f)));
		celda.setPadding(3f);
        tabla.addCell(celda);
	}

}
