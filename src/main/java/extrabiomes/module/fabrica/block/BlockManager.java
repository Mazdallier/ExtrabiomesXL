/**
 * This work is licensed under the Creative Commons
 * Attribution-ShareAlike 3.0 Unported License. To view a copy of this
 * license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package extrabiomes.module.fabrica.block;

import extrabiomes.helpers.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;

import com.google.common.base.Optional;

import extrabiomes.Extrabiomes;
import extrabiomes.api.Stuff;
import extrabiomes.events.BlockActiveEvent.AcaciaStairsActiveEvent;
import extrabiomes.events.BlockActiveEvent.AutumnStairsActiveEvent;
import extrabiomes.events.BlockActiveEvent.BaldCypressStairsActiveEvent;
import extrabiomes.events.BlockActiveEvent.CypressStairsActiveEvent;
import extrabiomes.events.BlockActiveEvent.FirStairsActiveEvent;
import extrabiomes.events.BlockActiveEvent.JapaneseMapleStairsActiveEvent;
import extrabiomes.events.BlockActiveEvent.NewWoodSlabActiveEvent;
import extrabiomes.events.BlockActiveEvent.PlankActiveEvent;
import extrabiomes.events.BlockActiveEvent.RainbowEucalyptusStairsActiveEvent;
import extrabiomes.events.BlockActiveEvent.RedCobbleStairsActiveEvent;
import extrabiomes.events.BlockActiveEvent.RedRockBrickStairsActiveEvent;
import extrabiomes.events.BlockActiveEvent.RedRockSlabActiveEvent;
import extrabiomes.events.BlockActiveEvent.RedwoodStairsActiveEvent;
import extrabiomes.events.BlockActiveEvent.SakuraBlossomStairsActiveEvent;
import extrabiomes.events.BlockActiveEvent.WallActiveEvent;
import extrabiomes.events.BlockActiveEvent.WoodSlabActiveEvent;
import extrabiomes.lib.BlockSettings;
import extrabiomes.lib.Element;
import extrabiomes.module.amica.buildcraft.FacadeHelper;
import extrabiomes.proxy.CommonProxy;
import net.minecraft.init.Blocks;

public enum BlockManager
{
    PLANKS(Stuff.planks, true)
    {
        @Override
        protected void create()
        {
            Stuff.planks = Optional.of(new BlockCustomWood());
        }
        
        @Override
        protected BlockSettings getSettings()
        {
            return BlockSettings.PLANKS;
        }
        
        @Override
        protected void prepare()
        {
            final CommonProxy proxy = Extrabiomes.proxy;
            final Block thisBlock = Stuff.planks.get();
            
            thisBlock.setBlockName("extrabiomes.planks");
            proxy.setBlockHarvestLevel(thisBlock, "axe", 0);
            proxy.registerBlock(thisBlock, extrabiomes.utility.MultiItemBlock.class, "planks");
            for (final BlockCustomWood.BlockType type : BlockCustomWood.BlockType.values())
            {
                FacadeHelper.addBuildcraftFacade(thisBlock, type.metadata());
            }
            
            proxy.registerOreInAllSubblocks("plankWood", thisBlock);
            
            Extrabiomes.postInitEvent(new PlankActiveEvent(thisBlock));
        }
    },
    NEWWOODSLAB(Stuff.newslabWood, true)
    {
        @Override
        protected void create()
        {
            Stuff.newslabWood = Optional.of(new BlockNewWoodSlab(getSettings(), false));
        }
        
        @Override
        protected BlockSettings getSettings()
        {
            return BlockSettings.NEWWOODSLAB;
        }
        
        @Override
        protected void prepare()
        {
            final CommonProxy proxy = Extrabiomes.proxy;
            final Block thisBlock = Stuff.newslabWood.get();
            
            thisBlock.setBlockName("extrabiomes.woodslab");
            proxy.setBlockHarvestLevel(thisBlock, "axe", 0);
            
            proxy.registerFuelHandler(new FuelHandlerWoodSlabs(thisBlock));
        }
    },
    NEWDOUBLEWOODSLAB(Stuff.newslabWoodDouble, true)
    {
        @Override
        protected void create()
        {
            Stuff.newslabWoodDouble = Optional.of(new BlockNewWoodSlab(getSettings(), true));
        }
        
        @Override
        protected BlockSettings getSettings()
        {
            return BlockSettings.NEWDOUBLEWOODSLAB;
        }
        
        @Override
        protected void prepare()
        {
            final CommonProxy proxy = Extrabiomes.proxy;
            final Block thisBlock = Stuff.newslabWoodDouble.get();
            
            thisBlock.setBlockName("extrabiomes.woodslab");
            proxy.setBlockHarvestLevel(thisBlock, "axe", 0);
            ItemNewWoodSlab.setSlabs((BlockSlab) Stuff.newslabWood.get(), (BlockSlab) Stuff.newslabWoodDouble.get());
            proxy.registerBlock(Stuff.newslabWood.get(), extrabiomes.module.fabrica.block.ItemNewWoodSlab.class, "woodslab2");
            proxy.registerBlock(thisBlock, extrabiomes.module.fabrica.block.ItemNewWoodSlab.class, "double_woodslab2");
            
            proxy.registerOreInAllSubblocks("slabWood", Stuff.newslabWood.get());
            
            // We can not create the slab reciepe till after we have created both the single and double slabs
            Extrabiomes.postInitEvent(new NewWoodSlabActiveEvent(thisBlock));
        }
    },
    WOODSLAB(Stuff.slabWood, true)
    {
        @Override
        protected void create()
        {
            Stuff.slabWood = Optional.of(new BlockCustomWoodSlab(getSettings(), false));
        }
        
        @Override
        protected BlockSettings getSettings()
        {
            return BlockSettings.WOODSLAB;
        }
        
        @Override
        protected void prepare()
        {
            final CommonProxy proxy = Extrabiomes.proxy;
            final Block thisBlock = Stuff.slabWood.get();
            
            thisBlock.setBlockName("extrabiomes.woodslab");
            proxy.setBlockHarvestLevel(thisBlock, "axe", 0);
            
            proxy.registerFuelHandler(new FuelHandlerWoodSlabs(thisBlock));
        }
    },
    DOUBLEWOODSLAB(Stuff.slabWoodDouble, true)
    {
        @Override
        protected void create()
        {
            Stuff.slabWoodDouble = Optional.of(new BlockCustomWoodSlab(getSettings(), true));
        }
        
        @Override
        protected BlockSettings getSettings()
        {
            return BlockSettings.DOUBLEWOODSLAB;
        }
        
        @Override
        protected void prepare()
        {
            final CommonProxy proxy = Extrabiomes.proxy;
            final Block thisBlock = Stuff.slabWoodDouble.get();
            
            thisBlock.setBlockName("extrabiomes.woodslab");
            proxy.setBlockHarvestLevel(thisBlock, "axe", 0);
            ItemWoodSlab.setSlabs((BlockSlab) Stuff.slabWood.get(), (BlockSlab) Stuff.slabWoodDouble.get());
            proxy.registerBlock(Stuff.slabWood.get(), extrabiomes.module.fabrica.block.ItemWoodSlab.class, "woodslab");
            proxy.registerBlock(thisBlock, extrabiomes.module.fabrica.block.ItemWoodSlab.class, "double_woodslab");
            
            LogHelper.info("Name: %s", Block.blockRegistry.getNameForObject(thisBlock));
            
            proxy.registerOreInAllSubblocks("slabWood", Stuff.slabWood.get());
            
            // We can not create the slab reciepe till after we have created both the single and double slabs
            Extrabiomes.postInitEvent(new WoodSlabActiveEvent(thisBlock));
        }
    },
    REDWOODSTAIRS(Stuff.stairsRedwood, true)
    {
        @Override
        protected void create()
        {
            Stuff.stairsRedwood = Optional.of(new BlockWoodStairs(Stuff.planks.get(), BlockCustomWood.BlockType.REDWOOD.metadata()));
        }
        
        @Override
        protected BlockSettings getSettings()
        {
            return BlockSettings.REDWOODSTAIRS;
        }
        
        @Override
        protected void prepare()
        {
            final CommonProxy proxy = Extrabiomes.proxy;
            final Block thisBlock = Stuff.stairsRedwood.get();
            
            thisBlock.setBlockName("extrabiomes.stairs.redwood");
            proxy.setBlockHarvestLevel(thisBlock, "axe", 0);
            proxy.registerBlock(thisBlock, "stairs.redwood");
            
            proxy.registerOre("stairWood", thisBlock);
            Extrabiomes.postInitEvent(new RedwoodStairsActiveEvent(thisBlock));
        }
    },
    FIRSTAIRS(Stuff.stairsFir, true)
    {
        @Override
        protected void create()
        {
            Stuff.stairsFir = Optional.of(new BlockWoodStairs(Stuff.planks.get(), BlockCustomWood.BlockType.FIR.metadata()));
        }
        
        @Override
        protected BlockSettings getSettings()
        {
            return BlockSettings.FIRSTAIRS;
        }
        
        @Override
        protected void prepare()
        {
            final CommonProxy proxy = Extrabiomes.proxy;
            final Block thisBlock = Stuff.stairsFir.get();
            
            thisBlock.setBlockName("extrabiomes.stairs.fir");
            proxy.setBlockHarvestLevel(thisBlock, "axe", 0);
            proxy.registerBlock(thisBlock, "stairs.fir");
            
            proxy.registerOre("stairWood", thisBlock);
            Extrabiomes.postInitEvent(new FirStairsActiveEvent(thisBlock));
        }
    },
    ACACIASTAIRS(Stuff.stairsAcacia, true)
    {
        @Override
        protected void create()
        {
            Stuff.stairsAcacia = Optional.of(new BlockWoodStairs(Stuff.planks.get(), BlockCustomWood.BlockType.ACACIA.metadata()));
        }
        
        @Override
        protected BlockSettings getSettings()
        {
            return BlockSettings.ACACIASTAIRS;
        }
        
        @Override
        protected void prepare()
        {
            final CommonProxy proxy = Extrabiomes.proxy;
            final Block thisBlock = Stuff.stairsAcacia.get();
            
            thisBlock.setBlockName("extrabiomes.stairs.acacia");
            proxy.setBlockHarvestLevel(thisBlock, "axe", 0);
            proxy.registerBlock(thisBlock, "stairs.acacia");
            
            proxy.registerOre("stairWood", thisBlock);
            Extrabiomes.postInitEvent(new AcaciaStairsActiveEvent(thisBlock));
        }
    },
    RAINBOWEUCALYPTUSSTAIRS(Stuff.stairsRainbowEucalyptus, true)
    {
        @Override
        protected void create()
        {
            Stuff.stairsRainbowEucalyptus = Optional.of(new BlockWoodStairs(Stuff.planks.get(), BlockCustomWood.BlockType.RAINBOW_EUCALYPTUS.metadata()));
        }
        
        @Override
        protected BlockSettings getSettings()
        {
            return BlockSettings.RAINBOWEUCALYPTUSSTAIRS;
        }
        
        @Override
        protected void prepare()
        {
            final CommonProxy proxy = Extrabiomes.proxy;
            final Block thisBlock = Stuff.stairsRainbowEucalyptus.get();
            
            thisBlock.setBlockName("extrabiomes.stairs.rainboweucalyptus");
            proxy.setBlockHarvestLevel(thisBlock, "axe", 0);
            proxy.registerBlock(thisBlock, "stairs.rainboweucalyptus");
            
            proxy.registerOre("stairWood", thisBlock);
            Extrabiomes.postInitEvent(new RainbowEucalyptusStairsActiveEvent(thisBlock));
        }
    },
    CYPRESSSTAIRS(Stuff.stairsCypress, true)
    {
        @Override
        protected void create()
        {
            Stuff.stairsCypress = Optional.of(new BlockWoodStairs(Stuff.planks.get(), BlockCustomWood.BlockType.CYPRESS.metadata()));
        }
        
        @Override
        protected BlockSettings getSettings()
        {
            return BlockSettings.CYPRESSSTAIRS;
        }
        
        @Override
        protected void prepare()
        {
            final CommonProxy proxy = Extrabiomes.proxy;
            final Block thisBlock = Stuff.stairsCypress.get();
            
            thisBlock.setBlockName("extrabiomes.stairs.cypress");
            proxy.setBlockHarvestLevel(thisBlock, "axe", 0);
            proxy.registerBlock(thisBlock, "stairs.cypress");
            
            proxy.registerOre("stairWood", thisBlock);
            Extrabiomes.postInitEvent(new CypressStairsActiveEvent(thisBlock));
        }
    },
    BALDCYPRESSSTAIRS(Stuff.stairsBaldCypress, true)
    {
        @Override
        protected void create()
        {
            Stuff.stairsBaldCypress = Optional.of(new BlockWoodStairs(Stuff.planks.get(), BlockCustomWood.BlockType.BALD_CYPRESS.metadata()));
        }
        
        @Override
        protected BlockSettings getSettings()
        {
            return BlockSettings.BALDCYPRESSSTAIRS;
        }
        
        @Override
        protected void prepare()
        {
            final CommonProxy proxy = Extrabiomes.proxy;
            final Block thisBlock = Stuff.stairsBaldCypress.get();
            
            thisBlock.setBlockName("extrabiomes.stairs.baldcypress");
            proxy.setBlockHarvestLevel(thisBlock, "axe", 0);
            proxy.registerBlock(thisBlock, "stairs.baldcypress");
            
            proxy.registerOre("stairWood", thisBlock);
            Extrabiomes.postInitEvent(new BaldCypressStairsActiveEvent(thisBlock));
        }
    },
    JAPANESEMAPLESTAIRS(Stuff.stairsJapaneseMaple, true)
    {
        @Override
        protected void create()
        {
            Stuff.stairsJapaneseMaple = Optional.of(new BlockWoodStairs(Stuff.planks.get(), BlockCustomWood.BlockType.JAPANESE_MAPLE.metadata()));
        }
        
        @Override
        protected BlockSettings getSettings()
        {
            return BlockSettings.JAPANESEMAPLESTAIRS;
        }
        
        @Override
        protected void prepare()
        {
            final CommonProxy proxy = Extrabiomes.proxy;
            final Block thisBlock = Stuff.stairsJapaneseMaple.get();
            
            thisBlock.setBlockName("extrabiomes.stairs.japanesemaple");
            proxy.setBlockHarvestLevel(thisBlock, "axe", 0);
            proxy.registerBlock(thisBlock, "stairs.japanesemaple");
            
            proxy.registerOre("stairWood", thisBlock);
            Extrabiomes.postInitEvent(new JapaneseMapleStairsActiveEvent(thisBlock));
        }
    },
    AUTUMNSTAIRS(Stuff.stairsAutumn, true)
    {
        @Override
        protected void create()
        {
            Stuff.stairsAutumn = Optional.of(new BlockWoodStairs(Stuff.planks.get(), BlockCustomWood.BlockType.AUTUMN.metadata()));
        }
        
        @Override
        protected BlockSettings getSettings()
        {
            return BlockSettings.AUTUMNSTAIRS;
        }
        
        @Override
        protected void prepare()
        {
            final CommonProxy proxy = Extrabiomes.proxy;
            final Block thisBlock = Stuff.stairsAutumn.get();
            
            thisBlock.setBlockName("extrabiomes.stairs.autumn");
            proxy.setBlockHarvestLevel(thisBlock, "axe", 0);
            proxy.registerBlock(thisBlock, "stairs.autumn");
            
            proxy.registerOre("stairWood", thisBlock);
            Extrabiomes.postInitEvent(new AutumnStairsActiveEvent(thisBlock));
        }
    },
    SAKURABLOSSOMSTAIRS(Stuff.stairsSakuraBlossom, true)
    {
        @Override
        protected void create()
        {
            Stuff.stairsSakuraBlossom = Optional.of(new BlockWoodStairs(Stuff.planks.get(), BlockCustomWood.BlockType.SAKURA_BLOSSOM.metadata()));
        }
        
        @Override
        protected BlockSettings getSettings()
        {
            return BlockSettings.SAKURABLOSSOMSTAIRS;
        }
        
        @Override
        protected void prepare()
        {
            final CommonProxy proxy = Extrabiomes.proxy;
            final Block thisBlock = Stuff.stairsSakuraBlossom.get();
            
            thisBlock.setBlockName("extrabiomes.stairs.sakurablossom");
            proxy.setBlockHarvestLevel(thisBlock, "axe", 0);
            proxy.registerBlock(thisBlock, "stairs.sakurablossom");
            
            proxy.registerOre("stairWood", thisBlock);
            Extrabiomes.postInitEvent(new SakuraBlossomStairsActiveEvent(thisBlock));
        }
    },
    REDROCKSLAB(Stuff.slabRedRock, false)
    {
        @Override
        protected void create()
        {
            Stuff.slabRedRock = Optional.of(new BlockRedRockSlab(false));
        }
        
        @Override
        protected BlockSettings getSettings()
        {
            return BlockSettings.REDROCKSLAB;
        }
        
        @Override
        protected void prepare()
        {
            final CommonProxy proxy = Extrabiomes.proxy;
            final Block thisBlock = Stuff.slabRedRock.get();
            
            thisBlock.setBlockName("extrabiomes.redrockslab");
            proxy.setBlockHarvestLevel(thisBlock, "pickaxe", 0);
            
            Extrabiomes.postInitEvent(new RedRockSlabActiveEvent(thisBlock));
        }
    },
    DOUBLEREDROCKSLAB(Stuff.slabRedRockDouble, false)
    {
        @Override
        protected void create()
        {
            Stuff.slabRedRockDouble = Optional.of(new BlockRedRockSlab(true));
        }
        
        @Override
        protected BlockSettings getSettings()
        {
            return BlockSettings.DOUBLEREDROCKSLAB;
        }
        
        @Override
        protected void prepare()
        {
            final CommonProxy proxy = Extrabiomes.proxy;
            final Block thisBlock = Stuff.slabRedRockDouble.get();
            
            thisBlock.setBlockName("extrabiomes.redrockslab");
            proxy.setBlockHarvestLevel(thisBlock, "pickaxe", 0);
            ItemRedRockSlab.setSlabs((BlockSlab) Stuff.slabRedRock.get(), (BlockSlab) Stuff.slabRedRockDouble.get());
            proxy.registerBlock(Stuff.slabRedRock.get(), extrabiomes.module.fabrica.block.ItemRedRockSlab.class, "slabRedRock");
            proxy.registerBlock(thisBlock, extrabiomes.module.fabrica.block.ItemRedRockSlab.class, "double_slabRedRock");
        }
    },
    REDCOBBLESTAIRS(Stuff.stairsRedCobble, false)
    {
        @Override
        protected void create()
        {
            Stuff.stairsRedCobble = Optional.of(new BlockCustomStairs(Block.getBlockFromItem(Element.RED_COBBLE.get().getItem()), Element.RED_COBBLE.get().getItemDamage()));
        }
        
        @Override
        protected BlockSettings getSettings()
        {
            return BlockSettings.REDCOBBLESTAIRS;
        }
        
        @Override
        protected void prepare()
        {
            final CommonProxy proxy = Extrabiomes.proxy;
            final Block thisBlock = Stuff.stairsRedCobble.get();
            
            thisBlock.setBlockName("extrabiomes.stairs.redcobble");
            proxy.setBlockHarvestLevel(thisBlock, "pickaxe", 0);
            proxy.registerBlock(thisBlock, "stairsRedCobble");
            
            Extrabiomes.postInitEvent(new RedCobbleStairsActiveEvent(thisBlock));
        }
    },
    REDROCKBRICKSTAIRS(Stuff.stairsRedRockBrick, false)
    {
        @Override
        protected void create()
        {
            Stuff.stairsRedRockBrick = Optional.of(new BlockCustomStairs(Block.getBlockFromItem(Element.RED_ROCK_BRICK.get().getItem()), Element.RED_ROCK_BRICK .get().getItemDamage()));
        }
        
        @Override
        protected BlockSettings getSettings()
        {
            return BlockSettings.REDROCKBRICKSTAIRS;
        }
        
        @Override
        protected void prepare()
        {
            final CommonProxy proxy = Extrabiomes.proxy;
            final Block thisBlock = Stuff.stairsRedRockBrick.get();

            thisBlock.setBlockName("extrabiomes.stairs.redrockbrick");
            proxy.setBlockHarvestLevel(thisBlock, "pickaxe", 0);
            proxy.registerBlock(thisBlock, "redrockbrick");
            
            Extrabiomes.postInitEvent(new RedRockBrickStairsActiveEvent(thisBlock));
        }
    },
    WALL(Stuff.wall, false)
    {
        @Override
        protected void create()
        {
            Stuff.wall = Optional.of(new BlockCustomWall());
        }
        
        @Override
        protected BlockSettings getSettings()
        {
            return BlockSettings.WALL;
        }
        
        @Override
        protected void prepare()
        {
            final CommonProxy proxy = Extrabiomes.proxy;
            final Block thisBlock = Stuff.wall.get();

            thisBlock.setBlockName("extrabiomes.wall");
            proxy.setBlockHarvestLevel(thisBlock, "pickaxe", 0);
            proxy.registerBlock(thisBlock, extrabiomes.utility.MultiItemBlock.class, "wall");
            
            Extrabiomes.postInitEvent(new WallActiveEvent(thisBlock));
        }
    };
    
    private static void createBlocks() throws Exception
    {
        for (final BlockManager block : BlockManager.values())
            if (block.getSettings().getEnabled())
            {
                try
                {
                    block.create();
                }
                catch (final Exception e)
                {
                    throw e;
                }
                block.blockCreated = true;
            }
    }
    
    public static void init() throws InstantiationException, IllegalAccessException
    {
        for (final BlockManager block : values())
        {
            if (block.blockCreated) {
                block.prepare();
                if( block._flammable && block._stuff.isPresent() ) {
                    try {
                        block._block = (Block)block._stuff.get();
                        Blocks.fire.setFireInfo(block._block, 5, 20);
                    } catch(ArrayIndexOutOfBoundsException e) {
                        LogHelper.severe("Unable to set "+block+" flammable", e);
                        block._flammable = false;
                    }
                }
            }
        }
    }
    
    public static void preInit() throws Exception
    {
        createBlocks();
    }
    
    private boolean blockCreated = false;

    private Block _block = null;
    private boolean _flammable = false;
    private Optional _stuff = null;

    private BlockManager(Optional stuff, boolean flammable) {
        _stuff = stuff;
        _flammable = flammable;
    }

    protected abstract BlockSettings getSettings();

    protected abstract void create();
    protected abstract void prepare();
}
