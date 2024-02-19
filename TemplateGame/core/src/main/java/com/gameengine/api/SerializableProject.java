package com.gameengine.api;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.game.Statics;
import com.game.Utils;
import lombok.NoArgsConstructor;

import java.io.IOException;

import static com.game.Statics.audio;
import static com.game.Statics.currentProject;

@NoArgsConstructor
public class SerializableProject {

    public String path;
    public String projectName;

    public SerializableGameObject rootGameObject;

    public SerializableProject(Project project) {
        path = project.path.file().getAbsolutePath();
        projectName = project.projectName;
        rootGameObject = new SerializableGameObject(project.rootGameObject);
    }

    public Project createProject() {
        Project project = new Project();
        project.path = Gdx.files.absolute(path);
        project.projectName = projectName;
        Statics.currentProject = project;
        Utils.compile();

        FileHandle[] assets = currentProject.path.child("assets").list();
        for (FileHandle file : assets) {
            if (file.extension().equalsIgnoreCase("java")) {
                if (!file.readString().isEmpty()) {
                    if (!currentProject.path.child("Temp").child(file.nameWithoutExtension() + ".jar").exists()) continue;
                    Class<?> c = Utils.getClass(file.nameWithoutExtension());
                    if (Component.class.isAssignableFrom(c)) {
                        Class<? extends Component> nc = (Class<? extends Component>) c;
                        currentProject.components.add(nc);
                    }
                }
            }
        }

        project.rootGameObject = rootGameObject.createGameObject(null);
        return project;
    }

}

