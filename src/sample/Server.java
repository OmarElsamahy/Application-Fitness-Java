package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
/*

server side where it accepts a new client if found
and call a new thread that initiates the handle client class to deal with client data

*/

public class Server extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    ThreadPoolExecutor executor=(ThreadPoolExecutor) Executors.newFixedThreadPool(10);//executor service to deal with each
    // client with a thread and reuses threads
    @Override
    public void start(Stage primaryStage) throws IOException {
        DBHandler.initializeDB();
        AnchorPane pane=new AnchorPane();
        TextArea ta=new TextArea();
        pane.getChildren().add(ta);
        primaryStage.setTitle("Server");
        primaryStage.setScene(new Scene(pane,300,300));
        primaryStage.show();
        ServerSocket serverSocket=new ServerSocket(8000);
        new Thread(()->
        {
            while(true)
            {
                try {
                    Socket s=serverSocket.accept();
                    executor.execute(new HandleClient(s));
                } catch (Exception e) {
                //    e.printStackTrace();
                }
            }
        }).start();
    }
}
