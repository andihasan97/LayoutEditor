package com.itsvks.layouteditor.managers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itsvks.layouteditor.LayoutEditor;
import com.itsvks.layouteditor.ProjectFile;
import com.itsvks.layouteditor.utils.Constants;
import com.itsvks.layouteditor.utils.FileUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class ProjectManager {
  private static ProjectManager INSTANCE;

  public ArrayList<HashMap<String, Object>> views;
  public ArrayList<HashMap<String, Object>> layouts;
  public ArrayList<HashMap<String, Object>> androidxWidgets;
  public ArrayList<HashMap<String, Object>> materialDesignWidgets;

  public ArrayList<HashMap<String, Object>> PALETTE_COMMON;
  public ArrayList<HashMap<String, Object>> PALETTE_TEXT;
  public ArrayList<HashMap<String, Object>> PALETTE_BUTTONS;
  public ArrayList<HashMap<String, Object>> PALETTE_WIDGETS;
  public ArrayList<HashMap<String, Object>> PALETTE_LAYOUTS;
  public ArrayList<HashMap<String, Object>> PALETTE_CONTAINERS;
  public ArrayList<HashMap<String, Object>> PALETTE_GOOGLE;
  public ArrayList<HashMap<String, Object>> PALETTE_LEGACY;

  private ProjectFile project;

  public static ProjectManager getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new ProjectManager();
    }
    return INSTANCE;
  }

  private ProjectManager() {
    CompletableFuture.runAsync(
        () -> {
          initPalette();
          initializeWidgetLists();
        });
  }

  public void openProject(ProjectFile project) {
    this.project = project;
  }

  public ProjectFile getOpenedProject() {
    return project;
  }

  public String getColorsXml() {
    return FileUtil.readFile(project.getColorsPath());
  }

  public String getStringsXml() {
    return FileUtil.readFile(project.getStringsPath());
  }

  private void initPalette() {
    PALETTE_COMMON = convertJsonToJavaObject(Constants.PALETTE_COMMON);
    PALETTE_TEXT = convertJsonToJavaObject(Constants.PALETTE_TEXT);
    PALETTE_BUTTONS = convertJsonToJavaObject(Constants.PALETTE_BUTTONS);
    PALETTE_WIDGETS = convertJsonToJavaObject(Constants.PALETTE_WIDGETS);
    PALETTE_LAYOUTS = convertJsonToJavaObject(Constants.PALETTE_LAYOUTS);
    PALETTE_CONTAINERS = convertJsonToJavaObject(Constants.PALETTE_CONTAINERS);
    PALETTE_GOOGLE = convertJsonToJavaObject(Constants.PALETTE_GOOGLE);
    PALETTE_LEGACY = convertJsonToJavaObject(Constants.PALETTE_LEGACY);
  }

  private void initializeWidgetLists() {
    views =
        new Gson()
            .fromJson(
                FileUtil.readFromAsset(Constants.VIEWS_FILE, LayoutEditor.getContext()),
                new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType());
    layouts =
        new Gson()
            .fromJson(
                FileUtil.readFromAsset(Constants.LAYOUTS_FILE, LayoutEditor.getContext()),
                new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType());

    materialDesignWidgets =
        new Gson()
            .fromJson(
                FileUtil.readFromAsset(
                    Constants.MATERIAL_DESIGN_WIDGETS_FILE, LayoutEditor.getContext()),
                new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType());
    androidxWidgets =
        new Gson()
            .fromJson(
                FileUtil.readFromAsset(Constants.ANDROIDX_WIDGETS_FILE, LayoutEditor.getContext()),
                new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType());
  }

  private ArrayList<HashMap<String, Object>> convertJsonToJavaObject(String filePath) {
    return new Gson()
        .fromJson(
            FileUtil.readFromAsset(filePath, LayoutEditor.getContext()),
            new TypeToken<ArrayList<HashMap<String, Object>>>() {}.getType());
  }
}
