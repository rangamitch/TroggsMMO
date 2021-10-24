package xyz.troggs.mmo.Items;

public class ItemImpl implements Item{

    private String id;
    private String name;

    private ItemRarity defaultRarity;

    private int levelRequirement;
    private String classRequirement;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ItemRarity getDefaultRarity() {
        return defaultRarity;
    }

    @Override
    public int getLevelRequirement() {
        return levelRequirement;
    }

    @Override
    public String getClassRequirement() {
        return classRequirement;
    }

    public ItemImpl setId(String id) {
        this.id = id;
        return this;
    }

    public ItemImpl setName(String name) {
        this.name = name;
        return this;
    }

    public ItemImpl setDefaultRarity(ItemRarity defaultRarity) {
        this.defaultRarity = defaultRarity;
        return this;
    }

    public ItemImpl setLevelRequirement(int levelRequirement) {
        this.levelRequirement = levelRequirement;
        return this;
    }

    public ItemImpl setClassRequirement(String classRequirement) {
        this.classRequirement = classRequirement;
        return this;
    }
}
