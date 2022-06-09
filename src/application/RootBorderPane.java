package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

public class RootBorderPane extends BorderPane
{
	private MenuBar menubar;
	private Menu mDatei;
	private MenuItem miBeenden;

	private Label lbRadios, lbName, lbTelefon, lbSofortKontakt;
	private RadioButton rbPersoenlich, rbTelefonisch, rbEgal;
	private TextField tfName, tfTelefonNr;
	private ToggleGroup toggleGroup;
	private Button btPruefen, btAbsenden;
	private CheckBox cbSofort;

	private HBox hBoxRadios;
	private GridPane gridPaneMitte;
	private FlowPane flowPaneUntenButtons;	
	
	public RootBorderPane()
	{
		initComponents();
		addComponents();
		addHandlers();
	}

	private void initComponents()
	{
		menubar = new MenuBar();
		mDatei = new Menu("Datei");
		miBeenden = new MenuItem("Beenden");
		
		lbRadios = new Label("Art der Kontaktaufnahme:");		
		lbName = new Label("Name:");
		lbTelefon = new Label("Telefonnummer:");
		lbSofortKontakt = new Label("Sofortige Kontaktaufnahme:");
		
		tfName = new TextField();
			tfName.setPromptText("Bitte Ihren Namen eingeben...");
		tfTelefonNr = new TextField();
			tfTelefonNr.setPromptText("Bitte Ihre Telefonummer eingeben...");
		
		rbPersoenlich = new RadioButton("persoenlich");
		rbTelefonisch = new RadioButton("telefonisch");
		rbEgal = new RadioButton("egal");
		
		cbSofort = new CheckBox("(binnen eines Tages)");
		
		toggleGroup = new ToggleGroup();
		
		btPruefen = new Button("Pruefen...");
		btAbsenden = new Button("Speichern und absenden...");
		
		hBoxRadios = new HBox(5);
		
		gridPaneMitte = new GridPane();
			gridPaneMitte.setVgap(5);
			gridPaneMitte.setHgap(5);
			gridPaneMitte.setPadding(new Insets(5));			

		flowPaneUntenButtons = new FlowPane();
			flowPaneUntenButtons.setHgap(5);
			flowPaneUntenButtons.setPadding(new Insets(5));
	}
	
	private void addComponents()
	{
		menubar.getMenus().addAll(mDatei);
		mDatei.getItems().addAll(miBeenden);

		hBoxRadios.getChildren().addAll(rbPersoenlich, rbTelefonisch, rbEgal);
		
		toggleGroup.getToggles().addAll(rbPersoenlich, rbTelefonisch, rbEgal);
		
		gridPaneMitte.add(lbRadios, 		0, 0);		
		gridPaneMitte.add(lbName, 	0, 1);
		gridPaneMitte.add(lbTelefon, 	0, 2);
		gridPaneMitte.add(lbSofortKontakt, 	0, 3);

		gridPaneMitte.add(hBoxRadios,	 	1, 0);		
		gridPaneMitte.add(tfName, 	1, 1);
		gridPaneMitte.add(tfTelefonNr, 	1, 2);
		gridPaneMitte.add(cbSofort, 	1, 3);		
		
		flowPaneUntenButtons.getChildren().addAll(btPruefen, btAbsenden);
		
		setTop(menubar);
		setCenter(gridPaneMitte);
		setBottom(flowPaneUntenButtons);
	}

	private void addHandlers()
	{
		miBeenden.setOnAction(event -> beenden());
		btPruefen.setOnAction(event -> pruefen());
		btAbsenden.setOnAction(event -> absenden());
	}
	// -------------------------- Handler - Methoden -----------------------------
	private void pruefen()
	{
		String text;
		if (checkEingaben() && !checkSofortPersoenlich())
			text = "Die Pruefung war erfolgreich. Die Eingaben der Kontaktanfrage sind in Ordnung."; 
		else
		{
			StringBuilder sb = new StringBuilder("Folgende Eingaben fehlen:\n");
			if (!checkEingaben())
			{
				if (!rbPersoenlich.isSelected()&&!rbTelefonisch.isSelected()&&!rbEgal.isSelected())
					sb.append("Art der Kontaktaufnahme\n");
				if (tfName.getText().isEmpty())
					sb.append("Name\n");
				if (tfTelefonNr.getText().isEmpty())
					sb.append("Telefonnummer");
				text = sb.toString();
			}
			else
				text = "Sofortige persoenliche Kontaktaufnahmen sollten derzeit wegen langer Wartezeiten nicht abgesendet werden"; 
		}
		Main.showAlert(text);
	}
	
	private void absenden()
	{
		if (checkEingaben())
		{
			FileChooser fc = new FileChooser();
			File selected = fc.showSaveDialog(null);
			String textSpeichern;
			if (selected != null)
			{
				String pfadDateiName = selected.getAbsolutePath();
				speichern(selected);
				textSpeichern = "Ihre Kundenanfrage wurde unter " + pfadDateiName + 
						        " gespeichert\n";
			}
			else
				textSpeichern = "Ihre Kundenanfrage wurde nicht gespeichert\n";
			Main.showAlert(textSpeichern);			
			
			StringBuilder sb = new StringBuilder(tfName.getText()).
								append(", danke fuer Ihre Anfrage!\n"). 
								append("Unser Kundenservice wird sich bei Ihnen melden\n");
		    if (cbSofort.isSelected())
		    		sb.append("Derzeit gibt es laengere Wartezeiten, danke f�r Ihre Geduld!");
			Main.showAlert(sb.toString());
			Platform.exit();
		}
		else
			Main.showAlert("Pruefen Sie bitte die Eingaben: Art der Kontaktaufnahme, Name und Telefonnummer werden benoetigt");
	}
	
	private void speichern(File selected)
	{
		if (selected != null)
		{
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(selected)))
			{
				// Rosa Munde;01/545 45 45
				String separator = ";";
				bw.write(tfName.getText() + separator + tfTelefonNr.getText());
			} catch (IOException e) {
				Main.showAlert(e.getMessage());
			}
		}
		else
			Main.showAlert("Fehler beim Speichern: null-Referenz fuer Datei erhalten");
	}

	private void beenden()
	{
		Platform.exit();
	}
	// -------------------------- Hilfsmethoden -----------------------------------
	private boolean checkSofortPersoenlich()
	{
		if (cbSofort.isSelected() && rbPersoenlich.isSelected())
			return true;
		else
			return false;
	}
	
	private boolean checkEingaben()
	{
		if (tfName.getText()!="" && 
			tfTelefonNr.getText()!="" &&
			(rbPersoenlich.isSelected() || rbTelefonisch.isSelected() || rbEgal.isSelected()))
			return true;
		else
			return false;
	}
}
