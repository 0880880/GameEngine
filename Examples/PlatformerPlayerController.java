import com.gameengine.api.Component;
import com.gameengine.api.Input;
import com.gameengine.api.components.RigidBody;

public class PlatformerPlayerController extends Component {

    public float speed = 10;

    private RigidBody rigidBody;

    public void start() {
        rigidBody = gameObject.getComponent(RigidBody.class);
    }

    public void update() {

        float x = 0;

        if (Input.isKeyPressed("A"))
            x = -1;
        if (Input.isKeyPressed("D"))
            x = 1;

        if (rigidBody != null) rigidBody.setLinearVelocity(x * speed, rigidBody.getLinearVelocityY());

    }

}