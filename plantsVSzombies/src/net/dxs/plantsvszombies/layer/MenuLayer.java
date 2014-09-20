package net.dxs.plantsvszombies.layer;

import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCSprite;

public class MenuLayer extends BaseLayer {

	public MenuLayer() {
		init();
	}

	private void init() {
		CCSprite sprite = CCSprite.sprite("image/menu/main_menu_bg.jpg");
		sprite.setAnchorPoint(0, 0);
		this.addChild(sprite);

		menu();
	}

	/**
	 * 菜单
	 */
	private void menu() {
		//创建一个菜单
		CCMenu menu = CCMenu.menu();
		//菜单条目
		CCMenuItemSprite item = CCMenuItemSprite.item(CCSprite.sprite("image/menu/start_adventure_default.png"), CCSprite.sprite("image/menu/start_adventure_press.png"), this, "click");
		menu.addChild(item);
		menu.setScale(0.5f);
		menu.setPosition(winSize.width / 2 - 25, winSize.height / 2 - 110);
		menu.setRotation(4.5f);
		this.addChild(menu);
	}

	public void click(Object object) {
		System.out.println("被点击了");
	}
}
