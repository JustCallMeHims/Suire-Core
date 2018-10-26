package me.suiremc.core.objects;

import org.bukkit.Material;

public class ItemValue {

    private Material material;
    private double value;
    private short data;

    public ItemValue(Material material, double value){
        this.material = material;
        this.value = value;
    }

    public ItemValue(Material material, double value, short data){
        this(material, value);
        this.data = data;
    }


    public Material getMaterial() {
        return material;
    }

    public double getValue() {
        return value;
    }

    public short getData() {
        return data;
    }

    public static ItemValue of(Material material, double value){
        return new ItemValue(material, value);
    }

    public static ItemValue of(Material material, double value, short data){
        return new ItemValue(material, value, data);
    }

}
