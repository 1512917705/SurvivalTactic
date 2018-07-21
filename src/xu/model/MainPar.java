package xu.model;

import java.io.Serializable;

import xu.model.base.BMainPar;
/**
 * 主参数类
 * @author 徐川江
 *
 */
public class MainPar extends BMainPar  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8766838689575616563L;
	private int[] mainpar;//主参数
	private int[] max;
	private int[] min;
	private int[] flux;//变异的波动强度，1-100取值，百分比

	/**
	 * 主参数类，构造函数
	 * 如果没有内容就null吧，
	 * 建议参考每个节点里面的模板内容，但是不要直接把模板放进来，
	 * 请自己创造数组，然后一个个放进来
	 * 
	 * @param mainpar
	 * @param max
	 * @param min
	 * @param flux
	 */
	public MainPar(int[] mainpar,int[] max,int[] min,int[] flux){
		this.mainpar = mainpar;
		this.max = max;
		this.min = min;
		this.flux = flux;
	}
	
	
	public int[] getMainpar() {
		return mainpar;
	}

	public void setMainpar(int[] mainpar) {
		this.mainpar = mainpar;
	}

	public int[] getMax() {
		return max;
	}

	public void setMax(int[] max) {
		this.max = max;
	}

	public int[] getMin() {
		return min;
	}

	public void setMin(int[] min) {
		this.min = min;
	}

	public int[] getFlux() {
		return flux;
	}

	public void setFlux(int[] flux) {
		this.flux = flux;
	} 

}
