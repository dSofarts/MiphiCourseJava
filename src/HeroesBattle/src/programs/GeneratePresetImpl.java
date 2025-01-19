package HeroesBattle.src.programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.GeneratePreset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

// Упорядочивание списка `unitList` имеет временную сложность O(n * log(n)), где `n` — число юнитов.
// Вложенные циклы:
//   Внешний цикл выполняется для каждого юнита, максимум n итераций.
//   Внутренний цикл добавляет юнитов, пока не достигнут лимит очков `maxPoints` (максимум 11 итераций на юнита из-за ограничения `unitLimit < 11`).

// Лучшая сложность: O(n * log(n) + n).
// Худшая сложность: O(n * log(n) + n * 11 * (FIELD_WIDTH * FIELD_HEIGHT)) ≈ O(n * FIELD_WIDTH * FIELD_HEIGHT), если юнитов много и есть ограничения по размещению.
public class GeneratePresetImpl implements GeneratePreset {

  @Override
  public Army generate(List<Unit> unitList, int maxPoints) {
    System.out.println("Generation process started");

    // Создаем объект армии для компьютера
    Army generatedArmy = new Army();
    List<Unit> finalizedUnits = new ArrayList<>();
    int totalPoints = 0;

    // Размер игрового поля
    final int FIELD_WIDTH = 3;
    final int FIELD_HEIGHT = 21;

    // Множество занятых позиций
    Set<String> usedPositions = new HashSet<>();

    // Сортировка юнитов по показателям "эффективность атаки" и "здоровье к стоимости"
    unitList.sort(Comparator.comparingDouble(
        unit -> -(unit.getBaseAttack() / (double) unit.getCost()
            + unit.getHealth() / (double) unit.getCost())));

    Random randomGenerator = new Random();

    // Перебор каждого юнита из списка
    for (Unit currentUnit : unitList) {
      int unitsPlaced = 0;

      // Добавляем юниты, пока есть доступные очки и не превышен лимит
      while (unitsPlaced < 11 && totalPoints + currentUnit.getCost() <= maxPoints) {
        // Генерация случайной координаты
        int coordX, coordY;
        String positionKey;
        do {
          coordX = randomGenerator.nextInt(FIELD_WIDTH);
          coordY = randomGenerator.nextInt(FIELD_HEIGHT);
          positionKey = coordX + "_" + coordY; // Формат уникального ключа позиции
        } while (usedPositions.contains(positionKey));

        // Добавляем позицию в занятые
        usedPositions.add(positionKey);

        // Создание нового юнита с заданной позицией
        Unit positionedUnit = new Unit(
            currentUnit.getUnitType() + " " + unitsPlaced,
            currentUnit.getUnitType(),
            currentUnit.getHealth(),
            currentUnit.getBaseAttack(),
            currentUnit.getCost(),
            currentUnit.getAttackType(),
            new HashMap<>(currentUnit.getAttackBonuses()),
            new HashMap<>(currentUnit.getDefenceBonuses()),
            coordX,
            coordY);

        System.out.println(positionedUnit.getName() + " x:" +
            positionedUnit.getxCoordinate() + " y:" +
            positionedUnit.getyCoordinate());
        finalizedUnits.add(positionedUnit);
        totalPoints += positionedUnit.getCost();
        unitsPlaced++;
      }
    }

    // Завершаем настройку армии
    generatedArmy.setUnits(finalizedUnits);
    generatedArmy.setPoints(totalPoints);
    System.out.println("Generation process completed");
    return generatedArmy;
  }
}