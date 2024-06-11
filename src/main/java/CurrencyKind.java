public enum CurrencyKind {
    USD("доллар"), EUR("евро"), RUB("рубль");
    private String name;
    CurrencyKind(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
