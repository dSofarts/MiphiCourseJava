package CheesProject.figures;

import CheesProject.ChessBoard;
import CheesProject.Main;

public class Pawn extends ChessPiece {

  public Pawn(String color) {
    super(color);
  }

  @Override
  public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine,
      int toColumn) {
    if (this.checkAccessibleCell(chessBoard, line, column, toLine, toColumn) &&
        this.isPathClear(chessBoard, line, column, toLine, toColumn)) {
      int distY;
      if (this.getColor().equalsIgnoreCase("white")) {
        distY = toLine - line;
      } else {
        distY = line - toLine;
      }
      int distX = Math.abs(toColumn - column);
      if (distY < 1 || distY > 2) return false;
      if (distX > 1) return false;
      if (distX == 1 && distY == 1) {
        return !chessBoard.board[toLine][toColumn].getColor().equalsIgnoreCase(this.getColor());
      }
      if (distY == 2 && distX == 0 && !this.isChessPieceWalk()) return true;
      return distY == 1 && distX == 0;
    }
    return false;
  }


  @Override
  public String getSymbol() {
    return "P";
  }
}
