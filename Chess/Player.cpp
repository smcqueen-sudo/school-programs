/**
 * @file Player.cpp
 * @author smcqueen22@georgefox.edu
 */
#include "Player.h"
#include "Queen.h"
#include "Bishop.h"
#include "Knight.h"
#include "Rook.h"
#include "Pawn.h"

Player::Player(Piece::Color color, const Board& board): _color(color), _board(board)
{
    std::string rank;
    std::string pawn_rank;
    if (color == Piece::Color::black)
    {
        rank = "8";
        pawn_rank = "7";
    }
    else
    {
        rank = "1";
        pawn_rank = "2";
    }

    Square& king_square = _board.square_at("e" + rank);
    King* king = new King(_color, king_square);
    _pieces.push_back(king);
    _king = king;

    Square& queen_square = _board.square_at("d" + rank);
    Queen* queen = new Queen(_color, queen_square);
    _pieces.push_back(queen);

    Square& bishop_r_square = _board.square_at("f" + rank);
    Bishop* bishop_r = new Bishop(_color, bishop_r_square);
    _pieces.push_back(bishop_r);

    Square& bishop_l_square = _board.square_at("c" + rank);
    Bishop* bishop_l = new Bishop(_color, bishop_l_square);
    _pieces.push_back(bishop_l);

    Square& knight_r_square = _board.square_at("g" + rank);
    Knight* knight_r = new Knight(_color, knight_r_square);
    _pieces.push_back(knight_r);

    Square& knight_l_square = _board.square_at("b" + rank);
    Knight* knight_l = new Knight(_color, knight_l_square);
    _pieces.push_back(knight_l);

    Square& rook_r_square = _board.square_at("h" + rank);
    Rook* rook_r = new Rook(_color, rook_r_square);
    _pieces.push_back(rook_r);

    Square& rook_l_square = _board.square_at("a" + rank);
    Rook* rook_l = new Rook(_color, rook_l_square);
    _pieces.push_back(rook_l);

    for (char i = 'a'; i != 'i'; i++)
    {
        Square& pawn_square = board.square_at(i + pawn_rank);
        Pawn* pawn = new Pawn(_color, pawn_square);
        _pieces.push_back(pawn);
    }
}

Piece::Color Player::color() const
{
    return _color;
}

bool Player::make_move(const std::string& from, const std::string& to)
{
    bool success = false;
    Square& from_sq = _board.square_at(from);
    Square& to_sq = _board.square_at(to);


    if (from_sq.is_occupied() && from_sq.occupant()->color() == _color)
    {
        std::cout << "Possible success 1" << std::endl;
        if (from_sq.occupant()->can_move_to(to_sq) &&
            !(to_sq.is_occupied() && to_sq.occupant()->color() == _color))
        {
            std::cout << "Possible success 2" << std::endl;
            if (_board.is_clear_rank(from_sq, to_sq) ||
                _board.is_clear_file(from_sq, to_sq) ||
                _board.is_clear_diag(from_sq, to_sq) ||
                (from_sq.occupant()->str() == "n" || from_sq.occupant()->str() == "N"))
            {
                std::cout << "Possible success" << std::endl;
                success = from_sq.occupant()->move_to(to_sq);
            }
        }
    }

    return success;
}

piece_value_t Player::piece_value() const
{
    piece_value_t total = 0;
    for (Piece* piece : _pieces)
    {
        if (piece->is_on_square())
        {
            total += piece->value();
        }
    }

    return total;
}

Player::~Player()
{
    for (Piece* piece : _pieces)
    {
        delete piece;
    }
}
