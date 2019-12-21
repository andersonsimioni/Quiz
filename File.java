package com.company;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Essa classe e' responsavel por administrar todas as
 * operacoes com arquivos no disco rigido
 */
public class File
{
    /**Caminho/localizacao do arquivo que sera trabalhado
     * */
    private final String Path;

    /** Essa funcao cria uma stream para editar dados do arquivo selecionado,
     * ela reescreve todo o conteudo do arquivo!
     * @param data = Conteudo em text que sera armazenado/escrito no
     *             arquivo que se localiza em 'path'
     */
    public void writeAllLines(String data)
    {
        try
        {
            char[] charList = data.toCharArray();
            FileOutputStream writer = new FileOutputStream(Path);

            for (int i = 0; i < charList.length; i++)
                writer.write((int)charList[i]);

            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /** A funcao reescreve todas as linhas do arquivo selecionado
     * @param lines = informacao dividiva linha por linha que sera armazenada no arquivo selecionado
     */
    public void writeLines(String[] lines)
    {
        String data = lines[0];

        for (int i = 1; i < lines.length; i++)
            data += lines[i] + "\n";

        writeAllLines(data);
    }

    /** A funcao cria uma stream de leitura para ler os bytes
     * do arquivo selecionado e converte-os para char adicionando
     * em uma string para poder retornar o texto do arquivo
     * @return = todo o conteudo de texto do arquivo selecionado
     */
    public String readAllLines()
    {
        try
        {
            int readed;
            String data = "";
            FileInputStream reader = new FileInputStream(Path);

            while ((readed = reader.read()) != -1)
                data += (char)readed;

            reader.close();

            return data;
        }
        catch (FileNotFoundException e)
        {
            throw new IllegalArgumentException("File not found!");
        }
        catch (IOException e)
        {
            throw new IllegalArgumentException("File error");
        }
    }

    /** Essa funcao le o texto normalmene do arquivo selecionado e
     * converte em uma array de string para retornar as linhas separadamente
     * @return = todas as linhas do texto do arquivo selecioado
     */
    public String[] readLines()
    {
        return readAllLines().split("\n");
    }

    public File(String path)
    {
        if(path.isEmpty() || path == null)
            throw new IllegalArgumentException("path is invalid!");

        Path = path;
    }
}
