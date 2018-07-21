package xu.library;

import java.io.Serializable;
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
public final class Behavior implements IBehaviorLibrary, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6495142983255608790L;
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
	 * 狂暴行为
	 * 
	 * @author 徐川江
	 *
	 */
	public enum kuangbao implements IBehavior {// 单例模式放内容
		INSTANCE;
		public static MainPar example;

		@Override
		public boolean run(World w, Creature c, MainPar mainPar, FreeWill freePar) {

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
			 * //天启类型变异（未完成） //增和删的概率必须一致，它们必须在一个地方进行判定
			 *
			 * 后代变异数量 在0~4中产生一个随机数，决定有几个后代变异，
			 * 
			 * 自由意志参数变异 每个生物在0-n之间，产生一个随机数，决定自由意志参数是否变异，何种变异。
			 * 
			 * 基因组变异操作如下 在0~基因组个数中，产生一个随机数，决定是哪个基因组是否变异，变异类型，（新增基因组，只增加一个行为节点，删除基因层）
			 * 
			 * 节点变异
			 * 在0~在该基因组内节点最大数中+x+x+y，产生一个随机数，决定哪个节点产生变异，为n~n+x则是节点增加（包含自由意志判定），
			 * 
			 * 节点内变异 在0~9中产生一个随机数，0~3则值变异，4~6则是自由意志行为变异，7~8则是节点改
			 * 
			 * （有性繁殖待定）
			 */
			int NumberOfMutantOrganisms = Reproduction.NumberOfMutantOrganisms(c);// 后代变异数量

			for (int x = 0, n = 0; x < c.getBreednum(); x++) {
				if(n<NumberOfMutantOrganisms){//开始变异
					int NumberOfVariantNodes = Reproduction.NumberOfVariantNodes(c);// 有几个次变异
					
					Reproduction.starVar(c);//开始变异
					
					n++;
				}
			}


			return false;
		}

	}
}
