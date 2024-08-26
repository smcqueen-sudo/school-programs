/**
 * @author smcqueen22@georgefox.edu
 * @file Chess.cpp
 */
#include "Chess.h"
#include "Board.h"
#include "Pawn.h"
#include "Player.h"

/**
 * Runs the chess game
 *
 * @return Exit code
 */
int main()
{
    Board board = Board();
    Player player_black = Player(Piece::Color::black, board);
    Player player_white = Player(Piece::Color::white, board);
    int turns_taken = 0;
    bool white_turn = true;

    // Could also do player_black.piece_value() > 200 && player_white.piece_value() > 200
    while (turns_taken < 20)
    {
        std::string move;
        std::string player;

        if (white_turn)
        {
            player = "White";
        }
        else
        {
            player = "Black";
        }

        std::cout << board << std::endl;

        bool invalid = true;

        while(invalid)
        {
            std::cout << player << ", enter your move: ";
            std::getline(std::cin, move);
            invalid = false;

            // These next statements are input validation, it checks each character individually to ensure
            // That the input that was entered was exactly correct
            if (move.length() != 5)
            {
                invalid = true;
                std::cout << "invalid move, invalid length" << std::endl;
            }
            else if (!((move[0] - '0') >= 49 && std::abs(49 - (move[0] - '0')) < 8))
            {
                invalid = true;
                std::cout << "invalid move, first character" << std::endl;
            }
            else if (!((move[1] - '0') <= 8 && std::abs(8 - (move[1] - '0')) < 8))
            {
                invalid = true;
                std::cout << "invalid move, second character" << std::endl;
            }
            else if(move[2] != ' ')
            {
                invalid = true;
                std::cout << "invalid move, third character" << std::endl;
            }
            else if (!((move[3] - '0') >= 49 && std::abs(49 - (move[3] - '0')) < 8))
            {
                invalid = true;
                std::cout << "invalid move, fourth character" << std::endl;
            }
            else if (!((move[4] - '0') <= 8 && std::abs(8 - (move[4] - '0')) < 8))
            {
                invalid = true;
                std::cout << "invalid move, fifth character" << std::endl;
            }
            else
            {
                std::string from = move.substr(0, 2);
                std::string to = move.substr(3, 2);

                if (white_turn)
                {
                    invalid = !(player_white.make_move(from, to));
                }
                else
                {
                    invalid = !(player_black.make_move(from, to));
                }

                if (invalid)
                {
                    std::cout << "invalid move" << std::endl;
                }
            }
        }

        white_turn = !white_turn;
        turns_taken++;
    }

    std::cout << board << std::endl;
    std::cout << "White players final score: " << player_white.piece_value() << std::endl;
    std::cout << "Black players final score: " << player_black.piece_value() << std::endl;
}

