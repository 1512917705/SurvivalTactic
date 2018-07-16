package xu.model;

import java.util.HashMap;
import java.util.Map;

import xu.model.base.BFreeWill;
/**
 * 自由意志类
 * 每个生物只有一个
 * 
 * @author 徐川江
 *
 */
public class FreeWill extends BFreeWill {
	/**
	 *自由意志参数 
	 */
	private int[] freeWill;//主参数 
	/**
	 * 记忆参数
	 * 所有判定和行为节点所存放的数据都在这里
	 */
	private Map<String, Integer> memory = new HashMap<String, Integer>();

	/**
	 * 自由意志类构造函数
	 * @param freeWill
	 * @param memory
	 */
	public FreeWill(int[] freeWill ,Map<String, Integer> memory){
		this.freeWill = freeWill;
		this.memory = memory;
	}
	

	public int[] getFreeWill() {
		return freeWill;
	}

	public void setFreeWill(int[] freeWill) {
		this.freeWill = freeWill;
	}

	public Map<String, Integer> getMemory() {
		return memory;
	}

	public void setMemory(Map<String, Integer> memory) {
		this.memory = memory;
	}

 
}
