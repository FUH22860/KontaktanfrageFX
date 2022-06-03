package application;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

public class RootBorderPane extends BorderPane {
	
	private MenuBar menuBar;
	private Menu mDatei;
	private MenuItem miBeenden;
	private RadioButton rbPersoenlich, rbTelefonisch, rbEgal;
	private CheckBox cbSofort;
	private TextField tfName, tfTelefonnummer;
	private Button btPruefen, btSpeichern;
	private ToggleGroup tgRadios;
	private GridPane gpZentrum;
	private HBox hbRadio;
	private FlowPane fpButton;
	
	public RootBorderPane() {
		initComponents();
		addComponents();
		addHandler();
	}

	private void initComponents() {
		
		menuBar = new MenuBar();
		mDatei = new Menu("Datei");
		miBeenden = new MenuItem("Beenden");
			
		tfName = new TextField();
			tfName.setPromptText("Bitte Ihren Namen eingeben");			
		tfTelefonnummer = new TextField();
			tfTelefonnummer.setPromptText("Bitte Ihre Telefonnummer eingeben");
					
		rbPersoenlich = new RadioButton("persoenlich");
		rbTelefonisch = new RadioButton("telefonisch");
		rbEgal = new RadioButton("egal");
		
		cbSofort = new CheckBox("(binnen eines Tages)");
		
		gpZentrum = new GridPane();
			gpZentrum.setHgap(5);
			gpZentrum.setVgap(5);
			gpZentrum.setPadding(new Insets(5));
			
		hbRadio = new HBox(5);
		
		fpButton = new FlowPane();
			fpButton.setHgap(5);
			fpButton.setPadding(new Insets(5));
		
		btPruefen = new Button("Pruefen");
		btSpeichern = new Button("Speichern und absenden");
			
		tgRadios = new ToggleGroup();
		
	}

	private void addComponents() {
		mDatei.getItems().addAll(miBeenden);
		menuBar.getMenus().addAll(mDatei);
		
		gpZentrum.add(new Label("Art der Kontaktaufnahme:"), 	0, 0);
		gpZentrum.add(new Label("Name:"), 						0, 1);
		gpZentrum.add(new Label("Telefonnummer:"), 				0, 2);
		gpZentrum.add(new Label("Sofortige Kontaktaufnahme:"), 	0, 3);
//		gpZentrum.add(new Label("(binnen eines Tages)"), 		2, 3);
		
		gpZentrum.add(hbRadio, 			1, 0);
		gpZentrum.add(cbSofort, 		1, 3);
		
		gpZentrum.add(tfName, 			1, 1);
		gpZentrum.add(tfTelefonnummer, 	1, 2);
		
//		GridPane.setColumnSpan(tfName, 3);
//		GridPane.setColumnSpan(tfTelefonnummer, 3);
		
		tgRadios.getToggles().addAll(rbPersoenlich, rbTelefonisch, rbEgal);
		
		hbRadio.getChildren().addAll(rbPersoenlich, rbTelefonisch, rbEgal);
		
		fpButton.getChildren().addAll(btPruefen, btSpeichern);
		
		setTop(menuBar);
		setCenter(gpZentrum);
		setBottom(fpButton);
		
	}

	private void addHandler() {
		miBeenden.setOnAction(event -> beenden());
		btPruefen.setOnAction(Event -> pruefen());
		// TODO Speichern
	}

	// ---------------------- handlers -----------------------
	
	private void beenden() {
		Platform.exit();
	}
	
	private void pruefen() {
		String name = tfName.getText();
		String tel = tfTelefonnummer.getText();
		
		try {
			if(name.isEmpty() | tel.isEmpty() | tgRadios.getSelectedToggle() == null) {
				Main.showAlert(AlertType.INFORMATION, "Pruefen Sie bitte die Eingaben: Art der Kontaktaufnahme, Name und Telefonnummer werden benoetigt");
			} else {
				if(rbPersoenlich.isSelected() & cbSofort.isSelected()) {
					Main.showAlert(AlertType.INFORMATION, "Sofortige persoenliche Kontaktaufnahmen sollten derzeit wegen langer Wartezeiten nicht abgesendet werden");
				} else {
					Main.showAlert(AlertType.INFORMATION, "Die Pruefung war erfolgreich. Die Eingaben der Kontaktanfrage sind in Ordnung");
				}
			}
			
		} catch (Exception e){
			Main.showAlert(AlertType.ERROR, e.getMessage());
		}
	}
	
}
