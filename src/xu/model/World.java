package xu.model;

import java.util.ArrayList;
import java.util.List;

import xu.model.Creature;
import xu.tool.Example;

/**
 * 世界类
 * 存放世界数据
 * 
 * @author 徐川江
 *
 */
public class World {
/*
 每个生物的速度分为，移动速度，施法速度，
如果此回合选择移动，那么行动时候，减去的行动点数，是移动速度。
如果此回合选择施法，那么行动时候，减去的行动点数，是施法速度
未完成
*/
	List<Creature> creList = new ArrayList<Creature>();//所有可运行的生物
	private Creature[][] creatureMap ;//世界就是一个x*y大小的存放生物的网格
	private int Max_x ;// 横
	private int Max_y ;// 纵
	private int num ;// 世界内生物总数量
	private long round ;//回合，和生物等级上限挂钩
	private double fight ;//灾难系数，攻击力和饥饿的系数,生物的攻击力和防御力要乘以它，1是正常
	private Creature player;//如果玩家模式，以玩家为中心，进行游戏

	//我选择开发速度，而不是运行速度
	//public BCreature firstOne = new Creature();// 头结点不放东西
	//public BCreature lastOne = new Creature();// 尾结点不放东西
	
	/*
	 * 测试创造世界
	public World( ) {
		creatureMap = new Creature[100][100];
		Max_x = 99;
		Max_y = 99;
		round = 0;
		fight = 1;
		player = new Creature();
		creatureMap[5][5]=player;
		player.setX(5);
		player.setY(5);
		
		creList.add(player);
	}
	 */

/**
 * 世界类 构造函数
 * @param Max_x 长
 * @param Max_y 宽
 * @param cre 初始怪物列表，注意放好坐标信息
 * @param play玩家
 */
	public World(int Max_x, int Max_y,Creature[] cre,Creature play) {
		//环境设置
		round = 0;
		fight = 1;
		
		//生物设置
		creatureMap = new Creature[Max_x][Max_y];
		this.Max_x = Max_x - 1;
		this.Max_y = Max_y - 1;
		this.player = play;
		creList.add(play);
		creatureMap[player.getX()][player.getY()]=player;
		
		 for(Creature c : cre){
				creatureMap[c.getX()][c.getY()]=c;
				creList.add(c);
		 }
	}
	


	public int getMax_x() {
		return Max_x;
	}

	public void setMax_x(int max_x) {
		Max_x = max_x;
	}

	public int getMax_y() {
		return Max_y;
	}

	public void setMax_y(int max_y) {
		Max_y = max_y;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public long getRound() {
		return round;
	}

	public void setRound(long round) {
		this.round = round;
	}

	public double getFight() {
		return fight;
	}

	public void setFight(double fight) {
		this.fight = fight;
	}
 

	public Creature[][] getCreatureMap() {
		return creatureMap;
	}

	public void setCreatureMap(Creature[][] creatureMap) {
		this.creatureMap = creatureMap;
	}

	public List<Creature> getCreList() {
		return creList;
	}

	public void setCreList(List<Creature> creList) {
		this.creList = creList;
	}

	public Creature getPlayer() {
		return player;
	}

	public void setPlayer(Creature player) {
		this.player = player;
	}

	
	
}
