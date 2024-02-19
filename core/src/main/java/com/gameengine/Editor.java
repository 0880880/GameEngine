package com.gameengine;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gameengine.api.*;
import com.gameengine.api.components.*;
import com.gameengine.api.graphics.Material;
import com.gameengine.api.graphics.Shader;
import com.gameengine.utils.DirectoryWatcher;
import com.gameengine.utils.RenderUtils;
import com.gameengine.utils.Utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.HashMap;

import static com.gameengine.Statics.*;

public class Editor implements Screen {

	ArrayList<Class<? extends Component>> builtInComponents = new ArrayList<>();

    boolean reload = false;

	void updateScripts() {
		reload = true;
	}

	Thread thread;

	int mouseButton;

    Array<FileHandle> shaderUpdates = new Array<>();

	@Override
	public void show() {
		builtInComponents.add(Transform.class);
		builtInComponents.add(Camera.class);
        builtInComponents.add(Renderable.class);
        builtInComponents.add(AudioListener.class);
        builtInComponents.add(AudioSource.class);

		editorCamera = (OrthographicCamera) editorViewport.getCamera();

		gui.createAssets();

		WatchService watchService = new DirectoryWatcher().getService(currentProject.path.path() + "/Assets");

		thread = new Thread(() -> {
            while (true) {
                WatchKey key = null;
                while (true) {
                    try {
                        if ((key = watchService.take()) == null) break;
                    } catch (InterruptedException ignored) {}
                    if (key != null) {
						for (WatchEvent<?> event : key.pollEvents()) {
							String file = event.context().toString();
							if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY || event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
								gui.refresh();
                                if (file.endsWith(".java")) {
                                    updateScripts();
                                }
                                if (file.endsWith(".sha")) {
                                    FileHandle shaderFile = currentProject.path.child("Assets").child(file);
                                    shaderUpdates.add(shaderFile);
                                }
                                for (Material material : engine.loadedMaterials.values()) {
                                    material.update();
                                }
							}
						}
						key.reset();
					}
                }
            }
        });

        thread.setDaemon(true);
		thread.start();

		Gdx.input.setInputProcessor(new InputMultiplexer(new InputProcessor() {

			@Override
			public boolean keyDown(int keycode) {
				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				return false;
			}

			@Override
			public boolean keyTyped(char character) {
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				return false;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				return false;
			}

			@Override
			public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				return false;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				return false;
			}

			@Override
			public boolean scrolled(float amountX, float amountY) {
                if (!gui.isAnyWindowHovered) {
                    float oldZoom = editorCamera.zoom;
                    Vector2 tmpMouse = mouse.cpy();
                    editorCamera.zoom += amountY / 10f;
                    editorCamera.zoom = MathUtils.clamp(((OrthographicCamera) editorViewport.getCamera()).zoom, .2f, 10);
                    float scaleChange = editorCamera.zoom - oldZoom;
                    //editorCamera.position.add(-(tmpMouse.x * scaleChange),-(tmpMouse.y * scaleChange),0);
                }

				return false;
			}
		},new GestureDetector(new GestureDetector.GestureListener() {

			@Override
			public boolean touchDown(float x, float y, int pointer, int button) {
				mouseButton = button;
				return false;
			}

			@Override
			public boolean tap(float x, float y, int count, int button) { return false; }

			@Override
			public boolean longPress(float x, float y) { return false; }

			@Override
			public boolean fling(float velocityX, float velocityY, int button) { return false; }

            @Override
            public boolean pan(float x, float y, float deltaX, float deltaY) {
                return false;
            }

			@Override
			public boolean panStop(float x, float y, int pointer, int button) { return false; }

			@Override
			public boolean zoom(float initialDistance, float distance) { return false; }

			@Override
			public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) { return false; }

			@Override
			public void pinchStop() { }
		})));

	}

	float lastX = 0;
	float lastY = 0;

	Color tmp = new Color();

    Vector2 start = new Vector2();

	boolean copyComponents(GameObject gameObject) {
		for (int i = 0; i < gameObject.components.size(); i++) {
			Component component = gameObject.components.get(i);
			if (component.getClass() == Camera.class) {
                gameCamera = gameObject.getComponent(Camera.class);
				return true;
			}
		}

		for (int i = 0; i < gameObject.children.size(); i++) {
			if (copyComponents(gameObject.children.get(i))) return true;
		}
		return false;
	}

	Vector2 positionOffset = new Vector2();

    private void setMouse() {
        mouse.set(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        mouse.sub(gui.sceneX,-gui.sceneY);
        if (isGameRunning) {
            mouse.set(gameCamera.getViewport().unproject(mouse));
        } else {
            mouse.set(editorViewport.unproject(mouse));
        }
        mouse.y = -mouse.y;
        mouse.set(mouse.x, mouse.y + editorCamera.position.y * 2);
    }

	@Override
	public void render(float delta) {

        ScreenUtils.clear(0,0,0,1);

        if (reload && !isGameRunning) {

            engine = null;

            HashMap<Long, HashMap<String, HashMap<String, Object>>> gameObjectsComponentsFieldMap = new HashMap<>();
            Utils.copyComponentsFields(gameObjectsComponentsFieldMap, currentProject.rootGameObject);

            allGameObjects.clear();

            javaComponentLoader = new JavaComponentLoader();
            engine = new Engine(new Sprite(), drawer, cameraHolder);

            SerializableProject sp = new SerializableProject(currentProject);
            currentProject = null;

            System.gc();

            currentProject = sp.createProject();

            Utils.addComponentsFields(gameObjectsComponentsFieldMap, currentProject.rootGameObject);

            Utils.addComponents();

            Utils.setGameObjectCounter();

            reload = false;
        }

        fbo.begin();

        if (selection != null && selection.getClass() == GameObject.class)
            selectedGameObject = (GameObject) selection;

        if (selection == null) selectedGameObject = null;

		editorViewport.apply();

		if (isGameRunning) {
			if (gameCamera != null) {
				com.gameengine.api.graphics.Color c = gameCamera.backgroundColor;
				ScreenUtils.clear(c.r, c.g, c.b,1);
			}
		} else {
			ScreenUtils.clear(0,.046f,.09f,1);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && Gdx.input.isKeyJustPressed(Input.Keys.S)) {
			Utils.save();
		}

        editorViewport.setScreenPosition(gui.sceneX, gui.sceneY);
        editorViewport.update(gui.lastSceneWidth, gui.lastSceneHeight, false);
        editorViewport.apply();

		if (!isGameRunning) {
			gameCamera = null;
		} else if (gameCamera == null) {
			copyComponents(currentProject.rootGameObject);
		}

        setMouse();

		lastX = mouse.x;
		lastY = mouse.y;

		boolean canSelectNew = Utils.tools();

		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !gui.isAnyWindowHovered) {
			if (canSelectNew) {
				selection = null;
				boolean res = select(currentProject.rootGameObject);
			}
		}

		if (!isGameRunning) {
            editorViewport.apply();
			batch.setProjectionMatrix(editorCamera.combined);
		} else {
            gameCamera.getViewport().setScreenPosition(gui.sceneX, gui.sceneY);
            gameCamera.getViewport().update(gui.lastSceneWidth, gui.lastSceneHeight, false);
			gameCamera.apply(batch, gui.lastSceneWidth, gui.lastSceneHeight);
		}
		batch.begin();

        for (int i = 0 ; i < shaderUpdates.size; i++) {
            FileHandle shaderFile = shaderUpdates.get(i);
            if (engine.loadedShaders.containsKey(shaderFile.path())) {
                Shader shader = engine.loadedShaders.get(shaderFile.path());
                shader.update(shaderFile, shaderFile.readString());
            } else {
                engine.loadShader(shaderFile.path(), shaderFile);
            }
            shaderUpdates.removeIndex(i);
        }

		if (!isGameRunning) RenderUtils.renderGrid(editorCamera);

        if (Gdx.input.isButtonJustPressed(Input.Buttons.MIDDLE) && !gui.isAnyWindowHovered && !isGameRunning) {
            start.set(mouse);
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.MIDDLE) && !gui.isAnyWindowHovered && !isGameRunning) {
            float deltaX = mouse.x - start.x;
            float deltaY = mouse.y - start.y;
            editorCamera.position.add(-deltaX, -deltaY, 0);
            editorViewport.apply();
            setMouse();
            start.set(mouse);
            editorViewport.apply();
        }

        engine.update();

		if (selectedGameObject != null) {
			com.gameengine.api.math.Vector2 position = selectedGameObject.transform.position;
			com.gameengine.api.math.Vector2 scale = selectedGameObject.transform.scale;

			float scX = scale.x / 2f;
			float scY = scale.y / 2f;

			if (Gdx.input.isKeyJustPressed(Input.Keys.G) && !gui.isAnyWindowHovered) {
				if (editMode == 4) editMode = 0;
				else editMode = 4;
				positionOffset.set(-mouse.x + position.x, -mouse.y + position.y);
			}

			if (editMode >= 4 && (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))) editMode = 0;
			if (editMode == 4) {
				selectedGameObject.transform.position.set(mouse.x, mouse.y).add(positionOffset.x, positionOffset.y);
			}

			if (Gdx.input.isKeyJustPressed(Input.Keys.S) && !gui.isAnyWindowHovered) {
				editMode = 5;
			}

			if (Gdx.input.isKeyJustPressed(Input.Keys.R) && !gui.isAnyWindowHovered) {
				editMode = 6;
			}

			if (editMode == 1) {
				RenderUtils.renderArrow(position.x, position.y, position.x + 1.5f, position.y, .2f, .2f, Color.RED, .02f * editorCamera.zoom);
				RenderUtils.renderArrow(position.x, position.y, position.x, position.y + 1.5f, .2f, .2f, Color.GREEN, .02f * editorCamera.zoom);
				drawer.filledRectangle(position.x, position.y, .2f, .2f, tmp.set(1,1,.8f,.2f));
				drawer.rectangle(position.x, position.y, .2f, .2f, tmp.set(.1f,.1f,0,.5f), .05f);
			}
			if (editMode == 2) {
				RenderUtils.renderArrow(position.x, position.y, position.x + 1.5f, position.y, .2f, .2f, Color.RED, .02f * editorCamera.zoom);
				RenderUtils.renderArrow(position.x, position.y, position.x, position.y + 1.5f, .2f, .2f, Color.GREEN, .02f * editorCamera.zoom);
			}
			if (editMode == 3) {
				drawer.setColor(Color.DARK_GRAY);
				drawer.circle(position.x, position.y, 1.5f, .12f);
				drawer.setColor(Color.LIGHT_GRAY);
				drawer.circle(position.x, position.y, 1.5f, .12f);
			}
		}

		batch.end();

        fbo.end();

        gui.fboTexture = fbo.getColorBufferTexture();

		try {
			gui.render();
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

	}

	private boolean select(GameObject gameObject) {
		com.gameengine.api.math.Vector2 position = gameObject.transform.position;
		com.gameengine.api.math.Vector2 scale = gameObject.transform.scale;
		if (mouse.x >= position.x - scale.x / 2f && mouse.y >= position.y - scale.y / 2f && mouse.x <= position.x + scale.x / 2f && mouse.y <= position.y + scale.y / 2f) {
			selection = gameObject;
			return true;
		} else {
			for (GameObject g : gameObject.children) {
				return select(g);
			}
		}
		return false;
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {
		// Invoked when your application is paused.
	}

	@Override
	public void resume() {
		// Invoked when your application is resumed after pause.
	}

	@Override
	public void hide() {
		// This method is called when another screen replaces this one.
	}

	@Override
	public void dispose() {
		// Destroy screen's assets here.
	}
}
