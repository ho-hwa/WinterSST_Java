package JavaIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Exam01_IOBasic {

	public static void main(String[] args) {
		// 도스 창에서 한 줄을 입력받아서 그대로 출력!
		// 우리한테 기본적으로 제공되는 것 - 표준입/출력에 대한 stream 기본 제공 
		// System.out -> 제공하는 모니터에 대한 출력 스트림
		System.out.println("output");
		// System.in -> 제공하는 키보드에 대한 입력 스트림 but 사용 어려움
		// Stream 결합을 통해 더 나은 형태의 새로운 스트림 사용
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		try {
			String msg = br.readLine(); // 입력대기 
			System.out.println("input : " + msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
