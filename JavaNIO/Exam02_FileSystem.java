package JavaNIO;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

public class Exam02_FileSystem {

	public static void main(String[] args) {
		// 우리가 사용하는 OS가 가지고 있는 파일시스템 정보를 얻어낼 수 있다. 
		FileSystem fileSystem = FileSystems.getDefault();
		
		for(FileStore store : fileSystem.getFileStores()){
			System.out.println("파일시스템 타입: " + store.type());
			try {
				System.out.println("전체 용량: " + store.getTotalSpace());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
