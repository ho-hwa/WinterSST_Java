package JavaNIO;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;


public class Exam04_WatchService extends Application {

		private TextArea textarea; 
		private ExecutorService executorService; // 특정 폴더 감시하는 Thread 사용해야 하니까 Thread Pool 이용. 
		private WatchService watchService; // 특정 폴더를 감시하는 감시자 
		
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
			Button startBtn = new Button("Start watch");
			startBtn.setPrefSize(100, 30);
			startBtn.setOnAction(event->{
				//WatchService 생성. 폴더 감시자 생성 
				try {
					executorService = Executors.newFixedThreadPool(2);
					watchService = FileSystems.getDefault().newWatchService();
				//어떤 폴더를 감시할지 Path를 설정 
					Path path = Paths.get("assets");
					path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, 
												StandardWatchEventKinds.ENTRY_DELETE,
												StandardWatchEventKinds.ENTRY_MODIFY);
					
					Runnable runnable = new Runnable(){

						@Override
						public void run() {
							try {
								while(true){
									// 감시하는 폴더에 대해 사건이 발생할 때까지 block (멈춰있다는 의미) 
									// 만약 사건이 발생하면 사건에 대한 정보를 가지고 key 생성 
									WatchKey key = watchService.take();
									// key 객체를 이용해서 특정 사건에 대한 정보 획득 
									List<WatchEvent<?>> list = key.pollEvents();
									// 여러 event가 발생할 수 있기 때문에 list로 받아 
									for(WatchEvent<?> event : list){
										// 지금 발생한 이벤트에 대한 파일 경로 객체 
										Path path = (Path)event.context();
										// 지금 발생한 이벤트의 종류 - 생성, 수정, 삭제 
										Kind<?> kind = event.kind();
										if( kind == StandardWatchEventKinds.ENTRY_CREATE){
											Platform.runLater(()->{
												textarea.appendText(path.getFileName() + "생성\n");
											});
										}
										if( kind == StandardWatchEventKinds.ENTRY_DELETE){
											Platform.runLater(()->{
												textarea.appendText(path.getFileName() + "삭제\n");
											});
										}
										if( kind == StandardWatchEventKinds.ENTRY_MODIFY){
											Platform.runLater(()->{
												textarea.appendText(path.getFileName() + "수정\n");
											});
										}
									}
									boolean valid = key.reset();
									if(!valid) break;
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					};
					executorService.execute(runnable);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			});						 
							
			Button stopBtn = new Button("Stop watch");
			stopBtn.setPrefSize(100, 30);
			stopBtn.setOnAction(event->{
				
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
