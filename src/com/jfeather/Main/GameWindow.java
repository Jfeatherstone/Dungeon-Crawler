package com.jfeather.Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jfeather.Exceptions.*;
import com.jfeather.Game.GameInstance;
import com.jfeather.Generation.*;
import com.jfeather.Items.*;
import com.jfeather.Player.Character;
import com.jfeather.Player.SaveSelect;

public class GameWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static GameWindow frame;
	public GameInstance instance;
	public volatile TitleScreen ts;
	private Inventory inv;
	private Character character;
	public static final String TITLE = "Dungeon Dash";
	// TODO: credit to Cheesy for the title
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new GameWindow();
					frame.setVisible(true);
					JFrame frame2 = new JFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public GameWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 520);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);
		setTitle(TITLE);
		setResizable(false);
		setBackground(new Color(0, 0, 0));
		
		Character jack = new Character("Jack");
		/*

		GameInstance instance = new GameInstance(jack);
		add(instance, BorderLayout.NORTH);
		addMouseListener(instance.ML);
		addMouseMotionListener(instance.MML);
		instance.setFPS(60);
		
		Weapon test = WeaponsGen.genWeapon(jack);
		inv.setActiveWeapon(test);
		
		instance.requestFocus();
		
		/*
		for (int i = 0; i < 5; i++) {
			Weapon testSword = WeaponsGen.genWeapon(jack, Weapon.WAND);
			inv.addItem(testSword);
		}
		
		for (int i = 5; i < 10; i++) {
			Weapon testSword = WeaponsGen.genWeapon(jack, Weapon.BOW);
			inv.addItem(testSword);
		}
		*/
		
		Thread title = new Thread() {
			@Override
			public void run() {
				ts = new TitleScreen(contentPane, jack);
				pack();
			}
		};
		title.start();
		
		/*
		new Thread () {
			@Override
			public void run() {
				//while (true) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//jack.setInvulnerable(true);
					//jack.subtractHealth(50);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//jack.setInvulnerable(false);
				//}
			}
		}.start();
		
		new Thread () {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//jack.subtractHealth(2);
				}
			}
		}.start();

		*/
		//inv.setUpdate(true);
		//inv.setUpdateInterval(40);
		
	}
	
	public Inventory createInv(Character c, int capacity) {
		Inventory inv;
		try {
			inv = new Inventory(c, capacity);
			add(inv, BorderLayout.SOUTH);
			//add(inv);
			// This has to be here to initialize the tool tips, because otherwise they are only initialized when you click
			for (int i = 0; i < capacity; i++) {
				inv.slots[i].setToolTipText("Empty Slot");
			}
			return inv;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public void close() {
		frame.dispose();
	}
	
	public void beginGame() {
		GameInstance instance = new GameInstance(character);
		add(instance, BorderLayout.NORTH);
		addMouseListener(instance.ML);
		addMouseMotionListener(instance.MML);
		instance.setFPS(60);
		inv = createInv(character, 5);
	}
	
}