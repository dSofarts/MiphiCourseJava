package CheesProject.figures;

import CheesProject.ChessBoard;

public class Bishop extends ChessPiece {

  public Bishop(String color) {
    super(color);
  }

  @Override
  public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine,
      int toColumn) {
    int distanceX = Math.abs(toColumn - column);
    int distanceY = Math.abs(toLine - line);
    if (distanceX != distanceY) {
      return false;
    }
    return (this.checkAccessibleCell(chessBoard, line, column, toLine, toColumn) &&
        this.isPathClear(chessBoard, line, column, toLine, toColumn));
  }

  @Override
  public String getSymbol() {
    return "B";
  }
}
