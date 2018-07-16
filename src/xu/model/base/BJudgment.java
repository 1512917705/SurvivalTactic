package xu.model.base;

import xu.model.Creature;
import xu.model.FreeWill;
import xu.model.MainPar;
import xu.model.World;

/**
 * 判定节点接口
 * 
 * @author 徐川江
 *
 */
public interface BJudgment  extends BNode{

	public  boolean run(World w,Creature c,MainPar mainPar, FreeWill freePar);
}
