package fit.hutech.spring.constants;

public enum Provider {
    LOCAL("LOCAL"),
    GOOGLE("GOOGLE");

    public final String value;

    Provider(String value) {
        this.value = value;
    }
}