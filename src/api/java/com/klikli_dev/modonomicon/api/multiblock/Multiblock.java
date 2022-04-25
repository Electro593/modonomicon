/*
 * LGPL-3.0
 *
 * Copyright (C) 2022 Authors of Patchouli
 *
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package com.klikli_dev.modonomicon.api.multiblock;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * An instance of a multiblock.
 * <br>
 * <br>
 * WARNING: This interface is provided only for usage with the API. For creating
 * a Multiblock instance use the methods provided in the API main class. Please
 * do not create your own implementation of this, as it'll not be compatible with
 * all the features in the mod.
 */
public interface Multiblock {

	// ================================================================================================
	// Builder methods
	// ================================================================================================

	/**
	 * Offsets the position of the multiblock by the amount specified.
	 * Works for both placement, validation, and rendering.
	 */
	Multiblock offset(int x, int y, int z);

	/**
	 * Offsets the view of the multiblock by the amount specified.
	 * Matters only for where the multiblock renders.
	 */
	Multiblock offsetView(int x, int y, int z);

	/**
	 * Sets the multiblock's symmetrical value. Symmetrical multiblocks
	 * check only in one rotation, not all 4. If your multiblock is symmetrical
	 * around the center axis, set this to true to prevent needless cycles.
	 */
	Multiblock setSymmetrical(boolean symmetrical);

	/**
	 * Sets the multiblock's ID. Not something you need to
	 * call yourself as the register method in the main API class does it for you.
	 */
	Multiblock setId(ResourceLocation res);

	// ================================================================================================
	// Getters
	// ================================================================================================

	/**
	 * Gets if this multiblock is symmetrical.
	 * 
	 * @see Multiblock#setSymmetrical
	 */
	boolean isSymmetrical();

	ResourceLocation getId();

	/**
	 * The multiblock type id for serialization.
	 */
	ResourceLocation getType();


	// ================================================================================================
	// Actual functionality
	// Note: DO NOT USE THESE METHODS IF YOUR MOD DOESN'T HAVE
	// A HARD DEPENDENCY ON PATCHOULI
	//
	// The stub API will return an empty multiblock that doesn't
	// do any of these things!
	// ================================================================================================

	/**
	 * Places the multiblock at the given position with the given rotation.
	 */
	void place(Level world, BlockPos pos, Rotation rotation);

	/**
	 * If this multiblock were anchored at world position {@code anchor} with rotation {@code rotation}, then
	 * return a pair whose first element is the final center position (after rotation and {@link #offset}),
	 * and whose second element describes each position of the multiblock.
	 * This is intended to be highly general, most of the other methods below are implemented in terms of this one.
	 * See the main Patchouli code to see what can be done with this.
	 */
	Pair<BlockPos, Collection<SimulateResult>> simulate(Level world, BlockPos anchor, Rotation rotation, boolean forView);

	/**
	 * Validates if the multiblock exists at the given position. Will check all 4
	 * rotations if the multiblock is not symmetrical.
	 * 
	 * @return The rotation that worked, null if no match
	 */
	@Nullable
	Rotation validate(Level world, BlockPos pos);

	/**
	 * Validates the multiblock for a specific rotation
	 */
	boolean validate(Level world, BlockPos pos, Rotation rotation);

	/**
	 * Fine-grained check for whether any one given block of the multiblock exists at the given position
	 * with the given rotation.
	 * 
	 * @param start The anchor position. The multiblock's {@link #offset} is not applied to this.
	 */
	boolean test(Level world, BlockPos start, int x, int y, int z, Rotation rotation);

	/**
	 * Gets the size of this multiblock
	 *
	 * @return The size of the multiblock
	 */
	Vec3i getSize();

	/**
	 * Serializes multiblock to the given buffer.
	 */
	void toNetwork(FriendlyByteBuf buffer);

	interface SimulateResult {
		/**
		 * Final world position this block will be matched or placed at
		 */
		BlockPos getWorldPosition();

		/**
		 * The matcher used at this position
		 */
		StateMatcher getStateMatcher();

		/**
		 * The character used to express the state matcher, if this is a dense multiblock.
		 */
		@Nullable
		Character getCharacter();

		/**
		 * @return Whether the multiblock is fulfilled at this position
		 */
		boolean test(Level world, Rotation rotation);
	}

}
