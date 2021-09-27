import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends JFrame {
    int port = 8000;
    InetAddress host;
    ServerSocket serverSocket;
    Socket clientSocket;
    InputStream is;
    OutputStream os;
    DataInputStream dis;
    DataOutputStream dos;

    JLabel x_tfield_label;
    JFormattedTextField x_tfield;
    JLabel y_tfield_label;
    JFormattedTextField y_tfield;

    // серверу нужна одна модель, с которой будут работать все клиенты
    model m = new model();


    //чтобы каждый клиент мог общаться с сервером независимо и больше чем один раз, взаимодействие с каждым
    //клиентом должно происходить в отдельном потоке
    public Server() {
        this.setTitle("Server");
        this.setSize(600,350);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        x_tfield_label = new JLabel("x: ");
        x_tfield_label.setBounds(105,80,25,25);
        add(x_tfield_label);

        x_tfield = new JFormattedTextField();
        x_tfield.setBounds(105,110,50,50);
        x_tfield.setEditable(false);
        x_tfield.setText("0");
        add(x_tfield);

        y_tfield_label = new JLabel("y: ");
        y_tfield_label.setBounds(195,80,25,25);
        add(y_tfield_label);

        y_tfield = new JFormattedTextField();
        y_tfield.setBounds(195,110,50,50);
        y_tfield.setEditable(false);
        y_tfield.setText("0");
        add(y_tfield);

        this.setVisible(true);

        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            serverSocket = new ServerSocket(port,0,host);
            System.out.println("Server started");
            int count = 0;

            while(true) {
                clientSocket = serverSocket.accept();

                count++;
                System.out.println("Client connected");
                WCS wcs = new WCS(clientSocket,m,x_tfield,y_tfield);
            }
            //serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    //изменение координат в форме - здесь же можно показать pop-up message
    synchronized
    void replaceCoords(String x,String y){ // потом заменить на объект
        x_tfield.setText(x);
        y_tfield.setText(y);
    }
    public static void main(String[] args) {
        new Server();
    }
}
