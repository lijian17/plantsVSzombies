package net.dxs.plantsvszombies.layer;

import org.cocos2d.nodes.CCSprite;

public class MenuLayer extends BaseLayer {

	public MenuLayer() {
		init();
	}

	private void init() {
		CCSprite sprite = CCSprite.sprite("image/menu/main_menu_bg.jpg");
		sprite.setAnchorPoint(0, 0);
		this.addChild(sprite);
	}
}
