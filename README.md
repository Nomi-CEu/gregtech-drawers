About
==================
Storage Drawers KAPPA Addon is an addon for the [Storage Drawers](https://www.curseforge.com/minecraft/mc-mods/storage-drawers) mod by Texelsaur.  
It's made for [skate's CraftBlock](https://www.curseforge.com/minecraft/modpacks/craftblock) modpack, adding drawers for all wood types.

This mod is a fork of XaeronTheProtogen's [Storage Drawers Unlimited](https://www.curseforge.com/minecraft/mc-mods/storage-drawers-unlimited) and uses its drawer creation code, as well as the drawer textures and models for Immersive Engineering, Botania and Quark. All other integrations have been removed while some new ones where added.

Added Drawers
==================
* Livingwood, Dreamwood & Shimmerwood Drawers (Botania)
* Treated Wood Drawer (Immersive Engineering)
* Stained Wood Drawers for all 16 colors (Quark)
* Infused Wood Drawer (Astral Sorcery)
* Myst Planks Drawer (Hearthwell)
* Sealed Planks Drawer (Embers)

Contributing
==================
To create a new drawer type create drawer & trim base textures at the following locations:
```
src\main\resources\assets\storagedrawerskappa\textures\blocks\MOD_ID\base\base_MATERIAL_NAME.png
src\main\resources\assets\storagedrawerskappa\textures\blocks\MOD_ID\base\tile_MATERIAL_NAME.png
```
Make sure to replace MOD_ID & MATERIAL_NAME with your actual values.  

Then head over to the `tools` directory and run the following script (with your arguments):
```bat
makeresources.bat MOD_ID MATERIAL_NAME
```
It will generate all needed model & texture files.  
The only manual thing left to do is adding the entry to the `en_us.lang` file.

----------------------------

To build the mod run `gradlew build` from repository root. You'll need good old java `8`.
