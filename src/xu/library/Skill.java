package xu.library;

import xu.library.inf.IJudgmentLibrary;
import xu.model.Creature;
import xu.model.World;
import xu.tool.Run;

/**
 * 技能库类
 * 
 * @author 徐川江
 *
 */
public final class Skill implements IJudgmentLibrary {

	/**
	 * 移动技能
	 * 
	 * @param c
	 *            生物
	 * @param w
	 *            世界
	 * @param move
	 *            移动方向（数字）
	 */
	public static int move( World w,Creature c, int move) {
		int x = c.getX();
		int y = c.getY();

		switch (move) {
		case 5:
			return 1;

		case 4:
			x = x - 1;
			break;
		case 6:
			x = x + 1;
			break;
		case 2:
			y = y - 1;
			break;
		case 8:
			y = y + 1;
			break;

		case 1:
			x = x - 1;
			y = y - 1;
			break;
		case 3:
			x = x + 1;
			y = y - 1;
			break;
		case 7:
			x = x - 1;
			y = y + 1;
			break;
		case 9:
			x = x + 1;
			y = y + 1;
			break;
		default:
			return 1;
		}

		if (x < 0 || y < 0) {
			return 1;
		}

		if (w.getCreatureMap()[x][y] == null) {
			w.getCreatureMap()[x][y] = c;
			w.getCreatureMap()[c.getX()][c.getY()] = null;
			c.setX(x);
			c.setY(y);
		} else {
			CreatureRun.injure(c, w.getCreatureMap()[x][y], w, c.getAttack());
		}
		return 1;
	}

	/**
	 * 技能，侦察术
	 * 
	 * 不消化回合，无消耗 根据xy获取所在敌人的数据
	 */
	public static int zc(World w,Creature c, int x, int y) {
		if (w.getCreatureMap()[x][y] == null)
			Run.addShowInfo("这个位置没有生物");
		else {
			Creature sw = w.getCreatureMap()[x][y];
			//System.out.println("横坐标" + x + "  纵坐标" + y);
			System.out.println("生命值" + sw.getLife()+"/"+sw.getLifeMax());
			System.out.println("体型" + sw.getBody());
			System.out.println("攻击力" + sw.getAttack());
		}
		
		return 0;
	}

	/**
	 * 技能，远程攻击
	 * 
	 * 消化回合，消耗饥饿值 ，饥饿不够则消耗血 根据xy获取所在敌人的数据
	 */
	public static int yc(World w,Creature c, int x, int y){
		if(x>(c.getX()+c.getVision())||x<(c.getX()-c.getVision())||y>(c.getY()+c.getVision())||y<(c.getY()-c.getVision())){
			Run.addShowInfo("超过视野范围,技能发动失败");
			return 1;
		}
		if (w.getCreatureMap()[x][y] == null)
			Run.addShowInfo("攻击砸空了");
		else 
			CreatureRun.injure(c, w.getCreatureMap()[x][y], w,c.getBody());
		return 1;

	}

	/**
	 * 技能，狂暴
	 * 
	 * 消化回合，消耗饥饿值 ，增加移动速度，增加攻击力 根据xy获取所在敌人的数据
	 */
	public static int kb(World w,Creature c, int x, int y){
		return 0;

	}

}
