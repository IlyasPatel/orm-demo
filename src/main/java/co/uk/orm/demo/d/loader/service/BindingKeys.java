package co.uk.orm.demo.d.loader.service;

public enum BindingKeys {

    TABLE("table"),

    ROW("row"),

    DEFAULT_ROW("defaultRow"),

    FIND("find"),

    FLUSH("flush");

    String keyName;

    BindingKeys(String value) {
        this.keyName = value;
    }

    public String getKeyName() {
        return keyName;
    }

    public static boolean isExists(String key) {
        for (BindingKeys k : BindingKeys.values()) {
            if (k.getKeyName().equalsIgnoreCase(key)) {
                return true;
            }
        }

        return false;
    }

}
