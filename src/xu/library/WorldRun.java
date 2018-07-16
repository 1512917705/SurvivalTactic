package xu.library;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import xu.model.Creature;
import xu.model.World;

/**
 * 世界运行方法类
 * 世界运行，倒计时，等世界相关方法
 * 
 * @author 徐川江
 *
 */
public final class WorldRun {

	/**
	 * 计算所有可运行的生物
	 * 
	 * 如果玩家模式，以玩家为中心，进行游戏
	 * 如果自动运行，所有生物课运行（未完成）
	 *
	 * @param world
	 */
	public static void setAllCanRunCreature(World world){
		Creature play = world.getPlayer();
		

		int x = world.getPlayer().getX();
		int y = world.getPlayer().getY();
		int v = world.getPlayer().getVision()+5;
		

		for(int i=1;i<2*v;i++)//
		{
			for(int j=1;j<2*v;j++)//生物
				{
				Creature[][] c = world.getCreatureMap();
				
				if((j+x-v)<0||(-i+y+v)<0)
				{
					continue;
				}
				
				if( c[j+x-v][-i+y+v] != null )
					world.getCreList().add((Creature)c[j+x-v][-i+y+v]);
				}
		}
		
        sort(world);
		
	}

	/**
	 * 死亡方法
	 * 
	 * @param w 世界
	 * @param inj 生物
	 */
	public static void died(World w, Creature inj) {
		w.getCreatureMap()[inj.getX()][inj.getY()]=null;
		inj.setLive(false);
		w.getCreList().remove(inj);
		
	}
	
	/**
	 * 对生物以行动点数进行排序
	 * 
	 * @param world
	 */
	public static void sort(World world){
		
		Collections.sort(world.getCreList(), new Comparator<Creature>(){//按照速度排序
            /*
             * int compare(Person p1, Person p2) 返回一个基本类型的整型，
             * 返回负数表示：p1 小于p2，
             * 返回0 表示：p1和p2相等，
             * 返回正数表示：p1大于p2
             */
            public int compare(Creature p1, Creature p2) {
                //按照Person的年龄进行升序排列
                if(p1.getSpeedPower() > p2.getSpeedPower()){
                    return 1;
                }
                if(p1.getSpeedPower() == p2.getSpeedPower()){
                    return 0;
                }
                return -1;
            }
        });
	}

	
}
