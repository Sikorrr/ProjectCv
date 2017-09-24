package org.bitbucket.sikorrr.cv.utility;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHandler {

  private static FirebaseDatabase firebaseDatabase;

  public static FirebaseDatabase getDatabase() {
    if (firebaseDatabase == null) {

      firebaseDatabase = FirebaseDatabase.getInstance();
      firebaseDatabase.setPersistenceEnabled(true);
    }
    return firebaseDatabase;
  }
}