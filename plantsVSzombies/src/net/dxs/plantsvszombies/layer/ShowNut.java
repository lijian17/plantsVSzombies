package net.dxs.plantsvszombies.layer;

import net.dxs.plantsvszombies.utils.CommonUtils;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.nodes.CCSprite;

/**
 * 展示用僵尸二
 * 
 * @author lijian
 * 
 */
public class ShowNut extends CCSprite {

	public ShowNut() {
		super("image/plant/nut/p_3_01.png");
		setScale(0.65f);
		setAnchorPoint(0.5f, 0);
		baseAction();
	}

	/**
	 * 僵尸基础动作
	 */
	private void baseAction() {
		CCAction animate = CommonUtils.animate("image/plant/nut/p_3_%02d.png", 11, true);
		this.runAction(animate);
	}

}
