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

public class Exam07_Interrupt extends Application {

	private TextArea textarea; 
	private Thread t;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		BorderPane root = new BorderPane();
		root.setPrefSize(700, 500);
		
		textarea = new TextArea();
		
		//textarea를 read only로 설정
		textarea.setEditable(false);
		root.setCenter(textarea);
		
		// 화면 아래 부분에 버튼 2개 추가 
		FlowPane bottom = new FlowPane();
		bottom.setPrefSize(700, 50);
		bottom.setPadding(new Insets(20,20,20,20));
		Button startBtn = new Button("Thread start");
		startBtn.setPrefSize(100, 30);
		startBtn.setOnAction(event -> {
			//thread 생성해서 textarea에 thread의 이름을 1초마다 계속 찍는 것
			//thread를 만들기
			t = new Thread(new Runnable(){

				@Override
				public void run() {
					while(true){
						try {
							Thread.sleep(1000); //interrupt에 의해서 exception 발생 
							Platform.runLater(()->{
								textarea.appendText("Hi\n");
							});
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				
			});
			t.setDaemon(true);
			t.start();
		});
		
		Button stopBtn = new Button("Thread stop");
		stopBtn.setPrefSize(100, 30);
		stopBtn.setOnAction(event -> {
			// 실행되고 있는 thread 중지 
			// t.stop(); 이제 이렇게 사용하지 못함 
			t.interrupt(); 
			// t라는 thread에 interrupt 걸렸다고 표시하는 것 
			// 만약 interrupt가 표시된 thread가 block 상태로 들어가면 자동적으로 exception 발생
			
		});
		bottom.getChildren().add(startBtn);
		bottom.getChildren().add(stopBtn);
		
		root.setBottom(bottom);
		
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch();
	}


}
