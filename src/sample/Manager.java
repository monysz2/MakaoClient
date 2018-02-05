package sample;

import sample.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Manager extends Thread{
    static BufferedReader in = null;
    static PrintWriter out = null;
    static Controller controller = null;

    public Manager(Socket socket) throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);


    }
    public void run()
    {

        while (true) {
            System.out.println("working");
            try {
                switch (in.readLine()) {
                    case "1":
                        controller.addCardToSet(in.readLine());
                        controller.clearSet();
                        controller.displaySet();
                        break;

                    case "2":
                        while (true) {
                            if (controller.cardToPut != null) {
                                out.println(controller.cardToPut);
                                break;
                            }
                            controller.cardToPut = null;
                        }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
