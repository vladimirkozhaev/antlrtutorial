package org.newlanguageservice.antlrtutorial;

import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.StyleClassedTextArea;
import org.newlanguageservice.ch1.CuttingLanguageLexer;
import org.newlanguageservice.ch1.CuttingLanguageParser;
import org.newlanguageservice.ch1.CuttingLanguageParser.ActionsContext;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DrawCanvas extends Application {

	private StyleClassedTextArea textArea;
	private Canvas canvas;
	private CuttingLanguageVisitor visitor;

	@Override
	public void start(Stage primaryStage) {
		Button btn = new Button();
		btn.setText("Compile");

		textArea = new StyleClassedTextArea();
		VirtualizedScrollPane vsPane = new VirtualizedScrollPane(textArea);

		textArea.setWrapText(true);
		//textArea.setStyleClass(from, to, styleClass);
		

		textArea.appendText("MoveTo(100,100)\n"
				+ "LineTo(200,200)");
		canvas = new Canvas(300, 250);

		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.GREEN);
		gc.setStroke(Color.BLUE);
		gc.setLineWidth(5);

		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				CuttingLanguageLexer lexer 
					= new CuttingLanguageLexer(new ANTLRInputStream(textArea.getText()));
				lexer.removeErrorListeners();
				CuttingLanguageParser parser
					= new CuttingLanguageParser(new CommonTokenStream(lexer));
				parser.removeErrorListeners();
				
				lexer.addErrorListener(new CuttingErrorListener());
				visitor = new CuttingLanguageVisitor(new Point(0, 0), gc);
				ParseTree tree = parser.actions();
				Point point = visitor.visit(tree);
				System.out.println(point);
				
				lexer = new CuttingLanguageLexer(new ANTLRInputStream(textArea.getText()));

				// Get a list of matched tokens
				CommonTokenStream tokens = new CommonTokenStream(lexer);

				// Pass the tokens to the parser
				parser = new CuttingLanguageParser(tokens);

				// Specify our entry point
				ActionsContext context = parser.actions();

				// Walk it and attach our listener
				ParseTreeWalker walker = new ParseTreeWalker();
				CuttingLanguageListener listener = new CuttingLanguageListener();
				walker.walk(listener, context);
				
				List<LexemeWithCoords> lexemes = listener.getLexemes();
				lexemes.forEach(lexeme->textArea.setStyleClass(lexeme.getCoords().getX(), 
						lexeme.getCoords().getY(), "keywords"));
				

			}
		});

		VBox root = new VBox(btn, vsPane, canvas);

		Scene scene = new Scene(root, 800, 600);
		scene.getStylesheets().add("stylesheet.css");
		primaryStage.setTitle("Hello World!");
		primaryStage.setScene(scene);

		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
