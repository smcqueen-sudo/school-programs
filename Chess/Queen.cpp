/**
 * @file Queen.cpp
 * @author smcqueen22@georgefox.edu
 */

#include <cstdlib>
#include <iostream>
#include "Queen.h"
#include "Square.h"

piece_value_t Queen::value() const
{
    return 9;
}

bool Queen::can_move_to(const Square& location) const
{
    int rank_d = std::abs((int)this->location()->rank() - (int)location.rank());
    int file_d = std::abs((int)this->location()->file() - (int)location.file());
    bool file_or_rank = location.file() == this->location()->file() || location.rank() == this->location()->rank();

    bool same_square = (this->location()->rank() == location.rank() && this->location()->file() == location.file());

    return !same_square && (file_or_rank || (rank_d == file_d));
}

std::string Queen::str() const
{
    std::string str_queen = "q";
    if (color() == Piece::Color::black)
    {
        str_queen = "Q";
    }

    return str_queen;
}


