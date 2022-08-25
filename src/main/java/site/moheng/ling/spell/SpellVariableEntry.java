package site.moheng.ling.spell;

public final class SpellVariableEntry<T> {
    public final SpellVariableType<T> type;
    public final T data;
    
    public SpellVariableEntry(SpellVariableType<T> type, T data) {
        this.type = type;
        this.data = data;
    }
    
    
}