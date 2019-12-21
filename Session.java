package com.company;

import java.util.List;

/**
 * Sessao de perguntas que o jogador respondera,
 * tambem armazena a pontuacao da sessao,
 * perguntas erradas e certas e o numero de perguntas
 * respondidas
 */
public class Session
{
    /**Nome da sessao de treinamento*/
    private final String Name;

    /**Questoes lidas do arquivo de treinamento*/
    private final Question[] Questions;

    private int SelectedQuestions;

    /**Armazena a pontuacao total do jogador na partida*/
    private int Score;

    /**Numero de questoes certas*/
    private int FailQuestions;

    /**Numero de questoes erradas*/
    private int SucessQuestions;

    /**Numero total de questoes jogadas na partida*/
    private int PlayedQuestions;

    /**
     * @return = numero de perguntas que o jogador quer responder
     */
    public int getSelectedQuestions()
    {
        return SelectedQuestions;
    }

    /**
     * @return = numero de perguntas que o jogador errou
     */
    public int getFailQuestions()
    {
        return FailQuestions;
    }

    /**
     * @return = numero de perguntas que o jogador acertou
     */
    public int getSucessQuestions()
    {
        return SucessQuestions;
    }

    /**
     * @return = numero de perguntas que o jogador respondeu
     */
    public int getPlayedQuestions()
    {
        return PlayedQuestions;
    }

    /** Adiciona o subtrai um valor da pontuacao da sessao
     */
    public void addScore(int add)
    {
        this.Score += add;
    }

    /**
     * @return = pontuacao atual da sessao
     */
    public int getScore()
    {
        return Score;
    }

    /**
     * @return = nome da sessao
     */
    public String getName()
    {
        return Name;
    }

    /**
     * @return = numero total de perguntas que a sessao possui
     */
    public int getTotalQuestions()
    {
        return Questions.length;
    }

    /**
     * @return = soma da pontuacao de todas as questoes da sessao
     */
    public int getTotalScore()
    {
        int sigma = Questions[0].getScore();

        for (int i = 1; i < Questions.length; i++)
            sigma += Questions[i].getScore();

        return sigma;
    }

    /**
     * Muda a pontuacao da sessao
     * @param score nova pontuacao
     */
    public void setScore(int score)
    {
        Score = score;
    }

    /**
     * Incrementa uma unidade no numero de questoes erradas da sessao
     */
    public void increamentFailQuestions()
    {
        FailQuestions++;
    }

    /**
     * Incrementa uma unidade no numero de questoes certas da sessao
     */
    public void increamentSucessQuestions()
    {
        SucessQuestions++;
    }

    /**
     * Incrementa uma unidade no numero de questoes respondidas da sessao
     */
    public void increamentPlayedQuestions()
    {
        PlayedQuestions++;
    }

    /**
     * Muda o valor de quantas questoes o jogador respondera
     * @param selectedQuestions = novo valor para quantidade de perguntas a serem respondidas na sessao
     */
    public void setSelectedQuestions(int selectedQuestions)
    {
        SelectedQuestions = selectedQuestions;
    }

    /**
     * Sorteia um indice aleatorio ate achar uma questao nao respondida ainda
     * para poder retornar
     * @return = Questao aleatoria nao respondida
     */
    public Question getRandomQuestion()
    {
        int randomId = ReallyRandom.get(0, Questions.length);

        while (Questions[randomId].isPlayed())
            randomId = ReallyRandom.get(0, Questions.length);

        return Questions[randomId];
    }

    /**
     * Converte os dados da sessao em uma string para armazenar no arquivo de
     * historico do jogador
     * @return = dados essenciais da classe convertidos para string
     */
    @Override
    public String toString()
    {
        return Name + " // "
                + PlayedQuestions + " // "
                + Score + " // "
                + SelectedQuestions + " // "
                + FailQuestions + " // "
                + SucessQuestions;
    }

    /** Converte uma string no formato correto para uma objeto do tipo 'Session'
     * @param data = texto lido no arquivo de historico do jogador
     * @return = dados do texto convertidos para um objeto do tipo 'Session'
     */
    public static Session parse(String data)
    {
        String[] params = data.split(" // ");

        try
        {
            return new Session(params[0],
            Integer.valueOf(params[1]),
            Integer.valueOf(params[2]),
            Integer.valueOf(params[3]),
            Integer.valueOf(params[4]),
            Integer.valueOf(params[5]));
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e.getMessage() + ", Invalid player file format!");
        }
    }

    /**
     * Cria um objeto do tipo 'Session' com perguntas para responder
     * @param name = nome da sessao de treinamento
     * @param questions = questoes que serao respondidas na sessao de treinamento
     */
    public Session(String name, Question[] questions)
    {
        if(questions == null || questions.length == 0)
            throw new IllegalArgumentException("Session questions invalid!");

        if(name.isEmpty() || name == null)
            throw new IllegalArgumentException("Session name is null or empty!");

        this.Name = name;
        this.Questions = questions;
        this.Score = 0;
        this.PlayedQuestions = 0;
        this.SelectedQuestions = 0;
        this.FailQuestions = 0;
        this.SucessQuestions = 0;
    }

    /**
     * Cria um objeto do tipo 'Session' porem sem perguntas pois esse metodo e' utilizado para
     * serializar e deserializar um objeto do tipo 'Session'
     * @param name = nome da sessao
     * @param playedQuestions = questoes respondidas
     * @param score = pontuacao obtida
     * @param selectedQuestions = numero de perguntas desejadas para responder
     * @param failQuestions = numero de perguntas erradas
     * @param sucessQuestions = numero de perguntas certas
     */
    public Session(String name, int playedQuestions, int score, int selectedQuestions, int failQuestions, int sucessQuestions)
    {
        Name = name;
        SelectedQuestions = selectedQuestions;
        Score = score;
        FailQuestions = failQuestions;
        SucessQuestions = sucessQuestions;
        PlayedQuestions = playedQuestions;

        this.Questions = new Question[0];
    }
}
