import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends JFrame{
    JLabel x_tfield_label;
    JFormattedTextField x_tfield;
    JLabel y_tfield_label;
    JFormattedTextField y_tfield;
    JButton set_values;
    JButton connect;

    int port = 8000;
    InetAddress host;
    Socket clientSocket;
    InputStream is;
    OutputStream os;
    DataInputStream dis;
    DataOutputStream dos;
    Thread t;

    public Client(){
        this.setTitle("Client");
        this.setSize(600,350);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        connect = new JButton( new AbstractAction("Connect to server") {
            @Override
            public void actionPerformed( ActionEvent e ) {
                try {
                    host = InetAddress.getLocalHost();
                } catch (UnknownHostException er) {
                    er.printStackTrace();
                }
                try {
                    clientSocket = new Socket(host, port);
                    System.out.println("Client started");
                    is = clientSocket.getInputStream();
                    os = clientSocket.getOutputStream();

                    t = new Thread(){
                        @Override
                        public void run(){

                            is = null;

                            try {
                                is = clientSocket.getInputStream();
                                dis = new DataInputStream(is);
                                while(true){
                                    String s = dis.readUTF(); // but ill have an object
                                    replaceCoords(s,s);//but ill replace with object fields
                                }
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                            finally{
                                try {
                                    is.close();
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                            }
                            //dis = new DataInputStream(is);
                            ////dos = new DataOutputStream(os);

                        }
                    };
                    t.start();


                } catch (IOException er) {
                    er.printStackTrace();
                }
            }
        });
        connect.setBounds(105,35,140,25);
        add(connect);

        x_tfield_label = new JLabel("x: ");
        x_tfield_label.setBounds(105,80,25,25);
        add(x_tfield_label);

        x_tfield = new JFormattedTextField();
        x_tfield.setBounds(105,110,50,50);
        add(x_tfield);

        y_tfield_label = new JLabel("y: ");
        y_tfield_label.setBounds(195,80,25,25);
        add(y_tfield_label);

        y_tfield = new JFormattedTextField();
        y_tfield.setBounds(195,110,50,50);
        add(y_tfield);


        set_values = new JButton(new AbstractAction("Set values") {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        set_values.setBounds(105,210,100,40);
        add(set_values);



        this.setVisible(true);
    }

/**void addString(String s,String s2){  // применяем изменения
        //String tmp_x = x_tfield.getText();
        //String tmp_y = y_tfield.getText();

        x_tfield.setText(s);
        y_tfield.setText(s2);
}
 */

//изменение координат в форме - здесь же можно показать pop-up message
    synchronized
    void replaceCoords(String x,String y){ // потом заменить на объект
        x_tfield.setText(x);
        y_tfield.setText(y);

        JOptionPane.showMessageDialog(null,"Coordinate data on server has changed"); // if doesn't work try parent - this
    }
    public static void main(String[] args) {
        new Client();
    }

}
