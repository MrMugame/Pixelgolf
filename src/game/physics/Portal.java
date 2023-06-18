package game.physics;

import game.Component;

public class Portal extends Component {
    private int id;

    public Portal(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
