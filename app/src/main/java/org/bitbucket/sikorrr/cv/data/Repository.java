package org.bitbucket.sikorrr.cv.data;

import java.util.List;
import org.bitbucket.sikorrr.cv.presentation.detail.model.Detail;
import org.bitbucket.sikorrr.cv.presentation.overview.model.Section;

public interface Repository {

  String getEmail();

  String getWebPage();

  String getPhoneNumber();

  List<Section> getSections();

  Section getSectionById(int id);

  Detail getDetailsById(int id);

  String getCircularImageUrl();

  String getBackgroundImage();
}
