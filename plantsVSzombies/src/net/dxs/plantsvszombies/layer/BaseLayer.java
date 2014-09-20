package net.dxs.plantsvszombies.layer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGSize;

public class BaseLayer extends CCLayer {

	/** 屏幕尺寸 **/
	protected CGSize winSize;

	public BaseLayer() {
		winSize = CCDirector.sharedDirector().getWinSize();
	}
}
