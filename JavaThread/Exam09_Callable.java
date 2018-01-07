package JavaThread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam09_Callable extends Application {

	private TextArea textarea; 
	private ExecutorService excutorservice;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		BorderPane root = new BorderPane();
		root.setPrefSize(700, 500);
		
		textarea = new TextArea();
		
		textarea.setEditable(false);
		root.setCenter(textarea);
		
		FlowPane bottom = new FlowPane();
		bottom.setPrefSize(700, 50);
		bottom.setPadding(new Insets(20,20,20,20));
		Button createPool = new Button("create Thread Pool");
		createPool.setPrefSize(100, 30);
		createPool.setOnAction(event->{
			//excutorservice = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
			// newFixedThreadPool(4) : 기본 thread 수는 0개
			// thread가 필요하면 하나 만들어서 빌려줌. 사용 가능! 
			// 이 thread pool 안에 최대 4개의 thread만 존재
			// 시간 제한 존재하지 않음. 안에 maximum 4개까지 항상 존재
			// 사용한 Thread는 pool안에 지속적으로 존재 
			
			excutorservice = Executors.newCachedThreadPool();
			// newCachedThreadPool(): 기본 thread 수는 0개 
			// thread를 사용하겠다 하면 하나의 thread 생성 
			// 만약 또 필요하면 pool 안에 또 만든다.
			// 나중에 사용 끝나면 60초 대기하다가 사용이 없으면 삭제! 
		});						 
						
		Button createThreadBtn = new Button("start Thread");
		createThreadBtn.setPrefSize(100, 30);
		createThreadBtn.setOnAction(event->{
			
			Callable callable = new Callable<String>(){
				
				@Override
				public String call() throws Exception {
					Thread.sleep(30000);
					return "소리없는 아우성";
				}
			};
			// 인자로 callable 들어가면 결과값 존재. method가 execute() X submit()
			Future<String> future = excutorservice.submit(callable);
			// Future은 pending completion 객체 (지연 완료 객체) 
			try {
				String result = future.get(); //프로그램이 block
				System.out.println(result);
			} catch (InterruptedException e){
				e.printStackTrace();
			} catch (ExecutionException e){
				e.printStackTrace();
			}
			
		});
		
		Button stopPoolBtn = new Button("stop Thread");
		stopPoolBtn.setPrefSize(100, 30);
		stopPoolBtn.setOnAction(event->{
			//1.
			excutorservice.shutdown();
			// 현재 thread pool 안에서 수행되고 있는 모든 thread가 종료된 후에 
			// thread pool을 종료시킴 
			
			//2. excutorservice.shutdownNow();
			// 현재 thread pool 안에 수행되는 모든 thread를 interrupt() 처리 
			// 그 후에 thread pool을 종료
			
		});
		bottom.getChildren().add(createPool);
		bottom.getChildren().add(createThreadBtn);
		bottom.getChildren().add(stopPoolBtn);
		
		root.setBottom(bottom);
		
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch();
	}


}
