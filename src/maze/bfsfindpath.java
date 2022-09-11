package maze;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;

public class bfsfindpath {
	// 迷宫所有的路径存储在二维数组当中
    private Point[][] maze;
    // 存储迷宫路径节点的队列结构，采用层层扩张的方式，寻找迷宫最优的路径信息
    private Queue<Point> queue;
    // 迷宫的大小
    private int side;
    // 记录迷宫路径节点的行走信息
    int[][] map ;
    //找路后的迷宫地图
    private static final int wall = 0;
    private static final int road = 1;
    private Point[] pathrecord;//保存最短路径
    private final int RIGHT = 0;//右
    private final int DOWN = 1;//下
    private final int LEFT = 2;//左
    private final int UP = 3;//上
    private int startx,starty;
    private int endx,endy;
  
//    public static void main(String[] args) throws FileNotFoundException {
//        createMaze cm = new createMaze(101,1,1,5);
//        int[][] map = cm.getmaze();
//        bfsfindpath maze = new bfsfindpath(map,int startx,int starty,);
//            
//        }
 
    public bfsfindpath(int[][] map,int startx,int starty,int endx,int endy) {//构造方法
    	this.startx = startx;
    	this.starty = starty;
    	this.endx = endx;
    	this.endy = endy;
    	
    	this.map = map;
    	
        this.side = map.length;
        this.maze = new Point[side][side];
        this.queue = new LinkedList<>();
        this.pathrecord = new Point[side*side];
        
        for (int i = 0; i < map.length; i++) {//初始化每个坐标
            for (int j = 0; j < map.length; j++) {
                int data = map[i][j];
                initPoint(data, i, j);
            }
        }
        PointState();
        bfs();
        find();
    }

    public void initPoint(int data, int i, int j) {//初始化每个坐标
        this.maze[i][j] = new Point(data, i, j);
    }

    public void PointState() {// 修改迷宫所有节点四个方向的行走状态信息
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                if(j<side-1 && this.maze[i][j+1].val == road){
                    this.maze[i][j].state[RIGHT] = true;
                }
                if(i<side-1 && this.maze[i+1][j].val == road){
                    this.maze[i][j].state[DOWN] = true;
                }
                if(j>0 && this.maze[i][j-1].val == road){
                    this.maze[i][j].state[LEFT] = true;
                }
                if(i>0 && this.maze[i-1][j].val == road){
                    this.maze[i][j].state[UP] = true;
                }
            }
        }
    }
    
    public void bfs() {// 寻找迷宫路径

        queue.offer(maze[startx][starty]);
        while(!queue.isEmpty()){
        	Point top = queue.peek();
            int x = top.getX();
            int y = top.getY();
            if(x == endx && y == endy){
                return;
            }

            if(maze[x][y].state[RIGHT]){
                maze[x][y].state[RIGHT] = false;
                maze[x][y+1].state[LEFT] = false;
                queue.offer(maze[x][y+1]);
                pathrecord[x*side+y+1] = maze[x][y];
            }

            if(maze[x][y].state[DOWN]){
                maze[x][y].state[DOWN] = false;
                maze[x+1][y].state[UP] = false;
                queue.offer(maze[x+1][y]);
                pathrecord[(x+1)*side+y] = maze[x][y];
            }

            if(maze[x][y].state[LEFT]){
                maze[x][y].state[LEFT] = false;
                maze[x][y-1].state[RIGHT] = false;
                queue.offer(maze[x][y-1]);
                pathrecord[x*side+y-1] = maze[x][y];
            }

            if(maze[x][y].state[UP]){
                maze[x][y].state[UP] = false;
                maze[x-1][y].state[DOWN] = false;
                queue.offer(maze[x-1][y]);
                pathrecord[(x-1)*side+y] = maze[x][y];
            }

            queue.poll();
        }
    }
    public void find() {// 打印迷宫路径搜索的结果
        int x = endx;
        int y = endy;
        
        while(true)
        {
            maze[x][y].val = 2;
            map[x][y] = 2;
            Point point = pathrecord[x*side+y];
            if(point == null){
                break;
            }
            x = point.getX();
            y = point.getY();
        }
            
//        for (int i = 0; i < side; i++) 
//        {
//            for (int j = 0; j < side; j++) 
//            {
//                map[i][j] = maze[i][j].val;
//            }
//        }

//            for (int i = 0; i < side; i++) {
//                for (int j = 0; j < side; j++) {
//                    if(maze[i][j].val == '*'){
//                        System.out.print('*' + " ");
//                    } else {
//                        System.out.print(maze[i][j].val + " ");
//                    }
//                }
//                System.out.println();
//            }
    }
    public int[][] getMaze(){
    	return map;
    }
}