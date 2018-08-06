package xu.tool;

import java.util.ArrayList;
import java.util.List;

import xu.library.Behavior.Move;
import xu.library.Behavior.multiplication;
import xu.library.Behavior.randomMove;
import xu.library.Judgment.lifeBai;
import xu.library.Reproduction;
import xu.model.BehaviorTree;
import xu.model.Creature;
import xu.model.FreeWill;
import xu.model.FreeWillBehavior;
import xu.model.Genome;
import xu.model.MainPar;
import xu.model.TreeNode;
import xu.model.World;

/**
 * 模板类
 * 存储模板世界，模板生物
 * 
 * @author 徐川江
 *
 */
public class Example {

	/**
	 * 获取世界
	 * @param body 玩家体型
	 * @param x 玩家X坐标
	 * @param y 玩家Y坐标
	 * @return
	 */
	public static World getWorld(int body ,int x ,int y) {
		World w = new World(99,99,new Creature[]{getCreature()},new Creature(body, x, y));
		
		return w;
	}
	
	/**
	 * 获取生物
	 * @return
	 */
	public static Creature getCreature() {
		MainPar mp1 = new MainPar(new int[] { 80 }, new int[] { 1 }, new int[] { 100 }, new int[] { 100 });
		MainPar mp3 = new MainPar(new int[] { 5 }, new int[] { 5 }, new int[] { 5 }, new int[] { 100 });

		FreeWillBehavior fwb1 = new FreeWillBehavior();
		TreeNode treeNode1 = new TreeNode(multiplication.INSTANCE, Reproduction.getExample(multiplication.example), fwb1);
		FreeWillBehavior fwb2 = new FreeWillBehavior();
		TreeNode treeNode2 = new TreeNode(randomMove.INSTANCE, Reproduction.getExample(randomMove.example), fwb2);
		FreeWillBehavior fwb3 = new FreeWillBehavior();
		TreeNode treeNode3 = new TreeNode(Move.INSTANCE, mp3, fwb3);

		List<TreeNode> tt1 = new ArrayList<TreeNode>();
		tt1.add(treeNode1);
		tt1.add(treeNode3);
		List<TreeNode> tt2 = new ArrayList<TreeNode>();
		tt2.add(treeNode2);
		

		FreeWill freeWill = new FreeWill(null, null);

		Genome g1 = new Genome(tt1);
		Genome g2 = new Genome(tt2);

		List<Genome> genomeList = new ArrayList<Genome>();
		genomeList.add(g1);
		genomeList.add(g2);

		BehaviorTree t1 = new BehaviorTree(genomeList , freeWill);
		Creature c1 = new Creature(5, 0, 8, 8, t1);

		return c1;
	}

}
