package PoemWords;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * This will build a user interactive interface.
 * 
 * @author Jean Dalmont
 *
 */
public class PoemWordsUI extends Application {
	
	/**
	 * Main method
	 * @param not used
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Create button for UI
	 */
	Button button;
	/**
	 * Create text area for UI
	 */
	TextArea tx;


	/**
	 * This will take in all parameters for the user interface.
	 * This will also have the layout of the user interface window.
	 */
	@Override
	public void start (Stage primaryStage) throws Exception {
		primaryStage.setTitle("Word Occurrences");
		button = new Button();
		tx = new TextArea();
		tx.setPrefHeight(150); 
		tx.setPrefWidth(400); 
		button.setText("Click Here");
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Connection c = null;

				Statement stmt = null;

				try {
					Class.forName("org.sqlite.JDBC");

					c = DriverManager.getConnection("jdbc:sqlite:wordOccurrences.db");

					System.out.println("Database Opened...\n");

					stmt = c.createStatement();
					String sql = "select * from Word";
					ResultSet rs = stmt.executeQuery(sql);
					while(rs.next()) {
						String word = rs.getString("myWords");
						int count = rs.getInt("count");
						tx.setText(tx.getText() + word + " = " + count + "\n");
					}
					stmt.close();

					c.close();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					System.out.println("SQL Exception");
					e.printStackTrace();
				}

				//tx.setText(wordCount.myWords());
			}
		});

		FlowPane layout = new FlowPane();
		layout.getChildren().add(button);
		layout.getChildren().add(tx);

		Scene scene = new Scene(layout, 500, 200);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
