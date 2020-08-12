package crafting.UI.hotkeys;

public enum Ctrl
{
    CTRL("Ctrl"),
    SHIFT("Shift"),
    CTRL_SHIFT("Ctrl+Shift");
    
    public final String pretty;
    
    private Ctrl(String pretty)
    {
        this.pretty = pretty;
    }
    
    @Override
    public String toString()
    {
        return this.pretty;
    }
}
