package com.company;

import java.util.Scanner;

/**
    Herda a classe question e especializa em uma questao de multipla escolha
 */
public class MultiChoice extends Question
{
    @Override
    public boolean isValidResponse(String response) {
        if(response.split(" ").length > 1)
            return false;

        char[] charArray = response.toCharArray();

        if(charArray.length == 0 || charArray.length > 1)
            return false;

        return ((int)charArray[0] >= 65 || (int)charArray[0] <= 90);
    }

    /**
     * Converte um numero em char para aplicar nas multiplas escolhas
     * @param n = indice da char na tabela ASCII
     * @return = char com indice 'n' na tabela ASCII
     */
    private static char getLetter(int n)
    {
        if(n < 0 || n > 25)
            throw new IllegalArgumentException("File format invalid!");

        return (char)(65 + n);
    }

    /**
     * Valida a resposta certa e a retorna
     * @param lines = linhas do arquivo aberto da sessao
     * @return = resposta correta lida no escopo da pergunta da sessao
     */
    private static String getTrueResponse(Scanner lines)
    {
        String trueResponse = lines.nextLine();

        if(trueResponse.isEmpty() || trueResponse == null)
            throw new IllegalArgumentException("True response format invalid!");

        if(trueResponse.split("").length > 1 || trueResponse.equals("PASSAR"))
            throw new IllegalArgumentException("True response format invalid!");

        return trueResponse;
    }

    /**
     * Le todas as alternativas e uni em uma unica string
     * @param lines = linhas do arquivo aberto da sessao
     * @param totalChoice
     * @param choiceId
     * @return
     */
    public static String getChoices(Scanner lines, int totalChoice, int choiceId)
    {
        return choiceId == totalChoice - 1 ? lines.nextLine() :
                    "\n" + "(" + getLetter(choiceId) + ") " + lines.nextLine() + getChoices(lines, totalChoice,choiceId + 1);
    }

    /** Cria uma questao do tipo multipla escolha
     * Converte um texto em uma questao do tipo multipla escolha
     * @param lines = linhas do arquivo da sessao
     * @param type = tipo de questao lido no arquivo
     * @param score = pontuacao da questao lido no arquivo
     * @return
     */
    public static Question create(Scanner lines, String type, int score)
    {
        try
        {
            String title = lines.nextLine();
            int numberOfChoices = Integer.valueOf(lines.nextLine());
            title += getChoices(lines, numberOfChoices, 0);
            String trueResponse = getTrueResponse(lines);

            return new MultiChoice(score, title, trueResponse);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e.getMessage() + " , File format invalid!");
        }
    }

    /**
     * @param score = pontos que a questao valera
     * @param title = titulo que sera mostrado ao jogador para responder
     * @param trueResponse = resposta correta da questao
     */
    public MultiChoice(int score, String title, String trueResponse)
    {
        super(score, title, trueResponse);
    }
}
