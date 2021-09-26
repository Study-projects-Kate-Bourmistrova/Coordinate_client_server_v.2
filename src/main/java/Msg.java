public class Msg {
    String x_coordinate;
    String y_coordinate;

    public Msg(String x_coordinate, String y_coordinate) {
        this.x_coordinate = x_coordinate;
        this.y_coordinate = y_coordinate;
    }

    public String getX_coordinate() {
        return x_coordinate;
    }

    public void setMsg(String x_coordinate, String y_coordinate) {

        this.x_coordinate = x_coordinate;
        this.y_coordinate = y_coordinate;
    }

    public String getY_coordinate() {
        return y_coordinate;
    }

    @Override
    public String toString(){
        return x_coordinate + " " + y_coordinate;
    }
}
