package net.dxs.plantsvszombies.bean;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.util.CGPointUtil;

import net.dxs.plantsvszombies.base.Bullet;

/**
 * 豌豆子弹
 * 
 * @author lijian
 * 
 */
public class Pease extends Bullet {

	public Pease() {
		super("image/fight/bullet.png");
		setScale(0.65);
		speed = 100;
		attack = 100;
	}

	@Override
	public void move() {
		//终点坐标
		CGPoint endPoint = CGPoint.ccp(CCDirector.sharedDirector().getWinSize().width - 20, this.getPosition().y);

		//子弹到终点所需时间
		float t = CGPointUtil.distance(getPosition(), endPoint) / speed;
		CCMoveTo moveTo = CCMoveTo.action(t, endPoint);

		this.runAction(CCSequence.actions(moveTo, CCCallFunc.action(this, "destroy"))); // 走到头 就销毁
	}

}
