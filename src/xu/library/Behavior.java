package xu.library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import xu.library.Judgment.FreeWillJud;
import xu.library.Judgment.Test;
import xu.library.inf.IBehaviorLibrary;
import xu.model.BehaviorTree;
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
		BEHAVIOR.add(Move.INSTANCE); // 问题是参数还是要自己去根据模板来初始化
		BEHAVIOR.add(multiplication.INSTANCE);// 繁殖
		BEHAVIOR.add(randomMove.INSTANCE);// 随意移动
		BEHAVIOR.add(randomMove.INSTANCE);// 随意移动
		BEHAVIOR.add(randomMove.INSTANCE);// 随意移动
	}

	/**
	 * 获取任意行为节点
	 * 
	 * @return
	 */
	public static IBehavior getRandomNode() {
		return BEHAVIOR.get(random.nextInt(BEHAVIOR.size()));
	}

	/**
	 * 测试行为节点
	 * 
	 * @author 徐川江
	 *
	 */
	public enum Test implements IBehavior {// 单例模式放内容
		INSTANCE;
		public static MainPar example = new MainPar(new int[] { 0 }, new int[] { 0 }, new int[] { 0 },
				new int[] { 0 });

		public boolean run(World w, Creature c, MainPar mainPar, FreeWill freePar) {
			return false;
		}

		@Override
		public MainPar getExample() {
			// TODO Auto-generated method stub
			return example;
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

		@Override
		public MainPar getExample() {
			// TODO Auto-generated method stub
			return example;
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
		public static MainPar example = new MainPar(new int[] { 0 }, new int[] { 0 }, new int[] { 0 },
				new int[] { 0 });

		@Override
		public boolean run(World w, Creature c, MainPar mainPar, FreeWill freePar) {
			int move = random.nextInt(8);
			Skill.move(w, c, move);
			return false;
		}

		@Override
		public MainPar getExample() {
			// TODO Auto-generated method stub
			return example;
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
		public static MainPar example = new MainPar(new int[] { 0 }, new int[] { 0 }, new int[] { 0 },
				new int[] { 0 });
		
		@Override
		public boolean run(World w, Creature c, MainPar mainPar, FreeWill freePar) {

			return false;
		}

		@Override
		public MainPar getExample() {
			// TODO Auto-generated method stub
			return example;
		}

	}

	/**
	 * 繁殖行为节点(测试阶段)
	 * 
	 * @author 徐川江
	 *
	 */
	public enum multiplication implements IBehavior {
		INSTANCE;
		public static MainPar example = new MainPar(new int[] { 0 }, new int[] { 0 }, new int[] { 0 },
				new int[] { 0 });

		@Override
		public boolean run(World w, Creature c, MainPar mainPar, FreeWill freePar) {
			/*
			 * //天启类型变异（未完成） //增和删的概率必须一致，它们必须在一个地方进行判定
			 *
			 * 后代变异数量 在0~4中产生一个随机数，决定有几个后代变异，
			 * 
			 * 自由意志参数变异 每个生物在0-n之间，产生一个随机数，决定自由意志参数是否变异，何种变异。
			 * 
			 * 基因组变异操作如下
			 * 在0~基因组个数中，产生一个随机数，决定是哪个基因组是否变异，变异类型，（新增基因组，只增加一个行为节点，删除基因层）
			 * 
			 * 节点变异
			 * 在0~在该基因组内节点最大数中+x+x+y，产生一个随机数，决定哪个节点产生变异，为n~n+x则是节点增加（包含自由意志判定），
			 * 
			 * 节点内变异 在0~9中产生一个随机数，0~3则值变异，4~6则是自由意志行为变异，7~8则是节点改
			 * 
			 * （有性繁殖待定）
			 */
			// int NumberOfMutantOrganisms =
			// Reproduction.NumberOfMutantOrganisms(c);// 后代变异数量
			int NumberOfMutantOrganisms = 10;// 测试
			double mul = 0.4;
			// System.out.println("繁殖");
			for (int x = 0, n = 0; x < c.getBreednum(); x++) {
				// 关于繁殖时候的消耗，和孩子数量，是个问题
				if ((c.getHungerLife() - c.getHungerLifeMax() * mul) < 0)// 饥饿不够
					return false;
				if ((c.getLife() - c.getLifeMax() * mul) < 0)// 生命不够
					return false;
				c.setLife(c.getLife() - c.getLifeMax() * mul);
				c.setHungerLifeMax(c.getHungerLife() - c.getHungerLifeMax() * mul);

				BehaviorTree bt = Reproduction.clone(c.getTree());
				// bt.getGenomeList().clear();
				Creature son = new Creature(c.getBody(), c.getVariation(), c.getX(), c.getY(), bt);
				if (n < NumberOfMutantOrganisms) {// 开始变异
					// int NumberOfVariantNodes =
					// Reproduction.NumberOfVariantNodes(son);// 有几个次变异

					Reproduction.starVar(son);// 开始变异

					n++;
				}

				Reproduction.location(w, c, son);

			}

			return false;
		}

		@Override
		public MainPar getExample() {
			// TODO Auto-generated method stub
			return example;
		}
	}
}
