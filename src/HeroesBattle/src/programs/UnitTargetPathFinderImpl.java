package HeroesBattle.src.programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.Edge;
import com.battle.heroes.army.programs.UnitTargetPathFinder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

// На каждом узле учитывается расстояние. В общем случае количество узлов пропорционально числу клеток на поле: ширина * высота
// Инициализация всех данных требует O(ширина * высота).
// Каждый узел добавляется в очередь приоритетов только один раз.
// Очередь с приоритетами (PriorityQueue) работает с логарифмической сложностью на вставку и извлечение: O(log(количество узлов)).
// Каждый узел имеет до 4-х соседей (определяется направлениями DIRECTIONS).
// Итоговая сложность: O(ширина * высота * log(ширина * высота))
public class UnitTargetPathFinderImpl implements UnitTargetPathFinder {

  private static final int FIELD_WIDTH = 27;
  private static final int FIELD_HEIGHT = 21;
  private static final int[][] MOVEMENT_DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

  @Override
  public List<Edge> getTargetPath(Unit attackUnit, Unit targetUnit, List<Unit> existingUnitList) {
    Set<String> blockedCells = markBlockedCells(existingUnitList, attackUnit, targetUnit);

    // Карта кратчайших расстояний
    Map<GridPoint, Integer> shortestPaths = new HashMap<>();
    Map<GridPoint, GridPoint> pathMap = new HashMap<>();
    PriorityQueue<GridPoint> priorityQueue = new PriorityQueue<>(
        Comparator.comparingInt(shortestPaths::get));

    // Установка начальной точки
    GridPoint startPoint = new GridPoint(attackUnit.getxCoordinate(), attackUnit.getyCoordinate());
    shortestPaths.put(startPoint, 0);
    priorityQueue.add(startPoint);

    while (!priorityQueue.isEmpty()) {
      GridPoint currentPoint = priorityQueue.poll();

      // Если достигли цели, выходим
      if (currentPoint.getX() == targetUnit.getxCoordinate()
          && currentPoint.getY() == targetUnit.getyCoordinate()) {
        break;
      }

      for (int[] direction : MOVEMENT_DIRECTIONS) {
        int nextX = currentPoint.getX() + direction[0];
        int nextY = currentPoint.getY() + direction[1];
        GridPoint nextPoint = new GridPoint(nextX, nextY);

        if (canMoveTo(nextX, nextY, blockedCells)) {
          int newDistance = shortestPaths.getOrDefault(currentPoint, Integer.MAX_VALUE) + 1;
          if (newDistance < shortestPaths.getOrDefault(nextPoint, Integer.MAX_VALUE)) {
            shortestPaths.put(nextPoint, newDistance);
            pathMap.put(nextPoint, currentPoint);
            priorityQueue.add(nextPoint);
          }
        }
      }
    }

    return reconstructPath(pathMap, attackUnit, targetUnit);
  }

  private Set<String> markBlockedCells(List<Unit> existingUnitList, Unit attackUnit,
      Unit targetUnit) {
    Set<String> blocked = new HashSet<>();
    for (Unit unit : existingUnitList) {
      if (unit.isAlive() && unit != attackUnit && unit != targetUnit) {
        blocked.add(unit.getxCoordinate() + "," + unit.getyCoordinate());
      }
    }
    return blocked;
  }

  private boolean canMoveTo(int x, int y, Set<String> blockedCells) {
    return x >= 0 && x < FIELD_WIDTH && y >= 0 && y < FIELD_HEIGHT && !blockedCells.contains(
        x + "," + y);
  }

  private List<Edge> reconstructPath(Map<GridPoint, GridPoint> pathMap, Unit attackUnit,
      Unit targetUnit) {
    List<Edge> pathEdges = new ArrayList<>();
    GridPoint targetPoint = new GridPoint(targetUnit.getxCoordinate(), targetUnit.getyCoordinate());
    GridPoint currentPoint = targetPoint;

    while (currentPoint != null) {
      pathEdges.add(new Edge(currentPoint.getX(), currentPoint.getY()));
      currentPoint = pathMap.get(currentPoint);
    }

    Collections.reverse(pathEdges);
    return pathEdges;
  }

  private static class GridPoint {

    private final int x;
    private final int y;

    public GridPoint(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public int getX() {
      return x;
    }

    public int getY() {
      return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
      GridPoint point = (GridPoint) obj;
      return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y);
    }
  }
}
