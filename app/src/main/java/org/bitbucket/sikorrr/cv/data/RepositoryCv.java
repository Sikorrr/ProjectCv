package org.bitbucket.sikorrr.cv.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import org.bitbucket.sikorrr.cv.presentation.detail.model.Detail;
import org.bitbucket.sikorrr.cv.presentation.overview.model.Section;
import org.bitbucket.sikorrr.cv.utility.FirebaseHandler;

public class RepositoryCv implements Repository {

  private static ArrayList<Section> sections = new ArrayList<>();
  private PersonalInfo personalInfo;
  private ModelChangedCallback modelChangedCallback;
  private FirebaseDatabase firebaseDatabase;
  private DatabaseReference detailDatabaseReference;
  private DatabaseReference sectionDatabaseReference;
  private DatabaseReference personalInfoDatabaseReference;
  private ValueEventListener detailEventListener;
  private ValueEventListener overviewEventListener;
  private ValueEventListener personalInfoEventListener;
  private String provider;
  private static List<Detail> details = new ArrayList<>();
  private static RepositoryCv repository = null;

  private RepositoryCv() {
  }

  public static RepositoryCv getRepository() {
    if (repository == null) {
      synchronized (RepositoryCv.class) {
        if (repository == null) {
          repository = new RepositoryCv();
        }
      }
    }
    return repository;
  }

  public void removeListeners() {
    sectionDatabaseReference.removeEventListener(overviewEventListener);
    detailDatabaseReference.removeEventListener(detailEventListener);
    personalInfoDatabaseReference.removeEventListener(personalInfoEventListener);
  }

  public void addCallback(ModelChangedCallback modelChangedCallback) {
    this.modelChangedCallback = modelChangedCallback;
  }

  public void removeCallback() {
    this.modelChangedCallback = null;
  }

  public void initializeData() {

    firebaseDatabase = FirebaseHandler.getDatabase();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    if (firebaseUser != null && firebaseUser.isAnonymous()) {
      provider = "anonymous";
    } else if (firebaseUser != null && !firebaseUser.isAnonymous()) {
      provider = firebaseUser.getUid();
    }

    sectionDatabaseReference =
        firebaseDatabase.getReference().child("users").child(provider).child("sections");
    overviewEventListener = new ValueEventListener() {
      @Override public void onDataChange(DataSnapshot dataSnapshot) {
        sections.clear();
        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
          sections.add(childSnapshot.getValue(Section.class));
        }
        if (modelChangedCallback != null) {
          modelChangedCallback.updateModel();
        }
      }

      @Override public void onCancelled(DatabaseError databaseError) {

      }
    };
    sectionDatabaseReference.addValueEventListener(overviewEventListener);
    detailDatabaseReference =
        firebaseDatabase.getReference().child("users").child(provider).child("details");
    detailEventListener = new ValueEventListener() {
      @Override public void onDataChange(DataSnapshot dataSnapshot) {
        details.clear();
        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
          Detail detail = childSnapshot.getValue(Detail.class);
          details.add(detail);
        }
        if (modelChangedCallback != null) {
          modelChangedCallback.updateModel();
        }
      }

      @Override public void onCancelled(DatabaseError databaseError) {

      }
    };

    detailDatabaseReference.addValueEventListener(detailEventListener);
    personalInfoDatabaseReference =
        firebaseDatabase.getReference().child("users").child(provider).child("personalInfo");
    personalInfoEventListener = new ValueEventListener() {
      @Override public void onDataChange(DataSnapshot dataSnapshot) {
        personalInfo = dataSnapshot.getValue(PersonalInfo.class);
        if (modelChangedCallback != null) {
          modelChangedCallback.updateModel();
        }
      }

      @Override public void onCancelled(DatabaseError databaseError) {

      }
    };
    personalInfoDatabaseReference.addValueEventListener(personalInfoEventListener);
  }

  @Override public String getEmail() {
    return personalInfo.getEmail();
  }

  @Override public String getWebPage() {
    return personalInfo.getWebPage();
  }

  @Override public String getPhoneNumber() {
    return personalInfo.getPhoneNumber();
  }

  @Override public ArrayList<Section> getSections() {
    return sections;
  }

  @Override public Section getSectionById(int id) {
    return sections.get(id);
  }

  @Override public Detail getDetailsById(int id) {
    return details.get(id);
  }

  public String getCircularImageUrl() {
    if (personalInfo != null) {
      return personalInfo.getCircularImageUrl();
    } else {
      return null;
    }
  }

  @Override public String getBackgroundImage() {
    if (personalInfo != null) {
      return personalInfo.getHeaderBackgroundImage();
    } else {
      return null;
    }
  }

  public String getName() {
    if (personalInfo != null) {
      return personalInfo.getName();
    } else {
      return null;
    }
  }
}
