package maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

import java.util.Scanner;
import java.util.Stack;

class createMaze {
    private int[][] maze;//手动迷宫
	private int[][] maze1;//prim算法
	private int[][] maze2;//递归分割
	private static final int road = 1;//0代表墙，1代表路
	private static final int wall = 0;
	private int side;
	private int option;
	
	
    createMaze(int side,int start_x,int start_y,int option) throws FileNotFoundException{
    	//自动生成的构造方法
    	this.side = side;
    	this.option = option;
    	switch(option)
		{
		case 1 :
			 maze1 = new int[side][side];
		     initializeMaze1(side,start_x,start_y);//prim算法
		     save();
		     break;
		case 2 :
			maze2 = new int[side][side];
	    	createMaze2();//递归分割算法
	    	save();
	    	break;
	    default :
	    	read();//手动
	    	break;
		}
    }
    
    public int[][] getmaze(){
		switch(option)
		{
		case 1 :
			return maze1;
		case 2 :
			return maze2;
		default :
			return maze;	
		}
		
	}
    
	public void initializeMaze1(int side,int start_x,int start_y){
		//设置尺寸起点并初始化迷宫
    	for(int i = 0;i<side;i++)
        {
        	for(int j = 0;j<side;j++)
        	{
        		maze1[i][j] = wall;
        	}
        }
    	
    	for (int i = 0; i < side; i++)
    	{
    		for (int j = 0; j < side; j++)
    		{
    			if (i != 0 && j != 0 && i != side - 1 && j != side - 1)
    			{
    				if (i % 2 != 0)
    					if (j % 2 == 1)
    						maze1[i][j] = road;
    			}
    		}
    	}//初始化迷宫使之有外围墙，一系列的待选路点
    	
    	prim(start_x,start_y);

    	for (int i = 0; i < side; i++)
    	{
    		for (int j = 0; j < side; j++)
    			if (maze1[i][j] == 5)
    				maze1[i][j] = 1;
    	}
    	maze1[start_x][start_y] = 1;//设置起点和终点为1
    	maze1[side - 2][side - 2] = 1;
    }
	
	public void prim(int X_index, int Y_index) {
		//随机迷宫通路
		int rand_position, x, y;
		boolean flag = false;
		x = X_index;
		y = Y_index;
		
		while (true)
		{
			flag = false;
			flag = IsHaveNeighbor(X_index, Y_index);
			if (!flag)
			{
				return;
			}
			else
			{
				maze1[X_index][Y_index] = 5;
				x = X_index;
				y = Y_index;
				while (true)
				{
					rand_position = (int)(Math.random()*4);
					if (rand_position == 0 && X_index >= 3 && maze1[X_index - 2][Y_index] == 1)
					{
						X_index = X_index - 2;
					}
					else if (rand_position == 1 && X_index < side - 3 && maze1[X_index + 2][Y_index] == 1)
					{
						X_index = X_index + 2;
					}
					else if (rand_position == 2 && Y_index >= 3 && maze1[X_index][Y_index - 2] == 1)
					{
						Y_index -= 2;
					}
					else if (rand_position == 3 && Y_index < side - 3 && maze1[X_index][Y_index + 2] == 1)
					{
						Y_index += 2;
					}
					
					maze1[(x + X_index) / 2][(y + Y_index) / 2] = 5;
					
					maze1[X_index][Y_index] = 5;
					
					prim(X_index, Y_index);
					
					break;
				}
			}
		}
	}
    
	public boolean IsHaveNeighbor(int X_index, int Y_index)
	{
		if ((X_index >= 3 && maze1[X_index - 2][Y_index] == 1) || (X_index < side - 3 && maze1[X_index + 2][Y_index] == 1) || (Y_index >= 3 && maze1[X_index][Y_index - 2] == 1) || (Y_index < side - 3 && maze1[X_index][Y_index + 2] == 1))
			return true;
		return false;
	}
	
	/******************打印********************/
	public static void printMaze(int[][] map) {
    	for(int i = 0;i<map.length;i++)
        {
        	for(int j = 0;j<map[i].length;j++)
        	{
        		System.out.print(map[i][j]+" ");
        	}
        	System.out.println();
        }
    }
	/*******************存盘 ************************/
	public void save() throws FileNotFoundException {
		File file = new File("automatic.txt");
		try(
		    PrintWriter output = new PrintWriter(file);
	    ){
			for(int i = 0;i<side;i++)
			{
				for(int j = 0;j<side;j++)
				{
					switch(option)
					{
					case 1:
						output.print(maze1[i][j]);
						output.print(' ');
						break;
					case 2:
						output.print(maze2[i][j]);
						output.print(' ');
						break;
					}
				}
				output.println();
			}
		}
	}
	
	/*******************读取 ***********************/
	public void read() throws FileNotFoundException{
		int i,j;
		switch(option)
		{
		case 3:
			File file1 = new File("Manual1.txt");
			maze = new int[25][25];
			i = 0;
			j = 0;
			try(
				Scanner input = new Scanner(file1);
			){
				while(input.hasNext())
				{
					if(j<25)
					{
						maze[i][j] = input.nextInt();
						j++;
					}
					else
					{
						maze[i+1][0] = input.nextInt();
						j = 1;
						i++;
					}
				}
			}
			break;
		case 4:
			File file2 = new File("Manual2.txt");
			maze = new int[51][51];
			i = 0;
			j = 0;
			try(
				Scanner input = new Scanner(file2);
			){
				while(input.hasNext())
				{
					if(j<51)
					{
						maze[i][j] = input.nextInt();
						j++;
					}
					else
					{
						maze[i+1][0] = input.nextInt();
						j = 1;
						i++;
					}
				}
			}
			break;
		case 5:
			File file3 = new File("Manual3.txt");
			maze = new int[101][101];
			i = 0;
			j = 0;
			try(
				Scanner input = new Scanner(file3);
			){
				while(input.hasNext())
				{
					if(j<101)
					{
						maze[i][j] = input.nextInt();
						j++;
					}
					else
					{
						maze[i+1][0] = input.nextInt();
						j = 1;
						i++;
					}
				}
			}
			break;
		}
	}	
	public static int[][] readfile(int side) throws FileNotFoundException {//从生成迷宫中读取出来
		File file = new File("automatic.txt");
		int[][] maze = new int[side][side];
		int i = 0;
		int j = 0;
		try(
			Scanner input = new Scanner(file);
		){
			while(input.hasNext())
			{
				if(j<side)
				{
					maze[i][j] = input.nextInt();
					j++;
				}
				else
				{
					maze[i+1][0] = input.nextInt();
					j = 1;
					i++;
				}
			}
		}
		return maze;
		
	}
/*********************************************手动分割线，两种不同算法******************************************************************************************************************************************************************************************************************************/
	public void createMaze2() {

		maze2 = new int[side][side];
		// 初始化迷宫，给迷宫添加一圈外墙
		for (int y = 0; y < side; y++) {
			for (int x = 0; x < side; x++) {
				if (x == 0 || x == side - 1 || y == 0 || y == side - 1)
					maze2[y][x] = wall;
				else
					maze2[y][x] = road;
			}
		}
		division(1, 1, side - 2, side - 2);
		// 设置起点和终点
		maze2[1][1] = road;
		maze2[side - 2][side - 2] = road;
		
	}
	private void connect(int startX, int startY, int endX, int endY) {
        int pos;
        
        Random r = new Random();
		if (startX == endX) {
            pos = startY + r.nextInt((endY - startY )/2+ 1)*2;//在奇数位置开门
            maze2[startX][pos] = road;
        } else if (startY == endY) {
            pos = startX + r.nextInt((endX - startX )/2+ 1)*2;
            maze2[pos][startY] = road;
        } else {
            System.out.println("wrong");
        }
    }
	
    private void division(int x, int y,int height,int width) {
        int xPos, yPos;
        Random r = new Random();
//      如果迷宫的宽度或者高度小于2了就不能再分割了
        if (height < 2 || width < 2)
            return;

        //横着画线，在偶数位置画线
        xPos = x+r.nextInt(height/2)*2+1;// 纵向分割分割线的横坐标
        for (int i = y; i < y + width; i++) {// 横向分割
        	maze2[xPos][i] = wall;
        }

        //竖着画一条线，在偶数位置画线
        yPos=y+r.nextInt(width/2)*2+1;// 横向分割线的纵坐标
        for (int i = x; i < x + height; i++) {// 纵向分割
            maze2[i][yPos] = wall;
        }

        //随机开三扇门，左侧墙壁为1，逆时针旋转
        int isClosed = r.nextInt(4) + 1;
        switch (isClosed) 
        {
        case 1:
        	connect(xPos + 1, yPos, x + height - 1, yPos);// 2
            connect(xPos, yPos + 1, xPos, y + width - 1);// 3
            connect(x, yPos, xPos - 1, yPos);// 4
            connect(xPos, y, xPos, yPos - 1);// 1
            break;
        case 2:
        	connect(xPos, yPos + 1, xPos, y + width - 1);// 3
            connect(x, yPos, xPos - 1, yPos);// 4
            connect(xPos, y, xPos, yPos - 1);// 1
            connect(xPos + 1, yPos, x + height - 1, yPos);// 2
            break;
        case 3:
        	connect(x, yPos, xPos - 1, yPos);// 4
            connect(xPos, y, xPos, yPos - 1);// 1
            connect(xPos + 1, yPos, x + height - 1, yPos);// 2
            connect(xPos, yPos + 1, xPos, y + width - 1);// 3
            break;
        case 4:
        	connect(xPos, y, xPos, yPos - 1);// 1
            connect(xPos + 1, yPos, x + height - 1, yPos);// 2
            connect(xPos, yPos + 1, xPos, y + width - 1);// 3
            connect(x, yPos, xPos - 1, yPos);// 4
            break;
        default:
            break;
        }

        // 左上角
        division(x, y, xPos - x, yPos - y);
        // 右上角
        division(x, yPos + 1, xPos - x, width - yPos + y - 1);
        // 左下角
        division(xPos + 1, y, height - xPos + x - 1, yPos - y);
        // 右下角
        division(xPos + 1, yPos + 1, height - xPos + x - 1, width - yPos + y - 1);

    }

}

