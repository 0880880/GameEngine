# Game Engine

A lightweight 2D game engine made with libGDX.

![Preview](Preview.png)

## **Features**

- **Java Scripting**
- **Audio**
- **2D Physics**
- **Shaders**

## **Example Script**

```java
import com.engine.api.Component;
import com.engine.api.Input;

public class MyScript extends Component {

        public String myVar;

        public void start() { 
                // Initialize
        }

        public void update() {
                if (Input.isKeyPressed("SPACE")) {
                        // Jump
                }
        }

}
```

## **Download**

Find the latest version in the [Releases](https://github.com/0880880/GameEngine/releases) page.

## Requirements

Java Development Kit (JDK): 11 or higher

## **License**

This project is governed by the MIT License. For detailed information, refer to [LICENSE](https://github.com/0880880/GameEngine/blob/core/LICENSE).
