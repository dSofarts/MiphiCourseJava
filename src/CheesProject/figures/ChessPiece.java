package CheesProject.figures;

import CheesProject.ChessBoard;

public abstract class ChessPiece {

  private final String color;
  private boolean isChessPieceWalk = false;

  public ChessPiece(String color) {
    this.color = color;
  }

  public String getColor() {
    return color;
  }

  public abstract boolean canMoveToPosition(ChessBoard chessBoard, int line, int column,
      int toLine, int toColumn);

  public abstract String getSymbol();

  public boolean isChessPieceWalk() {
    return isChessPieceWalk;
  }

  public void setChessPieceWalk(boolean chessPieceWalk) {
    isChessPieceWalk = chessPieceWalk;
  }

  protected boolean checkAccessibleCell(ChessBoard chessBoard, int line, int column,
      int toLine, int toColumn) {
    if (line == toLine && column == toColumn) return false;
    if (chessBoard.board[toLine][toColumn] != null && chessBoard
        .board[toLine][toColumn].getColor().equalsIgnoreCase(this.color)) {
      return false;
    }
    return toLine <= 7 && toLine >= 0 && toColumn <= 7 && toColumn >= 0;
  }

  protected boolean isPathClear(ChessBoard chessBoard, int startX, int startY, int endX, int endY) {
    int deltaX = Integer.compare(endX, startX);
    int deltaY = Integer.compare(endY, startY);

    int currentX = startX + deltaX;
    int currentY = startY + deltaY;

    while (currentX != endX || currentY != endY) {
      if (chessBoard.board[currentX][currentY] != null) {
        return false;
      }
      currentX += deltaX;
      currentY += deltaY;
    }

    return true;
  }
}
