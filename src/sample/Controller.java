package sample;


import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {
    BufferedReader in = null;
    PrintWriter out = null;
    @FXML
    HBox player1Cards;
    @FXML
    HBox player2Cards;
    @FXML
    Button getCard;
    @FXML
    ImageView set;
    @FXML
    ImageView table;
    @FXML
    Label state;
    @FXML
    ImageView userFieldToPut;
    @FXML
    Label color;
    @FXML
            Label cardsToTake;
    @FXML
            Label turnsToWait;
    @FXML
            Label value;

    Button cardBuffer = new Button();


    LinkedList<Button> cards = new LinkedList<>();
    LinkedList<ImageView> players2cards = new LinkedList<>();

    public Controller() throws IOException {
    }


    @FXML
    void getCard() throws IOException {
        out.println("0");
        getCard.setDisable(true);

    }

    void makao(int player)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("MAKAO!");
        alert.setHeaderText(null);
        alert.setContentText("MAKAO Player"+player+"!!!");
        alert.showAndWait();
    }

    void finish(int player)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Finish");
        alert.setHeaderText(null);
        alert.setContentText("Player"+player+"won !!!");
        alert.showAndWait();
        Platform.exit();
    }


    void giveValue()
    {
        List<String> choices = new ArrayList<>();
        choices.add("5");
        choices.add("6");
        choices.add("7");
        choices.add("8");
        choices.add("9");
        choices.add("10");
        choices.add("none");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("5",choices);
        dialog.setTitle("Give a value");
        dialog.setHeaderText("Give a value");

        Optional<String> result = dialog.showAndWait();
        String choice = result.get();
        if(choice.equals("none"))
        {
            out.println("11");
        }else
            out.println(choice);
    }
    void giveColor()
    {
        Alert alert = new Alert((Alert.AlertType.CONFIRMATION));
        alert.setTitle("Give a Color");
        alert.setHeaderText("Give a Color");

        ButtonType hearts = new ButtonType("Hearts");
        ButtonType diamonds = new ButtonType("Diamonds");
        ButtonType spades = new ButtonType("Spades");
        ButtonType clubs = new ButtonType("Clubs");


        alert.getButtonTypes().setAll(hearts,diamonds,spades,clubs);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == hearts)
        {
            out.println("hearts");
        }else if(result.get()==diamonds)
        {
            out.println("diamonds");
        }else if(result.get() == spades)
        {
            out.println("spades");
        }else if(result.get()==clubs)
        {
            out.println("clubs");
        }
    }

    void displayCardToPut(String card) {
        userFieldToPut.setImage(new Image("cards/" + card));
    }

    void displaySet() {

        player1Cards.getChildren().addAll(cards);

    }

    void clearSet() {
        player1Cards.getChildren().removeAll(cards);
    }

    void disableButtons() {
        for (Button button : cards) {
            button.setDisable(true);
        }
        getCard.setDisable(true);
    }

    void enableButtons() {
        for (Button button : cards) {
            button.setDisable(false);
        }
        getCard.setDisable(false);
    }

    void putCard(String card) throws IOException {

        out.println("1");
        out.println(card);


    }
    void displayColor() throws IOException {
        color.setText(in.readLine());
    }

    void displayValue() throws IOException {
        int valueToDisplay = Integer.parseInt(in.readLine());
        if(valueToDisplay==11)
        {
            value.setText("Jack");
        }else
            value.setText(Integer.toString(valueToDisplay));
    }

    void displayCardsToTake() throws IOException {
        cardsToTake.setText(in.readLine());
    }
    void displayTurnsToWait() throws IOException {
        turnsToWait.setText(in.readLine());
    }
    @FXML
    void receiveCard() throws IOException {
        System.out.println("Recieved");
        Button toAdd = new Button();
        String card = new String(in.readLine());
        ImageView image = new ImageView("cards/" + card);
        image.setFitHeight(100);
        image.setFitWidth(80);
        toAdd.setGraphic(image);
        toAdd.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                disableButtons();
                displayCardToPut(card);
                clearSet();
                cardBuffer = toAdd;
                cards.remove(toAdd);
                displaySet();
                try {
                    putCard(card);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        clearSet();
        cards.add(toAdd);
        displaySet();
    }
    void displayPlayer2Cards(int number)
    {
        player2Cards.getChildren().removeAll(players2cards);
        players2cards.clear();
        for(int i=0;i<number;i++)
        {
            ImageView image = new ImageView("cards/opponent.png");
            image.setFitHeight(100);
            image.setFitWidth(80);
            players2cards.add(image);
        }
        player2Cards.getChildren().addAll(players2cards);
    }
    @FXML
    void waitForServer() throws IOException {
        if (in.ready()) {
            String choice = in.readLine();
            System.out.println("Server>>>"+ choice);
            switch (Integer.parseInt(choice)) {
                case 10:
                    receiveCard();
                    break;
                case -10:
                    disableButtons();
                case -20:
                    state.setText("Wrong choice - try again");
                    clearSet();
                    cards.add(cardBuffer);
                    displaySet();
                    enableButtons();
                    break;
                case 20:
                    enableButtons();
                    state.setText("Your turn - put card");
                    break;
                case 30:
                    giveColor();
                    break;
                case 40:
                    giveValue();
                    break;
                case 5:
                    displayColor();
                    break;

                case 6:
                    displayCardsToTake();
                    break;
                case 7:
                    displayTurnsToWait();
                    break;
                case 8:
                    displayValue();
                    break;
                case 4:
                    table.setImage(new Image("cards/" + in.readLine()));
                    break;
                case 2:
                    state.setText("Player's 2 move");
                    disableButtons();

                    break;
                case 1:
                    state.setText("Player's 1 move");
                    enableButtons();

                    break;
                case 9:
                    displayPlayer2Cards(Integer.parseInt(in.readLine()));
                    break;
                case 100:
                    makao(1);
                    break;

                case 101:
                    makao(2);
                    break;

                case 200:
                    finish(1);
                    break;

                case 201:
                    finish(2);
                    break;

            }
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            in = new BufferedReader(new InputStreamReader(Main.socket.getInputStream()));
            out = new PrintWriter(Main.socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

