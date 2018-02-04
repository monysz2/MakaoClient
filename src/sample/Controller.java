package sample;

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
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.EventHandler;

public class Controller implements Initializable{
    Socket socket = null;
    Scanner in = null;
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
    int stat;
    LinkedList<Button> cards = new LinkedList<>();
    public Controller() throws IOException {
    }
    @FXML
    void getCard()
    {
        out.println("1");
        Button toAdd = new Button();
        String card = new String(in.next());
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
        displaySet(toAdd);
        stat=1;
        state.setText("Player2 move");
        waitForTurn();

    }
    @FXML
    void putCard()
    {
        String card = userFieldToPut.getImage().getUrl();

            if(stat==2) {
                out.println("2");
                String[] abc = card.split("/");
                out.println(abc[10].substring(5));
                String approval = in.next();
                if (Integer.parseInt(approval) == 1) {
                    table.setImage(new Image("cards/" + in.next()));
                    System.out.println("123");
                    // blockUser();
                } else
                    state.setText("Wrong choice!");
                stat=1;
            }else state.setText("Wait for Player 2");
            waitForTurn();

    }
    void blockUser()
    {
        for(Button button : cards)
        {
            button.disableProperty();

        }
        getCard.disableProperty();
    }
    void waitForTurn()
    {
        table.setImage(new Image("cards/"+in.next()));
        stat=2;
    }
    void displayCardToPut(String card)
    {
        userFieldToPut.setImage(new Image("cards/"+card));
    }
    void displaySet(Button toDisplay)
    {

            player1Cards.getChildren().addAll(toDisplay);

    }
@FXML
    void startGame()
    {
        //stat = Integer.parseInt(in.next());
        //out.println("3");
        //state.setText("Waiting for player 2");

        //String aaa = in.next();


        for(int i=0; i<5; i++)
        {
            out.println("1");
            Button toAdd = new Button();
            String card = new String(in.next());
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
            displaySet(toAdd);

        }
        if(stat==1)
        {
            waitForTurn();
        }



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            socket = new Socket("localhost", 6666);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
            table.setImage(new Image("cards/" + in.next()));
            stat = Integer.parseInt(in.next());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
