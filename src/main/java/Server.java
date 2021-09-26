import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    int port = 8000;
    InetAddress host;
    ServerSocket serverSocket;
    Socket clientSocket;
    InputStream is;
    OutputStream os;
    DataInputStream dis;
    DataOutputStream dos;

    // серверу нужна одна модель, с которой будут работать все клиенты
    model m = new model();


    //чтобы каждый клиент мог общаться с сервером независимо и больше чем один раз, взаимодействие с каждым
    //клиентом должно происходить в отдельном потоке
    public Server() {
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
                WCS wcs = new WCS(clientSocket,m);
            }
            //serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Server();
    }
}
