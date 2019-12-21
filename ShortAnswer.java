package com.company;

import java.util.Scanner;

/**
 * Herda a classe Question e a especializa em uma questao de resposta curta
 */
public class ShortAnswer extends Question
{
    @Override
    public boolean isValidResponse(String response)
    {
        return response.split(" ").length > 1;
    }

    /**
     * Le o texto e verifica se a resposta correta esta no formato certo para
     * entao retornar ela
     * @param lines = texto que sera lido
     * @return = resposta correta da pergunta
     */
    private static String getTrueResponse(Scanner lines)
    {
        String trueResponse = lines.nextLine();

        if(trueResponse.isEmpty() || trueResponse == null)
            throw new IllegalArgumentException("True response format invalid!");

        if(trueResponse.equals("PASSAR"))
            throw new IllegalArgumentException("True response format invalid!");

        return trueResponse;
    }

    /**
     * Cria um objeto do tipo 'Question' a partir de um texto, tipo e pontuacao
     * @param lines = texto que sera lido
     * @param type = tipo da questao
     * @param score = pontuacao da questao
     * @return = Questao do tipo resposta curta
     */
    public static Question create(Scanner lines, String type, int score)
    {
        try
        {
            String title = lines.nextLine();
            String trueResponse = getTrueResponse(lines);

            return new ShortAnswer(score, title, trueResponse);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e.getMessage() + " , File format invalid!");
        }
    }

    /**
     * @param score = pontuacao da questao
     * @param title = titulo que sera mostrado ao jogador para responder a questao
     * @param trueResponse = resposta correta da questao
     */
    public ShortAnswer(int score, String title, String trueResponse)
    {
        super(score, title, trueResponse);
    }
}
