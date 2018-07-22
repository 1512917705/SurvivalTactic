package xu.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 行为树类 存放行为树所有数据
 * 
 * @author 徐川江
 *
 */
public class BehaviorTree implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1127675928663030952L;

	/**
	 * 行为树类 构造函数
	 * 
	 * @param genomeList
	 *            基因组
	 * @param MaxNum
	 *            基因组数量
	 * @param freeWill
	 *            自由意志
	 */
	public BehaviorTree(List<Genome> genomeList, int MaxNum, FreeWill freeWill) {
		this.genomeList = genomeList;
		this.MaxNum = MaxNum;
		this.freeWill = freeWill;
	}

	private List<Genome> genomeList = new ArrayList<Genome>();
	private int MaxNum;
	private FreeWill freeWill;// 自由意志参数类

	public List<Genome> getGenomeList() {
		return genomeList;
	}

	public void setGenomeList(List<Genome> genomeList) {
		this.genomeList = genomeList;
	}

	public int getMaxNum() {
		return MaxNum;
	}

	public void setMaxNum(int maxNum) {
		MaxNum = maxNum;
	}

	public FreeWill getFreeWill() {
		return freeWill;
	}

	public void setFreeWill(FreeWill freeWill) {
		this.freeWill = freeWill;
	}

}
