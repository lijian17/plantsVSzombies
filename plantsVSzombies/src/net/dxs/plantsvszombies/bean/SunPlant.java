package net.dxs.plantsvszombies.bean;

import java.util.ArrayList;

import net.dxs.plantsvszombies.base.ProductPlant;

import org.cocos2d.actions.CCScheduler;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;

/**
 * 能生产阳光的向日葵
 * 
 * @author lijian
 * 
 */
public class SunPlant extends ProductPlant {
	private static final String resPath = "image/plant/sunflower/p_1_%02d.png";

	public SunPlant() {
		super("image/plant/sunflower/p_1_01.png");
		life = 100;
		//		price=50;
		motionless();
		create();
	}

	// 静止帧
	protected static ArrayList<CCSpriteFrame> motionlessFrames;// 静止帧集合
	protected CCAnimation motionlessAnimation;// 静止的帧
	protected CCAnimate motionlessAnimate;// 静止动作

	public void motionless() {
		if (motionlessFrames == null) {
			motionlessFrames = new ArrayList<CCSpriteFrame>();
			for (int i = 1; i < 9; i++) {
				motionlessFrames.add(CCSprite.sprite(String.format(resPath, i)).displayedFrame());
			}
		}
		motionlessAnimation = CCAnimation.animation("", 0.3f, motionlessFrames);
		motionlessAnimate = CCAnimate.action(motionlessAnimation);
		this.runAction(CCRepeatForever.action(motionlessAnimate));
	}

	@Override
	public void create() {
		CCScheduler.sharedScheduler().schedule("create", this, 5, false);
	}

	public void create(float f) {
		new Sun(this.getParent(), Sun.TYPE_BIG, ccp(getPosition().x, getPosition().y + 40), ccp(getPosition().x + 25, getPosition().y));
	}

	@Override
	public void baseAction() {

	}

}
