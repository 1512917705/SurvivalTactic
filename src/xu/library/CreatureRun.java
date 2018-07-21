package xu.library;

import java.util.List;

import xu.model.BehaviorTree;
import xu.model.Creature;
import xu.model.FreeWill;
import xu.model.FreeWillBehavior;
import xu.model.Genome;
import xu.model.MainPar;
import xu.model.TreeNode;
import xu.model.World;
import xu.model.base.INode;
import xu.tool.Run;

/**
 * 生物运行方法类 饥饿、死亡等非行为方法
 * 
 * @author 徐川江
 *
 */
public final class CreatureRun {

	/**
	 * 受伤函数
	 * 
	 * @param atkCre
	 *            攻击生物
	 * @param inj
	 *            受伤生物
	 * @param w
	 *            世界
	 */
	public static void injure(Creature atkCre, Creature inj, World w, int atk) {

		double l = inj.getLife() - atk;
		Run.addShowInfo("Z被攻击，损失" + atk + "点生命值");
		if (l >= 0) {
			inj.setLife(l);
		} else {
			// 杀完对方，回饥饿
			atkCre.setHungerLife(inj.getHungerLife());

			// 改成，死亡敌人半径两码所有生物回复饥饿
			int x = inj.getX();
			int y = inj.getY();
			for (int i = 1; i < 4; i++)//
			{
				for (int j = 1; j < 4; j++)// 生物
				{
					Creature[][] sw = w.getCreatureMap();
					if ((j + x - 4) < 0 || (-i + y + 4) < 0) {
						continue;
					}
					if (sw[j + x - 4][-i + y + 4] != null) {
						sw[x][y].setHungerLife(sw[x][y].getHungerLife() + inj.getHungerLife());
					}
				}
			}

			WorldRun.died(w, inj);

			if ((atkCre.getHungerLife() + inj.getLife()) > atkCre.getHungerLifeMax())
				atkCre.setHungerLife(atkCre.getHungerLife() + inj.getLife());
			else
				atkCre.setHunger(atkCre.getHungerLifeMax());
			Run.addShowInfo("Z死了");
		}
	}

	/**
	 * 回合运行
	 * 
	 * @param w
	 * @param c
	 */
	public static void TreeRun(World w, Creature c) {//
		List<Genome> tree = c.getTree().getGenomeList();// 行为树

		for (Genome g : tree) {// 一层层的运行
			int n = GenomeRun(w, c, g);
			if (n == 1)
				return;// 已经执行了行为
		}

	}

	/**
	 * 基因组运行
	 * 
	 * @param w
	 * @param c
	 * @param freePar
	 * @param ge
	 * @return
	 */
	public static int GenomeRun(World w, Creature c, Genome ge) {// 基因层运行
		List<TreeNode> nodeList = ge.getTreeNodeList();// 基因层
		FreeWill freePar = c.getTree().getFreeWill();
		for (TreeNode n : nodeList) {// 一个个节点的运行
			INode n1 = n.getValue();// 节点
			// System.out.println(n1.getClass().getName());
			// System.out.println(n1.run(w, c, n.getMainPar(), freePar));
			if (n1.run(w, c, n.getMainPar(), freePar))// 本次节点执行的结果
			{
				if (n.getFreeWillBehavior() != null)
					runFreeWillBehavior(freePar, n.getFreeWillBehavior());// 执行自由意志行为
			} else {
				// System.out.println(n1.getClass().getInterfaces()[0].getName().equals("xu.model.base.IBehavior"));
				if (n1.getClass().getInterfaces()[0].getName().equals("xu.model.base.IBehavior"))// 判定该节点是什么类型节点
				{
					// System.out.println(3);
					if (n.getFreeWillBehavior() != null)
						runFreeWillBehavior(freePar, n.getFreeWillBehavior());// 执行自由意志行为
					return 1;// 执行行为节点
				}
				return 0;// 判定节点遇见false
			}
		}

		return 0;

	}

	/**
	 * 运行自由意志行为
	 * 
	 * @param fw
	 * @param fwb
	 */
	public static void runFreeWillBehavior(FreeWill fw, FreeWillBehavior fwb) {
		int[] free = fw.getFreeWill();// 所有的自由意志参数
		int[] a = fwb.getA();
		int[] b = fwb.getB();
		int[] c = fwb.getC();
		int[] d = fwb.getD();

		for (int z = 0; z < a.length; z++) {
			switch (c[z]) {
			case 0:
				free[a[z]] = free[b[z]] + free[d[z]];
				break;
			case 1:
				free[a[z]] = free[b[z]] - free[d[z]];
				break;
			case 2:
				free[a[z]] = free[b[z]] * free[d[z]];
				break;
			case 3:
				free[a[z]] = free[b[z]] / free[d[z]];
				break;
			}
		}
	}

}
