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
	private List<Integer> freeWill = new ArrayList<Integer>();
	/**
	 * 记忆参数 所有判定和行为节点所内定使用的数据都在这里
	 */
	private Map<String, Integer> memory = new HashMap<String, Integer>();
	
	//根据memory中第n个参数的值，取得xMemory.get(memory.get(n))的值，这就是x坐标
	private List<Integer> xMemory = new ArrayList<Integer>();// X坐标记忆
	private List<Integer> YMemory = new ArrayList<Integer>();// Y坐标记忆
	/**
	 * 自由意志类构造函数
	 * 
	 * @param freeWill
	 * @param memory
	 */
	public FreeWill(List<Integer> freeWill, Map<String, Integer> memory) {
		this.freeWill = freeWill;
		this.memory = memory;
	}
	public FreeWill(List<Integer> freeWill, Map<String, Integer> memory,List<Integer> xMemory,List<Integer> YMemory) {
		this.freeWill = freeWill;
		this.memory = memory;
		this.xMemory = xMemory;
		this.YMemory = YMemory;
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
	public List<Integer> getxMemory() {
		return xMemory;
	}
	public void setxMemory(List<Integer> xMemory) {
		this.xMemory = xMemory;
	}
	public List<Integer> getYMemory() {
		return YMemory;
	}
	public void setYMemory(List<Integer> yMemory) {
		YMemory = yMemory;
	}

}
