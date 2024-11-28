package CheesProject.figures;

import CheesProject.ChessBoard;

public class Rook extends ChessPiece {

  public Rook(String color) {
    super(color);
  }

  @Override
  public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine,
      int toColumn) {
    if (this.checkAccessibleCell(chessBoard, line, column, toLine, toColumn) &&
        this.isPathClear(chessBoard, line, column, toLine, toColumn)) {
      int distanceX = Math.abs(toColumn - column);
      int distanceY = Math.abs(toLine - line);
      return (distanceY != 0 && distanceX == 0) || (distanceY == 0 && distanceX != 0);
    }
    return false;
  }

  @Override
  public String getSymbol() {
    return "R";
  }
}
