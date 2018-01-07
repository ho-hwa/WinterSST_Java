package JavaIO.bookSearchFIle.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import JavaIO.bookSearchFIle.dto.BookDTO;
import JavaIO.bookSearchFIle.service.BookService;

public class BookController {
	
	private BookService service = new BookService();
	
	public void execute(String type, String keyword) {
		// 사용자가 어떤 걸 원하는 지 판단 
		if(type.equals("search")){
			List<BookDTO> list = service.searchBookByKeyword(keyword);
			// 서비스를 통해서 실행시킨 결과를 받아서 이걸 어떻게 처리할지 결정 
			// 결과를 받아온다. 
			
			// 이번에는 파일에 저장. 객체를 통으로 파일에 저장 
			File file = new File("/Users/purificationheo/Desktop/test/" + keyword + ".txt");
			try {
				FileOutputStream fos = new FileOutputStream(file);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				
				oos.writeObject(list);
				// 객체를 직렬화시켜서 스트림을 통해 전달
				// 직렬화 과정을 거쳐서 저장하려는 객체를 문자열 형태로 변환 
				// ; 객체를 스트림을 통해서 전달하려면! 객체를 직렬화 시켜야 함!
				// 다행스럽게 java에서 제공하는 몇몇개는 직렬화가 이미 가능한 형태로 제공 
				// String, ArrayList, 
				// 이 외에 사용자가 직접 생성한 클래스는 당연히 직렬화 안돼. 
				// 우리 예제에서는 BookDTO가 직렬화 불가능한 클래스. 가능하게 만들어야할 필요 있음. 
				// -> marshaling : 일반적인 용어 cf> java에서는 marshaling을 직렬화 기법을 통해서 구현 
				
				oos.close();
				fos.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			// 처리 결과를 File에 저장
			// 1. 파일 객체 만들기 - 검색어.txt
			// File file = new File("/Users/purificationheo/Desktop/test/" + keyword + ".txt");
			// 2. 파일 객체에 대한 출력 스트림 생성(PrintWriter) 
			/*try {
				PrintWriter pr = new PrintWriter(file);
				for(BookDTO dto : list){
					pr.println(dto.getBtilte());
				}
				pr.flush();
				pr.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
		}
	}
}
