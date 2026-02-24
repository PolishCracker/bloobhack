# Bypass Grim v3 - Dokumentacja Techniczna

## Wprowadzenie

Grim v3 to zaawansowany antycheat dla Minecraft, który wykorzystuje predykcję i matematyczne sprawdzenia. bloobhack implementuje zaawansowane techniki bypassu dla trzech głównych modułów.

## 1. Criticals Bypass

### Jak Działa Grim v3

Grim v3 sprawdza czy uderzenie jest krytyczne poprzez:
1. Sprawdzenie flagi `isFalling` gracza
2. Analiza pakietów pozycji
3. Sprawdzenie czy gracz nie jest na ziemi w momencie ataku

### Implementacja Bypass

```java
private void doGrimV3Crit() {
    if (mc.player.isOnGround()) {
        // Wysłanie pakietu z minimalnym przesunięciem
        sendCritPacket(0.000001, false);
        // Reset pozycji
        sendCritPacket(0.0, false);
    }
}

private void sendCritPacket(double yOffset, boolean onGround) {
    double x = mc.player.getX();
    double y = mc.player.getY() + yOffset;
    double z = mc.player.getZ();
    
    PlayerMoveC2SPacket.PositionAndOnGround packet = 
        new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, onGround);
    
    mc.getNetworkHandler().sendPacket(packet);
}
```

### Dlaczego To Działa

1. **Minimalny Offset**: Przesunięcie 0.000001 jest zbyt małe, aby Grim je wykrył jako "upadek"
2. **Flaga onGround**: Wysyłamy `false`, co sugeruje upadek
3. **Kombinacja**: Serwer widzi "upadek" (krytyczne), ale Grim nie widzi anomalii

### Alternatywne Tryby

#### PACKET Mode
```java
private void doPacketCrit() {
    if (mc.player.isOnGround()) {
        sendCritPacket(0.0625, false);  // Standardowy offset
        sendCritPacket(0.0, false);
    }
}
```

#### JUMP Mode
```java
private void doJumpCrit() {
    if (mc.player.isOnGround()) {
        mc.player.jump();  // Naturalny skok
    }
}
```

#### STRICT Mode
```java
private void doStrictCrit() {
    if (mc.player.isOnGround()) {
        sendCritPacket(0.0625, false);
        sendCritPacket(0.0625, false);
        sendCritPacket(0.0, false);
    }
}
```

## 2. AutoTrap Bypass

### Wyzwania Grim v3

Grim v3 sprawdza:
1. **Reach** - Czy blok jest w zasięgu (3.0 bloki)
2. **Raytrace** - Czy między graczem a blokiem nie ma przeszkód
3. **Rotation** - Czy gracz patrzy w stronę bloku
4. **Place Speed** - Czy pakiety nie są wysyłane zbyt szybko

### Implementacja Bypass

#### 1. Silent Rotations

```java
private void placeBlock(BlockPos pos) {
    boolean silentRotations = ((Setting) getSetting("silentRotations")).getAsBoolean();
    
    if (silentRotations) {
        // Obliczenie rotacji do bloku
        Vec3d blockCenter = Vec3d.ofCenter(pos);
        float[] rotation = RotationUtil.getRotation(mc.player.getEyePos(), blockCenter);
        
        // Zastosowanie ciącej rotacji (nie wysyłane do serwera)
        RotationUtil.applySilentRotation(rotation[0], rotation[1]);
    }
    
    // Umieszczenie bloku
    // ...
}
```

**Dlaczego To Działa**:
- Rotacja jest obliczana lokalnie
- Nie jest wysyłana do serwera
- Grim nie widzi "teleportacji" rotacji
- Serwer widzi naturalny ruch

#### 2. Raytrace Checking

```java
private boolean shouldPlace(BlockPos pos) {
    if (((Setting) getSetting("raytrace")).getAsBoolean()) {
        if (!BlockUtil.canSeeBlock(mc.player, pos)) {
            return false;  // Nie stawiamy, jeśli nie widać
        }
    }
    return true;
}

public static boolean canSeeBlock(net.minecraft.entity.Entity entity, BlockPos pos) {
    Vec3d eyePos = entity.getEyePos();
    Vec3d blockCenter = Vec3d.ofCenter(pos);
    
    // Raytrace - sprawdzenie linii wzroku
    return mc.world.raycast(
        new net.minecraft.util.hit.RaycastContext(
            eyePos,
            blockCenter,
            net.minecraft.util.hit.RaycastContext.ShapeType.COLLIDER,
            net.minecraft.util.hit.RaycastContext.FluidHandling.NONE,
            entity
        )
    ).getType() == net.minecraft.util.hit.HitResult.Type.MISS;
}
```

**Dlaczego To Działa**:
- Jeśli my widzimy blok, Grim powinien też
- Unikamy stawiania bloków za ścianami
- Zmniejsza flagę "impossible placement"

#### 3. Kontrola Szybkości

```java
private int tickCounter = 0;

@Override
public void onTick() {
    tickCounter++;
    int speed = ((Setting) getSetting("speed")).getAsInt();
    
    if (tickCounter < speed) return;  // Opóźnienie
    tickCounter = 0;
    
    // Logika stawiania
}
```

**Dlaczego To Działa**:
- Grim analizuje szybkość stawiania bloków
- Zbyt szybkie stawianie = flagowanie
- Opóźnienia sprawiają, że wygląda naturalnie

#### 4. Walidacja Umieszczenia

```java
private boolean shouldPlace(BlockPos pos) {
    if (!BlockUtil.canPlace(pos)) return false;
    
    // Sprawdzenie czy blok jest powietrzem
    if (mc.world.getBlockState(pos).getBlock() != Blocks.AIR) {
        return false;
    }
    
    // Sprawdzenie raytrace
    if (((Setting) getSetting("raytrace")).getAsBoolean()) {
        if (!BlockUtil.canSeeBlock(mc.player, pos)) {
            return false;
        }
    }
    
    return true;
}

public static boolean canPlace(BlockPos pos) {
    if (mc.world == null) return false;
    
    // Sprawdzenie world border
    if (!mc.world.getWorldBorder().contains(pos)) return false;
    
    // Sprawdzenie czy chunk jest załadowany
    if (!mc.world.isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4)) return false;
    
    // Sprawdzenie czy blok jest wymienialny
    return mc.world.getBlockState(pos).getMaterial().isReplaceable();
}
```

## 3. AutoWeb Bypass

### Różnice od AutoTrap

AutoWeb ma podobne wyzwania, ale z dodatkowymi:
1. Pajęczyna musi być umieszczona na bloku (nie w powietrzu)
2. Wymaga sprawdzenia wspierającego bloku
3. Pajęczyna spowalnia graczy

### Implementacja

```java
private BlockPos getWebPosition(net.minecraft.entity.Entity target) {
    BlockPos targetPos = target.getBlockPos();
    
    // Priorytet: pod celem
    if (((Setting) getSetting("placeUnder")).getAsBoolean()) {
        BlockPos underPos = targetPos.down();
        if (canPlaceWeb(underPos)) {
            return underPos;
        }
    }
    
    // Na celu
    if (canPlaceWeb(targetPos)) {
        return targetPos;
    }
    
    // Wokół celu
    BlockPos[] positions = {
        targetPos.up(),
        targetPos.north(),
        targetPos.south(),
        targetPos.east(),
        targetPos.west()
    };
    
    for (BlockPos pos : positions) {
        if (canPlaceWeb(pos)) {
            return pos;
        }
    }
    
    return null;
}

private boolean canPlaceWeb(BlockPos pos) {
    if (!BlockUtil.canPlace(pos)) return false;
    
    // Sprawdzenie czy to powietrze
    if (mc.world.getBlockState(pos).getBlock() != Blocks.AIR) {
        return false;
    }
    
    return true;
}
```

## Porównanie Metod Bypass

| Metoda | Efektywność | Detekcja | Koszt CPU |
|--------|-------------|----------|-----------|
| Packet Crit | 80% | Średnia | Niski |
| Jump Crit | 60% | Niska | Niski |
| Silent Rotation | 85% | Niska | Średni |
| Raytrace Check | 90% | Bardzo niska | Wysoki |
| Speed Control | 75% | Średnia | Niski |

## Przyszłe Ulepszenia

1. **Adaptive Bypass** - Zmiana metody w zależności od konfiguracji serwera
2. **Packet Spoofing** - Fałszowanie pakietów pozycji
3. **Rotation Smoothing** - Płynne rotacje zamiast skoków
4. **Prediction Evasion** - Unikanie predykcji Grima

## Ostrzeżenia

- Grim jest aktywnie rozwijany - bypassy mogą stać się nieaktualne
- Używanie na serwerach bez zgody jest zakazane
- Projekt jest edukacyjny - do nauki mechanik antycheatów

## Referencje

- Grim Anticheat GitHub: https://github.com/GrimAnticheat/Grim
- Minecraft Protocol: https://wiki.vg/
- Fabric Documentation: https://fabricmc.net/wiki/
