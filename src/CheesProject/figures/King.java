package CheesProject.figures;

import CheesProject.ChessBoard;

public class King extends ChessPiece {

  public King(String color) {
    super(color);
  }

  @Override
  public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine,
      int toColumn) {
    if (this.checkAccessibleCell(chessBoard, line, column, toLine, toColumn)) {
      int distanceX = Math.abs(toColumn - column);
      int distanceY = Math.abs(toLine - line);
      return distanceX <= 1 && distanceY <= 1;
    }
    return false;
  }

  @Override
  public String getSymbol() {
    return "K";
  }

  public boolean isUnderAttack(ChessBoard board, int line, int column) {
    ChessPiece[][] chessBoard = board.board;
    for (int i = 0; i < chessBoard.length; i++) {
      for (int j = 0; j < chessBoard.length; j++) {
        if (chessBoard[i][j].canMoveToPosition(board, i, j, line, column)) {
          return true;
        }
      }
    }
    return false;
  }
}
