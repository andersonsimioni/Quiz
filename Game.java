package com.company;

/**
 * Classe responsavel por administrar todo o jogo,
 * ela administra tanto o jogador quanto as sessoes e perguntas jogadas,
 * ela chama todas as funcoes necessarias para o pleno funcionamento do jogo
 */
public class Game
{
    /**
     * Armazena e processa os dados do jogador criado ou selecionado
     */
    private final Player LocalPlayer;

    /**
     * Sessao aberta que contem as perguntas e todas as informcoes
     * da partida
     */
    private final Session CurrentSession;

    /** Mostra em tela o resultado da resposta da questao e
     * adiciona a pontuacao e informacoes extras na sessao
     * @param playedQuestion = Questao que vou respondida
     * @param result = resultado da resposta
     */
    private void showPlayResult(Question playedQuestion, boolean result)
    {
        int score = playedQuestion.getScore();
        String trueResponse = playedQuestion.getTrueResponse();

        if(result)
        {
            CurrentSession.addScore(playedQuestion.getScore());
            CurrentSession.increamentSucessQuestions();
            Console.writeLine("Correto! Você ganhou " + score + " pontos.\n");
        }
        else
        {
            CurrentSession.addScore(-playedQuestion.getScore());
            CurrentSession.increamentFailQuestions();
            Console.writeLine("Incorreto. A resposta correta é " + trueResponse + ". Você perdeu " + score + " pontos.\n");
        }
    }

    /**
     * Seleciona e joga uma questao aleatoria que ainda
     *  nao foi jogada na sessao
     */
    private void playRamdomQuestion()
    {
        Question toPlay = CurrentSession.getRandomQuestion();
        Console.writeLine("Pontos: " + toPlay.getScore());
        Console.writeLine(toPlay.getTitle());
        String response = Console.readLine();

        if(response.equals("PASSAR") == false)
        {
            showPlayResult(toPlay, toPlay.play(response));
            CurrentSession.increamentPlayedQuestions();
        }
        else
            Console.writeLine("Você optou por pular esta questão.\n");
    }

    /**
     * Pede ao usuario quantas questoes ele gostaria de responder
     * da sessao aberta atualmente
     */
    private void readQuestionsToPlay()
    {
        int totalQuestions = CurrentSession.getTotalQuestions();

        while (CurrentSession.getSelectedQuestions() <= 0 || CurrentSession.getSelectedQuestions() > totalQuestions)
        {
            Console.writeLine("Quantas questões você gostaria de responder (1 a " + totalQuestions + ")?");
            CurrentSession.setSelectedQuestions(Console.readInt32());
        }
    }

    /**
     * Pede ao usuario qual sessao deseja abrir para jogar e
     * logo apos isso verifica no arquivo do jogador se ele ja
     * jogou a sessao, caso sim pede uma outra sessao, caoso nao
     * roda a sessao
     * @return = Sessao escolhida pelo usuario para jogar
     */
    private static Session readSessionToPlay()
    {
        Console.writeLine("Informe o nome do arquivo com a sessão de treinamento?");
        SessionFileReader sessionInitializer = new SessionFileReader(Console.readLine());

        try
        {
            return sessionInitializer.readSession();
        }
        catch (Exception e)
        {
            Console.writeLine("File dont exist!");
            return readSessionToPlay();
        }
    }

    /**
     * Mostra as informacoes do jogador e da sessao atual, isso inclui:
     * -nome do jogador
     * -pontuacao atual
     * -pontuacao global
     * -nome da sessao
     * -numero de questoes selecionadas
     * -numero de questoes erradas
     * -numero de questoes certas
     */
    private void showPlayerInformations()
    {
        int globalPontuation = 0;

        Session[] playedSessions = new Session[LocalPlayer.getSessionsPlayed().size()];
        LocalPlayer.getSessionsPlayed().toArray(playedSessions);

        for(int i = 0; i < playedSessions.length; i++)
            globalPontuation += playedSessions[i].getScore();

        Console.writeLine("                   ");
        Console.writeLine("---Actual session---");
        Console.writeLine("->Player name: " + LocalPlayer.getFullName());
        Console.writeLine("->Global score: " + globalPontuation);
        Console.writeLine("->Session name: " + CurrentSession.getName());
        Console.writeLine("->Score: " + CurrentSession.getScore());
        Console.writeLine("->Selected questions number: " + CurrentSession.getSelectedQuestions());
        Console.writeLine("->Sucess questions: " + CurrentSession.getSucessQuestions());
        Console.writeLine("->Fail questions: " + CurrentSession.getFailQuestions());
        Console.writeLine("--------------------");
        Console.writeLine("                   ");
    }

    /**
     * Apos ler o arquivo do jogador, o programa carrega as sessoes lidas nesse
     * arquivo para dentro da classe Player.
     * Se acaso exista um historico de sessoes jogadas, o mesmo sera mostrado nessa
     * funcao.
     * As informacoes mostradas de cada sessao antiga sao:
     * -nome
     * -pontuacao
     * -numero de questoes selecionadas
     * -numero de questoes certas
     * -numero de questoes erradas
     */
    private void showOldSessionsInformations()
    {
        Session[] playedSessions = new Session[LocalPlayer.getSessionsPlayed().size()];
        LocalPlayer.getSessionsPlayed().toArray(playedSessions);

        for(int i = 0; i < playedSessions.length; i++)
        {
            Console.writeLine("                   ");
            Console.writeLine("---" + playedSessions[i].getName() + "---");
            Console.writeLine("->Score: " + playedSessions[i].getScore());
            Console.writeLine("->Selected questions number: " + playedSessions[i].getSelectedQuestions());
            Console.writeLine("->Sucess questions: " + playedSessions[i].getSucessQuestions());
            Console.writeLine("->Fail questions: " + playedSessions[i].getFailQuestions());
            Console.writeLine("-------------------");
            Console.writeLine("                   ");
        }
    }

    /**
     * Pergunta ao jogador se ele deseja jogar uma nova partida,
     * case a resposta seja 'n', ele jogara uma nova partida!
     */
    private void wantNewGame()
    {
        Console.writeLine("Type 'n' for new game or some key to close...");
        String response = Console.readLine();

        if(response.equals("n"))
            Game.create(LocalPlayer).start();
    }

    /**
     * Fim da partida, quando todas as perguntas sao respondidas,
     * e' mostrado na tela as informacoes da partida jogada e das
     * partidas antigas, apos isso pergunta se o jogador quer uma nova
     * partida
     */
    private void endGame()
    {
        Console.writeLine("-----" + (CurrentSession.getScore() > 0 ? ("WIN") : ("LOSE")) + "-----");
        showPlayerInformations();
        Console.writeLine("         ");
        Console.writeLine("---Old sessions---");
        showOldSessionsInformations();
        Console.writeLine("-----END GAME-----");

        Console.writeLine("Fim de jogo!");
        Console.writeLine("Sua pontuação final foi " + CurrentSession.getScore() + " pontos.");

        LocalPlayer.addPlayedSessionAndSave(CurrentSession);

        wantNewGame();
    }

    /**
     * Inicia o loop no jogo ate o numero de perguntas respondidas for
     * igual ao numero desejado
     */
    public void start()
    {
        readQuestionsToPlay();

        while (CurrentSession.getPlayedQuestions() < CurrentSession.getSelectedQuestions())
        {
            showPlayerInformations();
            playRamdomQuestion();
        }

        endGame();
    }

    /**
     * Primeiro de tudo seleciona/cria um jogador, depois pergunta a sessao desejada e
     * se for valida carrega
     * @param player = se for nulo cria/seleciona um jogador, se nao reutiliza 'LocalPlayer' para uma nova partida
     * @return  = uma nova partida configurada com o jogador e sessao de perguntas
     */
    public static Game create(Player player)
    {
        Player localPlayer = player == null ? (Player.createInConsole()) : (player);
        Session newSession = readSessionToPlay();

        while(localPlayer.playedSession(newSession.getName()))
        {
            Console.writeLine("O jogador ja jogou esta sessao, por favor escolha outra!");
            newSession = readSessionToPlay();
        }

        return new Game(localPlayer, newSession);
    }

    /**
     * @param localPlayer = jogador que jogara a sessao
     * @param currentSession = sessao que sera jogada
     */
    public Game(Player localPlayer, Session currentSession)
    {
        LocalPlayer = localPlayer;
        CurrentSession = currentSession;
    }
}
