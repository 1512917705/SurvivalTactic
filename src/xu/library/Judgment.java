package xu.library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import xu.library.inf.IJudgmentLibrary;
import xu.model.Creature;
import xu.model.FreeWill;
import xu.model.MainPar;
import xu.model.World;
import xu.model.base.IBehavior;
import xu.model.base.IJudgment;
import xu.model.base.INode;

/**
 * 判定库类 存放所有的ai判定
 * 
 * @author 徐川江
 *
 */
public final class Judgment implements IJudgmentLibrary , Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4069389911923399453L;
	static Random random = new Random();
	/**
	 * 判定库
	 */
	static List<IJudgment> JUDGMENT = new ArrayList<IJudgment>();// 这个list包含所有的判定，每次获取新节点，去里面随机捞一个
	static {
		JUDGMENT.add(FreeWillJud.INSTANCE); // 从单例模式中取节点
		JUDGMENT.add(FreeWillJud.INSTANCE);
		JUDGMENT.add(lifeBai.INSTANCE);
	}
	/*
	 * 所有的判定
	 * 采取坐标锁定的方式,但是可以适当增加其他类型锁定判定
	 * 
每个生物，有一个主锁定参数，为攻击法术和敌人判定，提供敌人坐标，
有视野*视野的生物记忆空间，可以记住视野内所有敌人的位置，
有x个永久记忆坐标，我也不知道有啥用，可能每个种族的生物可以记住它的出生地，每个种族会有势力范围
	 * 
	 * 敌人判定
	 * 视野内是否有敌人
	 * 
	 * 血量判定，饱腹值判定
	 * 自身血量是否高于n%
	 * 自身血量是否高于n
	 * 自身饱腹值是否大于n%
	 * 自身饱腹值是否大于n
	 * xy坐标敌人的血量是否高于n%
	 * xy坐标敌人的血量是否高于n
	 * xy坐标敌人的饱腹值是否高于n%
	 * xy坐标敌人的饱腹值是否高于n
	 * 
	 */

	/**
	 * 获取任意行为节点
	 * @return
	 */
	public static IJudgment getRandomNode(){
		return JUDGMENT.get(
				random.nextInt(JUDGMENT.size()));
	}
	/**
	 * 测试判定节点
	 * 
	 * @author 徐川江
	 *
	 */
	public enum Test implements IJudgment {// 单例模式放内容
		INSTANCE;
		public static MainPar example;// 数据模板

		public boolean run(World w, Creature c, MainPar mainPar, FreeWill freePar) {
			return false;
		}
		@Override
		public MainPar getExample() {
			return Reproduction.getExample(example);
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
	public enum FreeWillJud implements IJudgment {// 单例模式放内容
		INSTANCE;
		public static MainPar example;// 参数案例

		public boolean run(World w, Creature c, MainPar mainPar, FreeWill freePar) {
			int a = freePar.getFreeWill().get(mainPar.getMainpar()[0]);// 第一个参数
			int x = freePar.getFreeWill().get(mainPar.getMainpar()[1]);// >或=或>=的标志
			int b = freePar.getFreeWill().get(mainPar.getMainpar()[2]);// 第二个参数

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
		@Override
		public MainPar getExample() {
			return Reproduction.getExample(example);
		}
	}

	/**
	 * 视野内是否敌人
	 * 
	 * @author 徐川江
	 *
	 */
	public enum diren implements IJudgment {
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
		@Override
		public MainPar getExample() {
			return Reproduction.getExample(example);
		}
	}

	/**
	 * 对自己的血量百分比判定
	 * 
	 * @author 徐川江
	 *
	 */
	public enum lifeBai implements IJudgment {// 单例模式放内容
		INSTANCE;
		public static MainPar example = new MainPar(new int[] { 80 }, new int[] { 1 }, new int[] { 100 },
				new int[] { 100 });// 数据模板

		public boolean run(World w, Creature c, MainPar mainPar, FreeWill freePar) {
			return c.getLife() >= (mainPar.getMainpar()[0] * c.getLifeMax() / 100);
		}
		@Override
		public MainPar getExample() {
			return Reproduction.getExample(example);
		}
	}

}
