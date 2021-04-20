package com.sammy.omnis_backpacks.data;

import com.sammy.omnis_backpacks.init.Registries;
import net.minecraft.advancements.criterion.*;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

import static net.minecraft.data.ShapedRecipeBuilder.shapedRecipe;
import static net.minecraft.data.ShapelessRecipeBuilder.shapelessRecipe;

public class ModRecipeProvider extends RecipeProvider
{
    public ModRecipeProvider(DataGenerator generatorIn)
    {
        super(generatorIn);
    }
    
    @Override
    public String getName()
    {
        return "Recipe Provider";
    }
    
    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer)
    {
        ShapedRecipeBuilder.shapedRecipe(Registries.BACKPACK.get()).key('#', Tags.Items.LEATHER).key('Y', Tags.Items.CHESTS_WOODEN).key('X', Items.GOLD_INGOT).patternLine("#X#").patternLine("#Y#").patternLine("###").addCriterion("has_gold", hasItem(Items.GOLD_INGOT)).build(consumer);
        smithingReinforce(consumer, Registries.BACKPACK.get(), Registries.NETHERITE_BACKPACK.get());
    }
    private static void smithingReinforce(Consumer<IFinishedRecipe> recipeConsumer, Item itemToReinforce, Item output)
    {
        SmithingRecipeBuilder.smithingRecipe(Ingredient.fromItems(itemToReinforce), Ingredient.fromItems(Items.NETHERITE_INGOT), output).addCriterion("has_netherite_ingot", hasItem(Items.NETHERITE_INGOT)).build(recipeConsumer, Registry.ITEM.getKey(output.asItem()).getPath() + "_smithing");
    }

    protected static InventoryChangeTrigger.Instance hasItem(IItemProvider item)
    {
        return hasItem(ItemPredicate.Builder.create().item(item).build());
    }
    protected static InventoryChangeTrigger.Instance hasItem(ItemPredicate... predicate)
    {
        return new InventoryChangeTrigger.Instance(EntityPredicate.AndPredicate.ANY_AND, MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, predicate);
    }
}