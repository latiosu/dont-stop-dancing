package com.main.game.structs;

import com.badlogic.gdx.graphics.Texture;
import com.main.game.objects.Block;
import com.main.game.objects.Spawner;

import java.util.ArrayList;
import java.util.List;

public class Level {

	private List<Block> blocks;
	private List<Spawner> spawners;
	private Texture texture;

	public Level(Texture texture) {
		this.blocks = new ArrayList<>();
		this.spawners = new ArrayList<>();
		this.texture = texture;
	}

	public List<Block> getBlocks() {
		return blocks;
	}

	public List<Spawner> getSpawners() {
		return spawners;
	}

	public Texture getTexture() {
		return texture;
	}
}
