/**
 * @file Rook.cpp
 * @author smcqueen22@georgefox.edu
 */
#include "Rook.h"
#include "Square.h"

piece_value_t Rook::value() const
{
    return 5;
}

bool Rook::can_move_to(const Square& location) const
{
    bool same_square = (this->location()->rank() == location.rank() && this->location()->file() == location.file());
    return !same_square && (location.file() == this->location()->file() || location.rank() == this->location()->rank());
}

std::string Rook::str() const
{
    std::string str_rook = "r";
    if (color() == Piece::Color::black)
    {
        str_rook = "R";
    }

    return str_rook;
}
