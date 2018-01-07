package JavaThread;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam04_Synchronize extends Application {
	private TextArea textarea;
	private Button startBtn;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//화면 구성을 작성 
		//화면을 동서남북중앙 5개의 영역으로 분할할 때 사용하는 클래스 존재
		BorderPane root = new BorderPane();
		root.setPrefSize(700, 500);
		
		//글상자 하나 만듦 
		textarea = new TextArea();
		
		root.setCenter(textarea);

		//버튼들이 위치할 패널하나 생성
		FlowPane bottom = new FlowPane();
		bottom.setPrefSize(700, 50);
		//bottom에 여백 설정 
		bottom.setPadding(new Insets(10,10,10,10));

		startBtn = new Button("Thread 시작");
		startBtn.setPrefSize(100, 30);
		startBtn.setOnAction(event -> {
			// 공유 객체 생성
			SharedObject obj = new SharedObject(textarea);
			Thread t1 = new Thread(new SynchRunnable(obj,100));
			Thread t2 = new Thread(new SynchRunnable(obj,200));
			t1.start();
			t2.start();
		});
		
		// 하위 패널에 버튼을 부착.
		bottom.getChildren().add(startBtn);
		
		//남쪽 부분에 지금 만든 패널을 가져다 붙임. 
		root.setBottom(bottom);
		
		//장면을 만들어야해.
		Scene scene = new Scene(root);
		//해당 장면에 창을 붙여서 우리 눈에 보이게 만듦 
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		Launch();
		System.out.println(Thread.currentThread().getName());
	}

	private static void Launch() {
		
	}

}
//Thread에 의해서 공유될 객체를 만들기 위한 클래스

class SharedObject {
	
	private int number;
	private TextArea textarea;
	
	public SharedObject(TextArea textarea) {
		this.textarea = textarea;
	}
	
	public int getNumber(){
		return number;
	}
	
	//JAVA에서는 이런 공유자원에 대한 접근을 키워드 하나로 제어 가능
	//Synchronized keyword로 제어
	
	public void setNumber(int number) {
		this.number = number;
		// thread가 공유객체의 setter를 이용해서 값을 세팅할 예
		try {
			Thread.sleep(2000);
			// 2초 기다렸다가 textarea 영역에 number 값 출
			Platform.runLater(()->{
				textarea.appendText("숫자: " + number + "\n");
			});
		} catch (Exception e){
			e.printStackTrace();
			
		}
	}


}

//  Runnable interface를 구현한 class 작성
class SynchRunnable implements Runnable {

	private SharedObject sharedObject;
	private int number;
	
	public SynchRunnable() {
		
	}
	public SynchRunnable(SharedObject sharedObject, int number) {
		super();
		this.sharedObject = sharedObject;
		this.number = number;
	}
	// 이 클래스로 thread 생성. 당연히 공유 객체에 대한 reference는 field로 등장.
	@Override
	public void run() {
		sharedObject.setNumber(number);
	}
	
}
