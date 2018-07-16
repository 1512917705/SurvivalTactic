package xu.library;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import xu.library.Judgment.FreeWillJud;
import xu.library.Judgment.Test;
import xu.library.inf.IBehaviorLibrary;
import xu.model.Creature;
import xu.model.FreeWill;
import xu.model.MainPar;
import xu.model.World;
import xu.model.base.IBehavior;
import xu.model.base.IJudgment;
import xu.model.base.INode;

/**
 * 行为库类 存放所有的ai行为
 * 
 * @author 徐川江
 *
 */
public final class Behavior implements IBehaviorLibrary {
	static Random random = new Random();
	/**
	 * 行为库
	 */
	static List<IBehavior> BEHAVIOR = new ArrayList<IBehavior>();// 这个list包含所有的判定，每次获取新节点，去里面随机捞一个
	static {
		BEHAVIOR.add(Test.INSTANCE);// 从单例模式中取节点
		BEHAVIOR.add(Move.INSTANCE); // 问题是参数还是要自己去根据模板来初始化
		BEHAVIOR.add(randomMove.INSTANCE);
	}

	/**
	 * 测试行为节点
	 * 
	 * @author 徐川江
	 *
	 */
	public enum Test implements IBehavior {// 单例模式放内容
		INSTANCE;
		public static MainPar example;

		public boolean run(World w, Creature c, MainPar mainPar, FreeWill freePar) {
			return false;
		}
	}

	/**
	 * 向某个方向移动行为
	 * 
	 * @author 徐川江
	 *
	 */
	public enum Move implements IBehavior {// 单例模式放内容
		INSTANCE;
		public static MainPar example = new MainPar(new int[] { 1 }, new int[] { 9 }, new int[] { 1 },
				new int[] { 100 });

		@Override
		public boolean run(World w, Creature c, MainPar mainPar, FreeWill freePar) {
			int move = mainPar.getMainpar()[0];
			Skill.move(w, c, move);

			return false;
		}

	}

	/**
	 * 随机移动行为
	 * 
	 * @author 徐川江
	 *
	 */
	public enum randomMove implements IBehavior {// 单例模式放内容
		INSTANCE;
		public static MainPar example;

		@Override
		public boolean run(World w, Creature c, MainPar mainPar, FreeWill freePar) {
			int move = random.nextInt(8);
			Skill.move(w, c, move);
			return false;
		}
	}

	/**
	 * 繁殖行为节点
	 * 
	 * @author 徐川江
	 *
	 */
	public enum multiplication implements IBehavior {
		INSTANCE;
		public static MainPar example = new MainPar(new int[] { 1 }, new int[] { 9 }, new int[] { 1 },
				new int[] { 100 });

		@Override
		public boolean run(World w, Creature c, MainPar mainPar, FreeWill freePar) {
			/*
			 * 每个生物每次繁殖，生下四个后代，（未来可能改）
			 * 在0~4中产生一个随机数，决定有几个后代变异，
			 * 每个生物在0-var中，产生一个随机数，决定几个节点变异
			 * 
			 * 每个节点变异操作如下
			 * 在0~基因组个数中，产生一个随机数，决定是哪个基因组变异，为0则是天启类型变异，
			 * 在0~在该基因组内节点最大数中+x+x+y，产生一个随机数，决定哪个节点产生变异，为n~n+x则是节点增加（包含自由意志判定），为n+x~n+2x则是节点删除,为n+2x~n+2x+y则是自由意志值变异,
			 * 在0~9中产生一个随机数，0~3则值变异，4~6则是自由意志行为变异，7~8则是节点改
			 * 
			 * （有性繁殖待定）
			 */
			return false;
		}

	}
}
