package com.company;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Le um arquivo de sessao de treinamento selecionado e tenta
 * converte-lo em um objeto do tipo 'Session'
 */
public class SessionFileReader
{
    /**
     * Objeto que administra o arquivo que sera lido
     * para criar a sessao
     */
    private final File FileInstance;

    /**
     * Converte um texto lido em lines para um objeto do tipo 'Question'
     * @param lines = texto lido no arquivo da sessao
     * @return = Objeto Question criado a partir do texto lido
     */
    private Question readQuestion(Scanner lines)
    {
        String[] questionInfo = lines.nextLine().split(" ");
        int score = Integer.valueOf(questionInfo[1]);

        if(questionInfo.length != 2)
            throw new IllegalArgumentException("File format invalid!");

        if(questionInfo[0].equals(Question.Types.VF.toString()))
            return TrueOrFalse.create(lines, Question.Types.VF.toString(), score);

        if(questionInfo[0].equals(Question.Types.ME.toString()))
            return MultiChoice.create(lines, Question.Types.ME.toString(), score);

        if(questionInfo[0].equals(Question.Types.RC.toString()))
            return ShortAnswer.create(lines, Question.Types.RC.toString(), score);

        throw new IllegalArgumentException("No question type found, file format invalid!");
    }

    /**
     * Le todas as questoes e informacoes da sessao que esta dentro
     * do arquivo da sessao
     * @return
     */
    public Session readSession()
    {
        try
        {
            String data = FileInstance.readAllLines();
            Scanner lines = new Scanner(data);

            String sessionName = lines.nextLine();
            int totalQuestions = Integer.valueOf(lines.nextLine());

            Question[] sessionQuestions = new Question[totalQuestions];

            for (int i = 0; i < totalQuestions; i++)
                sessionQuestions[i] = readQuestion(lines);

            return new Session(sessionName, sessionQuestions);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e.getMessage() + ", File format invalid!");
        }
    }

    /**
     * Cria um leitor de arquivo de sessao de treinamento
     * @param path = caminho/localizacao do arquivo da sessao
     */
    public SessionFileReader(String path)
    {
        FileInstance = new File(path);
    }
}
