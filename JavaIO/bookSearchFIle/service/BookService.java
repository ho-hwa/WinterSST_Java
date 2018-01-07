package JavaIO.bookSearchFIle.service;

import java.util.List;

import JavaIO.bookSearchFIle.dao.BookDAO;
import JavaIO.bookSearchFIle.dto.BookDTO;

public class BookService {

	public List<BookDTO> searchBookByKeyword(String keyword) {
		// 로직 작성 
		// 별 다른 로직 없음. 대신 DB 처리만 존재. 
		BookDAO dao = new BookDAO();
		dao.select(keyword);
		return dao.select(keyword);
	}

}
