package machine;

// these states correspond to the menu option, states are used to enter and exit methods multiple times as needed
enum State {
    MENU, BUY, FILL, TAKE, REMAINING, EXIT;

    // changes user input "buy" to state BUY and so on...
    public static State getState(String state) {
        for (State value : State.values()) {
            if (value.toString().equals(state.toUpperCase())) return value;

        }
        return MENU;
    }
}


