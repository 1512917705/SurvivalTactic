package xu.model;

import java.io.Serializable;

import xu.model.base.IBehavior;

/**
 * 自由意志行动类 每个节点都可以自由的对某些自由意志进行加减乘除
 * 
 * @author 徐川江
 *
 */
public class FreeWillBehavior implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8521344157476705815L;
	private int iMax = 10;// a[i]中i的最大值，也就是每个节点里面运算参数的最大值
	// 格式是，a=b加减乘除d
	private int[] a = { 0 };// 每个a[i]存储的int值是第i个自由意志参数
	private int[] b = { 0 };// 第一个处理参数
	private int[] c = { 0 };// 处理符号
	private int[] d = { 0 };// 第二个处理参数
	private int max = 25;// a[i]的最大值
	private int min = 0;// a[i]的最小值的最大值

	/**
	 * 自由意志行动类 构造函数 死麻烦的操作
	 * 
	 * @param a
	 *            每个a[i]存储的int值是第i个自由意志参数
	 * @param b
	 *            第一个处理参数
	 * @param c
	 *            处理符号
	 * @param d
	 *            第二个处理参数
	 * @param iMax
	 *            a[i]中i的最大值，也就是每个节点里面运算参数的最大值
	 * @param max
	 *            a[i]的最大值
	 * @param min
	 *            a[i]的最小值的最大值
	 */
	public FreeWillBehavior(int[] a, int[] b, int[] c, int[] d, int iMax, int max, int min) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.iMax = iMax;
		this.max = max;
		this.min = min;
	}

	public FreeWillBehavior() {
	}
	
	public int[] getA() {
		if (a != null)
			return a;
		a = new int[] { 0 };
		return a;
	}

	public void setA(int[] a) {
		this.a = a;
	}

	public int[] getB() {
		if (b != null)
			return b;
		b = new int[] { 0 };
		return b;
	}

	public void setB(int[] b) {
		this.b = b;
	}

	public int[] getC() {
		if (c != null)
			return c;
		c = new int[] { 0 };
		return c;
	}

	public void setC(int[] c) {
		this.c = c;
	}

	public int[] getD() {
		if (d != null)
			return d;
		d = new int[] { 0 };
		return d;
	}

	public void setD(int[] d) {
		this.d = d;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getiMax() {
		return iMax;
	}

	public void setiMax(int iMax) {
		this.iMax = iMax;
	}

}
