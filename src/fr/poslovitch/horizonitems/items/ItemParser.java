package fr.poslovitch.horizonitems.items;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemParser {

    public static String[][] parse(File itemFile) {
        String[][] result = new String[2][3];

        List<String> parseable = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(itemFile))) {
            // Go through each line of the file and save them if they are parseable
            for (String line; (line = br.readLine()) != null; ) {
                // Remove indentation to make reading easier
                line = line.trim();

                // Checks if the line is comment or is empty. If so, ignore it.
                if (line.startsWith("#") || line.isEmpty()) continue;

                // Add it to the perseable list
                parseable.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String block = "";
        List<String> metaList = new ArrayList<>();
        List<String> itemList = new ArrayList<>();
        // Go through each saved line to get the data blocks
        for (String line : parseable) {
            // Check if we are already looking for a data block
            if (!block.equals("")) {
                // Check if this is the end of a data block
                if (line.startsWith("}")) {
                    block = "";
                    continue;
                }

                // Check if the current block is meta
                if (block.equals("meta")) {
                    metaList.add(line);
                }
                // Check if the current block is item
                else if (block.equals("item")) {
                    itemList.add(line);
                }
            }
            // Otherwise check if there is a meta block
            else if (line.startsWith("meta") && line.substring(4).contains("{")) {
                block = "meta";
            }
            // Otherwise check if there is an item block
            else if (line.startsWith("item") && line.substring(4).contains("{")) {
                block = "item";
            }
        }

        // Parse the data from the lists
        String[] meta = parseMetaBlock(metaList);
        String[] item = parseItemBlock(itemList);

        // Now store it in the result array
        result[0][0] = meta[0]; // ID
        result[0][1] = meta[1]; // Category
        result[1][0] = item[0]; // Item type
        result[1][1] = item[1]; // Item display name
        result[1][2] = item[2]; // Item lore

        return result;
    }

    private static String[] parseMetaBlock(List<String> metaBlock) {
        String[] result = new String[2];

        for (String line : metaBlock) {
            // Check if the line contains '=', then split it
            if (line.contains("=")) {
                String[] split = line.split("( *)=( *)", 2);

                // Check if ID is not set yet and the line is starting with id
                if (result[0] == null && split[0].equalsIgnoreCase("id")) {
                    result[0] = split[1].replace("\"", "");
                }
                // Otherwise check if category is not set and the line is starting with category
                else if (result[1] == null && split[0].equalsIgnoreCase("category")) {
                    result[1] = split[1].replace("\"", "");
                }
            }
        }

        return result;
    }

    private static String[] parseItemBlock(List<String> itemBlock) {
        String[] result = new String[3];

        for (String line : itemBlock) {
            // Check if the line contains '=', then split it
            if (line.contains("=")) {
                String[] split = line.split("( *)=( *)", 2);

                // Check if type is not set yet and the line is starting with type
                if (result[0] == null && split[0].equalsIgnoreCase("type")) {
                    result[0] = split[1].replace("\"", "");
                }
                // Otherwise check if display name is not set and the line is starting with name
                else if (result[1] == null && split[0].equalsIgnoreCase("name")) {
                    result[1] = split[1].replace("\"", "");
                }
                // Otherwise check if lore is not set and the line is starting with lore
                else if (result[2] == null && split[0].equalsIgnoreCase("lore")) {
                    // Checks if it is a multi-line lore
                    if (split[1].startsWith("[")) {
                        StringBuilder lore = new StringBuilder();

                        for (String part : split[1].split("( *),( *)")) {
                            part = part.replace("\"", "");

                            if (part.startsWith("[")) part = part.replace("[", "");

                            if (part.endsWith("]")) {
                                part = part.replace("]", "");
                                lore.append(part);
                            } else {
                                lore.append(part);
                                lore.append("\n");
                            }
                        }

                        result[2] = lore.toString();
                    }

                    // Otherwise considers it as a simple line
                    else {
                        result[2] = split[1].replace("\"", "");
                    }
                }
            }
        }

        return result;
    }
}
