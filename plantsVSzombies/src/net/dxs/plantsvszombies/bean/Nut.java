package net.dxs.plantsvszombies.bean;

import net.dxs.plantsvszombies.base.DefancePlant;
import net.dxs.plantsvszombies.utils.CommonUtils;

public class Nut extends DefancePlant {

	public Nut() {
		super("image/plant/nut/p_3_01.png");
		baseAction();
	}

	@Override
	public void baseAction() {
		this.runAction(CommonUtils.animate("image/plant/nut/p_3_%02d.png", 11, true));
	}

}
