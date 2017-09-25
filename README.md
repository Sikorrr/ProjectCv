## Aplikacja CV

![Alt Text](https://firebasestorage.googleapis.com/v0/b/sylwia-cv.appspot.com/o/small_splash.gif?alt=media&token=87578890-62ea-4d55-be65-a91a506c561b) 
![Alt Text](https://firebasestorage.googleapis.com/v0/b/sylwia-cv.appspot.com/o/overview_small.gif?alt=media&token=460ab5d7-a436-4bca-ad3d-9ba651070823) 
![Alt Text](https://firebasestorage.googleapis.com/v0/b/sylwia-cv.appspot.com/o/small_shared_element.gif?alt=media&token=c233ec94-4685-4d6b-8e3c-23992dea0276) 

### [Pobierz aplikację](https://drive.google.com/open?id=0B0Kj2C1ceopyYTl4ZUI3ZXVnRFU)


### Spis treści 
---
1. [Wstęp](#wstep) 
2. [Opis](#opis) 
3. [Budowanie aplikacji](#budowanie-aplikacji)



### Wstęp
---
Jest to mój pierwszy samodzielny projekt, którego celem było poszerzenie wiedzy na temat programowania na platformę Android oraz wykorzystanie w praktyce swoich umiejętności bazujących na zrealizowanych kursach:
* [Android for Beginners](https://www.udacity.com/course/android-basics-user-interface--ud834)
* [Developing Android Apps](https://www.udacity.com/course/new-android-fundamentals--ud851)
* [Intro to Java Programming](https://www.udacity.com/course/java-programming-basics--ud282)

##### Merge requesty mile widziane! 

### Opis
---


Aplikacja przedstawia CV w formie mobilnej, w której dane udostępniane są po uwierzytelnieniu użytkownika.

Dane przechowywane są za pomocą Firebase Realtime Database, umożliwiającej dostęp do danych w przypadku utraty połączenia internetowego.

Wykorzystana w aplikacji Firebase'owa autoryzacja umożliwia uwierzytelnienie użytkownika w oparciu o e-mail i hasło lub uruchomienie aplikacji za pomocą anonimowego konta, bez konieczności logowania się.

 __Zastosowane wzorce:__

 * MVP
 * Singleton
 * Repository



__Biblioteki:__

 * LeakCanary - wykrywanie wycieków pamięci.
 * Picasso - pobieranie i przechowywanie obrazów.

__Plany na rozwój aplikacji:__

 * dodawanie nowego konta i tworzenie CV z poziomu aplikacji
 * RxJava
 * testy

### Budowanie aplikacji
---
1. Pobierz repozytorium.
2. W *build.gradle* aplikacji dodaj własny klucz lub wykomentuj dany fragment kodu


```javascript
 signingConfigs {
    key {
      keyAlias keystoreProperties['keyAlias']
      keyPassword keystoreProperties['keyPassword']
      storeFile file(keystoreProperties['storeFile'])
      storePassword keystoreProperties['storePassword']
    }
  }
```
oraz

```javascript
  signingConfig signingConfigs.key
```
3. Wejdź na https://firebase.google.com/
4. Przejdź do konsoli
5. Utwórz projekt
6. Dodaj Firebase do swojej aplikacji dla systemu Android 
7. Podaj nazwę paczki *org&#46;bitbucket&#46;sikorrr&#46;cv*
8. Postępuj zgodnie z instrukcjami, pobierz plik konfiguracyjny google-service.json i zapisz w głównym katalogu modułu swojej aplikacji .
9. W menu po lewej stronie wybierz *Authentication* i wybierz metodę logowania.
10. Włącz konto anonimowe.
11. W menu po lewej stronie wybierz *Database*
12. Importuj dane z [przykładowe dane](test.json)
13. Wybierz *reguły*
14. Zdefiniuj następujące reguły: 

```javascript
{
  "rules": {
    "users": {
      "anonymous": {
         ".read": true,
        ".write": false
      }
    }
  }
}
```
15. Możesz korzystać z aplikacji bez konieczności logowania się. 

### Tworzenie nowego konta

1. Wejdż w *Authentication/ metody logowania* i odblokuj logowanie za pomocą E-mail/hasło.
2. Wybierz pole *Użytkownicy* oraz dodaj własnego użytkownika.
3.  Pobierz [przykładowe dane](test2.json) i **zamień nagłówek "test" na UID stworzonego użytkownika**
4.  Dodaj reguły:
```javascript
{
  "rules": {
    "users": {
      "anonymous": {
         ".read": true,
        ".write": false
      },
           "$uid": {
        ".read": "$uid === auth.uid",
        ".write": false
          }
        }
      }
    }
```


