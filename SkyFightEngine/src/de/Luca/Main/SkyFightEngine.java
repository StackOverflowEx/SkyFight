package de.Luca.Main;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;

import de.Luca.Blocks.Block;
import de.Luca.Blocks.BlockData;
import de.Luca.Blocks.BlockManager;
import de.Luca.Calculation.MainBeat;
import de.Luca.Calculation.PlayerCalc;
import de.Luca.Entities.EntityManager;
import de.Luca.Entities.Player;
import de.Luca.EventManager.EventManager;
import de.Luca.GUI.GUIListener;
import de.Luca.GUI.GUIManager;
import de.Luca.Loading.Loader;
import de.Luca.Models.Texture;
import de.Luca.Rendering.MasterRenderer;
import de.Luca.Rendering.RenderLoop;
import de.Luca.Text.TextManager;
import de.Luca.Utils.DefaultKeyListener;
import de.Luca.Window.Window;

public class SkyFightEngine {
		
	public static void init() {
		Window.init(1280, 720, "SkyFight");
		
		GL11.glGenTextures();
		
		TextManager.init();
		GUIManager.init();
		BlockManager.init();
		EntityManager.init();
		EventManager.registerEvent(new GUIListener());
		EventManager.registerEvent(new DefaultKeyListener());
		
		new MasterRenderer(new RenderLoop());
		MasterRenderer.begin();
		
		TextManager.generateFont("C:\\Windows\\Fonts\\Arial.ttf", 20f, "Arial", false, false);	
		
		Texture test = Loader.loadTexture("D:\\Icons\\test.png");

		BlockData bd = new BlockData(1f, 1f, "Dirt", test);
		Block b = new Block(bd, new Vector2f(-2, 2));
		Block b1 = new Block(bd, new Vector2f(0, 2));
		Block b2 = new Block(bd, new Vector2f(1, 1));
//		Block b3 = new Block(bd, new Vector2f(-1, -1));
		BlockManager.addBlock(b);
		BlockManager.addBlock(b1);
		BlockManager.addBlock(b2);
//		BlockManager.addBlock(b3);
		
		Player p = new Player(test, test, new Vector2f(1, 3));
		PlayerCalc.init(p);
		EntityManager.addEntity(p);
		
		MainBeat.init();
	}
	
	public static void stop() {
		TextManager.cleanUP();
		MasterRenderer.cleanUP();
		GUIManager.cleanUP();
		Loader.cleanUP();
		Window.closeWindow();
	}

}
