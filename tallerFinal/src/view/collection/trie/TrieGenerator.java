package view.collection.trie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.TrieController;

public class TrieGenerator {
	private int count = 0;
	static List<String> list;
	static {
		list = new ArrayList<String>();
		list.add("auto");
		list.add("don");
		list.add("dragon");
		list.add("autora");
		list.add("donar");
		list.add("ese");
		list.add("higo");
		list.add("esencia");
		list.add("higuera");
		list.add("himno");
		list.add("cara");
		list.add("fluido");
		list.add("caracol");
		list.add("flota");
		list.add("carta");
	}

	private TrieController controller;

	public TrieGenerator(TrieController controller) {
		super();
		this.controller = controller;
		this.count = 0;
	}

	public void insert() {

		reset();

		if (!this.controller.running()) {
			controller.setRunning(true);
			controller.insertWord(list.get(count));
			count++;
		}

	}

	private void reset() {
		if (count >= list.size()) {
			Collections.shuffle(list);
			count = 0;
		}
	}
}
