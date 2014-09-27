package net.dxs.plantsvszombies.layer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.dxs.plantsvszombies.bean.ShowZombies;
import net.dxs.plantsvszombies.utils.CommonUtils;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import android.view.MotionEvent;

public class FightLayer extends BaseLayer {

	/** 游戏地图 **/
	private CCTMXTiledMap gameMap;
	/** 植物集合 **/
	private ArrayList<ShowPlant> mArr_choose;
	/** 已选植物集合 **/
	private CopyOnWriteArrayList<ShowPlant> mArr_chose;
	/** 已选植物框 **/
	private CCSprite choseSprite;
	/** 供选择植物框 **/
	private CCSprite chooseSprite;
	/** 一起摇滚吧 **/
	private CCSprite startGame;

	public FightLayer() {
		init();
	}

	private void init() {
		//加载地图
		loadMap();
		// 加载僵尸的位置
		// 加载僵尸 摇晃的动作
		loadShowZombies();
		// 地图移动
		moveMap();
	}

	/**
	 * 移动地图
	 */
	private void moveMap() {
		CCMoveBy by = CCMoveBy.action(2, ccp(winSize.width - gameMap.getContentSize().width, 0));
		CCSequence sequence = CCSequence.actions(CCDelayTime.action(1), by, CCDelayTime.action(1), CCCallFunc.action(this, "loadContainer"));
		gameMap.runAction(sequence);
	}

	/**
	 * 加载容器
	 */
	public void loadContainer() {
		mArr_choose = new ArrayList<ShowPlant>();
		mArr_chose = new CopyOnWriteArrayList<ShowPlant>();

		//植物容器一
		choseSprite = CCSprite.sprite("image/fight/chose/fight_chose.png");
		choseSprite.setAnchorPoint(0, 1);
		choseSprite.setPosition(0, winSize.height);
		this.addChild(choseSprite);

		//植物容器二
		chooseSprite = CCSprite.sprite("image/fight/chose/fight_choose.png");
		chooseSprite.setAnchorPoint(0, 0);
		this.addChild(chooseSprite);

		//加载植物
		loadPlant();

		startGame = CCSprite.sprite("image/fight/chose/fight_start.png");
		startGame.setPosition(chooseSprite.getContentSize().width / 2, 30);
		chooseSprite.addChild(startGame);
	}

	int rowNum = 4;

	/**
	 * 加载植物
	 */
	private void loadPlant() {
		for (int i = 1; i <= 9; i++) {
			ShowPlant plant = new ShowPlant(i);
			CCSprite defaultSprite = plant.getDefaultSprite();
			defaultSprite.setAnchorPoint(0, 0);
			defaultSprite.setPosition(16 + ((i - 1) % rowNum) * 54, 175 - ((i - 1) / rowNum) * 59);

			CCSprite bgSprite = plant.getBgSprite();
			bgSprite.setAnchorPoint(0, 0);
			bgSprite.setPosition(16 + ((i - 1) % rowNum) * 54, 175 - ((i - 1) / rowNum) * 59);

			chooseSprite.addChild(defaultSprite);
			chooseSprite.addChild(bgSprite);

			mArr_choose.add(plant);
			//打开点击事件
			setIsTouchEnabled(true);
		}
	}

	private boolean isSel = false;

	public void setIsSel() {
		this.isSel = false;
	}

	private boolean isDel;

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		CGPoint touchPoint = this.convertTouchToNodeSpace(event);
		if (CGRect.containsPoint(chooseSprite.getBoundingBox(), touchPoint)) {//触摸点在植物选择框内
			if (CGRect.containsPoint(startGame.getBoundingBox(), touchPoint)) {
				System.out.println("一起摇滚吧");
			} else {
				for (ShowPlant plant : mArr_choose) {
					if (mArr_chose.size() < 5) {//如果已选择植物＜5,就可以继续选择
						if (CGRect.containsPoint(plant.getDefaultSprite().getBoundingBox(), touchPoint) && !isSel) {
							isSel = true;//解决快速点击选择植物的Bug
							CGPoint ccp = CGPoint.ccp(75 + mArr_chose.size() * 53, winSize.height - 65);
							CCMoveTo moveTo = CCMoveTo.action(0.2f, ccp);

							CCSequence sequence = CCSequence.actions(moveTo, CCCallFunc.action(this, "setIsSel"));

							plant.getDefaultSprite().runAction(sequence);
							mArr_chose.add(plant);
						}
					}
				}
			}

		} else if (CGRect.containsPoint(choseSprite.getBoundingBox(), touchPoint)) {//触摸点在已选择植物框内
			for (ShowPlant plant : mArr_chose) {
				if (CGRect.containsPoint(plant.getDefaultSprite().getBoundingBox(), touchPoint)) {
					CGPoint point = plant.getBgSprite().getPosition();
					CCMoveTo moveTo = CCMoveTo.action(0.2f, point);
					plant.getDefaultSprite().runAction(moveTo);
					mArr_chose.remove(plant);
					isDel = true;
				} else if (isDel) {
					CCMoveBy by = CCMoveBy.action(0.2f, CGPoint.ccp(-53, 0));
					plant.getDefaultSprite().runAction(by);
				}
			}
			isDel = false;
		}
		return super.ccTouchesBegan(event);
	}

	/**
	 * 加载展示用的僵尸
	 */
	private void loadShowZombies() {
		List<ShowZombies> showZombies = new ArrayList<ShowZombies>();

		List<CGPoint> points = CommonUtils.getMapPoint(gameMap, "zombies");
		for (int i = 0; i < points.size(); i++) {
			if (i % 2 == 0) {
				ShowZombies zombies = new ShowZombies();
				zombies.setPosition(points.get(i));
				gameMap.addChild(zombies);
				showZombies.add(zombies);
			} else {
				ShowNut nut = new ShowNut();
				nut.setPosition(points.get(i));
				gameMap.addChild(nut);
			}
		}
	}

	/**
	 * 加载地图
	 */
	private void loadMap() {
		gameMap = CCTMXTiledMap.tiledMap("image/fight/map_day.tmx");
		this.addChild(gameMap);
	}

}
