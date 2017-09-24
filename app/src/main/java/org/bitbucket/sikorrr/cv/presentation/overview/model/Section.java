package org.bitbucket.sikorrr.cv.presentation.overview.model;

public class Section {

  private String title;
  private int itemId;
  private String photoUrl;

  public Section() {
  }

  public Section(int itemId, String name, String photoUrl) {
    this.itemId = itemId;
    this.title = name;
    this.photoUrl = photoUrl;
  }

  public String getTitle() {
    return title;
  }

  public int getItemId() {
    return itemId;
  }

  public String getPhotoUrl() {
    return photoUrl;
  }
}
