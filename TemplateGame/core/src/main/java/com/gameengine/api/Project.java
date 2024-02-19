package com.gameengine.api;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;
import java.util.ArrayList;

import static com.game.Statics.json;

public class Project {

    public FileHandle path;
    public String projectName;

    public ArrayList<Class<? extends Component>> components = new ArrayList<>();

    public GameObject rootGameObject;

    public Project() {}

    public static Project loadProject(String path) {

        File pFolder = new File(path);

        if (pFolder.isDirectory()) {
            FileHandle projectSave = Gdx.files.absolute(pFolder.getAbsolutePath() + "\\main.prj");
            if (projectSave.exists())  {

                SerializableProject sp = json.fromJson(SerializableProject.class, projectSave.readString());

                Project p = sp.createProject();
                return p;

                /*p.path = Gdx.files.absolute(pFolder.getAbsolutePath());

                JsonValue root = new JsonReader().parse(projectSave.readString());
                p.projectName = root.getString("project_name");

                p.rootGameObject = new GameObject(p.projectName);*/

            }
        }

        return null;

    }

}

