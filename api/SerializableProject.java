package com.gameengine.api;


import java.io.IOException;


@NoArgsConstructor
public class SerializableProject {

 public String path;
 public String projectName;

 public SerializableGameObject rootGameObject;

 public SerializableProject(Project project) {}

 public Project createProject() {return null;}


}
