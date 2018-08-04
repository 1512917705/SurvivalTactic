package xu.model.base;

import xu.library.Reproduction;
import xu.model.Creature;
import xu.model.FreeWill;
import xu.model.MainPar;
import xu.model.World;

/**
 * 行为节点接口
 * 
 * @author 徐川江
 *
 */
public interface IBehavior  extends INode{

	public boolean run(World w,Creature c,MainPar mainPar, FreeWill freePar);

	public MainPar getExample();


}
