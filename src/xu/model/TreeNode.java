package xu.model;

import xu.model.base.BNode;

/**
 * 节点类 
 * 存放一个节点
 * 可能是判定节点，也可能是行为节点
 * 
 * @author 徐川江
 *
 */
public class TreeNode  {

	// 节点
	//public TreeNode left;

	// 节点
	public BNode value;
	
	private MainPar mainPar;//主参数类，
	
	private FreeWillBehavior freeWillBehavior;//自由意志参数修改行为


	/**
	 * 节点 构造函数
	 * 
	 * @param value 节点内容
	 * @param mainPar 参数，建议参考参数模板
	 * @param freeWillBehavior 自由意志行为
	 */
	public TreeNode( BNode value, MainPar mainPar, FreeWillBehavior freeWillBehavior) {
		//this.left = left;
		this.value = value;
		this.mainPar = mainPar;
		this.freeWillBehavior = freeWillBehavior;
	}


	public MainPar getMainPar() {
		return mainPar;
	}

	public void setMainPar(MainPar mainPar) {
		this.mainPar = mainPar;
	}


	public BNode getValue() {
		return value;
	}

	public void setValue(BNode value) {
		this.value = value;
	}

	public FreeWillBehavior getFreeWillBehavior() {
		return freeWillBehavior;
	}

	public void setFreeWillBehavior(FreeWillBehavior freeWillBehavior) {
		this.freeWillBehavior = freeWillBehavior;
	} 
	 
}
