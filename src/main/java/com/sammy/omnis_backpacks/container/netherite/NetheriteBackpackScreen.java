package com.sammy.omnis_backpacks.container.netherite;

import com.sammy.omnis_backpacks.container.AbstractBackpackContainer;
import com.sammy.omnis_backpacks.container.AbstractBackpackScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class NetheriteBackpackScreen extends AbstractBackpackScreen
{
    public NetheriteBackpackScreen(AbstractBackpackContainer container, PlayerInventory player, ITextComponent title)
    {
        super(container, player, title, "textures/gui/backpack_netherite");
        ySize = 222;
    }
}
