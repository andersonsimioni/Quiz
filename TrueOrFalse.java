package com.company;

import java.util.IllegalFormatException;
import java.util.Scanner;
import java.util.stream.Stream;

/**
    Herda a classe question e especializa em uma questao de verdadeiro ou falso
 */
public class TrueOrFalse extends Question
{
    @Override
    public boolean isValidResponse(String response)
    {
        return (response.equals("V") || response.equals("F"));
    }

    /**
     * Convert o texto lido em lines para um objeto do tipo 'TrueOrfalse'
     * @param lines = text que sera lido e convertido em uma questao
     * @param type = tipo da questao
     * @param score = pontos que ela valera
     * @return = objeto do tipo 'Question'/'TrueOrFalse'
     */
    public static Question create(Scanner lines, String type, int score)
    {
        try
        {
            String title = lines.nextLine();
            String trueResponse = lines.nextLine();

            return new TrueOrFalse(score, title, trueResponse);
        }
        catch (IllegalArgumentException e)
        {
            throw new IllegalArgumentException(e.getMessage() + ", File format is incorrect!");
        }
    }

    /**
     * Cria uma questao
     * @param score = pontos que a questao valera
     * @param title = titulo que sera mostrado ao jogador para responder
     * @param trueResponse = resposta correta da questao
     */
    public TrueOrFalse(int score, String title, String trueResponse)
    {
        super(score, title, trueResponse);
    }
}