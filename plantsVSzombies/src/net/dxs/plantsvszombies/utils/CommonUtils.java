package net.dxs.plantsvszombies.utils;

import java.util.ArrayList;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.transitions.CCJumpZoomTransition;

/**
 * 工具类
 * 
 * @author lijian
 * 
 */
public class CommonUtils {

	/**
	 * 序列帧播放
	 * 
	 * @param path
	 *            序列帧图片的路径 可扩展的
	 * @param num
	 *            序列帧图片的数量
	 * @param forever
	 *            是否永不停止的播放 true 永不停止
	 * @return 序列帧的动作
	 */
	public static CCAction animate(String path, int num, boolean forever) {
		ArrayList<CCSpriteFrame> frames = new ArrayList<CCSpriteFrame>();
		for (int i = 1; i <= num; i++) {
			CCSpriteFrame frame = CCSprite.sprite(String.format(path, i)).displayedFrame();
			frames.add(frame);
		}
		CCAnimation anim = CCAnimation.animation("", 0.2f, frames);
		if (forever) {
			CCAnimate animate = CCAnimate.action(anim);
			CCRepeatForever repeatForever = CCRepeatForever.action(animate);
			return repeatForever;
		} else {
			CCAnimate animate = CCAnimate.action(anim, false);
			return animate;
		}
	}

	/**
	 * 切换图层
	 * 
	 * @param layer
	 *            要换的图层
	 */
	public static void changeLayer(CCLayer layer) {
		//获得导演
		CCDirector director = CCDirector.sharedDirector();
		//获得场景
		CCScene scene = CCScene.node();
		//添加图层
		scene.addChild(layer);
		//将场景进行动画包装
		CCJumpZoomTransition transition = CCJumpZoomTransition.transition(2, scene);
		//替换场景
		director.replaceScene(transition);
	}

}
