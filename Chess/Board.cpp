/**
 * @file Board.h
 * @author smcqueen22@georgefox.edu
 */

#include <cstdlib>
#include <iostream>
#include "Board.h"
#include "Piece.h"

Board::Board()
{
    for (size_t r = 0; r < SIZE; r++)
    {
        for (size_t f = 0; f < SIZE; f++)
        {
            _squares[r][f] = new Square(r, f);
        }
    }
}

Square& Board::square_at(size_t rank, size_t file) const
{
    return *_squares[rank][file];
}

Square& Board::square_at(const std::string& identifier) const
{
    size_t file = std::abs(49 - (identifier[0] - '0'));
    size_t rank = std::abs(8 - (identifier[1] - '0'));
    return *_squares[rank][file];
}

bool Board::is_valid_rank(const Square& from, const Square& to) const
{
    return from.rank() == to.rank() && from.file() != to.file();
}

bool Board::is_valid_file(const Square& from, const Square& to) const
{
    return from.file() == to.file() && from.rank() != to.rank();
}

bool Board::is_valid_diag(const Square& from, const Square& to) const
{
    return std::abs((int)from.rank() - (int)to.rank()) == std::abs((int)from.file() - (int)to.file());
}

bool Board::is_clear_rank(const Square &from, const Square &to) const
{
    bool is_clear = true;

    if (is_valid_rank(from, to))
    {
        size_t file = from.file();

        if (from.file() > to.file())
        {
            for (size_t i = 1; i <= from.file() - to.file() - 1; i++)
            {
                if (_squares[from.rank()][file - i]->is_occupied())
                {
                    is_clear = false;
                }
            }
        }
        else
        {
            for (size_t i = 1; i <= to.file() - from.file() - 1; i++)
            {
                if (_squares[from.rank()][file + i]->is_occupied())
                {
                    is_clear = false;
                }
            }
        }
    }
    else
    {
        is_clear = false;
    }

    return is_clear;
}

bool Board::is_clear_file(const Square &from, const Square &to) const
{
    bool is_clear = true;

    if (is_valid_file(from, to))
    {
        size_t rank = from.rank();

        if (from.rank() > to.rank())
        {
            for (size_t i = 1; i <= from.rank() - to.rank() - 1; i++)
            {
                if (_squares[rank - i][from.file()]->is_occupied())
                {
                    is_clear = false;
                }
            }
        }
        else
        {
            for (size_t i = 1; i <= to.rank() - from.rank() - 1; i++)
            {
                if (_squares[rank + i][from.file()]->is_occupied())
                {
                    is_clear = false;
                }
            }
        }
    }
    else
    {
        is_clear = false;
    }

    return is_clear;
}

bool Board::is_clear_diag(const Square& from, const Square& to) const
{
    bool is_clear = true;
    // Calculate the direction that we need to go
    int rank_dir = ((int)to.rank() - (int)from.rank()) / std::abs((int)to.rank() - (int)from.rank());
    int file_dir = ((int)to.file() - (int)from.file()) / std::abs((int)to.file() - (int)from.file());

    if (is_valid_diag(from, to))
    {
        for (int i = 1; i < std::abs((int)to.rank() - (int)from.rank()); i++)
        {
            if (_squares[from.rank() + (i * rank_dir)][from.file() + (i * file_dir)]->is_occupied())
            {
                is_clear = false;
            }
        }
    }
    else
    {
        is_clear = false;
    }

    return is_clear;
}

Board::~Board()
{
    for (size_t r = 0; r < SIZE; r++)
    {
        for (size_t f = 0; f < SIZE; f++)
        {
            delete _squares[r][f];
        }
    }
}

std::ostream& operator<<(std::ostream& os, const Board& board)
{
    const int SIZE = 8;

    // Print first line of letters
    os << "    ";
    char files = 'a';

    for (size_t i = 0; i < SIZE; i++)
    {
        os << files << "   ";
        files += 1;
    }

    os << std::endl;

    // Print lines of board that have pieces
    for (size_t r = 0; r < SIZE; r++)
    {
        os << "  +---+---+---+---+---+---+---+---+" << std::endl;

        os << SIZE - r << " ";

        for (size_t f = 0; f < SIZE; f++)
        {
            os << "| ";
            os << board.square_at(r, f);
            os << " ";
        }
        os << "| " << SIZE - r << std::endl;
    }
    os << "  +---+---+---+---+---+---+---+---+" << std::endl;

    // Print second line of letters
    os << "    ";
    files = 'a';

    for (size_t i = 0; i < SIZE; i++)
    {
        os << files << "   ";
        files += 1;
    }

    return os;
}


