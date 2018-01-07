package JavaNIO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Exam03_Files {

	public static void main(String[] args) {
		// 특정 path 지정 
		Path path =  Paths.get("assets/test.txt");
		
		System.out.println("폴더 여부: " + Files.isDirectory(path));
		System.out.println("파일 여부: " + Files.isRegularFile(path));
		try {
			System.out.println("파일 크기: " + Files.size(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			System.out.println("마지막 수정일: " + Files.getLastModifiedTime(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 
}
