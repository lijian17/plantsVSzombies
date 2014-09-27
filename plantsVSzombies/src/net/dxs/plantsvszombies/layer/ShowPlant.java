package net.dxs.plantsvszombies.layer;

import java.util.HashMap;
import java.util.Map;

import org.cocos2d.nodes.CCSprite;

/**
 * 展示用植物
 * 
 * @author lijian
 * 
 */
public class ShowPlant extends BaseLayer {

	//模拟数据库的操作
	public static Map<Integer, HashMap<String, String>> plants;

	static {
		plants = new HashMap<Integer, HashMap<String, String>>();
		for (int i = 1; i <= 9; i++) {
			HashMap<String, String> plant = new HashMap<String, String>();
			plant.put("sum", 50 + "");
			plant.put("path", String.format("image/fight/chose/choose_default%02d.png", i));
			plants.put(i, plant);
		}
	}

	private int id;
	private CCSprite defaultSprite;
	private CCSprite bgSprite;
	private int sum = 50;

	public ShowPlant(int id) {
		super();
		this.id = id;
		HashMap<String, String> hashMap = plants.get(id);
		defaultSprite = CCSprite.sprite(hashMap.get("path"));
		defaultSprite.setAnchorPoint(0, 0);

		bgSprite = CCSprite.sprite(hashMap.get("path"));
		bgSprite.setAnchorPoint(0, 0);
		bgSprite.setOpacity(100);
	}

	public int getId() {
		return id;
	}

	public CCSprite getDefaultSprite() {
		return defaultSprite;
	}

	public CCSprite getBgSprite() {
		return bgSprite;
	}

}
