package xu.tool;

import java.util.Scanner;
import java.util.regex.Pattern;

import xu.library.CreatureRun;
import xu.library.Skill;
import xu.library.WorldRun;
import xu.model.Creature;
import xu.model.World;
import xu.view.View;

/**
 * 运行工具类 静态的一些运行方法
 * 
 * @author 徐川江
 *
 */
public final class Run {

	private static String showInfo = " ";// 上回合所有信息，在回合开始时候展示
	private static int gameType = 1;// 1正常模式 2自动运行模式
	private static int ReminderAutoRound = 0;

	/**
	 * 字符串是否是整数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 回合运行方法
	 * 
	 * @param w
	 * @param sc
	 * @return
	 */
	public static int roundRun(World w, Scanner sc) {
		/*
		 * 依行动点数排序，把所有可行动的生物弄成一个链表，初始行动点数全等于生物移动速度， 然后每回合，链表内所有生物行动点数+100，
		 * 第一名生物开始运行，不足100不行动/移动，如果移动/行动则减去对应的速度，原地不动减去100行动点数。
		 * 然后把此生物根据剩余行动点数移动到后面。 一直运行到表头生物的行动点数小于100，此时所有生物无法行动。
		 * 这时候，一回合就过去了，开始下一回合，所有生物行动点数+100
		 */
		// 回合开始
		WorldRun.setAllCanRunCreature(w,ReminderAutoRound);// 开始准备可运行的生物,给这些生物进行处理
		System.out.println(w.getRound());
		w.setRound(w.getRound() + 1);
		if (ReminderAutoRound > 0)
			ReminderAutoRound--;

		for (Creature c : w.getCreList()) {
			c.setSpeedPower(c.getSpeedPower() + 100);// 每个生物赋予100行动点数

			if ((c.getHungerLife() - c.getHunger()) < 0) {
				c.setLife(c.getLife() - c.getHunger() * 10);// 饿到掉血
			} else {
				c.setHungerLife(c.getHungerLife() - c.getHunger());// 变饥饿
				c.setLife(c.getLife() + 1);// 非饥饿状态，每回合回1血
			}
		}

		// System.out.println(w.getPlayer().getX());
		// System.out.println(w.getPlayer().getY());

		while (!w.getCreList().isEmpty()) {// 循环所有的生物
			WorldRun.sort(w);// 按行动点数排序
			Creature c = w.getCreList().get(0);// 行动点数最多的生物

			if (c.getSpeedPower() >= c.getSpeed()) {
				c.setSpeedPower(c.getSpeedPower() - c.getSpeed());// 扣行动点数

				if (c.getLife() < 0) {
					CreatureRun.injure(c, c, w, 100);
					continue;
				}
				
				if (c == w.getPlayer()) {// 玩家回合
					playRoundRun(w, c, sc);
					if (!c.isLive())
						return 1;// 玩家死亡情况
				} else
					CreatureRun.TreeRun(w, c);// 生物执行

				if (!c.isLive())// 死亡
					w.getCreList().remove(c);
			} else {
				w.getCreList().remove(c);// 本回合不能执行，全部删完则回合结束
			}
		}
		return 0;
	}

	/**
	 * 玩家运行回合
	 * 
	 * @param w
	 * @param c
	 * @param sc
	 * @return
	 */
	public static int playRoundRun(World w, Creature c, Scanner sc) {

		if (ReminderAutoRound > 0) {
			w.getPlayer().setLife(1000);
			return 0;
		}

		View.show(w);// 画出地图
		showInfo();// 展示上回合所有信息
		delShowInfo();// 清理信息
		String s;// 输入
		int round = 0;
		// System.out.println(c.getOld());
		do {// 一些技能不消耗回合
			System.out.println("请输入操作:");
			s = sc.nextLine();
			if (!s.equals(""))// 非空
			{
				if (Run.isInteger(s))// 确定是数字
					round = Skill.move(w, w.getPlayer(), Integer.parseInt(s));// 移动
				// 这里放置技能，设置等情况
				// 消耗回合的技能
				if (s.equals("yc")) {
					System.out.println("请输入横坐标");
					int x = sc.nextInt();// 输入
					System.out.println("请输入纵坐标");
					int y = sc.nextInt();// 输入
					round = Skill.yc(w, c, x, y);
				}
				if (s.equals("zd")) {
					System.out.println("请输入回合");
					int x = sc.nextInt();// 输入
					round = Skill.zd(w, c, x);
				}

				// 不消耗回合的技能
				if (s.equals("zc")) {
					System.out.println("请输入横坐标");
					int x = sc.nextInt();// 输入
					System.out.println("请输入纵坐标");
					int y = sc.nextInt();// 输入
					round = Skill.zc(w, c, x, y);
				}
			}
		} while (round == 0);

		return 0;
	}

	/**
	 * 展示上回合的所有信息
	 */
	public static void showInfo() {
		System.out.println(showInfo);
	}

	public static String getShowInfo() {
		return showInfo;
	}

	public static void addShowInfo(String show) {
		if (ReminderAutoRound == 0)// 正常模式才可以显示
			showInfo = showInfo + " \n " + show;
	}

	public static void delShowInfo() {
		showInfo = " ";
	}

	public static int getReminderAutoRound() {
		return ReminderAutoRound;
	}

	public static void setReminderAutoRound(int reminderAutoRound) {
		ReminderAutoRound = reminderAutoRound;
	}

}
