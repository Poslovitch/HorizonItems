package fr.poslovitch.horizonitems.util;

import fr.poslovitch.horizonitems.HorizonItems;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileLister {

    private HorizonItems plugin;

    public FileLister(HorizonItems plugin) {
        this.plugin = plugin;
    }

    public List<String> list(String directoryPath, final String fileExtension) {
        List<String> result = new ArrayList<String>();

        // Check if the locale folder exists
        // If it does exist, then no files from the JAR will be added if they are missing.
        File directory = new File(plugin.getDataFolder(), directoryPath);
        if (directory.exists()) {
            FilenameFilter filter = (dir, name) -> {
                return name.endsWith("." + fileExtension) && name.replace("." + fileExtension, "").matches("\\w+");
            };

            for (String fileName : directory.list(filter)) {
                result.add(fileName.replace("." + fileExtension, ""));
            }

            // Finish if there are any files in this folder
            if (!result.isEmpty()) {
                return result;
            }
        }

        // Else look in the JAR
        File jarFile = null;

        // Get the jar file from the plugin.
        // We have to do some Reflection here
        try {
            Method method = JavaPlugin.class.getDeclaredMethod("getFile");
            method.setAccessible(true);

            jarFile = (File) method.invoke(plugin);
        } catch (Exception e) {
            plugin.getLogger().severe("§cCould not load the JAR file of the plugin. Maybe Bukkit has done some security changes? Please inform Poslovitch about this on Github!");
            e.printStackTrace();
        }

        try {
            JarFile jar = new JarFile(jarFile);

            // Loop through all the entries
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String path = entry.getName();

                // Not in the folder
                if (!path.startsWith(directoryPath)) {
                    continue;
                }

                String file = path.replace(directoryPath + "/", "");
                String fileName = file.replace("." + fileExtension,"");

                if (file.endsWith("." + fileExtension) && fileName.matches("\\w+")) {
                    result.add(fileName);
                }
            }

            jar.close();
        } catch (Exception e) {
            plugin.getLogger().severe("§cCould not load the JAR of the plugin. This problem may have no solution, but please contact Poslovitch on Github!");
            e.printStackTrace();
        }

        return result;
    }
}
