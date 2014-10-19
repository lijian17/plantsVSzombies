package net.dxs.plantsvszombies.base;

import org.cocos2d.nodes.CCSprite;

/**
 * 对战元素基类
 * 
 * @author lijian
 * 
 */
public abstract class BaseElement extends CCSprite {

	public interface DieListener {
		void die();
	}

	/** 死亡的监听 **/
	private DieListener dieListener;

	/**
	 * 设置死亡监听器
	 * 
	 * @param dieListener
	 */
	public void setOnDieListener(DieListener dieListener) {
		this.dieListener = dieListener;
	}

	public BaseElement(String filePath) {
		super(filePath);
	}

	/**
	 * 原地不动的基本动作
	 */
	public abstract void baseAction();

	/**
	 * 销毁
	 */
	public void destroy() {
		if (dieListener != null) {
			dieListener.die();
		}
		this.removeSelf();
	}
}
