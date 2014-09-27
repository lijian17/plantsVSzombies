package net.dxs.plantsvszombies;

import net.dxs.plantsvszombies.layer.FightLayer;
import net.dxs.plantsvszombies.layer.MenuLayer;
import net.dxs.plantsvszombies.layer.WelcomeLayer;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//获得一个surfaceView
		CCGLSurfaceView surfaceView = new CCGLSurfaceView(this);
		setContentView(surfaceView);

		//获得一个导演
		CCDirector director = CCDirector.sharedDirector();
		director.attachInView(surfaceView);//开启绘制线程

		director.setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);
		director.setScreenSize(480, 320);

		director.setDisplayFPS(true);//显示帧率

		//获得一个场景
		CCScene scene = CCScene.node();

		//		WelcomeLayer layer = new WelcomeLayer();
		MenuLayer layer = new MenuLayer();
		//		FightLayer layer = new FightLayer();

		//添加图层
		scene.addChild(layer);

		//运行场景
		director.runWithScene(scene);
	}
}
