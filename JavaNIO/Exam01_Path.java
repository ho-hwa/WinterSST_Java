package JavaNIO;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Exam01_Path {

	public static void main(String[] args) {
		
		// 파일객체 만들 때 File Class 이용 (Java IO)
		// File file = new File("/Users/purificationheo/Desktop/test/test.txt")
		Path path = Paths.get("/Users/purificationheo/Desktop/test/test.txt");
		
		System.out.println(path.getFileName());
		System.out.println(path.getParent().getFileName());
		
	}

}
