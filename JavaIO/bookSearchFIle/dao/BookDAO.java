package JavaIO.bookSearchFIle.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import JavaIO.bookSearchFIle.dto.BookDTO;

public class BookDAO {
	public List<BookDTO> select(String keyword){
		// database 처리해서 결과를 service에게 전달
		// DB 총 6단계
		List<BookDTO> result = new ArrayList<BookDTO>();
		
		try {
		// 1. Driver Loading
			Class.forName("com.mysql.jdbc.Driver");
		// 2. DB 연결 
			String jdbcUrl = "jdbc:mysql://10.16.138.248:3306/library" + "?useUnicode=true&characterEncoding=UTF-8";
			String id = "java";
			String pw = "java";
			Connection con = DriverManager.getConnection(jdbcUrl, id, pw);
			System.out.println("Success connect");
		// 3. SQL 생성 
			String sql = "select btitle, bauthor, bprice from book" + " where btitle like?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
		// 4. 실행 
			ResultSet rs = pstmt.executeQuery();
		// 5. 결과처리 
			while(rs.next()){
				BookDTO dto = new BookDTO();
				dto.setBtilte(rs.getString("btitle"));
				dto.setBauthor(rs.getString("bauthor"));
				dto.setBprice(rs.getInt("bprice"));
				result.add(dto);
			}
			
		// 6. 자원해제
			rs.close();
			pstmt.close();
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
} 
