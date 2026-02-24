# Architektura bloobhack

## Przegląd Systemu

bloobhack jest zbudowany na modularnej architekturze, która umożliwia łatwe dodawanie nowych funkcji i bypassów.

```
┌─────────────────────────────────────────────┐
│           BloobhackClient (Entry Point)     │
├─────────────────────────────────────────────┤
│  EventBus  │  ModuleManager  │  GUI         │
├─────────────────────────────────────────────┤
│  Module Base Class (Abstract)               │
├─────────────────────────────────────────────┤
│  Combat  │  Movement  │  Render  │  Misc   │
├─────────────────────────────────────────────┤
│  Utility Classes (BlockUtil, RotationUtil)  │
├─────────────────────────────────────────────┤
│  Mixins (Packet Interception)               │
└─────────────────────────────────────────────┘
```

## Komponenty Główne

### 1. EventBus
**Plik**: `core/EventBus.java`

System eventów oparty na refleksji. Umożliwia modułom subskrybowanie do zdarzeń gry.

**Metody**:
- `subscribe(Class, Object, Method, int)` - Subskrybowanie do eventu
- `unsubscribe(Class, Object)` - Anulowanie subskrypcji
- `post(Object)` - Wysłanie eventu do wszystkich słuchaczy

**Przykład**:
```java
eventBus.subscribe(TickEvent.class, module, method, 0);
```

### 2. Module (Klasa Bazowa)
**Plik**: `core/Module.java`

Abstrakcyjna klasa bazowa dla wszystkich modułów.

**Metody Cyklu Życia**:
- `onEnable()` - Wywoływane przy włączeniu modułu
- `onDisable()` - Wywoływane przy wyłączeniu modułu
- `onTick()` - Wywoływane co tick (20x na sekundę)
- `onRender()` - Wywoływane podczas renderowania

**Kategorie Modułów**:
- COMBAT - Moduły bojowe
- MOVEMENT - Moduły ruchu
- RENDER - Moduły renderowania
- MISC - Pozostałe moduły

### 3. Setting (System Ustawień)
**Plik**: `core/Setting.java`

Obsługuje konfigurację modułów z walidacją zakresu.

**Typy Ustawień**:
- BOOLEAN - Wartości logiczne
- INTEGER - Liczby całkowite
- FLOAT - Liczby zmiennoprzecinkowe
- STRING - Teksty
- ENUM - Wyliczenia

**Przykład**:
```java
Setting speedSetting = new Setting("Speed", 2, SettingType.INTEGER, 1, 10);
```

### 4. ModuleManager
**Plik**: `core/ModuleManager.java`

Zarządza wszystkimi modułami w kliencie.

**Metody**:
- `registerModule(Module)` - Rejestracja modułu
- `getModule(String)` - Pobranie modułu po nazwie
- `getModulesByCategory(Category)` - Pobranie modułów po kategorii
- `getEnabledModules()` - Pobranie włączonych modułów

## Moduły Bojowe (Combat)

### Criticals
**Plik**: `features/combat/Criticals.java`

Wymusza uderzenia krytyczne z bypassem Grim v3.

**Tryby**:
1. **PACKET** - Wysyła pakiety z przesunięciem 0.0625
2. **JUMP** - Wykorzystuje skok gracza
3. **STRICT** - Wysyła wiele pakietów
4. **GRIM_V3** - Bypass Grim v3 z przesunięciem 0.000001

**Implementacja Bypass Grim v3**:
```java
private void doGrimV3Crit() {
    if (mc.player.isOnGround()) {
        sendCritPacket(0.000001, false);  // Minimalny offset
        sendCritPacket(0.0, false);        // Reset
    }
}
```

### AutoTrap
**Plik**: `features/combat/AutoTrap.java`

Automatycznie stawia obsydian wokół przeciwników.

**Cechy Bypass Grim v3**:
- Silent Rotations - Rotacje nie wysyłane do serwera
- Raytrace - Sprawdzenie widoczności bloków
- Opóźnienia - Unikanie wykrycia szybkości
- Walidacja - Sprawdzenie możliwości umieszczenia

**Logika**:
1. Znalezienie celu (gracz w zasięgu)
2. Obliczenie pozycji do umieszczenia
3. Obliczenie rotacji (Silent)
4. Umieszczenie bloku

### AutoWeb
**Plik**: `features/combat/AutoWeb.java`

Automatycznie stawia pajęczyny do spowolnienia wrogów.

**Opcje**:
- `placeUnder` - Stawianie pod celem
- `silentRotations` - Cicha rotacja
- `raytrace` - Sprawdzenie widoczności

## Utility Classes

### BlockUtil
**Plik**: `utility/BlockUtil.java`

Operacje na blokach:
- `canPlace(BlockPos)` - Sprawdzenie możliwości umieszczenia
- `canSeeBlock(Entity, BlockPos)` - Raytrace do bloku
- `getDistance(BlockPos)` - Dystans do bloku

### RotationUtil
**Plik**: `utility/RotationUtil.java`

Kalkulacje rotacji i Silent Rotations:
- `getRotation(Vec3d, Vec3d)` - Obliczenie kątów
- `applySilentRotation(float, float)` - Zastosowanie ciącej rotacji
- `normalizeAngle(float)` - Normalizacja kąta

## Mixiny

Mixiny przechwytują i modyfikują zachowanie gry:

| Mixin | Cel | Zastosowanie |
|-------|-----|--------------|
| MixinPlayerMoveC2SPacket | Pakiety ruchu | Modyfikacja pakietów pozycji |
| MixinClientPlayerEntity | Gracz | Zdarzenia gracza |
| MixinPlayerInteractEntityC2SPacket | Interakcja | Modyfikacja ataków |
| MixinGameRenderer | Rendering | Zdarzenia renderowania |
| MixinScreen | GUI | Zdarzenia interfejsu |

## Przepływ Danych

### Włączenie Modułu
```
User Input
    ↓
Module.toggle()
    ↓
Module.enable()
    ↓
Module.onEnable()
    ↓
Module.onTick() (każdy tick)
```

### Wysłanie Pakietu
```
Module Logic
    ↓
Mixin Intercepts
    ↓
Modify if needed
    ↓
Send to Server
```

## Rozszerzanie Systemu

### Dodawanie Nowego Modułu

1. Utwórz klasę w odpowiedniej kategorii:
```java
public class MyModule extends Module {
    public MyModule() {
        super("MyModule", "Opis", ModuleCategory.COMBAT);
    }
    
    @Override
    public void onTick() {
        // Logika
    }
}
```

2. Zarejestruj w `ModuleManager`:
```java
registerModule(new MyModule());
```

### Dodawanie Nowego Mixina

1. Utwórz klasę Mixin:
```java
@Mixin(SomeClass.class)
public class MixinSomeClass {
    @Inject(method = "someMethod", at = @At("HEAD"))
    private void onSomeMethod(CallbackInfo ci) {
        // Logika
    }
}
```

2. Zarejestruj w `bloobhack.mixins.json`:
```json
"mixins": [
    "MixinSomeClass"
]
```

## Bypass Grim v3 - Szczegóły Techniczne

### Criticals Bypass
Grim v3 sprawdza:
- Czy gracz jest na ziemi
- Czy jest flaga upadku
- Zmianę prędkości

**Bypass**: Wysyłanie pakietów z minimalnym przesunięciem (0.000001) na osi Y, które nie triggeruje flagi upadku, ale jest wystarczające dla serwera do uznania uderzenia za krytyczne.

### AutoTrap/AutoWeb Bypass
Grim v3 sprawdza:
- Reach (zasięg)
- Raytrace (przeszkody)
- Rotation (rotacja gracza)
- Place Speed (szybkość stawiania)

**Bypass**:
1. Silent Rotations - Rotacje obliczane lokalnie, nie wysyłane
2. Raytrace - Sprawdzenie widoczności przed stawianiem
3. Opóźnienia - Dodanie czasu między stawianiami
4. Walidacja - Sprawdzenie wszystkich warunków przed wysłaniem pakietu

## Bezpieczeństwo i Etyka

bloobhack jest projektem edukacyjnym. Użytkownicy są odpowiedzialni za:
- Używanie tylko na serwerach, na których jest dozwolone
- Przestrzeganie regulaminów serwerów
- Nie rozpowszechniania bez zgody autora
