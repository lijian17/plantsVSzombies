package net.dxs.plantsvszombies.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.dxs.plantsvszombies.base.Plant;
import net.dxs.plantsvszombies.bean.Nut;
import net.dxs.plantsvszombies.bean.PeasePlant;
import net.dxs.plantsvszombies.bean.PrimaryZombies;
import net.dxs.plantsvszombies.bean.SunPlant;
import net.dxs.plantsvszombies.layer.FightLayer;
import net.dxs.plantsvszombies.layer.ShowPlant;
import net.dxs.plantsvszombies.utils.CommonUtils;

import org.cocos2d.actions.CCProgressTimer;
import org.cocos2d.actions.CCScheduler;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import android.util.Log;

/**
 * 处理游戏业务的类
 * 
 * @author lijian
 * 
 */
public class GameController {

	private static final String TAG = "GameController";
	/** 游戏是否开始 **/
	public static boolean isStart;
	/** 游戏地图 **/
	private CCTMXTiledMap gameMap;
	/** 游戏植物集合 **/
	private List<ShowPlant> mArr_chose;
	/** 路的点集合 **/
	private List<CGPoint> roadPoints;
	/** 植物种植点数组 **/
	public CGPoint[][] towers = new CGPoint[5][9];

	private GameController() {

	}

	private static GameController instance = new GameController();

	public static GameController getInstance() {
		return instance;
	}

	/** 开辟行战场 **/
	static List<FightLine> mList_fightLine;

	static {
		mList_fightLine = new ArrayList<FightLine>();
		for (int i = 0; i < 5; i++) {
			FightLine fightLine = new FightLine();
			mList_fightLine.add(fightLine);
		}
	}

	/**
	 * 开始游戏
	 * 
	 * @param gameMap 游戏地图
	 * @param mArr_choose 游戏植物
	 */
	public void start(CCTMXTiledMap gameMap, List<ShowPlant> mArr_chose) {
		this.gameMap = gameMap;
		this.mArr_chose = mArr_chose;
		this.isStart = true;

		// 解析僵尸移动的点
		loadRoad();
		//addZombies();
		//添加僵尸,每隔5s调用一次方法,被调用的方法必须带float参数
		CCScheduler.sharedScheduler().schedule("addZombies", this, 5, false);
		// 添加植物
		loadPlantPoint();

		// 僵尸攻击植物
		// 植物攻击僵尸
		// 进度的显示
		progress();
	}

	/**
	 * 加载植物可种植的点
	 */
	private void loadPlantPoint() {
		String str = "tower%02d";
		for (int i = 1; i <= 5; i++) {
			String name = String.format(str, i);
			List<CGPoint> mapPoint = CommonUtils.getMapPoint(gameMap, name);
			for (int j = 0; j < mapPoint.size(); j++) {
				towers[i - 1][j] = mapPoint.get(j);
			}
		}
	}

	/**
	 * 添加僵尸
	 */
	public void addZombies(float t) {
		Random random = new Random();
		int lineNum = random.nextInt(5);//随机1-4
		//开始点
		CGPoint startPoint = roadPoints.get(lineNum * 2);
		//结束点
		CGPoint endPoint = roadPoints.get(lineNum * 2 + 1);

		PrimaryZombies zombies = new PrimaryZombies(startPoint, endPoint);
		gameMap.addChild(zombies, 1);

		FightLine fightLine = mList_fightLine.get(lineNum);
		fightLine.addZombies(zombies);

		progressTimer.setPercentage(progressTimer.getPercentage() + 5);
	}

	/**
	 * 解析路的点
	 */
	private void loadRoad() {
		roadPoints = CommonUtils.getMapPoint(gameMap, "road");
	}

	/**
	 * 结束游戏
	 */
	public void gameOver() {
		this.isStart = false;

	}

	/** 玩家当前选择的植物 **/
	private ShowPlant currentPlant;
	/** 当前需要安放的植物 **/
	private Plant current;

	/**
	 * 处理游戏开始后的点击事件
	 * 
	 * @param touchPoint 触摸点
	 */
	public void handlerTouch(CGPoint touchPoint) {
		CCNode chose = this.gameMap.getParent().getChildByTag(FightLayer.TAG_CHOSE);
		if (CGRect.containsPoint(chose.getBoundingBox(), touchPoint)) {//点击的点在植物容器内
			//玩家可能选择植物
			for (ShowPlant plant : mArr_chose) {
				if (CGRect.containsPoint(plant.getDefaultSprite().getBoundingBox(), touchPoint)) {
					currentPlant = plant;
					currentPlant.getDefaultSprite().setOpacity(100);
					switch (currentPlant.getId()) {
					case 1://一个豌豆射手
						Log.i(TAG, "豌豆射手--->");
						current = new PeasePlant();
						break;
					case 2://一个向日葵
						Log.i(TAG, "向日葵--->");
						current = new SunPlant();
						break;
					case 3://一个爆炸草莓
						Log.i(TAG, "爆炸草莓--->");
						break;
					case 4://一个坚果
						Log.i(TAG, "坚果--->");
						current = new Nut();
						break;
					case 5://一个土豆地雷
						Log.i(TAG, "土豆地雷--->");
						break;
					case 6://一个寒冰射手
						Log.i(TAG, "寒冰射手--->");
						break;
					case 7://一个食人花
						Log.i(TAG, "食人花--->");
						break;
					case 8://一个双重射手
						Log.i(TAG, "双重射手--->");
						break;
					case 9://一个蘑菇
						Log.i(TAG, "蘑菇--->");
						break;

					default:
						break;
					}
				}
			}
		} else if (currentPlant != null) {
			if (isBuild(touchPoint)) {//可以安放植物
				FightLine line = mList_fightLine.get(current.getLine());//得到当前行
				if (!line.isContains(current.getCol())) {//当前行的该列还没有安放植物
					this.gameMap.addChild(current);
					line.addPlant(current);
				}
			}
			currentPlant.getDefaultSprite().setOpacity(255);
			current = null;
			currentPlant = null;
		}
	}

	/**
	 * 判断是否在允许安放植物的范围
	 * 
	 * @param touchPoint
	 * @return
	 */
	private boolean isBuild(CGPoint touchPoint) {
		//列
		int column = (int) (touchPoint.x / 46);
		//行
		int line = (int) ((CCDirector.sharedDirector().getWinSize().height - touchPoint.y) / 54);

		if (column >= 1 && column <= 9 && line >= 1 && line <= 5) {//有效列和行(即植物有效安放位置)
			current.setPosition(towers[line - 1][column - 1]);
			current.setCol(column - 1);
			current.setLine(line - 1);
			return true;
		} else {
			return false;
		}
	}

	CCProgressTimer progressTimer;

	/**
	 * 更新进度
	 */
	private void progress() {
		progressTimer = CCProgressTimer.progressWithFile("image/fight/progress.png");

		progressTimer.setPosition(CCDirector.sharedDirector().getWinSize().width - 80, 13);
		gameMap.getParent().addChild(progressTimer);
		progressTimer.setScale(0.6f);

		progressTimer.setPercentage(0);//每增加一个僵尸需要调整进度，增加5   0-100
		// 设置样式
		progressTimer.setType(CCProgressTimer.kCCProgressTimerTypeHorizontalBarRL);

		CCSprite sprite = CCSprite.sprite("image/fight/flagmeter.png");
		sprite.setPosition(CCDirector.sharedDirector().getWinSize().width - 80, 13);
		gameMap.getParent().addChild(sprite);
		sprite.setScale(0.6f);
		CCSprite name = CCSprite.sprite("image/fight/FlagMeterLevelProgress.png");
		name.setPosition(CCDirector.sharedDirector().getWinSize().width - 80, 5);
		gameMap.getParent().addChild(name);
		name.setScale(0.6f);
	}
}
