package cs1302.gallery;

import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import java.util.Random;
import javafx.stage.Modality;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import java.time.LocalTime;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.text.Text;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import java.net.URL;
import java.net.URLEncoder;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.io.IOException;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.concurrent.*;
import java.util.*;

/**
 * Creates an iTunes GalleryApp.
 */
public class GalleryApp extends Application {

    String encodedString = null;
    URL url = null;
    InputStreamReader reader = null;
    String word = "rock";
    GridPane gridPane = new GridPane();
    int counter = 0;
    Timeline timeline = new Timeline();
    ProgressBar progress = new ProgressBar();
    VBox vbox = new VBox();
    BorderPane pane = new BorderPane();
    HBox hbox = new HBox();
    final Menu file = new Menu("File");
    final Menu help = new Menu("Help");
    MenuBar menuBar = new MenuBar();
    MenuItem exitItem = new MenuItem("Exit");
    MenuItem aboutMe = new MenuItem("About");
    ToolBar toolbar = new ToolBar();
    Button pauser = new Button("Pause");
    Text query = new Text("Search Query");
    TextField search = new TextField();
    Button updater = new Button("Update Images");

/**
 *makes a pane.
 *@return a BorderPane object
 *{@inheritdoc}
 */
    public BorderPane paneStarter() {
        menuBar.getMenus().addAll(file, help);
        file.getItems().add(exitItem);

        toolbar.getItems().add(pauser);
        toolbar.getItems().add(query);
        toolbar.getItems().add(search);
        toolbar.getItems().add(updater);

        vbox.getChildren().add(menuBar);
        vbox.getChildren().add(toolbar);

        progress.prefWidthProperty().bind(vbox.widthProperty().subtract(20));
        progress.setProgress(1);
        pane.setTop(vbox);
        pane.setBottom(hbox);
        pane.setCenter(gridPane);
        pane.setBottom(progress);

        return pane;
    }

      /**
      * start the initial stage.
      *{@inheritdoc}
      * @param stage
      */
    public void start(Stage stage) {


        help.getItems().add(aboutMe);
        aboutMe.setOnAction(e -> {
            FlowPane flowPane3 = new FlowPane();
            Text info  = new Text("Name:Samee Lalani\nEmail:stl26813@uga.edu\nVersion 1\nPic:");
            Image me = new Image("https://i.imgur.com/KGBYxDL.jpeg",100,100,false,false);
            flowPane3.getChildren().add(new ImageView(me));
            flowPane3.getChildren().add(info);
            Stage stage3 = new Stage();
            Scene scene3 = new Scene(flowPane3);
            stage3.setScene(scene3);
            stage3.initModality(Modality.APPLICATION_MODAL);
            stage3.setTitle("About Me");
            stage3.sizeToScene();
            stage3.showAndWait();
        });// about me pop up window
        exitItem.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });

        EventHandler <ActionEvent> handler = event -> runPlay();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(2), handler);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();        //decides how often the images will be swapped
        pauser.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    if (counter == 1) {
                        timeline.pause();
                    }
                    if (pauser.getText().equals("Play")) {
                        pauser.setText("Pause");
                        timeline.play();
                    } else if (pauser.getText().equals("Pause")) {
                        pauser.setText("Play");
                        timeline.pause();
                    }     //sets pause to play and stops images from moving
                }
            });
        updater.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    word = search.getText();
                    update(word);
                }
            });//adds the word in the search query to the url
        update(word);
        Scene scene = new Scene(paneStarter());
        stage.setMaxWidth(1280);
        stage.setMaxHeight(720);
        stage.setTitle("GalleryApp!");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    } // start

    /**
     *swaps out all the current images with images relevant to the text entered in the search query.
     *by using the url to grab the image objects and store them in the grid pane.
     *@param in the word entered into the search query.
     *
     */
    public void update(String in) {
        try {
            encodedString = URLEncoder.encode(in,"UTF-8");
        } catch (java.io.UnsupportedEncodingException uee) {
            return;
        } // try
        if (encodedString != null) {
            try {
                url = new URL("http://itunes.apple.com/search?term=" + encodedString);
            } catch (java.net.MalformedURLException mue) {
                return;
            } // try
        } // if
        try {
            reader = new InputStreamReader(url.openStream());
        } catch (IOException ioe) {
            return;
        } //try
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(reader);
        JsonObject root = je.getAsJsonObject();
        JsonArray results = root.getAsJsonArray("results"); // "results" array
        int numResults = results.size();// "results" array size
        ArrayList<Image> imgList = new ArrayList<Image>();
        if (numResults >= 20) {
            counter = 0;
            int x = 0, y = 0, num = 0;
            for (int i = 0; i < 20; i++) {
                JsonObject result = results.get(i).getAsJsonObject();
                JsonElement artworkUrl100 = result.get("artworkUrl100");
                if (artworkUrl100 != null) {
                    String artUrl = artworkUrl100.getAsString();
                    Image image = new Image(artUrl, 100, 100, false, false);
                    imgList.add(image);
                    progress.setProgress(1 * (i / 20));
                }
            }
            for (Image e : imgList) {
                gridPane.add(new ImageView(e),x,y);
                num++;
                x++;
                if (x == 5) {
                    x = 0;
                    y++;
                }
            }
        } else {
            timeline.pause();
            FlowPane flowPane2 = new FlowPane();
            Text info  = new Text("improper search, exit and try again");
            flowPane2.getChildren().add(info);
            Stage stage2 = new Stage();
            Scene scene2 = new Scene(flowPane2, 300, 100);
            stage2.setScene(scene2);
            stage2.initModality(Modality.APPLICATION_MODAL);
            stage2.setTitle("Ooops");
            stage2.sizeToScene();
            stage2.showAndWait();
        } //creates a pop up window when the search has less than 20 results
    } //updates the images based on what's put in the search query

    /**
     *Will be used to change the progress bar as we go.
     *@param prog makes setProgress a thread.
     */
    private void setProgress(final double prog) {
        Platform.runLater(() -> progress.setProgress(prog));
    } // setProgress


    /**
     *swaps out the images with another image in the search query every 2 seconds.
     */
    public void runPlay() {
        Random rand = new Random();

        try {
            encodedString = URLEncoder.encode(word,"UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            return;
        } // try
        if (encodedString != null) {
            try {
                url = new URL("http://itunes.apple.com/search?term=" + encodedString);
            } catch (java.net.MalformedURLException e) {
                return;
            } // try
        } // if

        try {
            reader = new InputStreamReader(url.openStream());
        } catch (IOException e) {
            return;
        } //try

        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(reader);

        JsonObject root = je.getAsJsonObject();
        JsonArray results = root.getAsJsonArray("results");

        int y = rand.nextInt(4);
        int x = rand.nextInt(3);
        int imageNum = 21 + rand.nextInt((results.size() - 22) + 1);

        JsonObject result = results.get(imageNum).getAsJsonObject();
        JsonElement artworkUrl100 = result.get("artworkUrl100");

        String artUrl = artworkUrl100.getAsString();
        Image image = new Image(artUrl, 100, 100, false, false);
        gridPane.add(new ImageView(image),y,x );
    } //executes the picture swap every two seconds

} // GalleryApp
