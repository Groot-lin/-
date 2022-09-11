package maze;

public class Point {
	int val;
    private int x;
    private int y;
    boolean[] state;
    
    Point(int val,int x,int y){
    	this.val = val;
    	this.x = x;
    	this.y = y;
    	state = new boolean[4];//记录上下左右的通路情况
    }
    
    public int getX() {
    	return x;
    }
    public int getY() {
    	return y;
    }
    
}
