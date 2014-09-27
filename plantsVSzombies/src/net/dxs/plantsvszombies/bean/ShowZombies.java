package net.dxs.plantsvszombies.bean;

import net.dxs.plantsvszombies.utils.CommonUtils;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.nodes.CCSprite;

/**
 * 展示用的僵尸
 * 
 * @author lijian
 * 
 */
public class ShowZombies extends CCSprite {

	public ShowZombies() {
		super("image/zombies/zombies_1/shake/z_1_01.png");
		setScale(0.65f);
		setAnchorPoint(0.5f, 0);
		baseAction();
	}

	/**
	 * 僵尸基础动作
	 */
	private void baseAction() {
		CCAction animate = CommonUtils.animate("image/zombies/zombies_1/shake/z_1_%02d.png", 2, true);
		this.runAction(animate);
	}

}
