package net.dxs.plantsvszombies.base;

/**
 * 防御型植物
 * 
 * @author lijian
 * 
 */
public abstract class DefancePlant extends Plant {

	public DefancePlant(String filepath) {
		super(filepath);
		life = 200;
	}

}