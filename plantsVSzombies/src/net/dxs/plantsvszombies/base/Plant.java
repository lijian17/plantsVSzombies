package net.dxs.plantsvszombies.base;

/**
 * 植物基类
 * 
 * @author lijian
 * 
 */
public abstract class Plant extends BaseElement {

	/** 生命值 **/
	protected int life = 100;

	/** 行号 **/
	protected int line;
	/** 列号 **/
	protected int col;

	public Plant(String filepath) {
		super(filepath);
		setScale(0.65);
		setAnchorPoint(0.5f, 0);// 将解析的点位放在两腿之间
	}

	/**
	 * 被攻击
	 * 
	 * @param attack 攻击力
	 */
	public void attacked(int attack) {
		life -= attack;
		if (life <= 0) {
			destroy();
		}
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getLife() {
		return life;
	}

}
