package com.company;

import com.sun.source.tree.Scope;

/**
Classe que armazenara informacoes comuns a todos os tipos de perguntas do jogo,
Exemplo: tipo, titulo e resposta.
 */
public abstract class Question
{
    /**Tipos de questoes*/
    public static enum Types
    {
        VF,
        ME,
        RC
    }

    /**Pontuacao que o jogador ganhara ou perdera ao responder
     * a questao
     */
    private final int Score;

    /**Titulo que sera apresentado ao jogador para escolher a resposta, Exemplo:
       "Dizer que a classe A especializa a classe B é o mesmo que dizer que:
        a classe B é subclasse de A
        a classe A é superclasse de B
        a classe A é subclasse de B
        a classe B é derivada de A
        a classe B é abstrata"*/
    private final String Title;

    /**Resposta correta para a pergunta.
     * caso a resposta do jogador seja igual, ele acertou*/
    private final String TrueResponse;

    /**Armazena se a questao ja foi respondida para que nao seja repetida*/
    private boolean Played;

    /**
     * @return = pontuacao que o jogador ganhara ou perdera ao responder a pergunta
     */
    public int getScore()
    {
        return Score;
    }

    /**
     * @return = titulo da questao que sera apresentado ao jogador para ele responder
     */
    public String getTitle()
    {
        return Title;
    }

    /**
     * @return = resposta correta da questao
     */
    public String getTrueResponse()
    {
        return TrueResponse;
    }

    /**
     * @return retorna TRUE caso a questao ja tenha sido respondida,
     * e FALSE caso nao tenha sido respondida
     */
    public boolean isPlayed()
    {
        return Played;
    }

    /**Verifica se a resposta e' valida
     * @param response = resposta que o jogador deu para a pergunta
     * @return = caso ela se encaixe no tipo de pergunta, TRUE, caso nao, FALSE
     */
    public abstract boolean isValidResponse(String response);

    /**
     * Verifica se a resposta do jogador esta correta e retorna TRUE caso sim, ou
     * FALSE caso nao
     * @param response = resposta do jogador
     * @return = TRUE se esta correta, FALSE se errada
     */
    public boolean play(String response)
    {
        if(response.isEmpty() || response == null || !isValidResponse(response))
            throw new IllegalArgumentException("Question play, response is incorrect is null or empty!");

        this.Played = true;

        return this.TrueResponse.equals(response);
    }

    /**
     * @param score = pontos que a questao valera
     * @param title = titulo a ser mostrado ao jogador na hora de responder
     * @param trueResponse = resposta correta da questao
     */
    public Question(int score, String title, String trueResponse)
    {
        if(title.isEmpty() || title == "")
            throw new IllegalArgumentException("Question constructor, title is null or empty!");

        if(trueResponse.isEmpty() || trueResponse == "")
            throw new IllegalArgumentException("Question constructor, trueResponse is null or empty!");

        if(score <= 0)
            throw new IllegalArgumentException("Question constructor, score is smaller than zero or equal 0!");

        this.Score = score;
        this.Title = title;
        this.Played = false;
        this.TrueResponse = trueResponse;
    }
}
