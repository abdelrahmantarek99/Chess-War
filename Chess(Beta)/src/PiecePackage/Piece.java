package PiecePackage;

import GamePackage.*;
import java.util.*;

abstract public class Piece {

    protected Cell pos;
    protected Color color;
    public int steps;
    public int last_move_id;

    public Piece(Cell pos, Color c) {
        this.pos = pos;
        this.color = c;
        steps = 0;
        last_move_id = 0;
    }
    
    public Piece(Piece p) {
        this.pos = new Cell(p.pos);
        this.color = p.color;
    }

    public Cell getPos() {
        return pos;
    }

    public void setPos(Cell pos) {
        this.pos = pos;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public abstract ArrayList<Cell> possible_moves(Cell pos, Board b);

    boolean check_valid_borders(int x, int y) {
        return (x > -1 && y > -1 && x < 8 && y < 8);
    }

    public boolean check_my_king(Cell currentPos, Cell nextPos, Piece king, Piece[][] board) {

        if (board[nextPos.getRow()][nextPos.getColumn()] != null && board[currentPos.getRow()][currentPos.getColumn()].color == board[nextPos.getRow()][nextPos.getColumn()].color) {
            if (!currentPos.isEqual(nextPos)) {
                return false;
            }
        }
        /*
        for(int i = 0; i < 8; i ++)
        {
            for(int j = 0; j < 8; j ++)
            {
                if(board[i][j] != null && board[i][j] instanceof King)
                {
                    if(board[i][j].color == Color.White)
                    {
                        System.out.println("White King At " + i + " " + j);
                    }
                    
                    if(board[i][j].color == Color.Black)
                    {
                        System.out.println("Black King At " + i + " " + j);
                    }
                }
            }
        }

        System.out.println("King Color And Position " + king.color.toString() + " " + king.pos.getRow() + " " + king.pos.getColumn());*/
        
        boolean ok = true;
        int x = king.pos.getRow(), y = king.pos.getColumn();
        
       /* if(board[x][y] == null)
            System.out.println("Null at " + x + " " + y);
        else
            System.out.println("Not Null at " + x + " " + y);*/
       
        
//         if(board[x][y] != null)
//        {
//            System.out.println("Not NUll " + board[x][y].getColor());
//            
//        }
//        else
//        {
//            System.out.println("NULL");
//        }
        //System.out.println("King Positon = " + x + " " + y);
        ///**///

        Piece cur = board[currentPos.getRow()][currentPos.getColumn()];
        Piece nxt = board[nextPos.getRow()][nextPos.getColumn()];
//        if (currentPos.getRow() != x || currentPos.getColumn() != y) {

            board[currentPos.getRow()][currentPos.getColumn()] = null;
            board[nextPos.getRow()][nextPos.getColumn()] = cur;
            board[nextPos.getRow()][nextPos.getColumn()].setPos(new Cell(nextPos.getRow(), nextPos.getColumn()));
//        }
        if(board[x][y] == null){
            System.out.println("King Positon = " + x + " " + y);
            System.out.println(currentPos.getRow() + " " + currentPos.getColumn());
            System.out.println(nextPos.getRow() + " " + nextPos.getColumn());
        }
        int dx[] = {-2, -2, -1, -1, 2, 2, 1, 1};
        int dy[] = {1, -1, 2, -2, -1, 1, -2, 2};
        ///**///
        ///checking if a knight is attacking me
        for (int i = 0; i < 8; i++) {
            if (check_valid_borders(x + dx[i], y + dy[i]) && board[x + dx[i]][y + dy[i]] instanceof Knight && board[x + dx[i]][y + dy[i]].color != king.color) {
                ok = false;
            }
        }

        /////////////general cases
        ///checking if any piece is attacking me from the left
        for (int i = y - 1; i >= 0; i--) {
            if (board[x][i] != null) {
                if (board[x][i] instanceof Rook || board[x][i] instanceof Queen) {
                    // System.out.println("X = " + x + ", y = b" + y + ", i = " + i + ", color = " + board[x][y].color);
                    if (board[x][i].color != board[x][y].color) {
                        ok = false;
                    }
                }
                break;
            }
        }
        ///checking if any piece is attacking me from the right
        for (int i = y + 1; i < 8; i++) {
            if (board[x][i] != null) {
                if (board[x][i] instanceof Rook || board[x][i] instanceof Queen) {
                    if (board[x][i].color != board[x][y].color) {
                        ok = false;
                    }
                }
                break;
            }
        }
        ///checking if any piece is attacking me from up 
        for (int i = x + 1; i < 8; i++) {
            if (board[i][y] != null) {
                if (board[i][y] instanceof Rook || board[i][y] instanceof Queen) {
                    if (board[i][y].color != board[x][y].color) {
                        ok = false;
                    }
                }
                break;
            }
        }
        ///checking if any piece is attacking me from down
        for (int i = x - 1; i >= 0; i--) {
            if (board[i][y] != null) {
                if (board[i][y] instanceof Rook || board[i][y] instanceof Queen) {
                    if (board[i][y].color != board[x][y].color) {
                        ok = false;
                    }
                }
                break;
            }
        }
        ///move_right_up
        for (int r = x + 1, c = y + 1; r < 8 && c < 8; r++, c++) {
            if (board[r][c] != null) {
                if (board[r][c] instanceof Bishop || board[r][c] instanceof Queen) {
                    if (board[r][c].color != board[x][y].color) {
                        ok = false;
                    }
                }
                break;
            }
        }
        ///move_left_up
        for (int r = x + 1, c = y - 1; r < 8 && c >= 0; r++, c--) {
            if (board[r][c] != null) {
                if (board[r][c] instanceof Bishop || board[r][c] instanceof Queen) {
                    if (board[r][c].color != board[x][y].color) {
                        ok = false;
                    }
                }
                break;
            }
        }
        ///move_right_down
        for (int r = x - 1, c = y + 1; r >= 0 && c < 8; r--, c++) {
            if (board[r][c] != null) {
                if (board[r][c] instanceof Bishop || board[r][c] instanceof Queen) {
                    if (board[r][c].color != board[x][y].color) {
                        ok = false;
                    }
                }
                break;
            }
        }
        ///move_left_down
        for (int r = x - 1, c = y - 1; r >= 0 && c >= 0; r--, c--) {
            if (board[r][c] != null) {
                if (board[r][c] instanceof Bishop || board[r][c] instanceof Queen) {
                    if (board[r][c].color != board[x][y].color) {
                        ok = false;
                    }
                }
                break;
            }
        }
        // checking if a pawn is attacking me
        if (king.color == Color.White) {
            if (check_valid_borders(x + 1, y + 1) && board[x + 1][y + 1] != null) {
                if (board[x + 1][y + 1].color == Color.Black && board[x + 1][y + 1] instanceof Pawn) {
                    ok = false;
                }
            }
            if (check_valid_borders(x + 1, y - 1) && board[x + 1][y - 1] != null) {
                if (board[x + 1][y - 1].color == Color.Black && board[x + 1][y - 1] instanceof Pawn) {
                    ok = false;
                }
            }
        } else if (king.color == Color.Black) {
            if (check_valid_borders(x - 1, y + 1) && board[x - 1][y + 1] != null) {
                if (board[x - 1][y + 1].color == Color.White && board[x - 1][y + 1] instanceof Pawn) {
                    ok = false;
                }
            }
            if (check_valid_borders(x - 1, y - 1) && board[x - 1][y - 1] != null) {
                if (board[x - 1][y - 1].color == Color.White && board[x - 1][y - 1] instanceof Pawn) {
                    ok = false;
                }
            }
        }
        // checking if a King is attacking me
        int dxx[] = {0, 0, -1, -1, -1, 1, 1, 1};
        int dyy[] = {-1, 1, -1, 0, 1, -1, 0, 1};
        for (int i = 0; i < 8; i++) {
            if (check_valid_borders(x + dxx[i], y + dyy[i]) && board[x + dxx[i]][y + dyy[i]] != null) {
//                if (board[x][y] == null) {
//                    System.out.println(currentPos.getRow() + " " + currentPos.getColumn());
//                    System.out.println("null at cell " + x + " " + y + " " + king.color.toString());
//                }
                if (board[x + dxx[i]][y + dyy[i]].color != board[x][y].color && board[x + dxx[i]][y + dyy[i]] instanceof King) {
                    ok = false;
                }
            }
        }
//        if (currentPos.getRow() != x || currentPos.getColumn() != y) {

            board[currentPos.getRow()][currentPos.getColumn()] = cur;
            board[nextPos.getRow()][nextPos.getColumn()] = nxt;
            board[currentPos.getRow()][currentPos.getColumn()].setPos(new Cell(currentPos.getRow(), currentPos.getColumn()));
//        }
        return ok;
    }

    void make_move(Cell from, Cell to) {

    }

    void delete_piece() {

    }

    @Override
    public abstract Piece clone();
}
