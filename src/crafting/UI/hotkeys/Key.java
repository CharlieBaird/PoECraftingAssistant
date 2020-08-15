package crafting.UI.hotkeys;

public enum Key {
    ZERO(48, "0"),
    ONE(49, "1"),
    TWO(50, "2"),
    THREE(51, "3"),
    FOUR(52, "4"),
    FIVE(53, "5"),
    SIX(54, "6"),
    SEVEN(55, "7"),
    EIGHT(56, "8"),
    NINE(57, "9"),
    A(65),
    B(66),
    C(67),
    D(68),
    E(69),
    F(70), 
    G(71),
    H(72),
    I(73),
    J(74),
    K(75),
    L(76),
    M(77),
    N(78),
    O(79),
    P(80),
    Q(81),
    R(82),
    S(83),
    T(84),
    U(85),
    V(86),
    W(87),
    X(88),
    Y(89),
    Z(90);
    
    public final int keycode;
    public final String pretty;
    
    private Key(int keycode, String pretty)
    {
        this.keycode = keycode;
        this.pretty = pretty;
    }
    
    private Key(int keycode)
    {
        this.keycode = keycode;
        this.pretty = this.name();
    }
    
    @Override
    public String toString()
    {
        return this.pretty;
    }
}
