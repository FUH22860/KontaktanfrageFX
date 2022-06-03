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
			primaryStage.setScene(scene);
				primaryStage.setTitle("Kontaktanfrage");
			primaryStage.show();
		}
		catch (Exception e)
		{
			showAlert(AlertType.ERROR, e.getMessage());
		}
	}

	public static void showAlert(AlertType alertType, String message)
	{
		if (message != null)
		{
			Alert alert = new Alert(alertType, message, ButtonType.OK);
			alert.setHeaderText(null);
			if (alertType == AlertType.WARNING)
				alert.setTitle("Warnung");
			else
				alert.setTitle("Hinweis-Meldung");
			alert.showAndWait();
		}
		else
		{
			Alert alert = new Alert(alertType, "Bitte waehlen Sie \"Weiter\" aus", ButtonType.NEXT);
			alert.setHeaderText(null);
			alert.setTitle("Hinweis-Meldung");
			alert.showAndWait();
		}
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}
