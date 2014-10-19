package net.dxs.plantsvszombies.bean;

import org.cocos2d.actions.CCScheduler;
import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCFiniteTimeAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.util.CGPointUtil;

import android.util.Log;

import net.dxs.plantsvszombies.base.BaseElement;
import net.dxs.plantsvszombies.base.Plant;
import net.dxs.plantsvszombies.base.Zombies;
import net.dxs.plantsvszombies.utils.CommonUtils;

/**
 * 普通僵尸
 * 
 * @author lijian
 * 
 */
public class PrimaryZombies extends Zombies {

	private static final String TAG = "PrimaryZombies";

	/** 起始点 **/
	private CGPoint startPoint;
	/** 结束点 **/
	private CGPoint endPoint;

	public PrimaryZombies(CGPoint startPoint, CGPoint endPoint) {
		super("image/zombies/zombies_1/walk/z_1_01.png");
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		setPosition(startPoint);

		move();
	}

	@Override
	public void move() {
		CCMoveTo moveTo = CCMoveTo.action(CGPointUtil.distance(startPoint, endPoint) / speed, endPoint);
		CCSequence sequence = CCSequence.actions(moveTo, CCCallFunc.action(this, "gameOver"));
		this.runAction(sequence);

		CCAction animate = CommonUtils.animate("image/zombies/zombies_1/walk/z_1_%02d.png", 7, true);
		this.runAction(animate);
	}

	public void gameOver() {
		destroy();
	}

	/** 记录僵尸的攻击状态 **/
	private boolean isAttack;
	/** 目标植物 **/
	private Plant targetPlant;

	@Override
	public void attack(BaseElement element) {
		if (!isAttack) {
			isAttack = true;
			this.stopAllActions();
			if (element instanceof Plant) {
				this.runAction(CommonUtils.animate("image/zombies/zombies_1/attack/z_1_attack_%02d.png", 10, true));
				Plant plant = (Plant) element;
				targetPlant = plant;
				CCScheduler.sharedScheduler().schedule("attack", this, 0.5f, false);
			}
		}
	}

	/**
	 * 
	 * @param t
	 */
	public void attack(float t) {
		Log.i(TAG, "僵尸啃植物" + attack);
		targetPlant.attacked(attack);
		if (targetPlant.getLife() <= 0) {
			isAttack = false;
			CCScheduler.sharedScheduler().unschedule("attack", this);
			this.stopAllActions();
			move();
		}
	}

	/** 僵尸是否死亡标记 **/
	private boolean isDie = false;

	@Override
	public void attacked(int attack) {
		this.life -= attack;
		if (life <= 0 && !isDie) {
			isDie = true;
			//僵尸头掉下动画
			CCAction animate_head = CommonUtils.animate("image/zombies/zombies_1/head/z_1_head_%02d.png", 6, false);
			//僵尸倒地动画
			CCAction animate_die = CommonUtils.animate("image/zombies/zombies_1/die/z_1_die_%02d.png", 6, false);
			CCSequence sequence = CCSequence.actions((CCFiniteTimeAction) animate_head, (CCFiniteTimeAction) animate_die, CCCallFunc.action(this, "destroy"));

			this.runAction(sequence);
		}
	}

	@Override
	public void baseAction() {
		// TODO Auto-generated method stub

	}

}
