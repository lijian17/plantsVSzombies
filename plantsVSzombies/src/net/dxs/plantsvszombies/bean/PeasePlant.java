package net.dxs.plantsvszombies.bean;

import net.dxs.plantsvszombies.base.AttackPlant;
import net.dxs.plantsvszombies.base.Bullet;
import net.dxs.plantsvszombies.utils.CommonUtils;

/**
 * 豌豆射手
 * 
 * @author lijian
 * 
 */
public class PeasePlant extends AttackPlant {

	public PeasePlant() {
		super("image/plant/pease/p_2_01.png");
		baseAction();
	}

	@Override
	public Bullet createBullet() {
		if (bullets.size() <= 0) {
			final Pease pease = new Pease();
			bullets.add(pease);

			pease.setPosition(ccp(getPosition().x + 20, getPosition().y + 35));//豌豆射手嘴部位置
			this.getParent().addChild(pease);
			pease.move();

			pease.setOnDieListener(new DieListener() {

				@Override
				public void die() {
					bullets.remove(pease);
				}
			});
		}
		return null;
	}

	@Override
	public void baseAction() {
		this.runAction(CommonUtils.animate("image/plant/pease/p_2_%02d.png", 8, true));
	}

}
