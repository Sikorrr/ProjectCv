package org.bitbucket.sikorrr.cv.presentation.detail.model;

import java.util.ArrayList;
import java.util.List;

public class Detail {

  private int itemId;

  private boolean confetti;

  private List<Item> items = new ArrayList<>();

  public List<Item> getItems() {
    return items;
  }

  public boolean isConfetti() {
    return confetti;
  }

  public static class Item {

    private String header;

    private List<Subitem> subitems = new ArrayList<>();

    public List<Subitem> getSubitems() {
      return subitems;
    }

    public String getHeader() {
      return header;
    }

    public String getSubitemLeft(int i) {

      if (subitems.size() == 0) {
        return null;
      }
      return subitems.get(i).left;
    }

    public String getSubitemRight(int i) {
      if (subitems.size() == 0) {
        return null;
      }
      return subitems.get(i).right;
    }
  }

  public static class Subitem {
    public String left;
    public String right;

    public Subitem(String left, String right) {
      this.right = right;
      this.left = left;
    }

    public Subitem() {
    }
  }

  public Detail(int itemId, List<Item> headers) {
    this.items = headers;
    this.itemId = itemId;
  }

  public Detail() {
  }
}
