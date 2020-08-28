package com.proyect.main.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> {

	private String url;
	private Page<T> page;
	
	private int totalPaginas;
	
	private int numeroElementosPaginas;
	
	private int paginaActual;
	
	private List<PageItem> paginas;
	
	public PageRender(String url, Page<T> page) {
		super();
		this.url = url;
		this.page = page;
		this.paginas=new ArrayList<PageItem>();
		
		numeroElementosPaginas=page.getSize();
		totalPaginas=page.getTotalPages();
		paginaActual=page.getNumber()+1;
		int desde,hasta;
		if(totalPaginas<=numeroElementosPaginas) {
			desde=1;
			hasta=totalPaginas;
		}else {
			if(paginaActual<=numeroElementosPaginas/2) {
				desde=1;
				hasta=numeroElementosPaginas;
			}else if(paginaActual>=totalPaginas-numeroElementosPaginas/2) {
				desde=totalPaginas-numeroElementosPaginas+1;
				hasta=numeroElementosPaginas;
			}else {
				desde=paginaActual-numeroElementosPaginas/2;
				hasta=numeroElementosPaginas;
			}
		}
		
		for(int i=0;i<hasta;i++) {
			paginas.add(new PageItem(desde+i, paginaActual==desde+i));
		}
		
		
		
	}

	public int getTotalPaginas() {
		return totalPaginas;
	}

	public void setTotalPaginas(int totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	public int getPaginaActual() {
		return paginaActual;
	}

	public void setPaginaActual(int paginaActual) {
		this.paginaActual = paginaActual;
	}

	public List<PageItem> getPaginas() {
		return paginas;
	}

	public void setPaginas(List<PageItem> paginas) {
		this.paginas = paginas;
	}

	public String getUrl() {
		return url;
	}
	
	
	public boolean isFirst() {
		return page.isFirst();
	}
	public boolean isLast() {
		return page.isLast();
	}
	public boolean isHasNext() {
		return page.hasNext();
	}
	public boolean isHasPrevious() {
		return page.hasPrevious();
	}
	
	
	
	
	
	
	
	
	
	
}
