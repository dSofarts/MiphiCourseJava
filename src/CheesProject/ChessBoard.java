package CheesProject;

import CheesProject.figures.ChessPiece;
import CheesProject.figures.King;
import CheesProject.figures.Rook;

public class ChessBoard {

  public ChessPiece[][] board = new ChessPiece[8][8];
  String nowPlayer;

  public ChessBoard(String player) {
    this.nowPlayer = player;
  }

  public String nowPlayerColor() {
    return this.nowPlayer;
  }

  public boolean moveToPosition(int startLine, int startColumn,
      int endLine, int endColumn) {
    if (checkPos(startLine) && checkPos(startColumn)) {
      if (!nowPlayer.equalsIgnoreCase(board[startLine][startColumn].getColor())) {
        return false;
      }
      if (board[startLine][startColumn] instanceof King
          && ((King) board[startLine][startColumn]).isUnderAttack(this, endLine, endColumn)) {
        return false;
      }
      if (board[startLine][startColumn].canMoveToPosition(this, startLine, startColumn, endLine,
          endColumn)) {
        board[endLine][endColumn] = board[startLine][startColumn];
        board[startLine][startColumn] = null;
        this.nowPlayer = this.nowPlayerColor().equalsIgnoreCase("white") ? "Black" : "White";
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  public void printBoard() {  //print board in console
    System.out.println("Turn " + nowPlayer);
    System.out.println();
    System.out.println("Player 2(Black)");
    System.out.println();
    System.out.println("\t0\t1\t2\t3\t4\t5\t6\t7");

    for (int i = 7; i > -1; i--) {
      System.out.print(i + "\t");
      for (int j = 0; j < 8; j++) {
        if (board[i][j] == null) {
          System.out.print(".." + "\t");
        } else {
          System.out.print(
              board[i][j].getSymbol() + board[i][j].getColor().substring(0, 1).toLowerCase()
                  + "\t");
        }
      }
      System.out.println();
      System.out.println();
    }
    System.out.println("Player 1(White)");
  }

  public boolean checkPos(int pos) {
    return pos >= 0 && pos <= 7;
  }

  public boolean castling0() {
    if (nowPlayer.equals("White")) {
      if (board[0][1] != null || board[0][2] != null || board[0][3] != null) return false;
      if (!(board[0][0] instanceof Rook) || board[0][0].getColor().equals("Black")) return false;
      if (!(board[0][4] instanceof King) || board[0][4].getColor().equals("Black")) return false;
      if (board[0][0].isChessPieceWalk() || board[0][4].isChessPieceWalk()) return false;
      board[0][4] = null;
      board[0][0] = null;
      board[0][2] = new King("White");
      board[0][2].setChessPieceWalk(true);
      board[0][3] = new Rook("White");
      board[0][3].setChessPieceWalk(true);
      nowPlayer = "Black";
      return true;
    } else {
      if (board[7][1] != null || board[7][2] != null || board[7][3] != null) return false;
      if (!(board[7][0] instanceof Rook) || board[7][0].getColor().equals("White")) return false;
      if (!(board[7][4] instanceof King) || board[7][4].getColor().equals("White")) return false;
      if (board[7][0].isChessPieceWalk() || board[7][4].isChessPieceWalk()) return false;
      board[7][4] = null;
      board[7][0] = null;
      board[7][2] = new King("Black");
      board[7][2].setChessPieceWalk(true);
      board[7][3] = new Rook("Black");
      board[7][3].setChessPieceWalk(true);
      nowPlayer = "White";
      return true;
    }
  }

  public boolean castling7() {
    if (nowPlayer.equals("White")) {
      if (board[0][5] != null || board[0][6] != null) return false;
      if (!(board[0][7] instanceof Rook) || board[0][7].getColor().equals("Black")) return false;
      if (!(board[0][4] instanceof King) || board[0][4].getColor().equals("Black")) return false;
      if (board[0][7].isChessPieceWalk() || board[0][4].isChessPieceWalk()) return false;
      board[0][4] = null;
      board[0][7] = null;
      board[0][6] = new King("White");
      board[0][6].setChessPieceWalk(true);
      board[0][5] = new Rook("White");
      board[0][5].setChessPieceWalk(true);
      nowPlayer = "Black";
      return true;
    } else {
      if (board[7][5] != null || board[7][6] != null) return false;
      if (!(board[7][7] instanceof Rook) || board[7][7].getColor().equals("White")) return false;
      if (!(board[7][4] instanceof King) || board[7][4].getColor().equals("White")) return false;
      if (board[7][7].isChessPieceWalk() || board[7][4].isChessPieceWalk()) return false;
      board[7][4] = null;
      board[7][7] = null;
      board[7][6] = new King("Black");
      board[7][6].setChessPieceWalk(true);
      board[7][5] = new Rook("Black");
      board[7][5].setChessPieceWalk(true);
      nowPlayer = "White";
      return true;
    }
  }


}
