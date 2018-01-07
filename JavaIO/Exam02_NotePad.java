package JavaIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Exam02_NotePad extends Application {

	private TextArea textarea; 
	private File file;
	
	private void printMsg(String msg){
		 Platform.runLater(()->{
			 textarea.appendText(msg +"\n");
		 });
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		BorderPane root = new BorderPane();
		root.setPrefSize(700, 500);
		
		textarea = new TextArea();
		root.setCenter(textarea);
		
		FlowPane bottom = new FlowPane();
		bottom.setPrefSize(700, 50);
		bottom.setPadding(new Insets(20,20,20,20));
		Button openFileBtn = new Button("Open file");
		openFileBtn.setPrefSize(100, 30);
		openFileBtn.setOnAction(event->{
			textarea.clear();
			
			 FileChooser fileChooser = new FileChooser();
			 fileChooser.setTitle("Choose file you want");
			 // 선택한 파일에 대한 객체 하나 획득 
			 file = fileChooser.showOpenDialog(primaryStage);
			 try {
				 FileReader fr = new FileReader(file);
				 BufferedReader br = new BufferedReader(fr);
				 String line = null;
				 
				 while((line=br.readLine()) != null){
					 printMsg(line);
				 }
				 // 내가 사용한 자원 해제 
				 br.close();
				 fr.close();
			 } catch (FileNotFoundException e){
				 e.printStackTrace();
			 } catch (IOException e) {
				e.printStackTrace();
			}
		});						 
						
		Button saveBtn = new Button("Save");
		saveBtn.setPrefSize(100, 30);
		saveBtn.setOnAction(event->{
			// 지금 사용하고 있는 파일에 대한 Stream 열어서 데이터 write 
			// Stream Used generally to print String
			try {
				PrintWriter pr = new PrintWriter(file);
				pr.print(textarea.getText()); 
				// 여기서 정말로 쓰기 작업이 일어나느 것 아니야.
				pr.flush();
				//현재 stream이 가지고 있는 저장공간에 있는 data 실제로 write 
				pr.close(); // stream의 기본 - 반드시 close! 후속처리
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Success save file");
				alert.show();
				
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}
		});
		
		Button saveAsBtn = new Button("Save as");
		saveAsBtn.setPrefSize(100, 30);
		saveAsBtn.setOnAction(event->{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save as");
			File file = fileChooser.showSaveDialog(primaryStage);
			
			try {
				PrintWriter pr = new PrintWriter(file);
				pr.print(textarea.getText());
				pr.flush();
				pr.close();

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Success save as another file");
				alert.show();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		bottom.getChildren().add(openFileBtn);
		bottom.getChildren().add(saveBtn);
		bottom.getChildren().add(saveAsBtn);
		
		root.setBottom(bottom);
		
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch();
	}


}
