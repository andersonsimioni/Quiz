package com.company;

import java.util.Scanner;

/**
 * Classe responsavel por administrar funcoes do console principal que se comunica com o usuario.
 *     Administra funcoes do tipo leitura e escrita de dados.
 */
public class Console
{
    /**Esse atributo fica responsavel por parte do trabalho de toda a leitura do console principal.*/
    private static final Scanner ScannerInstance = new Scanner(System.in);

    /**Retorna uma linha escrita pelo usuario no console principal, utiliza a classe Scanner para isso.
     * @return = Le todas as informacoes digitadas pelo usuario ate ele dar enter
     * */
    public static String readLine()
    {
        return ScannerInstance.nextLine();
    }

    /** Retorna um valor inteiro lido no console,
     * fica em loop ate ler um inteiro
     * @return = retorna uma informacao do tipo Int32 digitada pelo usuario via console
     */
    public static int readInt32()
    {
        while (true)
        {
            try
            {
                int n = Integer.valueOf(readLine());
                return n;
            }
            catch (Exception e)
            {
                writeLine("Please insert a number.");
            }
        }
    }

    /**Escreve uma nova linha do console principal.
     * @param data = Conteudo em texto que sera mostrado no console
     */
    public static void writeLine(String data)
    {
        System.out.println(data);
    }
}
