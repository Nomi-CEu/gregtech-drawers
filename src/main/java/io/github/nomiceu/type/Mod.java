package io.github.nomiceu.type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.minecraftforge.fml.common.ModContainer;
import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.fml.common.Loader;


public class Mod implements StreamableIterable<DrawerMaterial> {
	static final ArrayList<Mod> internal_modlist = new ArrayList<>();
		
	public final String modid;
	private String modname;
	private final Map<String, DrawerMaterial> materials;
	
	public Mod(String modid, DrawerMaterial.Builder... materialsIn) {
		this(modid, new String[0], new String[0], materialsIn);
	}
	
	public Mod(String[] modids, DrawerMaterial.Builder... materialsIn) {
		this(modids[0], withoutFirstElement(modids), new String[0], materialsIn);
	}
	
		private static String[] withoutFirstElement(String[] array) {
			String[] result = new String[array.length-1];
			System.arraycopy(array, 1, result, 0, result.length);
			return result;
		}
	
	public Mod(String modid, String[] other_modids, String[] disabled_modids, DrawerMaterial.Builder... materialsIn) {
		this.modid = modid;
		this.materials = Collections.unmodifiableMap(Arrays.stream(materialsIn)
								.map(builder -> builder.build(this))
								.filter(Optional::isPresent)
								.map(optional -> {
									DrawerMaterial mat = optional.get();
									return Pair.of(mat.getName(), mat);
								})
								.collect(Collectors.toMap(Pair::getKey, Pair::getValue)));
		internal_modlist.add(this);
	}
	
	public DrawerMaterial getMaterial(String name) {
		return materials.get(name);
	}
	
	@Override
	public Stream<DrawerMaterial> stream() {
		return materials.values().stream();
	}
	
	@Override
	public Stream<DrawerMaterial> parallelStream() {
		return materials.values().parallelStream();
	}
	
	public String getModName() {
		return modname == null? modname = Loader.instance().getModList().stream().filter(container -> container.getModId().equals(modid)).map(ModContainer::getName).findFirst().orElse(modid + " [not loaded]")
								: modname;
	}
	
	public DrawerMaterial getDefaultMaterial() {
		return materials.isEmpty()? null : materials.values().iterator().next();
	}
	
	public boolean isEnabled() {
		return true;
	}
	
	public Iterator<DrawerMaterial> iterator() {
		return materials.values().iterator();
	}
	
	public void init() {
		for(DrawerMaterial material : materials.values()) {
			material.init();
		}
	}
	
	public String toString() {
		return modid;
	}
	
	/*static class ExtrasOverride extends Mod {

		public ExtrasOverride(String modid, Builder... materialsIn) {
			super(modid, materialsIn);
		}

		public ExtrasOverride(String[] modids, Builder... materialsIn) {
			super(modids, materialsIn);
		}
		
		@Override
		public boolean isEnabled() {
			return super.isEnabled() && !Loader.isModLoaded("storagedrawersextras");
		}
		
	}*/
	
	
}
