package JavaThread;

import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam03_Race extends Application{
	
	private TextField field1;
	private ProgressBar bar1;
	private ProgressIndicator indicator1;
	private TextField field2;
	private ProgressBar bar2;
	private ProgressIndicator indicator2;
	private TextField field3;
	private ProgressBar bar3;
	private ProgressIndicator indicator3;
	
	private TextArea textarea;
	private Button btn;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// 일단 화면을 5영역으로 분할.
		BorderPane root = new BorderPane();
		// 화면의 크기를 설정
		root.setPrefSize(700, 500);
		
		//중앙 영역에 위치할 하나의 판넬을 구성.
		FlowPane center = new FlowPane();
		//여백 설정하기 
		center.setPadding(new Insets(10,10,10,10));
		//패널의 크기 
		center.setPrefSize(700,450);
		//이름 입력 상자 
		field1 = new TextField();
		field1.setPrefSize(100, 60);
		//progressBar 생성
		bar1 = new ProgressBar();
		bar1.setPrefSize(450, 60);
		//progressIndicator 생성
		indicator1 = new ProgressIndicator();
		indicator1.setPrefSize(100, 60);
		
		center.getChildren().add(field1);
		center.getChildren().add(bar1);
		center.getChildren().add(indicator1);

		field2 = new TextField();
		field2.setPrefSize(100, 60);
		//progressBar 생성
		bar2 = new ProgressBar();
		bar2.setPrefSize(450, 60);
		//progressIndicator 생성
		indicator2 = new ProgressIndicator();
		indicator2.setPrefSize(100, 60);
		
		center.getChildren().add(field2);
		center.getChildren().add(bar2);
		center.getChildren().add(indicator2);
		
		field3 = new TextField();
		field3.setPrefSize(100, 60);
		//progressBar 생성
		bar3 = new ProgressBar();
		bar3.setPrefSize(450, 60);
		//progressIndicator 생성
		indicator3 = new ProgressIndicator();
		indicator3.setPrefSize(100, 60);
		
		center.getChildren().add(field3);
		center.getChildren().add(bar3);
		center.getChildren().add(indicator3);
		
		textarea = new TextArea();
		textarea.setPrefSize(500, 100);
		center.getChildren().add(textarea);
		
		btn = new Button("Start");
		btn.setPrefSize(300, 50);
		btn.setOnAction(event -> {
			// 버튼이 눌리면 thread 3개를 만들어서 각각 실행 
			Thread t1 = new Thread(new MyProgressRunnable(field1, bar1, indicator1));
			t1.start();
			Thread t2 = new Thread(new MyProgressRunnable(field2, bar2, indicator2));
			t2.start();
			Thread t3 = new Thread(new MyProgressRunnable(field3, bar3, indicator3));
			t3.start();
		});
		
		center.getChildren().add(btn);
		
		root.setCenter(center);
		
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		
		primaryStage.show();
	}
	
	public static void main(String[] args){
		//javaFX application Thread 생성
		//이 thread가 start() 호출
		Launch();
	}

	private static void Launch() {
		// TODO Auto-generated method stub
		
	}
	
}

class MyProgressRunnable implements Runnable {

	private TextField field;
	private ProgressBar bar;
	private ProgressIndicator indicator;
	
	//기본 생성
	public MyProgressRunnable(){
		
	}
	
	//사용할 자원을 받아서 필드에 세팅하는 생성자
	public MyProgressRunnable(TextField field, ProgressBar bar, ProgressIndicator indicator) {
		super();
		this.field = field;
		this.bar = bar;
		this.indicator = indicator;
	}
	
	@Override
	public void run() {
		// 여기에서 progress bar 제어 
		// Thread의 동작 방식 - 난수를 발생시켜서 해당 난수값을 progressBar에 누적시킴.
		// 만약 progressBar value >= 1.0, 해당 user name textarea에 출력
		// loop 반복적으로 수행. sleep을 이용해서 조금씩 쉬었다가 적용.
		// 난수를 발생시키는 경우 Random class 이용
		Random random = new Random();
		double sum = 0.0;
		
		while(true){
			sum += random.nextDouble() * 0.1; //실수값 뽑아냄. btw 0.00 ~ 1.0 추출 
			bar.setProgress(sum); //progress 값 설정
			indicator.setProgress(sum); //indicator 값 설정
			try {
				Thread.sleep(100);
			} catch(Exception e){
				e.printStackTrace();
			}
			if(sum >= 1.0){
				break;
			}
		} 
		
	}

	
	
}
