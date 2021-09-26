import java.util.ArrayList;

// много клиентов наблюдают за одной моделью
public class model {
    ArrayList<Msg> all_msg = new ArrayList<>();
    ArrayList<IObserver> all_o = new ArrayList<>();

    public model() {
    }


    void update(){
        for(IObserver o : all_o){
            o.update(this);
        }
    }

    public void add(Msg m){
        all_msg.add(m);
        update();
    };
    public ArrayList<Msg> getAll_msg(){
        return all_msg;
    };
    public void subscribe(IObserver o){
            all_o.add(o);
    }
    //public void delete(String m){
    //    all_msg.remove(m);
    //}
    public Msg last(){
        return all_msg.get(all_msg.size()-1);
    };
}
