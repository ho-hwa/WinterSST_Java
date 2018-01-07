package JavaIO.bookSearchFIle.dto;

import java.io.Serializable;

// database에 있는 책 한권에 대한 정보를 담을 수 있는 객체 생성을 위한 클래스 

public class BookDTO implements Serializable{
	
	private String btilte; //책 제목
	private String bauthor; //책 저자 
	private int bprice; //책 가격 
	
	public BookDTO(){
		
	}

	public BookDTO(String btilte, String bauthor, int bprice) {
		super();
		this.btilte = btilte;
		this.bauthor = bauthor;
		this.bprice = bprice;
	}

	public String getBtilte() {
		return btilte;
	}

	public void setBtilte(String btilte) {
		this.btilte = btilte;
	}

	public String getBauthor() {
		return bauthor;
	}

	public void setBauthor(String bauthor) {
		this.bauthor = bauthor;
	}

	public int getBprice() {
		return bprice;
	}

	public void setBprice(int bprice) {
		this.bprice = bprice;
	}
	
	}

