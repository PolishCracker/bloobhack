# bloobhack - Educational Minecraft Client

**bloobhack** to edukacyjny klient Minecraft dla Fabric 1.21.4, stworzony w celach naukowych do analizy mechanik gry i systemów antycheatów.

## Informacje o Projekcie

- **Wersja**: 1.0.0
- **Minecraft**: 1.21.4
- **Loader**: Fabric
- **Autor**: Pogracz
- **Licencja**: MIT

## Cechy

### Moduły Bojowe
- **Criticals** - Wymusza uderzenia krytyczne z bypassem Grim v3
- **AutoTrap** - Automatycznie stawia obsydian wokół przeciwników
- **AutoWeb** - Automatycznie stawia pajęczyny do spowolnienia wrogów
- **Aura** - Podstawowa aura ataku
- **KillAura** - Zaawansowana aura ataku z targetowaniem
- **Reach** - Zwiększa zasięg ataku

### Moduły Ruchu
- **Speed** - Zwiększa prędkość poruszania
- **Flight** - Umożliwia latanie
- **NoFall** - Zapobiega obrażeniom z upadku

### Moduły Renderowania
- **ESP** - Rysuje pudełka wokół wrogów
- **Tracers** - Rysuje linie do wrogów

### Moduły Misc
- **AutoRespawn** - Automatycznie respawnuje po śmierci
- **NoRender** - Wyłącza renderowanie wybranych elementów

## Bypass Grim v3

bloobhack zawiera zaawansowane bypassy dla antycheata Grim v3:

### Criticals
- Wysyła pakiety pozycji z minimalnym przesunięciem na osi Y (0.000001)
- Unika wykrycia przez sprawdzanie obrażeń z upadku
- Obsługuje wiele trybów: Packet, Jump, Strict, Grim v3

### AutoTrap / AutoWeb
- Implementuje Silent Rotations do uniknięcia wykrycia rotacji
- Sprawdza widoczność bloków (Raytrace)
- Dodaje opóźnienia między stawianiem bloków
- Waliduje umieszczenie bloków przed wysłaniem pakietów

## Struktura Projektu

```
bloobhack-fabric/
├── src/main/java/com/bloobhack/
│   ├── BloobhackClient.java          # Punkt wejścia
│   ├── core/
│   │   ├── EventBus.java             # System eventów
│   │   ├── Module.java               # Klasa bazowa modułów
│   │   ├── Setting.java              # System ustawień
│   │   └── ModuleManager.java        # Zarządzanie modułami
│   ├── features/
│   │   ├── combat/                   # Moduły bojowe
│   │   ├── movement/                 # Moduły ruchu
│   │   ├── render/                   # Moduły renderowania
│   │   └── misc/                     # Moduły misc
│   ├── mixin/                        # Klasy Mixin
│   ├── utility/                      # Klasy narzędziowe
│   └── gui/                          # Interfejs graficzny
├── src/main/resources/
│   ├── fabric.mod.json               # Metadane modu
│   └── bloobhack.mixins.json         # Konfiguracja Mixinów
├── build.gradle                      # Konfiguracja Gradle
├── gradle.properties                 # Właściwości Gradle
└── settings.gradle                   # Ustawienia Gradle
```

## Kompilacja

```bash
cd bloobhack-fabric
./gradlew build
```

Plik JAR będzie dostępny w `build/libs/bloobhack-1.0.0.jar`

## Instalacja

1. Zainstaluj Fabric Loader dla Minecraft 1.21.4
2. Umieść plik JAR w folderze `mods/`
3. Uruchom grę

## Kompatybilność

- ✅ Vanilla Fabric
- ✅ Feather Client
- ✅ Inne klienty Fabric

## Edukacyjne Cele

Ten projekt został stworzony w celach edukacyjnych do:
- Zrozumienia mechanik Minecraft
- Analizy systemów antycheatów
- Nauki tworzenia modów Fabric
- Badania technik bypassu antycheatów

## Ostrzeżenia

- Używanie tego klienta na serwerach bez zgody właściciela jest zakazane
- Projekt jest przeznaczony wyłącznie do celów edukacyjnych
- Autor nie ponosi odpowiedzialności za niewłaściwe użycie

## Licencja

MIT License - patrz LICENSE

## Kontakt

Autor: Pogracz
