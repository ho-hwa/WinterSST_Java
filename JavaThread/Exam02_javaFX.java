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

//javaFX를 이용하여 화면에 창 띄우기 
//Application이라는 클래스가 추상 클래스 -> 추상 메소드가 있다!
public class Exam02_javaFX extends Application{

	private TextArea textarea;
	private Button startBtn;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//화면 구성을 작성 
		//화면을 동서남북중앙 5개의 영역으로 분할할 때 사용하는 클래스 존재
		System.out.println(Thread.currentThread().getName());
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
			Thread t = new Thread(new Runnable(){
				
				@Override
				public void run() {
					for(int i=0; i<10; i++){
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						// 화면에 있는 component(textare)를 제어하려면 또 다시 thread 이용해야해.
						Platform.runLater(()->{
							textarea.appendText("소리없는 아우성\n");
						});
						}
					}
			});
			
				t.start();
				
			Thread t1 = new Thread(new Runnable(){
					
					@Override
					public void run() {
						for(int i=0; i<10; i++){
							//현재 동작하는 Thread를 1초동안 sleep
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							Platform.runLater(()->{
								textarea.appendText("두번째 thread \n");
							});
						}
					}
			});
			
			t1.start(); 
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
