package net.dxs.plantsvszombies.base;

import java.util.HashMap;

import org.cocos2d.nodes.CCSprite;

/**
 * 显示植物的信息
 * 
 * @author lijian
 * 
 */
public class ShowPlant {
	private int id;
	private int sunNum = 50;// 阳光数

	private CCSprite bg; // 背景 精灵
	private CCSprite defaultSprite; // 默认显示的

	public ShowPlant(int id) {
		super();
		this.id = id;
		HashMap<String, String> hashMap = data.get(id);
		bg = CCSprite.sprite(hashMap.get("bg"));
		bg.setOpacity(100);
		bg.setAnchorPoint(0, 0);//改变锚点

		defaultSprite = CCSprite.sprite(hashMap.get("bg"));
		defaultSprite.setAnchorPoint(0, 0);//改变锚点

	}

	public int getId() {
		return id;
	}

	public int getSunNum() {
		return sunNum;
	}

	public void setSunNum(int sunNum) {
		this.sunNum = sunNum;
	}

	public CCSprite getBg() {
		return bg;
	}

	public CCSprite getDefaultSprite() {
		return defaultSprite;
	}

	public static HashMap<Integer, HashMap<String, String>> getData() {
		return data;
	}

	// 以下都是假数据 模拟数据库
	private static HashMap<Integer, HashMap<String, String>> data;
	static {
		data = new HashMap<Integer, HashMap<String, String>>();
		for (int id = 1; id <= 9; id++) {
			HashMap<String, String> item = new HashMap<String, String>();
			item.put("bg", String.format("image/fight/chose/choose_default%02d.png", id));
			item.put("sum", 50 + "");
			data.put(id, item);
		}

	}

}