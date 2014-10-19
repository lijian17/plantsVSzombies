package net.dxs.plantsvszombies.bean;

import java.util.concurrent.CopyOnWriteArrayList;

import net.dxs.plantsvszombies.base.Product;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;

/**
 * 生产的阳光
 * 
 * @author lijian
 * 
 */
public class Sun extends Product {

	public static final CopyOnWriteArrayList<Sun> suns = new CopyOnWriteArrayList<Sun>();// 放置生产出来的阳光

	public static final int TYPE_BIG = 1;
	public static final int TYPE_SMALL = 2;

	private int type;
	private CGPoint start;
	private CGPoint end;

	private int speed = 20;

	private static final String resPath = "image/product/sun.png";

	public Sun(CCNode parent, int type, CGPoint start, CGPoint end) {
		super(resPath);
		this.type = type;
		this.start = start;
		this.end = end;

		if (type == TYPE_BIG) {
			setScale(0.3);
		} else {

		}
		setPosition(start);
		parent.addChild(this);
		suns.add(this);
		move();
		motionless();
	}

	public void motionless() {
		runAction(CCRepeatForever.action(CCRotateBy.action(1, 180)));
	}

	public void move() {
		float t = (start.y - end.y) / speed;
		CCMoveTo moveTo = CCMoveTo.action(t, end);
		CCCallFunc callFunc = CCCallFunc.action(this, "distory");

		CCSequence sequence = CCSequence.actions(moveTo, CCDelayTime.action(5), callFunc);
		runAction(sequence);
	}

	public void distory() {
		suns.remove(this);
		this.removeSelf();
	}

	@Override
	public void baseAction() {

	}

}
