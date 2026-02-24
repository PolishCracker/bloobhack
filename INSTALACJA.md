# Instrukcja Instalacji bloobhack

## Wymagania Systemowe

- **Minecraft**: 1.21.4 (Java Edition)
- **Fabric Loader**: 0.16.9 lub nowszy
- **Java**: 21 lub nowsza
- **RAM**: Minimum 2GB dla Minecraft

## Krok 1: Instalacja Fabric Loader

### Metoda 1: Fabric Installer (Zalecane)

1. Pobierz Fabric Installer z: https://fabricmc.net/use/installer/
2. Uruchom instalator
3. Wybierz wersję: **1.21.4**
4. Kliknij "Install"
5. Wybierz folder instalacji Minecraft

### Metoda 2: Ręczna Instalacja

1. Pobierz `fabric-loader-0.16.9-1.21.4.jar` z: https://maven.fabricmc.net/
2. Umieść w folderze `.minecraft/mods/`
3. Uruchom grę z profilem Fabric

## Krok 2: Pobranie bloobhack

### Opcja A: Precompiled JAR (Jeśli dostępny)

1. Pobierz `bloobhack-1.0.0.jar`
2. Umieść w folderze `.minecraft/mods/`
3. Uruchom grę

### Opcja B: Kompilacja ze Źródła

1. Pobierz archiwum źródłowe: `bloobhack-1.0.0-source.tar.gz`
2. Rozpakuj: `tar -xzf bloobhack-1.0.0-source.tar.gz`
3. Przejdź do folderu: `cd bloobhack-fabric`
4. Skompiluj: `./gradlew build`
5. Plik JAR będzie w: `build/libs/bloobhack-1.0.0.jar`
6. Umieść w `.minecraft/mods/`

## Krok 3: Uruchomienie Gry

1. Otwórz Minecraft Launcher
2. Wybierz profil **Fabric** z wersją **1.21.4**
3. Kliknij **Play**
4. Czekaj na załadowanie modów

## Weryfikacja Instalacji

Po uruchomieniu gry:
1. Wejdź do świata
2. Otwórz chat (T)
3. Powinieneś zobaczyć wiadomość: `[bloobhack] Client loaded successfully`

## Lokalizacja Folderu .minecraft

### Windows
```
%APPDATA%\.minecraft
```

### macOS
```
~/Library/Application Support/minecraft
```

### Linux
```
~/.minecraft
```

## Rozwiązywanie Problemów

### Problem: "bloobhack nie pojawia się w modach"

**Rozwiązanie**:
1. Sprawdź czy Fabric Loader jest zainstalowany
2. Sprawdź czy plik JAR jest w `.minecraft/mods/`
3. Sprawdź czy nazwa pliku to `bloobhack-1.0.0.jar`
4. Usuń folder `.minecraft/versions/` i spróbuj ponownie

### Problem: "Crash przy starcie gry"

**Rozwiązanie**:
1. Sprawdź czy masz Java 21: `java -version`
2. Sprawdź czy masz Fabric 0.16.9
3. Sprawdź logi w `.minecraft/logs/latest.log`
4. Spróbuj usunąć inne mody

### Problem: "Mixin errors"

**Rozwiązanie**:
1. Upewnij się że Fabric Loader jest poprawnie zainstalowany
2. Sprawdź czy `fabric-loader-*.jar` jest w `.minecraft/mods/`
3. Usuń cache: `rm -rf .minecraft/versions/`

### Problem: "Gradle build fails"

**Rozwiązanie**:
1. Sprawdź Java: `java -version` (powinna być 21+)
2. Usuń cache: `rm -rf ~/.gradle/`
3. Spróbuj: `./gradlew clean build`

## Kompatybilność

### Kompatybilne Mody
- Fabric API
- Sodium
- Lithium
- Starlight
- Inne mody Fabric

### Potencjalnie Niezgodne
- Inne klienty (mogą konfliktować)
- Mody z podobnymi Mixinami

## Bezpieczeństwo

- **Antywirus**: Niektóre antywirus mogą flagować mod jako zagrożenie (fałszywy alarm)
- **Serwery**: Używanie na serwerach bez zgody jest zakazane
- **Prywatność**: Mod nie zbiera danych

## Odinstalacja

1. Usuń `bloobhack-1.0.0.jar` z `.minecraft/mods/`
2. Uruchom grę
3. Mod zostanie usunięty

## Wsparcie

Jeśli masz problemy:
1. Sprawdź logi: `.minecraft/logs/latest.log`
2. Przeczytaj KOMPILACJA.md
3. Sprawdź ARCHITEKTURA.md
4. Skontaktuj się z autorem

## Następne Kroki

Po instalacji:
1. Przeczytaj README.md
2. Zapoznaj się z dostępnymi modułami
3. Dostosuj ustawienia do swoich potrzeb
4. Testuj na prywatnych serwerach

## Licencja

bloobhack jest objęty licencją MIT. Patrz LICENSE dla szczegółów.
