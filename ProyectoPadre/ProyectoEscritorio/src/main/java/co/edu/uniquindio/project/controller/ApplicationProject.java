package co.edu.uniquindio.project.controller;

import java.io.IOException;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ApplicationProject extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			loadMain(primaryStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	public void loadMain(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader(ApplicationProject.class.getResource("/initPane.fxml"));
		Parent parent = loader.load();
		Scene scene = new Scene(parent);
		InitController controller = loader.getController();
		controller.setMain(this);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Init application");
		primaryStage.show();
	}
}
