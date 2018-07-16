package xu.library;

import java.util.Random;

import xu.model.FreeWill;
import xu.model.FreeWillBehavior;
import xu.model.MainPar;
import xu.model.TreeNode;
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
	
	/**
	 * 主参数值变异
	 * 
	 * @param mainPar
	 * @param i
	 */
	public static void mainParVar(MainPar mainPar, int i){//某些节点没有数据，不需要变异
		int[] par = mainPar.getMainpar();
		int[] max = mainPar.getMax();
		int[] min = mainPar.getMin();
		int[] flux = mainPar.getFlux();
		par[i] = par[i] + random.nextInt(par[i] + 1)*flux[i]/100 //在原值*(1-波动%)到原值*(1+波动%)中取随机值
				- random.nextInt(par[i] - 1)*flux[i]/100;

		if(par[i]>max[i])
			par[i]=max[i];
		if(par[i]<min[i])
			par[i]=min[i];
	}
	
	/**
	 * 自由意志参数值变异
	 * 目前自由意志参数数量定死，未来可能会有增删功能
	 * 
	 * @param freeWill
	 * @param i
	 */
	public static void freeWillVar(FreeWill freeWill, int i){
		
		int[] par = freeWill.getFreeWill();
		par[i] = par[i] + random.nextInt(par[i] + 1) 
				- random.nextInt(par[i] - 1);

	}
	
	/**
	 * 自由意志行为变异
	 * 增删改
	 * 
	 * @param fwb
	 */
	public static void freeWillBehaviorVar(FreeWillBehavior fwb){
		int x = random.nextInt(3);
		if(x==0)
		if(fwb.getA().length<fwb.getiMax())
			if(random.nextInt(fwb.getiMax()+2)==fwb.getiMax()){
				//新增自由行为，扩充数组
				int[] a = fwb.getA();
				int[] a1=new int[a.length+1];//新数组
				System.arraycopy(a, 0, a1, 0, a.length);//将a数组内容复制新数组a1
			    a1[a.length]=random.nextInt(fwb.getiMax());//a1数组新增元素
			    a=a1;//改变引用
			    fwb.setA(a);

				int[] b = fwb.getB();
				int[] b1=new int[b.length+1];//新数组
				System.arraycopy(b, 0, b1, 0, b.length);//将a数组内容复制新数组a1
			    b1[b.length]=random.nextInt(fwb.getiMax());//a1数组新增元素
			    b=b1;//改变引用
			    fwb.setB(b);

				int[] c = fwb.getC();
				int[] c1=new int[c.length+1];//新数组
				System.arraycopy(c, 0, c1, 0, c.length);//将a数组内容复制新数组a1
			    c1[c.length]=random.nextInt(4);//a1数组新增元素
			    c=c1;//改变引用
			    fwb.setC(c);

				int[] d = fwb.getD();
				int[] d1=new int[d.length+1];//新数组
				System.arraycopy(d, 0, d1, 0, d.length);//将a数组内容复制新数组a1
			    d1[d.length]=random.nextInt(fwb.getiMax());//a1数组新增元素
			    d=d1;//改变引用
			    fwb.setD(d);
			}
		
		if(x==1){
			//删
			int[] a = fwb.getA();
			int[] a1=new int[a.length-1];//新数组
			for(int i=0;i<a1.length;i++){
				a1[i]=a[i];
			}
		    fwb.setA(a1);

			int[] b = fwb.getB();
			int[] b1=new int[b.length+1];//新数组
			for(int i=0;i<b1.length;i++){
				b1[i]=b[i];
			}
		    fwb.setB(b1);

			int[] c = fwb.getC();
			int[] c1=new int[c.length+1];//新数组
			for(int i=0;i<c1.length;i++){
				c1[i]=c[i];
			}
		    fwb.setC(c1);

			int[] d = fwb.getD();
			int[] d1=new int[d.length+1];//新数组
			for(int i=0;i<d1.length;i++){
				d1[i]=d[i];
			}
		    fwb.setD(d1);
		}
		
		if(x==2){//修改
			int i = random.nextInt(fwb.getA().length);

			fwb.getA()[i] = fwb.getA()[i] + random.nextInt(fwb.getA()[i] + 1) 
					- random.nextInt(fwb.getA()[i] - 1);
			if(fwb.getA()[i]>fwb.getMax())
				fwb.getA()[i]=fwb.getMax();
			if(fwb.getA()[i]<fwb.getMin())
				fwb.getA()[i]=fwb.getMin();

			fwb.getB()[i] = fwb.getB()[i] + random.nextInt(fwb.getB()[i] + 1) 
					- random.nextInt(fwb.getB()[i] - 1);
			if(fwb.getB()[i]>fwb.getMax())
				fwb.getB()[i]=fwb.getMax();
			if(fwb.getB()[i]<fwb.getMin())
				fwb.getB()[i]=fwb.getMin();

			fwb.getC()[i] = fwb.getC()[i] + random.nextInt(fwb.getC()[i] + 1) 
					- random.nextInt(fwb.getC()[i] - 1);
			if(fwb.getC()[i]>fwb.getMax())
				fwb.getC()[i]=fwb.getMax();
			if(fwb.getC()[i]<fwb.getMin())
				fwb.getC()[i]=fwb.getMin();

			fwb.getD()[i] = fwb.getD()[i] + random.nextInt(fwb.getD()[i] + 1) 
					- random.nextInt(fwb.getD()[i] - 1);
			if(fwb.getD()[i]>fwb.getMax())
				fwb.getD()[i]=fwb.getMax();
			if(fwb.getD()[i]<fwb.getMin())
				fwb.getD()[i]=fwb.getMin();
			
		}
			
	}
	
	
	
	
}
