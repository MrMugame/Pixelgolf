package game.physics;

import game.Component;

public class Material extends Component {
    private MaterialType material;

    public Material(MaterialType material) {
        this.material = material;
    }

    public MaterialType getMaterial() {
        return material;
    }
}
