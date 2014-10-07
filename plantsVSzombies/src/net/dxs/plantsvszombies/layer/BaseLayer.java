package net.dxs.plantsvszombies.layer;

import net.dxs.plantsvszombies.R;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGSize;

/**
 * 图层基类
 * 
 * @author lijian
 * 
 */
public class BaseLayer extends CCLayer {

	public static SoundEngine soundEngine;

	/** 屏幕尺寸 **/
	protected CGSize winSize;

	// 随着类的加载而加载
	static {
		soundEngine = SoundEngine.sharedEngine();
		soundEngine.preloadSound(CCDirector.theApp, R.raw.day);
		soundEngine.preloadSound(CCDirector.theApp, R.raw.night);
		soundEngine.preloadSound(CCDirector.theApp, R.raw.start);
		soundEngine.preloadEffect(CCDirector.theApp, R.raw.onclick);
	}
	// 构造方法前
	{

	}

	public BaseLayer() {
		winSize = CCDirector.sharedDirector().getWinSize();
	}
}
