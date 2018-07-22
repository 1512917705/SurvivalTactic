package xu.library;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import xu.model.BehaviorTree;
import xu.model.Creature;
import xu.model.FreeWill;
import xu.model.FreeWillBehavior;
import xu.model.Genome;
import xu.model.MainPar;
import xu.model.TreeNode;
import xu.model.World;
import xu.model.base.IJudgment;
import xu.model.base.BMainPar;
import xu.model.base.INode;

/**
 * 繁殖类
 * 
 * @author 徐川江
 *
 */
public final class Reproduction {
	static Random random = new Random();
	// 增和删的概率必须一致，它们必须在一个地方进行判定

	private static int FreeWillVar = 1;// 自由意志参数变异概率（小）
	private static int NodeVar = 1;// 节点变异概率（大）
	// 基因组
	private static int addGenomeVar = 1;
	private static int delGenomeVar = 1;
	private static int editGenomeVar = 1;
	// 筛选到某个节点，如果增，则在其前增加（避免行为后加行为），删则删它，改则改它
	private static int addNodeVar = 1;
	private static int delNodeVar = 1;
	private static int editNodeVar = 1;
	// 自由意志判定节点变异
	private static int addFreeWillVar = 1;
	private static int delFreeWillVar = 1;
	private static int editFreeWillVar = 1;// 再进行一次第几个节点进行变异的判定
	// 节点内变异
	// 0~3则，4~6则是
	private static int editValVar = 1;// 值变异
	private static int freewillValVar = 1;// 自由意志行为变异

	/**
	 * 深度克隆
	 * 
	 * @param c
	 * @return
	 */
	public static BehaviorTree clone(BehaviorTree bt) {

		try {
			// save the object to a byte array
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bout);
			out.writeObject(bt);
			out.close();

			// read a clone of the object from byte array
			ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
			ObjectInputStream in = new ObjectInputStream(bin);
			Object ret = in.readObject();
			in.close();

			return (BehaviorTree) ret;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 开始变异 从基因组开始变异
	 * 
	 * @param c
	 * @return
	 */
	public static void starVar(Creature c) {
		int fOrN = random.nextInt(FreeWillVar + NodeVar);

		if (fOrN < FreeWillVar) {
			freeWillVar(c.getTree().getFreeWill());// 进行参数变异
			return;
		}

		int MutantGenomeLocation = random.nextInt(c.getTree().getGenomeList().size());// 第几个基因要变异
		Genome g = c.getTree().getGenomeList().get(MutantGenomeLocation);// 获取到该变异的基因
		if (GenomeVar(c, g, MutantGenomeLocation))
			return;// 如果是新增和删除，则结束变异

		int MutantNodeLocation = random.nextInt(g.getTreeNodeList().size());// 第几个节点变异
		TreeNode tn = g.getTreeNodeList().get(MutantNodeLocation);// 获取该变异的节点
		NoidVar(c, g, tn, MutantNodeLocation);// 进行节点变异

	}

	/**
	 * 有几个后代发生变异 在0-最大生物数量中波动，最多max-1，最少0
	 * 
	 * @param c
	 * @return 有几个值进行变异
	 */
	public static int NumberOfMutantOrganisms(Creature c) {
		return random.nextInt(c.getBreednum());
	}

	/**
	 * 有几次变异 在0-var中波动，最多var-1，最少0
	 * 
	 * @param c
	 * @return 有几个值进行变异
	 */
	public static int NumberOfVariantNodes(Creature c) {
		return random.nextInt(c.getVariation());
	}

	/**
	 * 基因组变异
	 * 
	 * @param tn
	 */
	public static boolean GenomeVar(Creature c, Genome g, int MutantGenomeLocation) {
		int type = random.nextInt(editGenomeVar + delGenomeVar + addGenomeVar);// 变异类型
		if (type < editGenomeVar) {
			// 修改基因组

			return false;
		} else {
			if (type > (editGenomeVar + addGenomeVar)) {
				// 删除基因组
				c.getTree().getGenomeList().remove(g);
				return true;
			} else {
				// 新增基因组
				// c.getTree().getGenomeList().add(MutantGenomeLocation,
				// element);
				return true;
			}
		}
	}

	/**
	 * 参数变异
	 * 
	 * @param tn
	 */
	public static void freeWillVar(FreeWill fw) {
		int type = random.nextInt(editFreeWillVar + delFreeWillVar + addFreeWillVar);// 变异类型
		if (type < editFreeWillVar) {
			// 修改参数
		} else {
			if (type > (editFreeWillVar + addFreeWillVar)) {
				// 删除参数
			} else {
				// 新增参数
			}
		}
	}

	/**
	 * 节点变异
	 * 
	 * @param tn
	 */
	public static void NoidVar(Creature c, Genome g, TreeNode tn, int MutantNodeLocation) {
		int type = random.nextInt(editNodeVar + delNodeVar + addNodeVar);// 节点变异类型
		if (type < editNodeVar) {
			// 修改节点
		} else {
			if (type > (editNodeVar + addNodeVar)) {
				// 删除节点
				g.getTreeNodeList().remove(tn);
			} else {
				// 新增判定节点
				// g.getTreeNodeList().add(MutantNodeLocation, element);
			}
		}
	}

	/**
	 * 主参数值变异
	 * 
	 * @param mainPar
	 * @param i
	 */
	public static void mainParVar(MainPar mainPar, int i) {// 某些节点没有数据，不需要变异
		int[] par = mainPar.getMainpar();
		int[] max = mainPar.getMax();
		int[] min = mainPar.getMin();
		int[] flux = mainPar.getFlux();
		par[i] = par[i] + random.nextInt(par[i] + 1) * flux[i] / 100 // 在原值*(1-波动%)到原值*(1+波动%)中取随机值
				- random.nextInt(par[i] - 1) * flux[i] / 100;

		if (par[i] > max[i])
			par[i] = max[i];
		if (par[i] < min[i])
			par[i] = min[i];
	}

	/**
	 * 自由意志参数值变异 目前自由意志参数数量定死，未来可能会有增删功能
	 * 
	 * @param freeWill
	 * @param i
	 */
	public static void freeWillVar(FreeWill freeWill, int i) {

		int[] par = freeWill.getFreeWill();
		par[i] = par[i] + random.nextInt(par[i] + 1) - random.nextInt(par[i] - 1);

	}

	/**
	 * 自由意志行为变异 增删改
	 * 
	 * @param fwb
	 */
	public static void freeWillBehaviorVar(FreeWillBehavior fwb) {
		int x = random.nextInt(3);
		if (x == 0)
			if (fwb.getA().length < fwb.getiMax())
				if (random.nextInt(fwb.getiMax() + 2) == fwb.getiMax()) {
					// 新增自由行为，扩充数组
					int[] a = fwb.getA();
					int[] a1 = new int[a.length + 1];// 新数组
					System.arraycopy(a, 0, a1, 0, a.length);// 将a数组内容复制新数组a1
					a1[a.length] = random.nextInt(fwb.getiMax());// a1数组新增元素
					a = a1;// 改变引用
					fwb.setA(a);

					int[] b = fwb.getB();
					int[] b1 = new int[b.length + 1];// 新数组
					System.arraycopy(b, 0, b1, 0, b.length);// 将a数组内容复制新数组a1
					b1[b.length] = random.nextInt(fwb.getiMax());// a1数组新增元素
					b = b1;// 改变引用
					fwb.setB(b);

					int[] c = fwb.getC();
					int[] c1 = new int[c.length + 1];// 新数组
					System.arraycopy(c, 0, c1, 0, c.length);// 将a数组内容复制新数组a1
					c1[c.length] = random.nextInt(4);// a1数组新增元素
					c = c1;// 改变引用
					fwb.setC(c);

					int[] d = fwb.getD();
					int[] d1 = new int[d.length + 1];// 新数组
					System.arraycopy(d, 0, d1, 0, d.length);// 将a数组内容复制新数组a1
					d1[d.length] = random.nextInt(fwb.getiMax());// a1数组新增元素
					d = d1;// 改变引用
					fwb.setD(d);
				}

		if (x == 1) {
			// 删
			int[] a = fwb.getA();
			int[] a1 = new int[a.length - 1];// 新数组
			for (int i = 0; i < a1.length; i++) {
				a1[i] = a[i];
			}
			fwb.setA(a1);

			int[] b = fwb.getB();
			int[] b1 = new int[b.length + 1];// 新数组
			for (int i = 0; i < b1.length; i++) {
				b1[i] = b[i];
			}
			fwb.setB(b1);

			int[] c = fwb.getC();
			int[] c1 = new int[c.length + 1];// 新数组
			for (int i = 0; i < c1.length; i++) {
				c1[i] = c[i];
			}
			fwb.setC(c1);

			int[] d = fwb.getD();
			int[] d1 = new int[d.length + 1];// 新数组
			for (int i = 0; i < d1.length; i++) {
				d1[i] = d[i];
			}
			fwb.setD(d1);
		}

		if (x == 2) {// 修改
			int i = random.nextInt(fwb.getA().length);

			fwb.getA()[i] = fwb.getA()[i] + random.nextInt(fwb.getA()[i] + 1) - random.nextInt(fwb.getA()[i] - 1);
			if (fwb.getA()[i] > fwb.getMax())
				fwb.getA()[i] = fwb.getMax();
			if (fwb.getA()[i] < fwb.getMin())
				fwb.getA()[i] = fwb.getMin();

			fwb.getB()[i] = fwb.getB()[i] + random.nextInt(fwb.getB()[i] + 1) - random.nextInt(fwb.getB()[i] - 1);
			if (fwb.getB()[i] > fwb.getMax())
				fwb.getB()[i] = fwb.getMax();
			if (fwb.getB()[i] < fwb.getMin())
				fwb.getB()[i] = fwb.getMin();

			fwb.getC()[i] = fwb.getC()[i] + random.nextInt(fwb.getC()[i] + 1) - random.nextInt(fwb.getC()[i] - 1);
			if (fwb.getC()[i] > fwb.getMax())
				fwb.getC()[i] = fwb.getMax();
			if (fwb.getC()[i] < fwb.getMin())
				fwb.getC()[i] = fwb.getMin();

			fwb.getD()[i] = fwb.getD()[i] + random.nextInt(fwb.getD()[i] + 1) - random.nextInt(fwb.getD()[i] - 1);
			if (fwb.getD()[i] > fwb.getMax())
				fwb.getD()[i] = fwb.getMax();
			if (fwb.getD()[i] < fwb.getMin())
				fwb.getD()[i] = fwb.getMin();

		}

	}

	/**
	 * 新生儿将在母亲身边随机位置出现。 先在1码范围随机，如果有人则2码，依次增加。
	 * 
	 * @param w
	 * @param mother
	 * @param son
	 */
	public static void location(World w, Creature mother, Creature son) {
		for (int num = 1; num < mother.getVision(); num++) {
			int x = mother.getX();
			int y = mother.getY();
			x = x - num + random.nextInt(1 + 2 * num);
			y = y - num + random.nextInt(1 + 2 * num);
			if (x < 0)
				x = 1;
			if (y < 0)
				y = 1;
			if (x > w.getMax_x())
				x = w.getMax_x();
			if (y > w.getMax_y())
				y = w.getMax_y();
			if (w.getCreatureMap()[x][y] == null) {
				w.getCreatureMap()[x][y] = son;
				w.getCreList().add(son);
				son.setX(x);
				son.setY(y);
				son.setLife(son.getLifeMax()*0.5);
				son.setHungerLifeMax(son.getHungerLifeMax()*0.5);
				return;
			}
		}
		;

	}

}
