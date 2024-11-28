package CheesProject.figures;

import CheesProject.ChessBoard;

public class Horse extends ChessPiece {

  public Horse(String color) {
    super(color);
  }

  @Override
  public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine,
      int toColumn) {
    if (this.checkAccessibleCell(chessBoard, line, column, toLine, toColumn)) {
      int distanceX = Math.abs(toLine - line);
      int distanceY = Math.abs(toColumn - column);
      return (distanceX == 2 && distanceY == 1) || (distanceX == 1 && distanceY == 2);
    }
    return false;
  }

  @Override
  public String getSymbol() {
    return "H";
  }
}
