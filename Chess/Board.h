/**
 * @file Board.h
 * @author smcqueen22@georgefox.edu
 */
#ifndef BOARD_H
#define BOARD_H

#include <iostream>
#include "Square.h"

class Board {
  public:
    static const size_t SIZE = 8;

    /**
     * Constructor for the board
     */
    Board();

    /**
     * Returns the square at the given position
     *
     * @param rank Rank of square
     * @param file File of Square
     * @return Square at given position
     */
    Square& square_at(size_t rank, size_t file) const;

    /**
     * Returns the square at the given identifier. ex) b3 would correspond to rank 5, file 1
     *
     * @param identifier Identifier to return
     * @return Square at identifier
     */
    Square& square_at(const std::string& identifier) const;

    /**
     * Checks if the rank is valid in between two squares
     *
     * @param from Square to start at
     * @param to Square to end at
     * @return True if the rank is valid
     */
    bool is_valid_rank(const Square& from, const Square& to) const;

    /**
     * Checks if the file is valid in between the two squares
     *
     * @param from Square to start at
     * @param to Square to end at
     * @return True if the file is valid
     */
    bool is_valid_file(const Square& from, const Square& to) const;

    /**
     * Checks if the diagonal is valid in between the two squares
     *
     * @param from Square to start at
     * @param to Square to end at
     * @return True if the diagonal is valid
     */
    bool is_valid_diag(const Square& from, const Square& to) const;

    /**
     * Checks if the rank is clear in between the two squares
     *
     * @param from Square to start at
     * @param to Square to end at
     * @return True if the rank is clear
     */
    bool is_clear_rank(const Square& from, const Square& to) const;

    /**
     * Checks if the file is clear in between the two squares
     *
     * @param from Square to start at
     * @param to Square to end at
     * @return True if the file is clear
     */
    bool is_clear_file(const Square& from, const Square& to) const;

    /**
     * Checks if the diagonal is clear in between the two squares
     *
     * @param from Square to start at
     * @param to Square to end at
     * @return True if the diagonal is clear
     */
    bool is_clear_diag(const Square& from, const Square& to) const;

    /**
     * Deconstructor for board
     */
    ~Board();

  private:
    Square* _squares[SIZE][SIZE] = {};
};

/**
 * << Operator override for board
 *
 * @param os OS to output too
 * @param square Board to output
 * @return Reference to the os
 */
std::ostream& operator<<(std::ostream& os, const Board& board);

#endif
