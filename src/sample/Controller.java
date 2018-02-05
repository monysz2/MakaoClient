package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class Controller implements Initializable{
    BufferedReader in = null;
    PrintWriter out = null;
    @FXML
    HBox player1Cards;
    @FXML
    HBox player2Cards;
    @FXML
    Button putCard;
    @FXML
    Button getCard;
    @FXML
    Button start;
    @FXML
    ImageView set;
    @FXML
    ImageView table;
    @FXML
    Label state = new Label();
    @FXML
            ImageView userFieldToPut;
    int stat,strike=0;
    String response = null;
    String cardToPut = null;
    LinkedList<Button> cards = new LinkedList<>();
    Manager manager;

    public Controller(BufferedReader reader, PrintWriter writer) throws IOException {
        this.in = reader;
        this.out = writer;
    }

    public Controller() throws IOException{}



    @FXML
    void getCard() throws IOException {
        out.println("1");
        Button toAdd = new Button();
        String card = in.readLine();
        ImageView image = new ImageView("cards/"+card);
        image.setFitHeight(100);
        image.setFitWidth(80);
        toAdd.setGraphic(image);
        toAdd.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                displayCardToPut(card);
            }
        });
        cards.add(toAdd);
        displaySet();
        getCard.setDisable(true);

    }



    void displayCardToPut(String card)
    {
        userFieldToPut.setImage(new Image("cards/"+card));
    }
    void displaySet()
    {

            player1Cards.getChildren().addAll(cards);

    }
    void clearSet()
    {
            player1Cards.getChildren().removeAll(cards);
    }
    void disableButtons()
    {
        for(Button button : cards)
        {
            button.setDisable(true);
        }
    }

    void enableButtons()
    {
        for(Button button : cards)
        {
            button.setDisable(false);
        }
    }
void addCardToSet(String card)
{
    Button toAdd = new Button();
    ImageView image = new ImageView("cards/"+card);
    image.setFitHeight(100);
    image.setFitWidth(80);
    toAdd.setGraphic(image);
    toAdd.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
        @Override
        public void handle(javafx.scene.input.MouseEvent event) {
            putCard(image.getImage().getUrl());
            clearSet();
            cards.remove(toAdd);
            displaySet();
        }
    });
    cards.add(toAdd);
}
    void putCard(String card)
    {
        String[] abc = card.split("/");
        cardToPut = abc[10];

    }
@FXML
    void startGame() throws IOException {
        //stat = Integer.parseInt(in.next());
        //out.println("3");
        //state.setText("Waiting for player 2");

        //String aaa = in.next();


        for(int i=0; i<5; i++)
        {
            //1 = ready
            out.println("1");
            Button toAdd = new Button();
            String card=new String(in.readLine());
            ImageView image = new ImageView("cards/"+card);
            image.setFitHeight(100);
            image.setFitWidth(80);
            toAdd.setGraphic(image);
            toAdd.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent event) {
                    displayCardToPut(card);
                    clearSet();
                    cards.remove(toAdd);
                    displaySet();
                }
            });
            cards.add(toAdd);






        }
    displaySet();
        if(stat==1)
        {

           disableButtons();


        }



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.setImplicitExit(false);
        try {
            manager = new Manager(Main.socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        manager.controller = this;
        manager.start();

    }
}
