# Riichi Trainer - projekt na zaliczenie z Androida

Prosta aplikacja quizowa testująca znajomość gry Riichi Mahjong. Sam rdzeń aplikacji to sprawdzanie odpowiedzi quizu więc w razie potrzeby można zaadaptować tą grę do innej tematyki.

Aplikacja ma trzy kategorie pytań z gry - każda z nich ćwiczy przydatną umiejętość co pozwala zwiększyć swoje szanse wygranych.

Aplikacja nie wymaga do instalacji żadnych bibliotek ani połączenia z internetem w trakcie grania w nią.

Ta aplikacja to projekt gradle dla kodu Java i wymaga SDK Androdia lokalnie zainstalowanego do uruchomienia i zbudowania projektu.

## Glowne funkcjonalności

- Wiele aktywności: MainActivity, SettingsActivity, QuizModeActivity, QuizActivity, ResultActivity, StatsActivity, ResourcesActivity.
- Implicit intents: ACTION_VIEW dla linkow wiki, ACTION_SEND dla udostepniania wynikow
- SharedPreferences dla zapisywania nicku, ustawien i statystyk
- Obsluga lifecycle: dla QuizActivity (tylko tam jest realnie potrzeba)
- Rysowanie custom elementow (Canvas) w klasach TimerPieView (timer, wycinek kola) i TileStripView (rysuje obramowke kafelkow mahjong + nazwe kafelka)
- Aktualizacje timera bazujaca na AsyncTask
- Proste animacje przy udzieleniu odpowiedzi w quizie
- Opcjonalna obsluga dzwieku dla efektow oraz muzyki w tle (muzyke trzeba by bylo znalezc)
- Obsługa wibracji (oraz ustawienie odpowiedniej zgody - permissions) przy odpowiadaniu na pytania

## Wazne obserwacje

- Aplikacja nie implementuje pełnego AI ani nie sprawdza ręcznie mechanik, scoringu ani stanu ręki. Sprawdzanie pytania odbywa się na sprawdzeniu która z 4 odpowiedzi jest zaznaczona jako poprawna
- Zbiór przygotowanych pytań znajduję się (hardcoded) w klasie QuestionBank.java
- Aplikacja quizowa nie obsluguje rysowania tekstury kafelków mahjong, gdyz wymagaloby to wczytania texture atlas wszystkich 144 kafelkow w grze co zajmuje duzo czasu. Z tego powodu jest rysowana tylko ramka oraz tekst w srodku sygnalizujący konkretny kafelek
- Aplikacja pewnie wspiera uruchamianie muzyki w tle ale nie mam zadnej sensownej probki ktora by pasowala aby to przetestowac realnie
