/**
 * @file Piece.cpp
 * @author smcqueen22@georgefox.edu
 */
#include "Piece.h"
#include "Square.h"

Piece::Color Piece::color() const
{
    return _color;
}

Square* Piece::location() const
{
    return _location;
}

void Piece::set_location(Square* location)
{
    if (_location != nullptr)
    {
        _location->set_occupant(nullptr);
    }

    _location = location;

    if (location != nullptr)
    {
        if (location->is_occupied())
        {
            location->occupant()->capture();
        }

        location->set_occupant(this);
    }
}

bool Piece::is_on_square() const
{
    return _location != nullptr;
}

bool Piece::move_to(Square& location)
{
    bool success = false;
    if (can_move_to(location))
    {
        success = true;
        set_location(&location);
    }
    return success;
}

void Piece::capture()
{
    _location->_occupant = nullptr;
    _location = nullptr;
}

std::ostream& operator<<(std::ostream& os, const Piece& piece)
{
    os << piece.str();
    return os;
}