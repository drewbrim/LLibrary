package net.ilexiconn.llibrary.server.structure;

import com.google.common.collect.Lists;

import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author jglrxavpok
 * @since 1.1.0
 */
public abstract class StructureGenerator {
    public static final EnumFacing[] CLOCKWISE_FACINGS = { EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST };

    public static EnumFacing getNextClockwise(EnumFacing facing) {
        int index = ArrayUtils.indexOf(CLOCKWISE_FACINGS, facing);
        if (index < 0) {
            throw new IllegalArgumentException();
        }
        return CLOCKWISE_FACINGS[(index + 1) % CLOCKWISE_FACINGS.length];
    }

    public abstract void generate(World world, int x, int y, int z, Random random);

    public abstract StructureGenerator rotate(EnumFacing front, EnumFacing top);

    public abstract StructureGenerator rotateTowards(EnumFacing facing);
}
