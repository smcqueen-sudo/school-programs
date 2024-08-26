/**
 * @file Pawn.cpp
 * @author smcqueen22@georgefox.edu
 */

#include <cstdlib>
#include <iostream>
#include "Pawn.h"
#include "Square.h"

piece_value_t Pawn::value() const
{
    return 1;
}

bool Pawn::can_move_to(const Square& location) const
{
    bool diagonal = false;
    bool straight = false;

    if (this->color() == Piece::Color::white)
    {
        diagonal = (int)this->location()->rank() - (int)location.rank() == 1;
        diagonal = diagonal && std::abs((int)this->location()->file() - (int)location.file()) == 1;
        diagonal = diagonal && location.is_occupied()  && location.occupant()->color() == Piece::Color::black;

        straight = (int)this->location()->rank() - (int)location.rank() == 1;
        straight = straight || ((int)this->location()->rank() - (int)location.rank() == 2 && !_moved);
        straight = straight && (int)this->location()->file() == (int)location.file();
    }
    else
    {
        diagonal = (int)this->location()->rank() - (int)location.rank() == -1;
        diagonal = diagonal && std::abs((int)this->location()->file() - (int)location.file()) == 1;
        diagonal = diagonal && location.is_occupied() && location.occupant()->color() == Piece::Color::white;

        straight = (int)this->location()->rank() - (int)location.rank() == -1;
        straight = straight || ((int)this->location()->rank() - (int)location.rank() == -2 && !_moved);
        straight = straight && (int)this->location()->file() == (int)location.file();
    }

    bool same_square = (this->location()->rank() == location.rank() && this->location()->file() == location.file());

    return !same_square && (diagonal || straight);
}

bool Pawn::move_to(Square &location)
{
    bool success = false;

    if (Piece::move_to(location))
    {
        success = true;
        _moved = true;
    }

    return success;
}

std::string Pawn::str() const
{
    std::string str_king = "p";
    if (color() == Piece::Color::black)
    {
        str_king = "P";
    }

    return str_king;
}
