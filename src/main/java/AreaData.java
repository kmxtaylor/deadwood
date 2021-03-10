// class for Area, so then it's easier to store and access the dimensions
// import java.util.*;

public class AreaData{
    private int x;
    private int y;
    private int h;
    private int w;

    AreaData() {}

    AreaData(String x, String y, String h, String w) {
        this.x = Integer.parseInt(x);
        this.y = Integer.parseInt(y);
        this.h = Integer.parseInt(h);
        this.w = Integer.parseInt(w);
    }

    AreaData(int x, int y, int h, int w) {
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getH() {
        return h;
    }

    public int getW() {
        return w;
    }

}