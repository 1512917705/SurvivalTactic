package xu.library;

import java.util.List;
import java.util.Map;

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
		//System.out.println(l);
		Run.addShowInfo("Z被攻击，损失" + atk + "点生命值");
		if (l >= 0) {
			inj.setLife(l);
		} else {
			// 杀完对方，回饥饿
			atkCre.setHungerLife(inj.getHungerLife());

			// 改成，死亡敌人半径两码所有生物回复饥饿
			int huifu = 1;
			int huifuRound = 2*huifu+1;
			int x = inj.getX();
			int y = inj.getY();
			for (int i = 1; i < huifuRound; i++)//
			{
				for (int j = 1; j < huifuRound; j++)// 生物
				{
					//System.out.println(j + x - 4);
					//System.out.println(-i + y + 4);
					Creature[][] sw = w.getCreatureMap();
					if ((j + x - huifuRound) < 0 || (-i + y + huifuRound) < 0||(j + x - huifuRound)>w.getMax_x()||(-i + y + huifuRound)>w.getMax_y()) {
						continue;
					}
					if (sw[j + x - huifuRound][-i + y + huifuRound] != null) {
						sw[j + x - huifuRound][-i + y + huifuRound]
								.setHungerLife(sw[j + x - huifuRound][-i + y + huifuRound].getHungerLife() + inj.getHungerLife());
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
	 * 目前去掉自由意志
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
				//if (n.getFreeWillBehavior() != null)
					//runFreeWillBehavior(freePar, n.getFreeWillBehavior());// 执行自由意志行为
			} else {
				// System.out.println(n1.getClass().getInterfaces()[0].getName().equals("xu.model.base.IBehavior"));
				if (n1.getClass().getInterfaces()[0].getName().equals("xu.model.base.IBehavior"))// 判定该节点是什么类型节点
				{
					// System.out.println(3);
					//if (n.getFreeWillBehavior() != null)
						//runFreeWillBehavior(freePar, n.getFreeWillBehavior());// 执行自由意志行为
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
		List<Integer> free = fw.getFreeWill();// 所有的自由意志参数
		int[] a = fwb.getA();
		int[] b = fwb.getB();
		int[] c = fwb.getC();
		int[] d = fwb.getD();
		int z;
		for (z = 0; z < a.length; z++) {
			if(a[z]>free.size())
				a[z]=a[z]%free.size();
			if(b[z]>free.size())
				b[z]=b[z]%free.size();
			if(d[z]>free.size())
				d[z]=d[z]%free.size();
			free.get(b[z]);
			switch (c[z]) {
			case 0:
				free.set(a[z], free.get(b[z]) + free.get(d[z]));
				break;
			case 1:
				free.set(a[z], free.get(b[z]) - free.get(d[z]));
				break;
			case 2:
				free.set(a[z], free.get(b[z]) * free.get(d[z]));
				break;
			case 3:
				free.set(a[z], free.get(b[z]) / free.get(d[z]));
				break;
			}
		}
		
	}

	/**
	 * 返回生物的节点数量
	 * 
	 * @param c
	 * @return
	 */
	public static int NodeNum(Creature c) {
		int num = 0;
		for (Genome g : c.getTree().getGenomeList()) {

			System.out.println("基因组" + g.getTreeNodeList().size());
			for (TreeNode tn : g.getTreeNodeList()) {
				num++;
				System.out.print("节点名称" + tn.getValue().getClass().getName() + "    ");
				if(tn.getMainPar()!=null)
				System.out.print("节点参数" + tn.getMainPar().getMainpar().toString() + "    ");
				//System.out.print("自由意志行为" + tn.getFreeWillBehavior().getA().toString() + "    ");
			}

		}
		System.out.println();
		return num;
	}

	/**
	 * 返回生物的节点数量
	 * 
	 * @param c
	 * @return
	 */
	public static int FreewillNum(Creature c) {
		int num = 0;
		for (Integer i : c.getTree().getFreeWill().getFreeWill()) {
			num++;
			System.out.print(i + "    ");
		}
		System.out.println();
		return num;
	}

}
