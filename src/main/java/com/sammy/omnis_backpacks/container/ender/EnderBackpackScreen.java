package com.sammy.omnis_backpacks.container.ender;

import com.sammy.omnis_backpacks.container.AbstractBackpackContainer;
import com.sammy.omnis_backpacks.container.AbstractBackpackScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class EnderBackpackScreen extends AbstractBackpackScreen
{
    public EnderBackpackScreen(AbstractBackpackContainer container, PlayerInventory player, ITextComponent title)
    {
        super(container, player, title, "textures/gui/backpack_gold");
    }

}
