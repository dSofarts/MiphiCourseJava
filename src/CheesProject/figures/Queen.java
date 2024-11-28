package CheesProject.figures;

import CheesProject.ChessBoard;

public class Queen extends ChessPiece {

  public Queen(String color) {
    super(color);
  }

  @Override
  public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine,
      int toColumn) {
    if (this.checkAccessibleCell(chessBoard, line, column, toLine, toColumn) &&
        this.isPathClear(chessBoard, line, column, toLine, toColumn)) {
      int distanceX = Math.abs(toColumn - column);
      int distanceY = Math.abs(toLine - line);
      if ((distanceY != 0 && distanceX == 0) || (distanceY == 0 && distanceX != 0))
        return true;
      return distanceY == distanceX;
    }
    return false;
  }

  @Override
  public String getSymbol() {
    return "Q";
  }
}
