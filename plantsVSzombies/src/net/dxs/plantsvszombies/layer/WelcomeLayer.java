package net.dxs.plantsvszombies.layer;

import net.dxs.plantsvszombies.R;
import net.dxs.plantsvszombies.utils.CommonUtils;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGRect;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.MotionEvent;

public class WelcomeLayer extends BaseLayer {

	private CCSprite sprite;
	private CCSprite start;

	public WelcomeLayer() {
		soundEngine.playSound(CCDirector.theApp, R.raw.day, true);
		init();
	}

	private void init() {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				SystemClock.sleep(6000);
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				setIsTouchEnabled(true);
				start.setVisible(true);
				sprite.stopAllActions();
				sprite.removeSelf();
				WelcomeLayer.this.addChild(start);

				super.onPostExecute(result);
			}

		}.execute();
		logo();
	}

	/**
	 * logo展示
	 */
	private void logo() {
		CCSprite logo = CCSprite.sprite("image/popcap_logo.png");
		this.addChild(logo);
		logo.setPosition(winSize.width / 2, winSize.height / 2);

		// logo 显示1秒钟  消失1秒钟  
		CCHide hide = CCHide.action();// 消失 

		CCSequence sequence = CCSequence.actions(CCDelayTime.action(1), hide, CCDelayTime.action(1), CCCallFunc.action(this, "welcome"));
		logo.runAction(sequence);
	}

	/**
	 * 欢迎界面
	 */
	public void welcome() {
		CCSprite welcome = CCSprite.sprite("image/welcome.jpg");
		welcome.setAnchorPoint(0, 0);
		this.addChild(welcome);

		loading();
	}

	/**
	 * 加载数据进度条动画
	 */
	private void loading() {
		String path = "image/loading/loading_%02d.png";
		CCAction animate = CommonUtils.animate(path, 9, false);

		sprite = CCSprite.sprite("image/loading/loading_01.png");
		sprite.setPosition(winSize.width / 2, 30);
		this.addChild(sprite);
		sprite.runAction(animate);

		start = CCSprite.sprite("image/loading/loading_start.png");
		start.setPosition(winSize.width / 2, 30);
		start.setVisible(false);
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		if (CGRect.containsPoint(start.getBoundingBox(), this.convertTouchToNodeSpace(event))) {
			soundEngine.playEffect(CCDirector.theApp, R.raw.onclick);
			gotoMenu();
		}
		return super.ccTouchesEnded(event);
	}

	/**
	 * 进入菜单界面
	 */
	private void gotoMenu() {
		CommonUtils.changeLayer(new MenuLayer());
	}

}
