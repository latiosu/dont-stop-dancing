package com.main.game.structs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.main.game.objects.Block;
import com.main.game.objects.Spawner;

import java.util.ArrayList;
import java.util.List;

public class Level {

	private List<Block> blocks;
	private List<Spawner> spawners;
	private Color bgColour;

	public Level(Color bgColour) {
		this.blocks = new ArrayList<>();
		this.spawners = new ArrayList<>();
		this.bgColour = bgColour;
	}

	public List<Block> getBlocks() {
		return blocks;
	}

	public List<Spawner> getSpawners() {
		return spawners;
	}

	public Color getBgColour() {
		return bgColour;
	}

	public void setBgColour(Color bgColour) {
		this.bgColour = bgColour;
	}
}
