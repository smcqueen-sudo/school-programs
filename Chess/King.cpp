/**
 * @file King.cpp
 * @author smcqueen22@georgefox.edu
 */

#include <cstdlib>
#include <iostream>
#include "King.h"
#include "Square.h"

piece_value_t King::value() const
{
    return 200;
}

bool King::can_move_to(const Square& location) const
{
    int rank_d = std::abs((int)this->location()->rank() - (int)location.rank());
    int file_d = std::abs((int)this->location()->file() - (int)location.file());
    bool same_square = (this->location()->rank() == location.rank() && this->location()->file() == location.file());
    return !same_square && (rank_d <= 1 && file_d <= 1);
}

std::string King::str() const
{
    std::string str_king = "k";
    if (color() == Piece::Color::black)
    {
        str_king = "K";
    }

    return str_king;
}
