package JavaThread;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
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

public class Exam10_Completion extends Application {

	private TextArea textarea; 
	private ExecutorService executorservice; //일반 Thread Pool
	private ExecutorCompletionService<Integer> executorCompletionService;
	
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
			
			executorservice = Executors.newCachedThreadPool(); 
			// thread pool that has special purposes 
			executorCompletionService = new ExecutorCompletionService<Integer>(executorservice);
		});						 
						
		Button createThreadBtn = new Button("start Thread");
		createThreadBtn.setPrefSize(100, 30);
		createThreadBtn.setOnAction(event->{
			Random random = new Random();
			for(int i=0; i<10; i++){
				final int k = i+1;
				Callable callable = new Callable<Integer>() {

					@Override
					public Integer call() throws Exception {
						Thread.sleep(random.nextInt(3000));
						return k;
					}		
				};
				executorCompletionService.submit(callable);
			}
			
				Runnable runnable = new Runnable() {

				@Override
				public void run() {
					// 결과 취합 
					int sum = 0;
					for(int i=0; i<10; i++){
						try {
							Future<Integer> future = executorCompletionService.take();
							// take() thread pool 안의 완료된 thread의 future 객체를 가져오는 것.
							// 만약 완료된 것이 없다면 기다리기. 완료될 때까지.
						try {
							sum += future.get();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					System.out.println("결과값 : " + sum);
				}
			};
			executorservice.execute(runnable);
		});
		
		Button stopPoolBtn = new Button("stop Thread");
		stopPoolBtn.setPrefSize(100, 30);
		stopPoolBtn.setOnAction(event->{
			//1.
			executorservice.shutdown();
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
