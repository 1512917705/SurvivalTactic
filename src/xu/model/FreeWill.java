package xu.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xu.model.base.BFreeWill;

/**
 * 自由意志类 每个生物只有一个
 * 
 * @author 徐川江
 *
 */
public class FreeWill extends BFreeWill implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8648353559301026942L;
	/**
	 * 自由意志参数
	 */
	private List<Integer> freeWill = new ArrayList<Integer>();// 主参数
	/**
	 * 记忆参数 所有判定和行为节点所存放的数据都在这里
	 */
	private Map<String, Integer> memory = new HashMap<String, Integer>();

	/**
	 * 自由意志类构造函数
	 * 
	 * @param freeWill
	 * @param memory
	 */
	public FreeWill(List<Integer> freeWill, Map<String, Integer> memory) {
		// if (freeWill != null)
		this.freeWill = freeWill;
		// else
		// this.freeWill.add(0);
		// if (memory != null)
		this.memory = memory;
		// else
		// this.memory.put("0", 0);

	}

	public Map<String, Integer> getMemory() {
		if (memory != null)
			return memory;
		memory = new HashMap<String, Integer>();
		memory.put("0", 0);
		return memory;
	}

	public void setMemory(Map<String, Integer> memory) {
		this.memory = memory;
	}

	public List<Integer> getFreeWill() {
		if (freeWill != null)
			return freeWill;
		freeWill = new ArrayList<Integer>();
		this.freeWill.add(0);
		return freeWill;
	}

	public void setFreeWill(List<Integer> freeWill) {
		this.freeWill = freeWill;
	}

}
