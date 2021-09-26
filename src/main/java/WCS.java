import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.Socket;

//Thread нужен для обеспечения работы каждого клиента в своем потоке
//IObserver нужен для отслеживания изменений и совершения соотв реакций на них


//выходной поток потребуется сразу, а входной будет в отдельном потоке
public class WCS extends Thread implements IObserver {

    Socket cs;
    model m;
    InputStream is;
    OutputStream os;
    DataInputStream dis;
    DataOutputStream dos;
    //int count = 0;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public WCS(Socket cs, model m) {
        this.cs = cs;
        this.m = m;
        try {
            os = cs.getOutputStream();
            dos = new DataOutputStream(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //this.count = count;
        m.subscribe(this);
        this.start();
        //send("Привет!");

    }
    public void send(Msg s) {
        try {
            dos.writeUTF(gson.toJson(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //методом run прослушиваем, какие сообщения пришли от клиента
    @Override
    public void run() {
        try {
            is = cs.getInputStream();
            dis = new DataInputStream(is);

            while (true) {
                String obj = dis.readUTF();
                Msg msg = gson.fromJson(obj, Msg.class);
                m.add(msg);
                System.out.println("Json2 : " + gson.toJson(msg));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(model m) {
        //что делать при получении новых данных
        //когда переписываем на координаты меняем просто стринг на msg
        ////Msg notification = new Msg();
        /// notification.setMsg("Some client has changed the coordinates");
        //здесь можно разослать оповещение об обновлении координаты
        send(m.last());
    }
}
