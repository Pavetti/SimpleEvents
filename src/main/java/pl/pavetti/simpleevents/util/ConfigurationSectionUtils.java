package pl.pavetti.simpleevents.util;

import lombok.experimental.UtilityClass;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

@UtilityClass
public class ConfigurationSectionUtils {

    public static ConfigurationSection getMainSection(YamlConfiguration yamlConfiguration, String primarySectionName) {
        if (yamlConfiguration.getConfigurationSection(primarySectionName) == null) {
            return yamlConfiguration.createSection(primarySectionName);
        }
        return yamlConfiguration.getConfigurationSection(primarySectionName);
    }

    public static ConfigurationSection getSection(ConfigurationSection parentSection, String name) {
        if (parentSection.getConfigurationSection(name) == null) {
            return parentSection.createSection(name);
        }
        return parentSection.getConfigurationSection(name);
    }
}
