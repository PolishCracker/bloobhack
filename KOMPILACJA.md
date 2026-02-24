# Instrukcja Kompilacji bloobhack

## Wymagania

- **Java**: 21 lub nowsza
- **Gradle**: 8.0 lub nowsza
- **Git**: Do klonowania i zarządzania kodem

## Instalacja Wymagań

### Na Ubuntu/Debian:
```bash
# Java 21
sudo apt-get install openjdk-21-jdk

# Gradle (opcjonalnie, można użyć wrapper)
sudo apt-get install gradle
```

### Na macOS:
```bash
# Java 21
brew install openjdk@21

# Gradle
brew install gradle
```

### Na Windows:
- Pobierz Java 21 z: https://adoptium.net/
- Pobierz Gradle z: https://gradle.org/releases/

## Kompilacja

### Metoda 1: Użycie Gradle Wrapper (Zalecane)

```bash
cd bloobhack-fabric

# Na Linux/macOS
./gradlew build

# Na Windows
gradlew.bat build
```

### Metoda 2: Użycie zainstalowanego Gradle

```bash
cd bloobhack-fabric
gradle build
```

## Wyjście Kompilacji

Po pomyślnej kompilacji, plik JAR będzie dostępny w:
```
build/libs/bloobhack-1.0.0.jar
```

## Instalacja do Minecraft

1. Zainstaluj Fabric Loader dla Minecraft 1.21.4
2. Umieść plik `bloobhack-1.0.0.jar` w folderze `mods/`
3. Uruchom grę

## Rozwiązywanie Problemów

### Problem: "Could not create service of type ScriptPluginFactory"
**Rozwiązanie**: Upewnij się, że używasz Gradle 8.0 lub nowszej
```bash
gradle --version
```

### Problem: "Unsupported class version 65"
**Rozwiązanie**: Upewnij się, że używasz Java 21 lub nowszej
```bash
java -version
```

### Problem: "Could not find fabric-loom"
**Rozwiązanie**: Sprawdź połączenie internetowe i spróbuj ponownie
```bash
gradle clean build
```

## Tworzenie Własnych Modułów

Aby dodać nowy moduł, utwórz plik w odpowiedniej kategorii:

```java
package com.bloobhack.features.combat;

import com.bloobhack.core.Module;

public class MyModule extends Module {
    public MyModule() {
        super("MyModule", "Opis modułu", ModuleCategory.COMBAT);
    }
    
    @Override
    public void onTick() {
        // Logika modułu
    }
}
```

Następnie zarejestruj moduł w `ModuleManager.registerModules()`:
```java
registerModule(new MyModule());
```

## Debugowanie

Aby zobaczyć szczegółowe logi kompilacji:
```bash
gradle build --info
```

## Więcej Informacji

- Dokumentacja Fabric: https://fabricmc.net/wiki/
- Dokumentacja Gradle: https://docs.gradle.org/
