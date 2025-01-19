package HeroesBattle.src.programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.programs.PrintBattleLog;
import com.battle.heroes.army.programs.SimulateBattle;
import java.util.LinkedList;
import java.util.List;

// Проверка живых юнитов: O(P).
// Сортировка юнитов: O(NlogN+MlogM).
// Симуляция раунда: O(P)
// Удаление мертвых юнитов: O(P)

// Итого общая сложность O(P*P+P*logP)
public class SimulateBattleImpl implements SimulateBattle {

  private PrintBattleLog printBattleLog; // Позволяет логировать. Использовать после каждой атаки юнита

  @Override
  public void simulate(Army playerArmy, Army computerArmy) throws InterruptedException {
    // Получаем списки юнитов из армий
    List<Unit> playerForces = playerArmy.getUnits();
    List<Unit> computerForces = computerArmy.getUnits();

    // Пока в обеих армиях есть живые юниты
    while (containsAliveUnits(playerForces) && containsAliveUnits(computerForces)) {
      // Упорядочиваем юниты по силе атаки
      orderUnitsByAttack(playerForces);
      orderUnitsByAttack(computerForces);

      // Проводим раунд боя
      conductBattleRound(playerForces, computerForces);

      // Удаляем павших юнитов
      cleanupDeadUnits(playerForces);
      cleanupDeadUnits(computerForces);
    }

    // Определяем исход боя
    if (containsAliveUnits(playerForces)) {
      System.out.println("Player wins!");
    } else if (containsAliveUnits(computerForces)) {
      System.out.println("Computer wins!");
    } else {
      System.out.println("It's a draw!");
    }
  }

  private boolean containsAliveUnits(List<Unit> units) {
    return units.stream().anyMatch(Unit::isAlive);
  }

  private void orderUnitsByAttack(List<Unit> units) {
    units.sort((unitA, unitB) -> Integer.compare(unitB.getBaseAttack(), unitA.getBaseAttack()));
  }

  private void conductBattleRound(List<Unit> playerForces, List<Unit> computerForces)
      throws InterruptedException {
    Queue<Unit> turnQueue = new LinkedList<>();

    // Формируем очередь ходов из всех живых юнитов
    turnQueue.addAll(playerForces);
    turnQueue.addAll(computerForces);

    while (!turnQueue.isEmpty()) {
      Unit activeUnit = turnQueue.poll();

      // Пропускаем мертвых юнитов
        if (!activeUnit.isAlive()) {
            continue;
        }

      // Находим цель для атаки
      Unit targetUnit = activeUnit.getProgram().attack();

      // Если цель существует и жива, наносим урон
      if (targetUnit != null && targetUnit.isAlive()) {
        executeAttack(activeUnit, targetUnit);

        // Логируем действие
        battleLogger.printBattleLog(activeUnit, targetUnit);
      }
    }
  }

  private void executeAttack(Unit attacker, Unit target) {
    // Уменьшаем здоровье цели
    int inflictedDamage = attacker.getBaseAttack();
    target.setHealth(target.getHealth() - inflictedDamage);

    // Проверяем, выжил ли юнит
    if (target.getHealth() <= 0) {
      target.setAlive(false);
    }
  }

  private void cleanupDeadUnits(List<Unit> units) {
    units.removeIf(unit -> !unit.isAlive());
  }
}