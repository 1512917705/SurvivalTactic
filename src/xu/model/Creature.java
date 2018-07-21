package xu.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;

/**
 * 生物类 存放生物数据。
 * 
 * @author 徐川江
 *
 */
public class Creature  implements Cloneable,Serializable  { 
	/**
	 * 
	 */
	private static final long serialVersionUID = 2366273962292007692L;
	/*
给每个生物安排一个随机生成的32位ID，让生物可以永久记住其他生物，可以做到复仇，或者效忠，或者报恩。
给每个生物安排一个根据行为树而计算的32位数，来确定基因相似程度，基因相似的就是友军

 */
	// 一阶段必须属性
	private boolean live;// 是否活着
	private int vision;// 视野，正方形，目前是10
	private int old;// 回合数
	private int x;// 位置
	private int y;// 位置
	private double lifeMax;
	private double life;// 生命值
	private double hungerLifeMax;
	private double hungerLife;// 饱腹值,目前和生命值保值一致，未来这个数据，会随着世界的大小和生物密度而改变。
	private int attack;// 攻击力

	private BehaviorTree tree;
	private Random random = new Random();
	private double speed;// 速度
	private double speedPower;// 行动点数

	// 我选择开发速度，而不是运行速度
	// private ICreature previousOne;// 链表，前一个生物
	// private ICreature nextOne;// 链表，后一个生物

	// 二阶段
	private int variation;// 变异值
	private int breeding;// 繁殖回合，和体型相关，
	private int breednum;// 繁殖数量，2-8，饥饿消耗2次方递增，饥饿用完就耗血。
	private int rangking;// 辈分，这个生物是第几代
	
	/*
	 * 每回合都会扣饱腹值，饱腹值扣完，就扣血量。杀死敌人回复饱腹值。饱腹值为正则缓慢回血。 饱腹值上限和体型相关，和技能相关。
	 */
	private double hunger;// 饥饿，和节点总数，和基因层数相关
	private int[] name;// 名字，待定

	// 属性值，互相牵制，增加一个属性，会少许降低另一个属性。
	// 属性的加成是递减的，
	// 一阶段先不做这个
	private double str;// 力量 降感知 降体质
	private double dex;// 敏捷 降意志 降体质
	private double con;// 体质 降魔力 降体质
	private double mag;// 魔力 降体质
	private double wil;// 意志 降敏捷 降体质
	private double per;// 感知 降力量 降体质
	private int body;// 体型1~10,特殊属性，影响明显,测试区没有上限，但是对速度还是有影响



	/**
	 * 生物类 构造函数
	 * 无行为树，不建议使用，仅玩家使用
	 * @param body
	 * @param x
	 * @param y
	 */
	public Creature(int body, int x, int y) {// 测试
		live = true;
		vision = 10;
		old = 0;
		this.body = body ;
		lifeMax = 70 + body * 10;// 170-80 血量    10体型砍1体型需要1刀，砍2体型需要2刀
		life = lifeMax;
		attack = 10 + body * 3;// 40-13攻击力
		hungerLifeMax = life;
		hungerLife = life;
		speed = 160 - body * 12;//40-148速度
		this.x = x;
		this.y = y;
	}

	/**
	 * 生物类 构造函数
	 * 
	 * @param body 体型
	 * @param variation 变异概率
	 * @param x 
	 * @param y
	 * @param t 行为树
	 */
	public Creature(int body, int variation, int x, int y, BehaviorTree t) {
		live = true;
		old = 0;
		this.body = body;
		lifeMax = 70 + body * 10;// 10体型砍1体型需要1刀，砍2体型需要2刀
		life = lifeMax;
		attack = 10 + body * 3;
		hungerLifeMax = life;
		hungerLife = life;
		speed = 160 - body * 10;

		this.tree = t;
		this.variation = variation;
		breeding = 90 * body;// 90回合繁殖后代
		hunger = 0.1 * body + tree.getMaxNum() / 10000;// 0节点时候，100回合饿死
		vision = 12 - body/2;

		old = 1;
		this.variation =variation;

		this.x = x;
		this.y = y;
	}

	public BehaviorTree getTree() {
		return tree;
	}

	public void setTree(BehaviorTree tree) {
		this.tree = tree;
	}

	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public int[] getName() {
		return name;
	}

	public void setName(int[] name) {
		this.name = name;
	}

	public int getBody() {
		return body;
	}

	public void setBody(int body) {
		this.body = body;
	}

	public int getVariation() {
		return variation;
	}

	public void setVariation(int variation) {
		this.variation = variation;
	}

	public double getLife() {
		return life;
	}

	public void setLife(double life) {
		this.life = life;
	}

	public double getLifeMax() {
		return lifeMax;
	}

	public void setLifeMax(double lifeMax) {
		this.lifeMax = lifeMax;
	}

	public double getHungerLifeMax() {
		return hungerLifeMax;
	}

	public void setHungerLifeMax(double hungerLifeMax) {
		this.hungerLifeMax = hungerLifeMax;
	}

	public int getRangking() {
		return rangking;
	}

	public void setRangking(int rangking) {
		this.rangking = rangking;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getBreeding() {
		return breeding;
	}

	public void setBreeding(int breeding) {
		this.breeding = breeding;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getHunger() {
		return hunger;
	}

	public void setHunger(double hunger) {
		this.hunger = hunger;
	}

	public int getVision() {
		return vision;
	}

	public void setVision(int vision) {
		this.vision = vision;
	}

	public int getOld() {
		return old;
	}

	public void setOld(int old) {
		this.old = old;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getHungerLife() {
		return hungerLife;
	}

	public void setHungerLife(double hungerLife) {
		this.hungerLife = hungerLife;
	}

	public int getBreednum() {
		return breednum;
	}

	public void setBreednum(int breednum) {
		this.breednum = breednum;
	}

	public double getStr() {
		return str;
	}

	public void setStr(double str) {
		this.str = str;
	}

	public double getDex() {
		return dex;
	}

	public void setDex(double dex) {
		this.dex = dex;
	}

	public double getCon() {
		return con;
	}

	public void setCon(double con) {
		this.con = con;
	}

	public double getMag() {
		return mag;
	}

	public void setMag(double mag) {
		this.mag = mag;
	}

	public double getWil() {
		return wil;
	}

	public void setWil(double wil) {
		this.wil = wil;
	}

	public double getPer() {
		return per;
	}

	public void setPer(double per) {
		this.per = per;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public double getSpeedPower() {
		return speedPower;
	}

	public void setSpeedPower(double speedPower) {
		this.speedPower = speedPower;
	}

}
