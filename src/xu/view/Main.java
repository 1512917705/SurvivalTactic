package xu.view;

import java.util.List;
import java.util.Scanner;

import javax.swing.text.html.HTMLDocument.Iterator;

import xu.library.Behavior;
import xu.library.CreatureRun;
import xu.library.Judgment;
import xu.library.WorldRun;
import xu.model.Creature;
import xu.model.World;
import xu.model.base.BNode;
import xu.tool.Example;
import xu.tool.Run;

public class Main {

	/**
	 * 主函数，游戏入口
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		//去Example里拿模板世界，模板生物
		World w = Example.getWorld(5 , 5,5);
		
		for(;;){
			if (Run.roundRun(w, sc)==1)//运行一回合
			{
				System.out.println("你死了，一切结束");
			}
		}
	}
}
