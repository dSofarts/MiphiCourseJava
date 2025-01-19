package HeroesBattle.src.programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.SuitableForAttackUnitsFinder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SuitableForAttackUnitsFinderImpl implements SuitableForAttackUnitsFinder {

  @Override
  public List<Unit> getSuitableUnits(List<List<Unit>> unitsByRow, boolean isLeftArmyTarget) {
    List<Unit> suitableUnits = new ArrayList<>();

    // Проходим по каждой строке из unitsByRow
    for (List<Unit> row : unitsByRow) {
      // Определяем целевого юнита по минимальной или максимальной координате Y в зависимости от isLeftArmyTarget
      if (isLeftArmyTarget) {
        // Для левой армии (компьютера) ищем юнита с минимальной координатой Y
        Unit targetUnit = row.stream()
            .filter(Unit::isAlive)
            .min(Comparator.comparingInt(Unit::getyCoordinate))
            .orElse(null);
        if (targetUnit != null) {
          suitableUnits.add(targetUnit);
        }
      } else {
        // Для правой армии (игрока) ищем юнита с максимальной координатой Y
        Unit targetUnit = row.stream()
            .filter(Unit::isAlive)
            .max(Comparator.comparingInt(Unit::getyCoordinate))
            .orElse(null);
        if (targetUnit != null) {
          suitableUnits.add(targetUnit);
        }
      }
    }

    return suitableUnits;
  }
}
