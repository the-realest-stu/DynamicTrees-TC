package dynamictreestc.proxy;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.client.ModelHelper;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicSapling;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;

import dynamictreestc.DynamicTreesTC;
import dynamictreestc.ModContent;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preInit() {
		super.preInit();
	}
	
	@Override
	public void init() {
		super.init();
		registerColorHandlers();
	}
	
	@Override public void postInit() {
		super.postInit();
	}

	public void registerColorHandlers() {	
		final int magenta = 0x00FF00FF; // for errors.. because magenta sucks.
		
		ModelHelper.regColorHandler(ModContent.silverwoodLeaves, new IBlockColor() {
			@Override
			public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
				return 0xFFFFFF;
			}
		});
		
		for (BlockDynamicLeaves leaves: TreeHelper.getLeavesMapForModId(DynamicTreesTC.MODID).values()) {
			ModelHelper.regColorHandler(leaves, new IBlockColor() {
				@Override
				public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
					boolean inWorld = worldIn != null && pos != null;
					
					Block block = state.getBlock();
					
					if (TreeHelper.isLeaves(block)) {
						return ((BlockDynamicLeaves) block).getProperties(state).foliageColorMultiplier(state, worldIn, pos);
					}
					return magenta;
				}
			});
		}

		for (TreeFamily tree: ModContent.trees) {
			BlockDynamicSapling sapling = (BlockDynamicSapling) tree.getCommonSpecies().getDynamicSapling().getBlock();
			ModelHelper.regDynamicSaplingColorHandler(sapling);
		}
	}
	
}