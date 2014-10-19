package net.dxs.plantsvszombies.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cocos2d.actions.CCScheduler;

import net.dxs.plantsvszombies.base.AttackPlant;
import net.dxs.plantsvszombies.base.BaseElement.DieListener;
import net.dxs.plantsvszombies.base.Bullet;
import net.dxs.plantsvszombies.base.Plant;
import net.dxs.plantsvszombies.base.Zombies;

/**
 * 行战场
 * 
 * @author lijian
 * 
 */
public class FightLine {
	int lineNum;

	/** 存放僵尸的集合 **/
	private List<Zombies> mArr_zombies;
	/** 存放植物的集合 **/
	private Map<Integer, Plant> mMap_plant;
	private List<AttackPlant> mArr_AttackPlants;

	public FightLine() {
		mArr_zombies = new ArrayList<Zombies>();
		mMap_plant = new HashMap<Integer, Plant>();
		mArr_AttackPlants = new ArrayList<AttackPlant>();

		//僵尸攻击植物
		CCScheduler.sharedScheduler().schedule("attackPlant", this, 0.5f, false);

		//生产子弹
		CCScheduler.sharedScheduler().schedule("createPease", this, 0.5f, false);

		//植物攻击僵尸
		CCScheduler.sharedScheduler().schedule("attackZombies", this, 0.2f, false);
	}

	/**
	 * 植物攻击僵尸
	 * 
	 * @param t
	 */
	public void attackZombies(float t) {
		//得到弹夹
		for (AttackPlant plant : mArr_AttackPlants) {
			List<Bullet> bullets = plant.getBullets();
			for (Bullet bullet : bullets) {
				for (Zombies zombies : mArr_zombies) {
					int x = (int) bullet.getPosition().x;
					int left = (int) (zombies.getPosition().x - 10);
					int right = (int) (zombies.getPosition().x + 10);
					if (x >= left && x <= right) {//如果在范围内就发生了碰撞
						zombies.attacked(bullet.getAttack());//僵尸生命力减少
						bullet.destroy();//子弹消失
					}
				}
			}
		}
	}

	/**
	 * 生产子弹
	 * 
	 * @param t
	 */
	public void createPease(float t) {
		if (mArr_AttackPlants.size() > 0 && mArr_zombies.size() > 0) {
			for (AttackPlant plant : mArr_AttackPlants) {
				plant.createBullet();
			}
		}
	}

	/**
	 * 僵尸啃植物
	 * 
	 * @param t
	 */
	public void attackPlant(float t) {
		if (mArr_zombies.size() > 0 && mMap_plant.size() > 0) {
			for (Zombies zombies : mArr_zombies) {
				int col = (int) (zombies.getPosition().x / 46 - 1);
				Plant plant = mMap_plant.get(col);
				if (plant != null) {
					zombies.attack(plant);
				}
			}
		}
	}

	/**
	 * 添加僵尸
	 * 
	 * @param zombies
	 */
	public void addZombies(final Zombies zombies) {
		mArr_zombies.add(zombies);

		zombies.setOnDieListener(new DieListener() {//死亡的监听

			@Override
			public void die() {
				mArr_zombies.remove(zombies);
			}
		});
	}

	/**
	 * 判断某列上是否有植物
	 * 
	 * @param col
	 * @return
	 */
	public boolean isContains(int col) {
		return mMap_plant.containsKey(col);
	}

	/**
	 * 添加植物
	 * 
	 * @param plant
	 */
	public void addPlant(final Plant plant) {
		mMap_plant.put(plant.getCol(), plant);

		if (plant instanceof AttackPlant) {
			mArr_AttackPlants.add((AttackPlant) plant);
		}

		plant.setOnDieListener(new DieListener() {//死亡的监听
			@Override
			public void die() {
				mMap_plant.remove(plant.getCol());
				if (plant instanceof AttackPlant) {
					mArr_AttackPlants.remove((AttackPlant) plant);
				}
			}
		});
	}
}
