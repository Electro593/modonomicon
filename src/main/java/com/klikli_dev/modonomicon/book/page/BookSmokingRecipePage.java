/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.modonomicon.book.page;

import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.api.ModonimiconConstants.Data.Page;
import com.klikli_dev.modonomicon.book.BookTextHolder;
import com.klikli_dev.modonomicon.book.error.BookErrorManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.SmokingRecipe;

public class BookSmokingRecipePage extends BookProcessingRecipePage<SmokingRecipe> {
    public BookSmokingRecipePage(BookTextHolder title1, ResourceLocation recipeId1, BookTextHolder title2, ResourceLocation recipeId2, BookTextHolder text, String anchor) {
        super(RecipeType.SMOKING, title1, recipeId1, title2, recipeId2, text, anchor);
    }

    @Override
    public ResourceLocation getType() {
        return Page.SMOKING_RECIPE;
    }

    public static BookSmokingRecipePage fromJson(JsonObject json) {
        var common = BookRecipePage.commonFromJson(json);
        var anchor = GsonHelper.getAsString(json, "anchor", "");
        return new BookSmokingRecipePage(common.title1(), common.recipeId1(), common.title2(), common.recipeId2(), common.text(), anchor);
    }

    public static BookSmokingRecipePage fromNetwork(FriendlyByteBuf buffer) {
        var common = BookRecipePage.commonFromNetwork(buffer);
        var anchor = buffer.readUtf();
        return new BookSmokingRecipePage(common.title1(), common.recipeId1(), common.title2(), common.recipeId2(), common.text(), anchor);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer) {
        super.toNetwork(buffer);
        buffer.writeUtf(this.anchor);
    }
}