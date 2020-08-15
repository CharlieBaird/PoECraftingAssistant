package crafting.UI.hotkeys;

public enum Task
{
    SAVE_FILTER("Save filter"),
    RUN_FILTER("Run/stop filter (Global hotkey)"),
    FOCUS_WINDOW("Bring window to focus/front (Global hotkey)"),
    NEW_FILTER("Create new filter"),
    OPEN_FILTER("Open an existing filter");
    
    public final String pretty;
    
    private Task(String pretty)
    {
        this.pretty = pretty;
    }
}
