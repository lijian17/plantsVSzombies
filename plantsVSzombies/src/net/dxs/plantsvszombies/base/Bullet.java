package net.dxs.plantsvszombies.base;

/**
 * 子弹
 * 
 * @author lijian
 * 
 */
public abstract class Bullet extends Product {
	protected int attack = 10;// 攻击力
	protected int speed = 60;// 移动速度

	public Bullet(String filepath) {
		super(filepath);
	}

	@Override
	public void baseAction() {

	}

	/**
	 * 移动
	 */
	public abstract void move();

	/**
	 * 获得子弹攻击力
	 * 
	 * @return
	 */
	public int getAttack() {
		return attack;
	}
}