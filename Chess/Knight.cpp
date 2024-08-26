/**
 * @file Knight.cpp
 * @author smcqueen22@georgefox.edu
 */

#include <cstdlib>
#include <iostream>
#include "Knight.h"
#include "Square.h"

piece_value_t Knight::value() const
{
    return 3;
}

bool Knight::can_move_to(const Square& location) const
{
    int rank_n = std::abs((int)this->location()->rank() - (int)location.rank());
    int file_n = std::abs((int)this->location()->file() - (int)location.file());
    bool same_square = (this->location()->rank() == location.rank() && this->location()->file() == location.file());
    return !(same_square) && rank_n + file_n == 3 && rank_n <= 2 && file_n <= 2;
}

std::string Knight::str() const
{
    std::string str_knight = "n";
    if (color() == Piece::Color::black)
    {
        str_knight = "N";
    }

    return str_knight;
}
