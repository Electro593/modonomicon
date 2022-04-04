/*
 * LGPL-3-0
 *
 * Copyright (C) 2022 klikli-dev
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

package com.klikli_dev.modonomicon.registry;

import com.klikli_dev.modonomicon.Modonomicon;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundRegistry {
    //TODO: make mod loader agnostic
    public static DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Modonomicon.MODID);

    public static final RegistryObject<SoundEvent> TURN_PAGE = SOUNDS.register("turn_page", () -> loadSoundEvent("turn_page"));

    /**
     * Creates the sound event object for the given sound event name, as specified in sounds.json Automatically appends
     * MODID.
     *
     * @param name the sound event name without domain.
     * @return the sound event.
     */
    private static SoundEvent loadSoundEvent(String name) {
        ResourceLocation location = new ResourceLocation(Modonomicon.MODID, name);
        return new SoundEvent(location);
    }
}