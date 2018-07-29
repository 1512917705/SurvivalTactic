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
	public static int move(World w, Creature c, int move) {
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
	 * 不消耗回合，无消耗 根据xy获取所在敌人的数据
	 */
	public static int zc(World w, Creature c, int x, int y) {
		if (w.getCreatureMap()[x][y] == null)
			Run.addShowInfo("这个位置没有生物");
		else {
			Creature sw = w.getCreatureMap()[x][y];
			// System.out.println("横坐标" + x + " 纵坐标" + y);
			System.out.println("生命值" + sw.getLife() + "/" + sw.getLifeMax());
			System.out.println("体型" + sw.getBody());
			System.out.println("攻击力" + sw.getAttack());
			System.out.println("基因组" + sw.getTree().getGenomeList().size());
			System.out.println("节点" + CreatureRun.NodeNum(sw));
			System.out.println("自由意志"+CreatureRun.FreewillNum(sw));
		}

		return 0;
	}

	/**
	 * 技能，远程攻击
	 * 
	 * 消耗回合，消耗饥饿值 ，饥饿不够则消耗血 根据xy获取所在敌人的数据
	 */
	public static int yc(World w, Creature c, int x, int y) {
		if (x > (c.getX() + c.getVision()) || x < (c.getX() - c.getVision()) || y > (c.getY() + c.getVision())
				|| y < (c.getY() - c.getVision())) {
			Run.addShowInfo("超过视野范围,技能发动失败");
			return 1;
		}
		if (w.getCreatureMap()[x][y] == null)
			Run.addShowInfo("攻击砸空了");
		else
			CreatureRun.injure(c, w.getCreatureMap()[x][y], w, c.getBody());
		return 1;

	}

	/**
	 * 技能，狂暴 因为未做buff，所以先放弃
	 * 
	 * 消耗回合，消耗饥饿值 ，增加移动速度，增加攻击力 根据xy获取所在敌人的数据
	 */
	public static int kb(World w, Creature c, int x, int y) {
		if ((c.getHungerLife() - c.getHungerLifeMax() * 0.3) < 0)// 判定饥饿够不够
			c.setHungerLife(c.getHungerLife() - c.getHungerLifeMax() * 0.3);// 消耗饥饿
		else if ((c.getLife() - c.getLifeMax() * 0.3) > 0)// 判定生命值够不够
			c.setLife(c.getLife() - c.getLifeMax() * 0.3);// 消耗生命值
		else {
			Run.addShowInfo("技能释放失败");
			return 1;
		}

		return 1;
	}

	/**
	 * 技能，瞬移 消耗回合，消耗饥饿值 ，瞬移到视野内任意位置（最大到视野范围，每增加1个格子，消耗增加百分之五，10格消耗百分之百饥饿
	 * 只能使用饥饿值来发动
	 */
	public static int sy(World w, Creature c, int x, int y,int z) {
		if(w.getCreatureMap()[x][y]!=null) {
			Run.addShowInfo("技能释放失败");
			return 1;
		}
		if ((c.getHungerLife() - c.getHungerLifeMax() * 0.1 * z) < 0)// 判定饥饿够不够
			c.setHungerLife(c.getHungerLife() - c.getHungerLifeMax() * 0.1 * z);// 消耗饥饿
		else {
			Run.addShowInfo("技能释放失败");
			return 1;
		}
		w.getCreatureMap()[x][y] = c;
		return 1;
	}
	
	/**
	 * 增加一个只能给其他人使用的回血技能，消耗攻击力*2的饥饿或者血，为其他人回复攻击力/2的血量
	 */
	public static int hx(World w, Creature c, int x, int y) {
		if ((c.getHungerLife() - c.getHungerLifeMax() * 0.3) < 0)// 判定饥饿够不够
			c.setHungerLife(c.getHungerLife() - c.getHungerLifeMax() * 0.3);// 消耗饥饿
		else if ((c.getLife() - c.getLifeMax() * 0.3) > 0)// 判定生命值够不够
			c.setLife(c.getLife() - c.getLifeMax() * 0.3);// 消耗生命值
		else {
			Run.addShowInfo("技能释放失败");
			return 1;
		}

		return 1;
	}

}
