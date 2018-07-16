package xu.library;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import xu.library.inf.IJudgmentLibrary;
import xu.model.Creature;
import xu.model.FreeWill;
import xu.model.MainPar;
import xu.model.World;
import xu.model.base.BJudgment;
import xu.model.base.BNode;

/**
 * 判定库类
 * 存放所有的ai判定
 * 
 * @author 徐川江
 *
 */
public final class Judgment implements IJudgmentLibrary {
	static Random random = new Random();

	/**
	 * 判定库
	 */
	static List<BJudgment> JUDGMENT = new ArrayList<BJudgment>();// 这个list包含所有的判定，每次获取新节点，去里面随机捞一个
	static {
		JUDGMENT.add(FreeWillJud.INSTANCE); // 从单例模式中取节点
		JUDGMENT.add(FreeWillJud.INSTANCE);
		JUDGMENT.add(lifeBai.INSTANCE);
	}

	/**
	 * 测试判定节点
	 * @author 徐川江
	 *
	 */
	public enum Test implements BJudgment {// 单例模式放内容
		INSTANCE;
		public static MainPar example;// 数据模板

		public boolean run(World w, Creature c, MainPar mainPar, FreeWill freePar) {
			return false;
		}
	}
	/*
	 * public static class Test extends BJudgment { public static MainPar
	 * example;
	 * 
	 * public void whateverMethod() { } public static boolean run(World w,
	 * Creature c, MainPar mainPar, FreeWill freePar) {
	 * System.out.println("drawshape"); return false; } }
	 */

	/**
	 * 自由意志判定，内容根据里面的参数变化
	 * 
	 * @author 徐川江
	 *
	 */
	public enum FreeWillJud implements BJudgment {// 单例模式放内容
		INSTANCE;
		public static MainPar example;// 参数案例

		public boolean run(World w, Creature c, MainPar mainPar, FreeWill freePar) {
			int a = freePar.getFreeWill()[mainPar.getMainpar()[0]];// 第一个参数
			int x = freePar.getFreeWill()[mainPar.getMainpar()[1]];// >或=或>=的标志
			int b = freePar.getFreeWill()[mainPar.getMainpar()[2]];// 第二个参数

			switch (x) {
			case 0:
				return a > b;
			case 1:
				return a >= b;
			case 2:
				return a == b;
			default:
				return false;
			}
		}
	}

	/**
	 * 视野内是否敌人
	 * 
	 * @author 徐川江
	 *
	 */
	public enum diren implements BJudgment {
		INSTANCE;
		public static MainPar example;// 数据模板

		public boolean run(World w, Creature c, MainPar mainPar, FreeWill freePar) {
			int x = w.getPlayer().getX();
			int y = w.getPlayer().getY();
			int v = w.getPlayer().getVision();

			for (int i = 1; i < 2 * v; i++)//
			{
				for (int j = 1; j < 2 * v; j++)// 生物
				{
					Creature[][] sw = w.getCreatureMap();

					if ((j + x - v) < 0 || (-i + y + v) < 0) {
						continue;
					}

					if (sw[j + x - v][-i + y + v] != null && sw[j + x - v][-i + y + v] != c) {
							return true;
					}
				}
			}
			return false;
		}
	}

	/**
	 * 对自己的血量百分比判定
	 * 
	 * @author 徐川江
	 *
	 */
	public enum lifeBai implements BJudgment {// 单例模式放内容
		INSTANCE;
		public static MainPar example = new MainPar(new int[] { 80 }, new int[] { 1 }, new int[] { 100 },
				new int[] { 100 });// 数据模板

		public boolean run(World w, Creature c, MainPar mainPar, FreeWill freePar) { 
			return c.getLife() >= (mainPar.getMainpar()[0]*c.getLifeMax()/100);
		}
	}

}
