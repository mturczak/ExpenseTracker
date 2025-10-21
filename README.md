# 💰 ExpenseTracker

Nowoczesna, minimalistyczna aplikacja mobilna do śledzenia wydatków osobistych, stworzona w Android Studio z wykorzystaniem Kotlin i Material Design 3.

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white)
![Material Design](https://img.shields.io/badge/Material%20Design-757575?style=for-the-badge&logo=material-design&logoColor=white)

## 📸 Screenshots

<p align="center">
  <img src="Screenshots/img.png" width="200" alt="Ekran główny"/>
  <img src="Screenshots/img_1.png" width="200" alt="Lista wydatków"/>
  <img src="Screenshots/img_2.png" width="200" alt="Dodawanie wydatku"/>
  <img src="Screenshots/img_3.png" width="200" alt="Statystyki"/>
</p>

## 📱 Funkcjonalności

- ✅ **Zarządzanie wydatkami** - Dodawanie, edycja i usuwanie wydatków
- 📊 **Statystyki** - Wizualizacja wydatków według kategorii z interaktywnymi wykresami
- 🎨 **8 kategorii wydatków** - Jedzenie, Transport, Rozrywka, Zakupy, Rachunki, Zdrowie, Edukacja, Inne
- 💾 **Lokalna baza danych** - Room Database zapewnia trwałe przechowywanie danych
- 🌙 **Nowoczesny design** - Minimalistyczny interfejs z Material Design 3
- 📱 **Responsywny UI** - Płynne animacje i intuicyjna nawigacja

## 🛠️ Technologie

### Architektura
- **MVVM** (Model-View-ViewModel)
- **Repository Pattern**
- **Single Activity Architecture**

### Biblioteki i narzędzia
- **Kotlin** - Język programowania
- **Android Jetpack:**
  - Room - Lokalna baza danych
  - LiveData - Reaktywne zarządzanie danymi
  - ViewModel - Przechowywanie stanu UI
  - Navigation Component - Nawigacja między fragmentami
  - Data Binding - Powiązanie UI z danymi
- **Material Design 3** - Komponenty UI
- **MPAndroidChart** - Wykresy i wizualizacje
- **Coroutines** - Asynchroniczne operacje

## 📂 Struktura projektu

```
app/
├── src/main/
│   ├── java/com/example/expensetracker/
│   │   ├── data/
│   │   │   ├── database/      # Room Database
│   │   │   ├── model/         # Encje danych
│   │   │   └── repository/    # Warstwa dostępu do danych
│   │   ├── ui/
│   │   │   ├── adapter/       # RecyclerView adaptery
│   │   │   ├── fragment/      # Fragmenty UI
│   │   │   └── viewmodel/     # ViewModels
│   │   ├── utils/             # Klasy pomocnicze
│   │   └── MainActivity.kt    # Główna aktywność
│   └── res/
│       ├── layout/            # Pliki XML layoutów
│       ├── values/            # Kolory, style, stringi
│       └── drawable/          # Zasoby graficzne
```

## 🚀 Instalacja i uruchomienie

### Wymagania
- Android Studio Hedgehog (2023.1.1) lub nowszy
- Android SDK 24 lub wyższy
- Kotlin 1.9+

### Kroki instalacji

1. **Sklonuj repozytorium**
   ```bash
   git clone https://github.com/mturczak/ExpenseTracker.git
   cd ExpenseTracker
   ```

2. **Otwórz projekt w Android Studio**
   - File → Open → Wybierz folder projektu

3. **Synchronizuj projekt z plikami Gradle**
   - Android Studio automatycznie pobierze wymagane zależności

4. **Uruchom aplikację**
   - Podłącz urządzenie Android lub uruchom emulator
   - Kliknij "Run" (▶️) w Android Studio

## 💡 Jak używać

### Dodawanie wydatku
1. Kliknij przycisk "Dodaj wydatek" (FAB)
2. Wprowadź kwotę, wybierz kategorię i dodaj opis
3. Wybierz datę wydatku
4. Kliknij "Zapisz"

### Przeglądanie statystyk
1. Przejdź do zakładki "Statystyki"
2. Zobacz całkowite wydatki i średnią
3. Przeglądaj wykres kołowy według kategorii
4. Sprawdź szczegółową listę wydatków w każdej kategorii

### Edycja/Usuwanie wydatku
- **Edycja:** Kliknij na wydatek na liście
- **Usuwanie (sposób 1):** Przesuń wydatek w lewo ← i kliknij "COFNIJ" jeśli zmieniłeś zdanie
- **Usuwanie (sposób 2):** Przytrzymaj wydatek i potwierdź usunięcie w dialogu

## 🎨 Design

Aplikacja wykorzystuje nowoczesny, minimalistyczny design z:
- Zaokrąglonymi rogami (16-20dp)
- Subtelną paletą kolorów
- Emoji jako ikony kategorii
- Czystymi kartami i spacingiem
- Intuicyjną nawigacją dolną

### Paleta kolorów
- **Primary:** `#1A1A2E` - Ciemny niebieski
- **Accent:** `#E94560` - Koralowy czerwony
- **Background:** `#F8F9FA` - Jasny szary
- **Surface:** `#FFFFFF` - Biały

## 📊 Funkcje do dodania w przyszłości

- [ ] Eksport danych do CSV/PDF
- [ ] Filtry i wyszukiwanie wydatków
- [ ] Budżety miesięczne z alertami
- [ ] Tryb ciemny (Dark Mode)
- [ ] Synchronizacja w chmurze
- [ ] Wielowalutowość
- [ ] Przypomnienia o regularnych wydatkach
- [ ] Backup i przywracanie danych

## 👤 Autor

**Miłosz Turczak**

- GitHub: [@mturczak](https://github.com/mturczak)
- Email: [kontakt dostępny przez GitHub]

## 📄 Licencja

Ten projekt jest dostępny na licencji MIT - zobacz plik [LICENSE](LICENSE) dla szczegółów.

## 🤝 Wkład w projekt

Zapraszam do zgłaszania Issues i Pull Requestów! Każdy wkład jest mile widziany.

1. Fork projektu
2. Stwórz branch z nową funkcją (`git checkout -b feature/NowaFunkcja`)
3. Commit zmian (`git commit -m 'Dodaj nową funkcję'`)
4. Push do brancha (`git push origin feature/NowaFunkcja`)
5. Otwórz Pull Request

## 🙏 Podziękowania

- Material Design 3 za komponenty UI
- MPAndroidChart za bibliotekę wykresów
- Społeczność Android Developers za wsparcie

---

⭐ Jeśli podoba Ci się ten projekt, zostaw gwiazdkę na GitHub!
