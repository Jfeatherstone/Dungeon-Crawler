package com.jfeather.Main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.jfeather.Exceptions.InventoryCapacityException;
import com.jfeather.Game.GameInstance;
import com.jfeather.Player.SaveSelect;

public class TitleScreen implements MouseListener {

	public JLabel title, titleGif, startText, startTextHighlighted, exitText, exitTextHighlighted;
	public JPanel dialog;
	public JLabel[] pointer; 
	public boolean runGif = false;
	private GameWindow gw = new GameWindow();
	
	public TitleScreen(JPanel dialogPanel) {
		dialog = new JPanel();
		dialog = dialogPanel;
		
		// Set up the pointer
		pointer = new JLabel[3];
		for (int i = 0; i < pointer.length; i++) {
			pointer[i] = new JLabel(new ImageIcon("Sprites/TitleScreen/TitleScreenPointer.png"));
			dialog.add(pointer[i]);
			pointer[i].setVisible(false);
		}
		pointer[0].setBounds(400, 132, 60, 25);
		//pointer[1].setBounds(r);
		pointer[2].setBounds(415, 247, 60, 25);
		
		// Add the background
		// This will eventually be a looped gif, but for now a png works
		title = new JLabel(gif("Sprites/TitleScreen/TitleScreenBackground3.png"));
		dialog.add(title);
		title.addMouseListener(this);
		
		// Define the text buttons
		// If these aren't defined, the mouse listener will run into a NullPointerException because it will try and disable them or something, idk
		startText = new JLabel();
		startTextHighlighted = new JLabel();
		
		exitText = new JLabel();
		exitTextHighlighted = new JLabel();
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (!runGif) {
			// If the animation hasn't started yet, run that first
			runTitle();
			runGif = true;
		} else {
			int x = (int) (e.getLocationOnScreen().getX() - dialog.getLocationOnScreen().getX());
			int y = (int) (e.getLocationOnScreen().getY() - dialog.getLocationOnScreen().getY());
			//If the button is over the start button
			if ((x > startText.getX() && x < startText.getX() + startText.getWidth()) && (y > startText.getY() && y < startText.getY() + startText.getHeight())) {
				//SaveSelect ss = new SaveSelect();
				//JOptionPane.showMessageDialog(null, ss.dialog);
				//GameInstance instance = new GameInstance(GameWindow.frame);

			}
			if ((x > exitText.getX() && x < exitText.getX() + exitText.getWidth()) && (y > exitText.getY() && y < exitText.getY() + exitText.getHeight())) {
				// Close the window
				try {
					gw.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = (int) (e.getLocationOnScreen().getX() - dialog.getLocationOnScreen().getX());
		int y = (int) (e.getLocationOnScreen().getY() - dialog.getLocationOnScreen().getY());
		//If the button is over the start button
		if ((x > startText.getX() && x < startText.getX() + startText.getWidth()) && (y > startText.getY() && y < startText.getY() + startText.getHeight())) {
			startText.setVisible(false);
			startTextHighlighted.setVisible(true);
			pointer[0].setVisible(true);
		}
		if ((x > exitText.getX() && x < exitText.getX() + exitText.getWidth()) && (y > exitText.getY() && y < exitText.getY() + exitText.getHeight())) {
			exitText.setVisible(false);
			exitTextHighlighted.setVisible(true);
			pointer[2].setVisible(true);
		}

	}
	@Override
	public void mouseExited(MouseEvent e) {
		// Set all of the highlighted text to not be visible
		startTextHighlighted.setVisible(false);
		exitTextHighlighted.setVisible(false);
		pointer[0].setVisible(false);
		// Enable all of the un-highlighted text labels
		startText.setVisible(true);
		exitText.setVisible(true);
		pointer[2].setVisible(false);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void runTitle() {
		try {
			// First, remove the mouse listener from the background, so it doesn't conflict later
			title.removeMouseListener(this);
			
			// Add the title buttons
			titleButtons();
			
			// Add the animated picture
			titleGif = new JLabel(gif("Sprites/TitleScreen/TitleScreenBackground3.png"));
			dialog.add(titleGif);
			
			// Remove the static picture
			dialog.remove(title);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void titleButtons() {
		// Add the buttons on the title screen using a fancy animation
		// Since the animations don't loop, they can be reused as the non-highlighted buttons
		// This includes adding the mouse listeners to these components, so that the button detection works later

		// Start Button
		startTextHighlighted = new JLabel(gif("Sprites/TitleScreen/TitleScreenStartTextHighlighted.png"));
		dialog.add(startTextHighlighted);
		startTextHighlighted.setBounds(465, 120, 110, 45);
		startTextHighlighted.addMouseListener(this);
		// Set the highlighted button to not be visible
		startTextHighlighted.setVisible(false);
		startText = new JLabel(gif("Sprites/TitleScreen/TitleScreenStartTextFadeIn.gif"));
		dialog.add(startText);
		startText.setBounds(465, 120, 110, 45);
		startText.addMouseListener(this);
					
		// Exit Button
		exitTextHighlighted = new JLabel(gif("Sprites/TitleScreen/TitleScreenExitTextHighlighted.png"));
		dialog.add(exitTextHighlighted);
		exitTextHighlighted.setBounds(480, 235, 110, 45);
		exitTextHighlighted.addMouseListener(this);
		// Set the highlighted button to not be visible
		exitTextHighlighted.setVisible(false);
		exitText = new JLabel(gif("Sprites/TitleScreen/TitleScreenExitTextFadeIn.gif"));
		dialog.add(exitText);
		exitText.setBounds(480, 235, 110, 45);
		exitText.addMouseListener(this);

	}
	
	public ImageIcon gif(String path) {
		// This also works for pngs, but it is the best way (i think) to use gifs, pngs can be done other ways but this works for both
		URL url = null;
		try {
			File link = new File(path);
			url = link.toURI().toURL();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return (new ImageIcon(url));

	}
	
}


