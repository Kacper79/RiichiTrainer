# Riichi Trainer - projekt na zajecia z Androida

Prosta aplikacja quizowa z tematyki gry Riichi Mahjong. Rdzeniem projektu jest proste sprawdzanie odpowiedzi w quizie. 

Aplikacja ma trzy kategorie pytań z gry - każda z nich ćwiczy przydatną umiejętość co pozwala zwiększyć swoje szanse wygranych.

Aplikacja nie wymaga do instalacji żadnych bibliotek ani połączenia z internetem w trakcie grania w nią.

Ta aplikacja to projekt gradle dla kodu Java i wymaga SDK Androdia lokalnie zainstalowanego do uruchomienia i zbudowania projektu.

## Glowne funkcjonalności

- Wiele aktywności: MainActivity, SettingsActivity, QuizModeActivity, QuizActivity, ResultActivity, StatsActivity, ResourcesActivity.
- Implicit intents: ACTION_VIEW dla linkow wiki, ACTION_SEND dla udostepniania wynikow
- SharedPreferences dla zapisywania nicku, ustawien i statystyk
- Obsluga lifecycle: dla QuizActivity (tylko tam jest realnie potrzeba)
- Rysowanie custom elementow w klasach TimerPieView (timer, wycinek kola) i TileStripView (rysuje obramowke kafelkow mahjong)
- Timer bazowany na AsyncTask
- Proste animacje przy udzieleniu odpowiedzi w quizie
- Opcjonalna obsluga dzwieku dla efektow oraz muzyki w tle
- Obsługa wibracji (oraz ustawienie odpowiedniej zgodsy - permissions) przy odpowiadaniu na pytania

## Wazne obserwacje

- Aplikacja nie używa pełnego AI ani nie sprawdza ręcznie mechanik, scoringu ani stanu ręki. Sprawdzanie pytania odbywa się na sprawdzeniu która z 4 odpowiedzi jest zaznaczona jako poprawna
- Zbiór przygotowanych pytań znajduję się (hardcoded) w klasie QuestionBank.java
- Aplikacja quizowa nie obsluguje rysowania tekstury kafelków mahjong, gdyz wymagaloby to wczytania texture atlas wszystkich 144 kafelkow w grze co zajmuje duzo czasu. Z tego powodu jest rysowana tylko ramka oraz tekst w srodku sygnalizujący konkretny kafelek
