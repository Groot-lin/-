package maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class dfsfindpath {
	ArrayList<ArrayList<Point>> res = new ArrayList<>();
    //创建一个 ArrayList<Point> line 记录某条路径
    ArrayList<Point> line = new ArrayList<>();
    private int[][] maze;
    static int v[][];//判断是否走过
    private int[][] next = {{0,1},{1,0},{0,-1}, {-1,0} };//方向数组,右下左上
    private int side;//迷宫大小
    private static final int wall = 0;
    private static final int road = 1;
    private int startx,starty;
    private int endx,endy;
    private int minindex;
//    public static void main(String[] args) throws FileNotFoundException {
//    	createMaze map = new createMaze(51,1,1,2);
//    	dfsfindpath t = new dfsfindpath(map.getmaze(),1,1,map.getmaze().length-2,map.getmaze().length-2);
//    }
    
    dfsfindpath(int[][] maze,int startx,int starty,int endx,int endy) throws FileNotFoundException{
    	this.maze = maze;
    	v = new int[maze.length][maze.length];
    	side = maze.length;
    	this.startx = startx;
    	this.starty = starty;
    	this.endx = endx;
    	this.endy = endy;
    	dfs(startx,starty);
    	printmin();
    	savepath();
    }
    
    public void dfs(int i,int j) {//第一次调用dfs时参数就是startx,starty;深度优先遍历
        if (i < 1 || i > side-2 || j < 1 || j > side-2 || maze[i][j] == wall || maze[i][j] == 2) {
            return;
        }
        
        if (i == endx && j == endy) {
        	line.add(new Point(road,i,j));
            res.add(new ArrayList<>(line));//加入链表
            line.remove(line.size() - 1);
        }
        else {
            
        	line.add(new Point(road,i,j));
            
            maze[i][j] = 2;
           
            for(int k=0;k<4;k++) 
            {                
          		 int tx=i+next[k][0];                   
          		 int ty=j+next[k][1];
          		 if(maze[tx][ty]==road&&v[tx][ty]==0) 
          		 {          			
          		      v[tx][ty]=1;      
          		      dfs(tx,ty);       
          			  v[tx][ty]=0;      
                 }
            }
            // 回溯
            maze[i][j] = road;
            if(line.size()>0)
            	line.remove(line.size() - 1);
            }
    }
    public int[][] getmaze(){
    	return maze;
    }
    public int getminindex() {
    	return minindex;
    }
    public void printmin() {
    	ArrayList<Point> step;
    	
    	int size = Integer.MAX_VALUE;
        for (int i = 0; i < res.size(); i++) 
        {
            if (res.get(i).size() < size) {
                size = res.get(i).size();
                minindex = i;
            }
        }
        
    }
    public void savepath() throws FileNotFoundException
    {
    	File file = new File("path.txt");
    	try(
    		PrintWriter output = new PrintWriter(file);
    	){
    		int size = res.size();
    		for(int o = 0;o<size;o++)
    		{
    			output.println(o);
    			int[][] map = findpath(o);
    			for(int i = 0;i<side;i++)
    			{
    				for(int j = 0;j<side;j++)
    				{
    					output.print(map[i][j]);
						output.print(' ');
    				}
    				output.println();
    		    }
    		}
    	}
    }
    public void findpath(ArrayList<Point> line) {
    	for(int i  = 0;i<maze.length;i++)
    	{
    		for(int j = 0;j<maze.length;j++)
    		{
    			if(maze[i][j] == 2)
    				maze[i][j] = road;
    		}
    	}//更新
    	for(int i = 0;i<line.size();i++)
    	{
    		maze[line.get(i).getX()][line.get(i).getY()] = 2;
    	}
    }

    
    public int[][] findpath(int index) {
    	for(int i  = 0;i<maze.length;i++)
    	{
    		for(int j = 0;j<maze.length;j++)
    		{
    			if(maze[i][j] == 2)
    				maze[i][j] = road;
    		}
    	}
    	ArrayList<Point> line = res.get(index);
    	
    	for(int i = 0;i<line.size();i++)
    	{
    		maze[line.get(i).getX()][line.get(i).getY()] = 2;
    	}
    	return maze;
    }
     
//    public void printMaze() {//用来输出的
//    	for (int i = 0; i < maze.length; i++) {
//    		for (int j = 0; j < maze[0].length; j++) {
//    			if (maze[i][j] == 0)
//    				System.out.print("[]");
//    			else if(maze[i][j] == 1)
//    				System.out.print("  ");
//    			else
//    				System.out.print("&");
//    		}
//    		System.out.println();
//    	}
//    }
//    public void printroute(ArrayList<Point> line) {
//    	for(int i = 0;i<line.size();i++)
//    	{
//    		System.out.print("("+line.get(i).getX()+","+line.get(i).getY()+")");
//    	}
//    	System.out.println();
//    	System.out.println();
//    }//用来输出的
}
    /********************************************************************************************************************************************************/
//    public static boolean setWay(int[][] map,int i,int j){//这个也是dfs
//    	int side = map.length-2;
//        if (map[side][side] == 2)
//        {
//            return true;
//        }
//        else 
//        {
//            if (map[i][j] == 1)
//            {//表示这个点还没有走过
//                //按照策略走
//                map[i][j] = 2;//假设能走通
//                if (setWay(map,i+1,j)){
//                    return true;
//                }else if (setWay(map,i,j+1)){
//                    return true;
//                }else if (setWay(map,i-1,j)){
//                    return true;
//                }else if (setWay(map,i,j-1)){
//                    return true;
//                }else {
//                    //说明该点走不通是死路
//                    map[i][j] = 1;
//                    return false;
//                }
//            }
//            else 
//            {
//                //说明该点为1,2,3 走不通
//                return false;
//            }
//        }
//    }
