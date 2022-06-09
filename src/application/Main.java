package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Main extends Application
{
	public void start(Stage primaryStage)
	{
		try
		{
			RootBorderPane root = new RootBorderPane();
			Scene scene = new Scene(root, 450, 200);
			
			primaryStage.setTitle("Kontaktanfrage");
			
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch (Exception e)
		{
			showAlert(e.getMessage());
		}
	}
	
	public static void showAlert(String message)
	{
		if (message != null) 
		{
			Alert alert = new Alert(AlertType.INFORMATION, message, ButtonType.OK);
			alert.setHeaderText(null); 
			alert.setTitle("Hinweis");
			alert.showAndWait(); 
		}
		else
		{
			Alert alert = new Alert(AlertType.INFORMATION, "Bitte mit \"Weiter\" bestaetigen", ButtonType.NEXT);
			alert.setHeaderText(null);
			alert.setTitle("Hinweis");
			alert.showAndWait();
		}
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
