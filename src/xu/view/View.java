package xu.view;

import java.util.Scanner;

import xu.model.Creature;
import xu.model.Player;
import xu.model.World;
import xu.tool.Example;

/**
 * 视图类，未完成
 * @author 徐川江
 *
 */
public class View {
	
	/**
	 * 主界面，未使用
	 */
	public static void window(){

        Scanner sc = new Scanner(System.in); 
        
        for(;;)
        {
		System.out.println("欢迎来到生存斗争，请输入1-4");
		System.out.println("1 开始新游戏");
		System.out.println("2 加载存档");
		System.out.println("3 设置");
		System.out.println("4 退出");
		

        int sz = sc.nextInt(); 
        if(sz==1)
        {
        	show(Example.getWorld(5,5,5));
        	continue;
        }
        if(sz==2)
        	continue;
        if(sz==3)
        	continue;
        if(sz==4)
        	break;

		System.out.println("您的输入有误");
		
	}
	}
	
	/**
	 * 地图绘制，每回合绘制一次
	 * @param world
	 */
	public static void show(World world){
		int x = world.getPlayer().getX();
		int y = world.getPlayer().getY();
		int v = world.getPlayer().getVision();
		

		for(int i=1;i<2*(v+1);i++)//第一行
		{	
			if((i+x-v-1)<10)
			{

				if((i+4)%5==0)
					{
					if(i+x-v-1>=0)
					{
						System.out.print(i+x-v-1);
						System.out.print(" ");
					}
					else
						System.out.print(i+x-v-1);
					}
				else
					System.out.print("  ");
				}
			else
			{
				if((i+4)%5==0)
					System.out.print(i+x-v-1);
				else
					System.out.print("  ");
				
			}
		}
		System.out.println(" ");
		
		for(int i=1;i<2*v;i++)//中间
		{

			if((-i+y+v)<10)//第一个
			{

				if((i+5)%5==0)
					{
					if(-i+y+v>=0)
					{
						System.out.print(-i+y+v);
						System.out.print(" ");
					}
						else
							System.out.print(-i+y+v);
					}
				else
					System.out.print("  ");
				}
			else
			{
				if((i+5)%5==0)
					System.out.print(-i+y+v);
				else
					System.out.print("  ");
			}
			
			for(int j=1;j<2*v;j++)//生物
				{
				Creature[][] c = world.getCreatureMap();
				
				if((j+x-v)<0||(-i+y+v)<0||(j + x - v)>world.getMax_x()||(-i + y + v)>world.getMax_y())
				{
					System.out.print("# ");
					continue;
				}
				
				if( c[j+x-v][-i+y+v] == null )
					System.out.print(". ");
				else
					if( (Creature)c[j+x-v][-i+y+v] == world.getPlayer() )
						System.out.print("@ ");
					else
					System.out.print("Z ");
				}

			if((-i+y+v)<10)//尾巴
			{

				if((i+5)%5==0)
					{
						System.out.print(-i+y+v);
						System.out.print(" ");
					}
				else
					System.out.print("  ");
				}
			else
			{
				if((i+5)%5==0)
					System.out.print(-i+y+v);
				else
					System.out.print("  ");
			}
			System.out.println("");
		}

		for(int i=1;i<2*(v+1);i++)
		{	
			if((i+x-v-1)<10)
			{

				if((i+4)%5==0)
					{
					if(i+x-v-1>=0)
					{
						System.out.print(i+x-v-1);
						System.out.print(" ");
					}
					else
						System.out.print(i+x-v-1);
					}
				else
					System.out.print("  ");
				}
			else
			{
				if((i+4)%5==0)
					System.out.print(i+x-v-1);
				else
					System.out.print("  ");
				
			}
		}
		System.out.println(" ");
	}

	public void newGame(){
		
		
	}

}
