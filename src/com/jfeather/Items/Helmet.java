package com.jfeather.Items;

import javax.swing.ImageIcon;

// TODO: Stuff might be wonky here, check it out later

public class Helmet {

	public int defense, rarity, agility, luck, intelligence, strength;
	public ImageIcon sprite;
	public String name, toolTip;
	
	public Helmet(String itemName, String itemDescr, int itemRarity, int itemArmorRating, int itemStrength, int itemIntelligence, int itemAgility, int itemLuck, ImageIcon itemSprite) {
		defense = itemArmorRating;
		agility = itemAgility;
		luck = itemLuck;
		intelligence = itemIntelligence;
		strength = itemStrength;
		name = itemName;
		sprite = itemSprite;
		String descr = DescrWrap.descrWrap(itemDescr, name);
		String rarityColor = "";
		switch (itemRarity) {
			case 0: rarityColor = "black"; break;
			case 1: rarityColor = "white"; break;
			case 2: rarityColor = "green"; break;
			case 3: rarityColor = "blue"; break;
			case 4: rarityColor = "purple"; break;
			case 5: rarityColor = "yellow"; break;
			case 6: rarityColor = "orange"; break;
			default: System.out.println("Invalid item rarity! ("+name+")"); rarityColor = "white"; break;
		}
		String stats = "";
		if (strength != 0)
			stats = stats + "Strength +<font color='red'>"+strength+"</font><br>";
		if (intelligence != 0)
			stats = stats + "Intelligence +<font color='red'>"+intelligence+"</font><br>";
		if (agility != 0)
			stats = stats + "Agility +<font color='red'>"+agility+"</font><br>";
		if (luck != 0)
			stats = stats + "Luck +<font color='red'>"+luck+"</font><br>";


		toolTip = "<html> <b><font color='"+rarityColor+"'>"+name+"</font><br>Armor: <font color='red'>"+defense+"</font><br>"+stats+"<i>"+descr;
	}

	
}
