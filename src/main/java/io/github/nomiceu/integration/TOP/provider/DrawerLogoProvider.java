package io.github.nomiceu.integration.TOP.provider;

import com.jaquadro.minecraft.storagedrawers.block.BlockDrawers;
import com.jaquadro.minecraft.storagedrawers.block.tile.TileEntityDrawers;
import io.github.nomiceu.GTDConfig;
import mcjty.theoneprobe.Tools;
import mcjty.theoneprobe.api.*;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import mcjty.theoneprobe.config.Config;

import java.util.Objects;

import static com.jaquadro.minecraft.storagedrawers.StorageDrawers.config;
import static mcjty.theoneprobe.api.TextStyleClass.MODNAME;

public class DrawerLogoProvider implements IBlockDisplayOverride {
    @Override
    public boolean overrideStandardInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer entityPlayer, World world, IBlockState blockState, IProbeHitData probeHitData) {
        Block block = blockState.getBlock();
        if (!(block instanceof BlockDrawers) || !GTDConfig.fixTOPTaped)
            return false;

        String modid = Tools.getModName(block);

        TileEntityDrawers tile = (TileEntityDrawers) Objects.requireNonNull(world.getTileEntity(probeHitData.getPos()));

        if (!config.cache.keepContentsOnBreak || tile.isSealed())
            return false;

        ItemStack pickBlock = probeHitData.getPickBlock();

        if (pickBlock == null || pickBlock.isEmpty())
            return false;

        if (pickBlock.hasTagCompound() && pickBlock.getTagCompound() != null) {
            if (pickBlock.getTagCompound().hasKey("tile")){
                NBTTagCompound compound = pickBlock.getTagCompound();
                compound.removeTag("tile");
                pickBlock.setTagCompound(compound);
            }
        }

        if (Tools.show(mode, Config.getRealConfig().getShowModName()))
            probeInfo.horizontal()
                    .item(pickBlock)
                    .vertical()
                    .itemLabel(pickBlock)
                    .text(MODNAME + modid);
        else
            probeInfo.horizontal(probeInfo.defaultLayoutStyle()
                    .alignment(ElementAlignment.ALIGN_CENTER))
                    .item(pickBlock);

        return true;
    }
}
