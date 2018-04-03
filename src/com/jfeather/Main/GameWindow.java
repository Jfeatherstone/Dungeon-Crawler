package com.jfeather.Main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.swing.border.EmptyBorder;

import com.jfeather.Exceptions.*;
import com.jfeather.Game.GameInstance;
import com.jfeather.Generation.*;
import com.jfeather.Items.*;
import com.jfeather.Player.Character;

public class GameWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static GameWindow frame;
	public GameInstance instance;
	public TitleScreen ts;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new GameWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public GameWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);
		setTitle("Game");
		setResizable(false);
		/*
		WeaponsGen rwg = new WeaponsGen();
		//Inventory inv = createInv(jack, 10);
		jack.level = 10;
		Weapon testSword = rwg.genWeapon(jack, "sword");
		//inv.addItem(testSword);
		 */
		new Thread() {
			@Override
			public void run() {
				ts = new TitleScreen(contentPane);
				pack();
			}
		}.start();
		
		new Thread() {
			@Override
			public void run() {
				//TitleText test = new TitleText(contentPane, "abcde", 50, 50, 25, "red");
				//test.addTo(contentPane);
				contentPane.add(new JLabel());
			}
		}.start();
		
		//Character jack = new Character("Jack");
		//createInstance(jack);
		
	}
	
	public Inventory createInv(Character c, int capacity) {
		Inventory inv;
		try {
			inv = new Inventory(c, capacity);
			contentPane.add(inv.dialog, BorderLayout.SOUTH);
			for (int i = 0; i < capacity; i++) {
				inv.slots[i].setToolTipText("Empty Slot");
			}
			return inv;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public void createInstance(Character c) {
		instance = new GameInstance(createInv(c, 10));
		contentPane.add(instance.dialog);
	}
	
	public void close() {
		frame.dispose();
		
	}
}