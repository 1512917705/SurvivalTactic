package xu.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 基因组类 
 * 存放一串基因组。
 * 
 * @author 徐川江
 *
 */
public class Genome  {
	/**
	 * 基因组 构造函数
	 * 里面放一串节点，注意前面的节点是判定，而尾节点是一个行为
	 * @param treeNodeList
	 */
	public Genome( List<TreeNode> treeNodeList){
		this.treeNodeList = treeNodeList;
	}

	private List<TreeNode> treeNodeList = new ArrayList<TreeNode>();

	public List<TreeNode> getTreeNodeList() {
		return treeNodeList;
	}

	public void setTreeNodeList(List<TreeNode> treeNodeList) {
		this.treeNodeList = treeNodeList;
	};

}
