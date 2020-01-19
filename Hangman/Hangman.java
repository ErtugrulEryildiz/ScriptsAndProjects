import javafx.application.Application;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Hangman extends Application {


    private boolean isKeyPressable = false;             // Controls whether in which window user can type.
    private boolean isGameStarted = false;
    private boolean isGameEnded = false;
    private int invalidGuesses = 0;
    private String hiddenWord = "";                     // Word to guess.

    private Stage primaryStage;                         // Entire game uses this stage.
    private Stage threeButtonPopup;                     // Pop window to give user warning with three options.
    private Stage singleButtonPopup;                    // Pop window to give user warning with two options.
    private Scene scene;                                // Primary scene that game uses.

    private BorderPane parentPane;                      // Every pane and node will go to here.
    private HBox IOPanel;                               // Input output Pane for file operations.
    private HBox bottomPane;                            // Start Game button displayed in this pane.
    private VBox mainPane;                              // Alphabet and hidden word will be shown here.
    private VBox hangmanPane;                           // Pane where hangman will be placed.
    private GridPane hiddenWordPane;                    // Hidden word's black boxes will be displayed here.
    private GridPane alphabetPane;                      // Buttons for alphabet will be shown here.
    private StackPane[] stackedBlackBoxes;              // Stack pane that will contain black boxes and hidden letters.
    private StackPane[] stackedGreenBoxes;              // Stack pane that will contain green boxes alphabet letters.
    private Rectangle[] blackBox;                       // black boxes for hidden word
    private Rectangle[] greenBox;                       // green boxes for alphabet
    private Label[] hiddenLetters;                      // Hidden word's letters.
    private Label[] alphabetLetters;                    // Alphabet's letters.
    private Label[] remaining_guess;                    // Label to show how many more guesses user can make.

    private Canvas canvas;                              // Canvas to draw hang man.
    private GraphicsContext graphicsContext;            // Tool to draw hangman.

    private Button startIOBt;                           // Starts the game.
    private Button loadIOBt;                            // Loads an existing game.
    private Button saveIOBt;                            // Saves a started game.
    private Button exitIOBt;                            // Exits the game.
    private Button startGameBt;                         // Starts the game.



    public void start(Stage initPrimaryStage) {
        primaryStage = initPrimaryStage;
        initializeGUI();        // All the graphics are initialized but not shown yet.
        initializeHandlers();   // All the handlers of start and play scene are initialized.
        initializeGameData();
        startScene();         // Show starting scene to user.
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("Hangman.png")));
        primaryStage.setTitle("Hangman");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    private void initializeGUI() {
        parentPane = new BorderPane();
        initializeNorthPane();
        initializeCenterPane();
        initializeWestPane();
        initializeSouthPane();
        scene = new Scene(parentPane, 750, 500);
    }

    private void startScene() {
        parentPane.setTop(IOPanel);
        parentPane.setCenter(null); // Since this pane is not be used in start scene, set it to null.
        parentPane.setLeft(null);   // Since this pane is not be used in start scene, set it to null.
        parentPane.setRight(null);  // Since this pane is not be used in start scene, set it to null.
        parentPane.setBottom(null); // Since this pane is not be used in start scene, set it to null.
        saveIOBt.setDisable(true);
        isGameStarted = false;
        isGameEnded = false;
        isKeyPressable = false;
        resetGameData();
    }
    private void playScene() {
        parentPane.setTop(IOPanel);
        parentPane.setCenter(mainPane);
        parentPane.setLeft(hangmanPane);
        parentPane.setRight(null);      // Since this pane is not be used in start scene, set it to null.
        parentPane.setBottom(null);     // Since this pane is not be used in start scene, set it to null.
        isKeyPressable = true;          // Since game is started, user's key inputs are valuable.
        saveIOBt.setDisable(true);      // Initially Disable save button.
    }

    private void initializeNorthPane() {
        IOPanel = new HBox(10);
        IOPanel.setAlignment(Pos.TOP_LEFT);
        IOPanel.setStyle("-fx-background-color: rgba(90, 90, 90)");
        Label IOlabel = new Label("\t     HANGMAN GAME   \t");
        IOlabel.setFont(Font.font(24));
        IOlabel.setTextFill(Color.YELLOW);
        startIOBt = new Button("", new ImageView(new Image(getClass().getResourceAsStream("New.png"))));
        loadIOBt = new Button("", new ImageView(new Image(getClass().getResourceAsStream("Load.png"))));
        saveIOBt = new Button("", new ImageView(new Image(getClass().getResourceAsStream("Save.png"))));
        exitIOBt = new Button("", new ImageView(new Image(getClass().getResourceAsStream("Exit.png"))));
        IOPanel.getChildren().setAll(startIOBt, loadIOBt, saveIOBt, exitIOBt,IOlabel);
    }
    private void initializeCenterPane() {
        mainPane = new VBox(25);
        mainPane.setStyle("-fx-background-color: rgba(255, 255, 255)");
        HBox remainingGuessPanel = new HBox(3);
        remaining_guess = new Label[2];
        remaining_guess[0] = new Label("Remaining Guesses: ");
        remaining_guess[1] = new Label("");
        remainingGuessPanel.getChildren().addAll(remaining_guess[0],remaining_guess[1]);
        hiddenWordPane = new GridPane();
        hiddenWordPane.setHgap(3);
        hiddenWordPane.setVgap(3);
        alphabetPane = new GridPane();
        alphabetPane.setHgap(3);
        alphabetPane.setVgap(3);
        mainPane.getChildren().addAll(remainingGuessPanel, hiddenWordPane, alphabetPane);
    }
    private void initializeWestPane() {
        hangmanPane = new VBox();
        hangmanPane.setStyle("-fx-background-color: rgba(255, 255, 255)");
        canvas = new Canvas(350,500);
        graphicsContext = canvas.getGraphicsContext2D();
        hangmanPane.getChildren().add(canvas);
    }
    private void initializeSouthPane() {
        bottomPane = new HBox();
        startGameBt = new Button("Start Game");
        bottomPane.getChildren().add(startGameBt);
        bottomPane.setAlignment(Pos.CENTER);
        parentPane.setBottom(bottomPane);
    }

    private void initializeGameData() {
        invalidGuesses = 0;
        isKeyPressable = false;
        isGameStarted = false;
        isGameEnded = false;
        remaining_guess[1].setText(Integer.toString(10 - invalidGuesses));
        try {   // Read the word to be guessed.
            File file = new File(getClass().getResource("words.txt").toURI());
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            ArrayList<String> words = new ArrayList<>();
            String word = bufferedReader.readLine();
            while(word != null) {
                words.add(word);
                word = bufferedReader.readLine();
            }
            hiddenWord = words.get((int)(Math.random() * words.size()));
        } catch (IOException ioe) {System.out.println("IOException occurred.");}
        catch (URISyntaxException uri) {System.out.println(uri.getMessage());}
        constructGreenAndBlackBoxes();
    }
    private void initializeHandlers() {
        startIOBt.setOnAction(event -> {
            if(isGameStarted && !isGameEnded) loadThreeButtonWindow(event);  // Allow user to save current game.
            else {
                startScene();                               // Load starting scene
                parentPane.setBottom(bottomPane);           // Load Start Game button at the bottom of start scene.
            }
        });
        loadIOBt.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Hangman Files, (*.hng)","*.hng"));
            File file = fileChooser.showOpenDialog(null);
            if(file != null) {
                try {
                    resetGameData();
                    loadFromFile(file);
                    playScene();
                } catch (IOException ioe) { System.out.println("IO Exception occurred."); }
            }
        });
        saveIOBt.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Hangman Files, (*.hng)","*.hng"));
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                try {
                    saveCurrentGame(file);
                    saveIOBt.setDisable(true);
                } catch (IOException ioe) { System.out.println(ioe.getMessage()); }
            }
        });
        exitIOBt.setOnAction(event -> {
            if(isGameStarted && !isGameEnded) loadThreeButtonWindow(event);
            else { primaryStage.close(); }
        });
        startGameBt.setOnAction(event -> {
            initializeGameData();
            playScene(); // Allows game to move onto play scene.
        });
        scene.setOnKeyPressed(keyPressed -> {
            if(isKeyPressable) { processKeyCode(keyPressed); }
        });
    }

    // Helper methods
    private void constructGreenAndBlackBoxes() {
        stackedBlackBoxes = new StackPane[hiddenWord.length()]; // Stack Pane that will contain rectangle and label
        blackBox = new Rectangle[hiddenWord.length()];
        hiddenLetters = new Label[hiddenWord.length()];         // Hidden word letter.
        for(int i=0; i<hiddenWord.length(); i++) {              // Construct hidden word's boxes.
            hiddenLetters[i] = new Label(Character.toString(hiddenWord.charAt(i)));
            hiddenLetters[i].setTextFill(Color.BLACK);          // Set letter's color to black, so it will be invisible.
            blackBox[i] = new Rectangle(25,25,Color.BLACK);
            stackedBlackBoxes[i] = new StackPane(blackBox[i],hiddenLetters[i]);
            hiddenWordPane.add(stackedBlackBoxes[i],i%10,i/10); // Ten boxes at each line.
        }
        stackedGreenBoxes = new StackPane[26];                  // Stack Pane that will contain rectangle and label
        greenBox = new Rectangle[26];
        alphabetLetters = new Label[26];                        // Alphabet letters.
        for(int i=0; i<26; i++) {                               // Construct alphabet's boxes.
            alphabetLetters[i] = new Label(Character.toString((char)('A' + i)));
            alphabetLetters[i].setTextFill(Color.WHITE);
            greenBox[i] = new Rectangle(25,25,Color.LIMEGREEN);
            stackedGreenBoxes[i] = new StackPane(greenBox[i],alphabetLetters[i]);
            alphabetPane.add(stackedGreenBoxes[i],i%10,i/10); // Ten boxes at each line.
        }
    }
    private void endTheGame() {
        isGameEnded = true;
        singleButtonPopup = new Stage();
        singleButtonPopup.setTitle("Game Over!");
        VBox vBox = new VBox(20);
        Button closeBt = new Button("Close");
        Label label;
        ImageView imageView;
        if(isAllLettersFound()) {
            imageView = new ImageView(new Image("winner.jpg"));
            label = new Label(""); }
        else {
            imageView = new ImageView(new Image("game_over.jpg"));
            label = new Label("Word was \'" + hiddenWord + "\'.");
            for(Label lb : hiddenLetters) if(lb.getTextFill().equals(Color.BLACK)) lb.setTextFill(Color.RED);
        }
        vBox.getChildren().addAll(imageView,label,closeBt);
        vBox.setAlignment(Pos.CENTER);
        Scene scene_1 = new Scene(vBox,400,350);
        singleButtonPopup.setScene(scene_1);
        singleButtonPopup.initOwner(primaryStage);
        singleButtonPopup.initModality(Modality.WINDOW_MODAL);
        singleButtonPopup.show();
        closeBt.setOnAction(event -> {
            saveIOBt.setDisable(true);
            singleButtonPopup.close();
        });
    }
    private boolean isValidKeyCode(KeyCode keyCode) {
        if (keyCode.isLetterKey()) { // If keyCode is letter key and never pressed before,
            for(int i=0; i<=26; i++) {
                if (alphabetLetters[((int) (keyCode.toString().charAt(0)) - 65)].getTextFill().equals(Color.WHITE)) {
                    return true;
                }
            }
        } return false;
    }
    private void loadThreeButtonWindow(Event event) {
        threeButtonPopup = new Stage();
        threeButtonPopup.setTitle("Warning!");
        VBox vBox = new VBox(10);
        Label label = new Label("Do you want to save your current game?");
        HBox buttons = new HBox(10);
        Button yesBt = new Button("YES");
        Button noBt = new Button("NO");
        Button cancelBt = new Button("CANCEL");
        buttons.getChildren().addAll(yesBt,noBt,cancelBt);
        buttons.setAlignment(Pos.BOTTOM_CENTER);
        vBox.getChildren().addAll(label,buttons);
        vBox.setAlignment(Pos.CENTER);
        initializeThreeButtonWindowHandlers(yesBt, noBt, cancelBt, event);
        Scene scene_1 = new Scene(vBox,250,125);
        threeButtonPopup.setScene(scene_1);
        threeButtonPopup.initOwner(primaryStage);
        threeButtonPopup.initModality(Modality.WINDOW_MODAL);
        threeButtonPopup.showAndWait();
    }
    private void initializeThreeButtonWindowHandlers(Button yesBt, Button noBt, Button cancelBt, Event event) {
        yesBt.setOnAction(event1 -> {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Hangman Files (*.hng)","*.hng"));
                File file = fileChooser.showSaveDialog(threeButtonPopup);
                if(file != null) {
                    saveCurrentGame(file); // Save current game's important data.
                    if(event.getSource().equals(startIOBt)) {
                        startScene();
                        parentPane.setBottom(bottomPane);
                    } else { primaryStage.close(); }
                }
                threeButtonPopup.close();
            } catch (IOException ioe) { System.out.println("IO Exception occurred."); }
        });
        noBt.setOnAction(event1 -> {
            threeButtonPopup.close();
            if(event.getSource().equals(startIOBt)) {
                startScene();
                parentPane.setBottom(bottomPane);
            } else {primaryStage.close();}
        });
        cancelBt.setOnAction(event1 -> { threeButtonPopup.close(); });
    }
    private void saveCurrentGame(File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(hiddenWord);   // Save hidden word.
        bufferedWriter.newLine();
        bufferedWriter.write(Integer.toString(invalidGuesses)); // Save how many guesses were left.
        bufferedWriter.newLine();
        for(int i=0; i<hiddenLetters.length; i++) { // Save which letters were correctly guessed.
            if(hiddenLetters[i].getTextFill().equals(Color.WHITE)) {
                bufferedWriter.write(hiddenLetters[i].getText());
                bufferedWriter.write(" ");
            }
        }
        bufferedWriter.newLine();
        for(int i=0; i<26; i++) { // Save which alphabet letters picked.
            if(alphabetLetters[i].getTextFill().equals(Color.BLUE)) {
                bufferedWriter.write(alphabetLetters[i].getText());
                bufferedWriter.write(" ");
            }
        }
        bufferedWriter.close();
    }
    private void loadFromFile(File file) throws IOException{
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        hiddenWord = bufferedReader.readLine();
        invalidGuesses = Integer.parseInt(bufferedReader.readLine());
        constructGreenAndBlackBoxes();
        String line = bufferedReader.readLine();
        String[] splitLetters = line.split(" ");
        for(int i=0; i<splitLetters.length; i++) {    // load proper found hidden letters.
            for (int j=0 ; j<hiddenLetters.length; j++) {
                if(splitLetters[i].equals(hiddenLetters[j].getText())) { hiddenLetters[j].setTextFill(Color.WHITE); }
            }
        }
        line = bufferedReader.readLine();
        splitLetters = line.split(" ");
        for(int i=0; i<splitLetters.length; i++) { // load proper alphabet letters.
            for(int j=0; j<26; j++) {
                if (splitLetters[i].equals(alphabetLetters[j].getText())) { alphabetLetters[j].setTextFill(Color.BLUE);}
            }
        }
        remaining_guess[1].setText(Integer.toString(10-invalidGuesses));
        updateHangman();
    }
    private void updateHangman() { // Draw hangman properly.
        graphicsContext.clearRect(0,0,canvas.getWidth(),canvas.getHeight()); // Clear out everything
        graphicsContext.setLineWidth(5);
        if(invalidGuesses > 0) graphicsContext.strokeLine(275,400,75,400);
        if(invalidGuesses > 1) graphicsContext.strokeLine(75,400,75,100);
        if(invalidGuesses > 2) graphicsContext.strokeLine(75,100,200,100);
        if(invalidGuesses > 3) graphicsContext.strokeLine(200,100,200,150);
        if(invalidGuesses > 4) graphicsContext.strokeOval(175,150,50,50);
        if(invalidGuesses > 5) graphicsContext.strokeLine(200,200,200,300);
        if(invalidGuesses > 6) graphicsContext.strokeLine(200,300,175,325);
        if(invalidGuesses > 7) graphicsContext.strokeLine(200,300,225,325);
        if(invalidGuesses > 8) graphicsContext.strokeLine(200,250,175,225);
        if(invalidGuesses > 9) graphicsContext.strokeLine(200,250,225,225);
        if(invalidGuesses == 10 || isAllLettersFound()) endTheGame(); // 10 invalid guesses ends game.

    }
    private void resetGameData() {
        hiddenWord = "";
        invalidGuesses = 0;
        remaining_guess[1].setText(Integer.toString(10-invalidGuesses));
        for(int i=0; i<hiddenLetters.length; i++) { // Make old black boxes and letters invisible.
            hiddenLetters[i].setText("");
            blackBox[i].setFill(Color.WHITE);
        }
        for(int i=0; i<alphabetLetters.length; i++) { alphabetLetters[i].setTextFill(Color.WHITE); }
        updateHangman();
    }
    private boolean isAllLettersFound() {
        if(hiddenWord.length() != 0) {
            boolean flag = true; // assume all letters are found
            for (int i = 0; i < hiddenLetters.length; i++) {
                if (hiddenLetters[i].getTextFill().equals(Color.BLACK)) {
                    flag = false;
                }
            }
            return flag;
        } else { return false; }
    }
    private void processKeyCode(KeyEvent keyPressed) {
        boolean keyFound = false;
        KeyCode keyCode = keyPressed.getCode();     // Pressed key's code.
        if (isValidKeyCode(keyCode)) {
            isGameStarted = true;
            saveIOBt.setDisable(false); // User can save current work now.
            for (int i = 0; i < hiddenLetters.length; i++) { // Check each letter of hidden word.
                if (keyCode.toString().toLowerCase().equals(hiddenLetters[i].getText().toLowerCase())) {
                    hiddenLetters[i].setTextFill(Color.WHITE); // Update hidden word's letters.
                    keyFound = true;
                }
            }
            for (int i = 0; i < 26; i++) { // Update each letter of Alphabet.
                if (keyCode.toString().toUpperCase().equals(Character.toString((char) (i + 65)))) {
                    alphabetLetters[i].setTextFill(Color.BLUE);
                }
            }
            if (!keyFound) remaining_guess[1].setText(Integer.toString(10 - ++invalidGuesses));
            updateHangman(); // Draw hangman.
        }
    }
    public static void main(String[] args) {launch(args);}
}