/**
 * @file Bishop.cpp
 * @author smcqueen22@georgefox.edu
 */

#include <cstdlib>
#include <iostream>
#include "Bishop.h"
#include "Square.h"


piece_value_t Bishop::value() const
{
    return 3;
}

bool Bishop::can_move_to(const Square& location) const
{
    int rank_d = std::abs((int)this->location()->rank() - (int)location.rank());
    int file_d = std::abs((int)this->location()->file() - (int)location.file());
    bool same_square = (this->location()->rank() == location.rank() && this->location()->file() == location.file());
    return !same_square && rank_d == file_d;
}

std::string Bishop::str() const
{
    std::string str_bishop = "b";
    if (color() == Piece::Color::black)
    {
        str_bishop = "B";
    }

    return str_bishop;
}
