package de.endsmasher.voidmaker.backend;

import lombok.Data;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Data
public class VoidGenerator extends ChunkGenerator {
    private final Biome worldBiome;

    /**
     * making all chunks without blocks
     */

    @Override
    public ChunkData generateChunkData(World world, Random random, int ChunkX, int ChunkZ, BiomeGrid biome) {
        ChunkData chunkData = super.createChunkData(world);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++)
                biome.setBiome(x, z, worldBiome);
        }
        return chunkData;
    }

    @Override
    public boolean canSpawn(World world, int x, int z) {
        return true;
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return Collections.emptyList();
    }

    /**
     * set the default spawn location
     */

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world, 0, 100, 0);
    }
}
