package com.company;

import java.util.ArrayList;
import java.util.Scanner;

/**
    Armazenara todas as informacoes do jogador local
 */
public class Player
{
    /**Armazena o primeiro nome do jogador*/
    private final String FirstName;

    /**Armazena sobrenome do jogador*/
    private final String LastName;

    /**Historico de sessoes jogadas*/
    private ArrayList<Session> SessionsPlayed;

    /**
     * @return = historico de sessoes jogadas pelo jogador
     */
    public ArrayList<Session> getSessionsPlayed()
    {
        return SessionsPlayed;
    }

    /** Adiciona uma sessao ao historico de sessoes do jogador
     * @param session = sessao que sera adicionada ao historico do jogador
     */
    public void addPlayedSessionAndSave(Session session)
    {
        SessionsPlayed.add(session);
        savePlayerFile();
    }

    /**Retorna o nome completo do player
     * @return = nome completo do jogador que e' a uniao de FirstName + " " + LastName
     */
    public String getFullName()
    {
        return FirstName + " " + LastName;
    }

    /**Retorna uma chave de acesso ao arquivo do jogador
     * @return = nome do arquivo que contera o historico de sessoes jogadas pelo jogador
     */
    public String getPlayerKey()
    {
        return FirstName + LastName;
    }

    /**Cria um player via console pedindo informacoes diretamento ao usuario
     * @return = um objeto do tipo Player com nome, sobrenome e historico de sessoes
     * correspondente ao jogador
     */
    public static Player createInConsole()
    {
        Console.writeLine("Please insert your first name:");
        String firstName = Console.readLine();

        Console.writeLine("Please insert your last name:");
        String lastName = Console.readLine();

        return new Player(firstName, lastName);
    }

    /**Verifica se o jogador ja jogou uma sessao
     * @param name = sessao que sera verificada se o jogador ja jogou
     * @return = caso ja tenha jogado, TRUE, caso nao, FALSE
     */
    public boolean playedSession(String name)
    {
        for (Session s : SessionsPlayed)
            if(s.getName().equals(name))
                return true;

        return false;
    }

    /**Verifica se o jogador tem um arquivo de historico, caso tenha
     * a funcao le o historico e o carrega para dentro do jogo, caso nao tenha
     * a funcao cria um arquivo novo para que o jogador possa salvar seu historico
     * a partir dai
     */
    private void loadPlayerFile()
    {
        File playerFile = new File(getPlayerKey());
        String[] lines = new String[0];

        try
        {
            lines = playerFile.readLines();
        }
        catch (Exception e)
        {
            playerFile.writeAllLines("");
            Console.writeLine("Player file dont exist! We create a new file.");
        }

        for(int i = 0; i < lines.length; i++)
            if(lines[i] != "")
                SessionsPlayed.add(Session.parse(lines[i]));
    }

    /**Salva o historico de sessoes do jogador mais as sessoes jogadas na partida aberta*/
    public void savePlayerFile()
    {
        File playerFile = new File(getPlayerKey());

        String[] lines = new String[SessionsPlayed.size()];
        Session[] sessionsArr = new Session[SessionsPlayed.size()];
        SessionsPlayed.toArray(sessionsArr);

        for(int i = 0; i < lines.length; i++)
            lines[i] = sessionsArr[i].toString();

        playerFile.writeLines(lines);
    }

    /**
     * @param firstName = primeiro nome do jogador
     * @param lastName = sobrenome do jogador
     */
    public Player(String firstName, String lastName)
    {
        if(firstName.isEmpty() || firstName == null)
            throw new IllegalArgumentException("Player first name is null or empty!");

        if(lastName.isEmpty() || lastName == null)
            throw new IllegalArgumentException("Player last name is null or empty!");

        this.FirstName = firstName;
        this.LastName = lastName;
        this.SessionsPlayed = new ArrayList<Session>();

        loadPlayerFile();
    }
}
