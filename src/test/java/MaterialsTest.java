import ca.jamiesinn.trailgui.trails.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.io.File;
import java.io.IOException;

public class MaterialsTest
{
    private FileConfiguration config;

    @Rule
    public ErrorCollector errors = new ErrorCollector();

    public MaterialsTest()
    {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("config.yml").getFile());
        System.out.println(file.toString());
        config = new YamlConfiguration();
        try
        {
            config.load(file);
        }
        catch (IOException | InvalidConfigurationException e)
        {
            e.printStackTrace();
        }
    }


    @Test
    public void TestConfigMaterials()
    {
        System.out.println("Checking config for Material validation");
        if (getConfig().isConfigurationSection("trails"))
        {
            ConfigurationSection section = getConfig().getConfigurationSection("trails");
            for (String key : section.getKeys(false))
            {
                if (section.isConfigurationSection(key))
                {
                    ConfigurationSection trailTypeSection = section.getConfigurationSection(key);
                    System.out.println("Loading section: " + trailTypeSection.getName());
                    try
                    {
                        Trail trail;
                        if (trailTypeSection.getString("type").equalsIgnoreCase("ITEM_CRACK"))
                        {
                            trail = new ItemTrail(trailTypeSection);
                        }
                        else if (trailTypeSection.getString("type").equalsIgnoreCase("BLOCK_CRACK")
                                || trailTypeSection.getString("type").equalsIgnoreCase("BLOCK_DUST")
                                || trailTypeSection.getString("type").equalsIgnoreCase("FALLING_DUST"))
                        {
                            trail = new BlockTrail(trailTypeSection);
                        }
                        else if (trailTypeSection.getString("type").equalsIgnoreCase("REDSTONE"))
                        {
                            trail = new RedstoneTrail(trailTypeSection);
                        }
                        else if (trailTypeSection.getBoolean("is_effect", false))
                        {
                            trail = new EffectTrail(trailTypeSection);
                        }
                        else if (trailTypeSection.getString("type").equalsIgnoreCase("SCULK_CHARGE"))
                        {
                            trail = new SculkChargeTrail(trailTypeSection);
                        }
                        else if (trailTypeSection.getString("type").equalsIgnoreCase("SHRIEK"))
                        {
                            trail = new ShriekTrail(trailTypeSection);
                        }
                        else
                        {
                            trail = new NormalTrail(trailTypeSection);
                        }

                        Assert.assertNotNull(trail.getType());
                        System.out.println("Loaded trail with type: "+trail.getType());
                    }
                    catch (Exception ex)
                    {
                        Assert.fail("Failed to load '" + trailTypeSection.getName() + "'. Error: " + ex.getMessage());
                    }
                }
            }
        }
    }

    private FileConfiguration getConfig()
    {
        return config;
    }
}
