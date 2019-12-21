package com.company;

import java.util.Random;

/**
 * Administra o gerador de numeros aleatorios
 */
public class ReallyRandom
{
    /**
     * Gerador de numeros aleatorios
     */
    private static final Random Generator = new Random();

    /**
     * @param from = numero minimo que sera gerado
     * @param to = numero maximo que sera gerado
     * @return = numero aleatorio maior que 'from' e menor que 'to'
     */
    public static int get(int from, int to)
    {
        return (int)(from + Generator.nextFloat()*(to-from));
    }
}
