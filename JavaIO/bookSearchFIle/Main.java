package JavaIO.bookSearchFIle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import JavaIO.bookSearchFIle.controller.BookController;
import JavaIO.bookSearchFIle.dto.BookDTO;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {
	
	TextArea textarea;
	TextField textfield;
	BookController controller = new BookController();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		BorderPane root = new BorderPane();
		root.setPrefSize(700, 500);
		
		textarea = new TextArea();
		root.setCenter(textarea);
		
		FlowPane bottom = new FlowPane(); 
		bottom.setPadding(new Insets(20, 20, 20, 20));
		textfield = new TextField();
		textfield.setPrefSize(200,30);
		
		Button searchBtn = new Button("Search and save as file");
		searchBtn.setPrefSize(200, 30);
		searchBtn.setOnAction(event->{
			controller.execute("search", textfield.getText());
		});
		
		Button openBtn = new Button("open file");
		openBtn.setPrefSize(200, 30);
		openBtn.setOnAction(event -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select File");
			File file = fileChooser.showOpenDialog(primaryStage);
			
			try {
			/*	FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String line = null;
				while((line=br.readLine())!=null){
					final String k = line;
					Platform.runLater(()->{
						textarea.appendText(k + "\n");
					}); 
				}*/ 
			 FileInputStream fis = new FileInputStream(file);
			 ObjectInputStream ois = new ObjectInputStream(fis);
			 
			try {
				List<BookDTO> list = (List<BookDTO>)ois.readObject();
				 for(BookDTO dto: list){
					 Platform.runLater(()->{
						 textarea.appendText(dto.getBtilte() + "\n");
					 });
				 };
			}
			 catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		bottom.getChildren().add(textfield);
		bottom.getChildren().add(searchBtn);
		bottom.getChildren().add(openBtn);
		
		root.setBottom(bottom);
		Scene SCENE = new Scene(root);
		primaryStage.setScene(SCENE);
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		// 프로그램 시작하면 화면에 사용자가 이용할 창 띄우기 
		launch();
	}

}
