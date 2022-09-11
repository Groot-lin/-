package maze;


import java.io.FileNotFoundException;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application{
	int side;//迷宫长度
	int[][] map;
	MainPane mazePane;//迷宫面板
	int count;//记录迷宫路径条数
	Button bfsfind;//广度优先遍历算法按钮
	Button dfsfind;//深度优先遍历算法按钮
	int index;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
        
		
		
		ImageView menu = new ImageView(new Image("file:H:\\电脑迷宫鼠\\maze.gif"));
		menu.setFitHeight(700);
		menu.setFitWidth(800);//主页图片
		
		Button[] menubt = new Button[5];
		for(int i = 1;i<=5 ;i++)
		{
			menubt[i-1] = createButton(primaryStage,i);
		}
		show(primaryStage,menu,menubt[0],menubt[1],menubt[2],menubt[3],menubt[4]);
		
    
	}
	public void show(Stage primaryStage,ImageView menu,Button menubt1,Button menubt2,Button menubt3,Button menubt4,Button menubt5) {//**********************************总控制台
		Pane menupane = new Pane();
		menupane.getChildren().addAll(menu,menubt1,menubt2,menubt3,menubt4,menubt5);
		
		Scene scene = new Scene(menupane,800,700);
		primaryStage.setTitle("Mazemouse");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public Button createButton(Stage primaryStage,int option){/***********************************************这是按钮**********************************************/
		switch(option)
		{
		case 1 :
			Button menubt1 = new Button("prim");
			menubt1.setPrefHeight(30);
			menubt1.setPrefWidth(100);
			menubt1.setLayoutX(500);	
			menubt1.setLayoutY(200);
			menubt1.setOnAction(e->{
				prim(primaryStage);
			
			});//menubt1
			return menubt1;
		case 2 :
			Button menubt2 = new Button("partition");
			menubt2.setPrefHeight(30);
			menubt2.setPrefWidth(100);
			menubt2.setLayoutX(170);	
			menubt2.setLayoutY(200);
			menubt2.setOnAction(e->{
			try {
				partition(primaryStage);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			});//menubt2
			return menubt2;
		case 3 :
			Button menubt3 = new Button("manual1");
			menubt3.setPrefHeight(30);
			menubt3.setPrefWidth(100);
			menubt3.setLayoutX(100);	
			menubt3.setLayoutY(500);
			menubt3.setOnAction(e->{
			try {
				manual1(primaryStage);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			});//menubt3
			return menubt3;
		case 4 :
			Button menubt4 = new Button("manual2");
			menubt4.setPrefHeight(30);
			menubt4.setPrefWidth(100);
			menubt4.setLayoutX(350);	
			menubt4.setLayoutY(500);
			menubt4.setOnAction(e->{
			try {
				manual2(primaryStage);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			});//menubt4
			return menubt4;
		case 5 :
			Button menubt5 = new Button("manual3");
			menubt5.setPrefHeight(30);
			menubt5.setPrefWidth(100);
			menubt5.setLayoutX(600);	
			menubt5.setLayoutY(500);
			menubt5.setOnAction(e->{
			try {
				manual3(primaryStage);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			});//menubt5
			return menubt5;
		}
		return new Button();
	}
	/***********************************************************************************这里是生成迷宫*****************************************************************************************/
	public void prim(Stage primaryStage)  {//prim 算法生成迷宫,单路径
		
		
		Text text = new Text(140,50,"Welcome to prim!");
		TextField textfield=new TextField();
		textfield.setEditable(true);
		textfield.setLayoutX(150);
		textfield.setLayoutY(130);
		textfield.setPrefWidth(100);
		textfield.setPrefHeight(40);
		textfield.setPromptText("setSide :");
		textfield.setOnAction(x->{
			String str = textfield.getText();
			side = Integer.parseInt(str);
		});//文本内容
        
		Button bt = new Button("确定");
		bt.setPrefHeight(40);
		bt.setPrefWidth(100);
		bt.setLayoutX(150);	
		bt.setLayoutY(230);
		bt.setOnAction(e->{
			createMaze maze;
			try{
			    maze = new createMaze(side,1,1,1);
				map = maze.getmaze();
		        
		        Button findpath = new Button("findpath");
		        findpath.setPrefHeight(40);
		        findpath.setPrefWidth(100);
		        findpath.setLayoutX(side*10+50);	
		        findpath.setLayoutY((side*10)/2.0-20);
		        findpath.setOnAction(f->{
		        	try {
						int[][] map = createMaze.readfile(side);
						bfsfindpath bfs = new bfsfindpath(map,1,1,side-2,side-2);
						
						MainPane mp = new MainPane(map);
						Scene scene = new Scene(mp,side*10,side*10);
						primaryStage.setScene(scene);
						primaryStage.setTitle("Has been found!");
						primaryStage.show();
						
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        });


		        MainPane mp = new MainPane(map);
		        mp.getChildren().add(findpath);
		        Scene scene = new Scene(mp,side*10+200,side*10);
		        primaryStage.setTitle("prim");
		        primaryStage.setScene(scene);
				primaryStage.show();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
		
        
        
		Pane pane = new Pane();
		pane.getChildren().addAll(textfield,bt,text);
		Scene scene_ = new Scene(pane,400,300);
		primaryStage.setScene(scene_);
		primaryStage.setTitle("what's the side?");
		primaryStage.show();
		
		
		
		
	}
	public void partition(Stage primaryStage) throws FileNotFoundException {//递归分隔法生成迷宫,多路径
		
		Text text = new Text(130,50,"Welcome to partition!");
		TextField textfield=new TextField();
		textfield.setEditable(true);
		textfield.setLayoutX(150);
		textfield.setLayoutY(130);
		textfield.setPrefWidth(100);
		textfield.setPrefHeight(40);
		textfield.setPromptText("setSide :");
		textfield.setOnAction(x->{
			String str = textfield.getText();
			side = Integer.parseInt(str);
		    count = 0;
		});//文本内容
        
		Button bt = new Button("确定");
		bt.setPrefHeight(40);
		bt.setPrefWidth(100);
		bt.setLayoutX(150);	
		bt.setLayoutY(230);
		bt.setOnAction(e->{
			createMaze maze;
		    try{
			    maze = new createMaze(side,1,1,2);
				int[][] map = maze.getmaze();
		        
				dfsfind = new Button("dfsfind");
		        dfsfind.setPrefHeight(20);
		        dfsfind.setPrefWidth(100);
		        dfsfind.setLayoutX(50);	
		        dfsfind.setLayoutY(side*2);
		        dfsfind.setOnAction(f->{
		        	try {
						int[][] endmap = createMaze.readfile(side);
						dfsfindpath dfs = new dfsfindpath(endmap,1,1,side-2,side-2);
						int index = dfs.getminindex();
						
						endmap = dfs.findpath(index);
					    MainPane mazePane = new MainPane(endmap);
					    
					    Scene scene = new Scene(mazePane,side*10,side*10);
					    primaryStage.setScene(scene);
						primaryStage.show();
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        });//找多路径
		        
		        bfsfind = new Button("bfsfind");
		        bfsfind.setPrefHeight(20);
		        bfsfind.setPrefWidth(100);
		        bfsfind.setLayoutX(50);	
		        bfsfind.setLayoutY(side*6);
		        bfsfind.setOnAction(f->{
		        	try {
						int[][] endmap = createMaze.readfile(side);
						bfsfindpath bfs = new bfsfindpath(endmap,1,1,side-2,side-2);
						
//						mazePane = clear();
						mazePane = new MainPane(endmap);
					    Scene scene = new Scene(mazePane,side*10,side*10);
				        primaryStage.setScene(scene);
						primaryStage.show();

					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
		        });//找最短路径
                
                Pane contorl = new Pane();//控制面板
                contorl.setLayoutX(side*10);
                contorl.setLayoutY(0);
                contorl.setPrefHeight(side*10);
                contorl.setPrefWidth(200);
                contorl.getChildren().addAll(dfsfind,bfsfind);

		        mazePane = new MainPane(map);
		        Pane zong = new Pane();
		        zong.getChildren().addAll(contorl,mazePane);//一个总面板装两个子面板
		        Scene scene = new Scene(zong,side*10+200,side*10);
		        primaryStage.setTitle("Has been found!");
		        primaryStage.setScene(scene);
				primaryStage.show();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
		
        
        
		Pane pane = new Pane();
		pane.getChildren().addAll(textfield,bt,text);
		Scene scene = new Scene(pane,400,300);
		primaryStage.setScene(scene);
		primaryStage.setTitle("what's the side?");
		primaryStage.show();

	}
	public void manual1(Stage primaryStage) throws FileNotFoundException {//手动生成迷宫1
		try{
		    createMaze maze = new createMaze(25,1,1,3);
			map = maze.getmaze();
			int side = map.length;
	        
	        Button findpath = new Button("findpath");
	        findpath.setPrefHeight(40);
	        findpath.setPrefWidth(100);
	        findpath.setLayoutX(side*10+50);	
	        findpath.setLayoutY((side*10)/2.0-20);
	        findpath.setOnAction(f->{
	        	bfsfindpath bfs = new bfsfindpath(map,1,1,side-2,side-2);

				MainPane mp = new MainPane(map);
				Scene scene = new Scene(mp,side*10,side*10);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Has been found!");
				primaryStage.show();
	        });
	        MainPane mazePane = new MainPane(map);
	        mazePane.getChildren().add(findpath);
			Scene scene = new Scene(mazePane,side*10+200,side*10);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Maze");
			primaryStage.show();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}      
	}
	public void manual2(Stage primaryStage) throws FileNotFoundException {//手动生成迷宫2
		try{
		    createMaze maze = new createMaze(51,1,1,4);
			map = maze.getmaze();
			int side = map.length;
	        
	        Button findpath = new Button("findpath");
	        findpath.setPrefHeight(40);
	        findpath.setPrefWidth(100);
	        findpath.setLayoutX(side*10+50);	
	        findpath.setLayoutY((side*10)/2.0-20);
	        findpath.setOnAction(f->{
	        	bfsfindpath bfs = new bfsfindpath(map,1,1,side-2,side-2);

				MainPane mp = new MainPane(map);
				Scene scene = new Scene(mp,side*10,side*10);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Has been found!");
				primaryStage.show();
	        });
	        MainPane mazePane = new MainPane(map);
	        mazePane.getChildren().add(findpath);
			Scene scene = new Scene(mazePane,side*10+200,side*10);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Maze");
			primaryStage.show();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}      
	}
	public void manual3(Stage primaryStage) throws FileNotFoundException {//手动生成迷宫3
		try{
		    createMaze maze = new createMaze(101,1,1,5);
			map = maze.getmaze();
			int side = map.length;
	        
	        Button findpath = new Button("findpath");
	        findpath.setPrefHeight(40);
	        findpath.setPrefWidth(100);
	        findpath.setLayoutX(side*10+50);	
	        findpath.setLayoutY((side*10)/2.0-20);
	        findpath.setOnAction(f->{
	        	bfsfindpath bfs = new bfsfindpath(map,1,1,side-2,side-2);

				MainPane mp = new MainPane(map);
				Scene scene = new Scene(mp,side*10,side*10);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Has been found!");
				primaryStage.show();
	        });
	        MainPane mazePane = new MainPane(map);
	        mazePane.getChildren().add(findpath);
			Scene scene = new Scene(mazePane,side*10+200,side*10);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Maze");
			primaryStage.show();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}      
	}
	
}


