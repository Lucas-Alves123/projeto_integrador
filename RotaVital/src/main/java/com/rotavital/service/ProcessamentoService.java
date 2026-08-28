package com.rotavital.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class ProcessamentoService {

    public List<String> gerarDadosMock(int quantidade) {
        List<String> dados = new ArrayList<>(quantidade);
        for (int i = 0; i < quantidade; i++) {
            dados.add("REQ-" + i);
        }
        return dados;
    }

    private long operacaoPesada(String dado) {
        long resultado = 0;
        for (int i = 0; i < 5000; i++) {
            resultado += (long) (Math.tan(i) * Math.atan(i));
        }
        return resultado;
    }

    public long processarSequencial(List<String> dados) {
        long start = System.currentTimeMillis();
        long somaTotal = 0;
        
        for (String dado : dados) {
            somaTotal += operacaoPesada(dado);
        }
        
        long tempo = System.currentTimeMillis() - start;
        return tempo;
    }

    public long processarParalelo(List<String> dados, int numeroThreads) throws Exception {
        long start = System.currentTimeMillis();
        
        ExecutorService executor = Executors.newFixedThreadPool(numeroThreads);
        
        int tamanhoFatia = dados.size() / numeroThreads;
        List<Callable<Long>> tarefas = new ArrayList<>();
        
        for (int i = 0; i < numeroThreads; i++) {
            final int inicio = i * tamanhoFatia;
            final int fim = (i == numeroThreads - 1) ? dados.size() : (i + 1) * tamanhoFatia;
            
            tarefas.add(() -> {
                long somaParcial = 0;
                for (int j = inicio; j < fim; j++) {
                    somaParcial += operacaoPesada(dados.get(j));
                }
                return somaParcial; 
            });
        }
        
        List<Future<Long>> resultados = executor.invokeAll(tarefas);
        long somaTotal = 0;
        
        for (Future<Long> f : resultados) {
            somaTotal += f.get(); 
        }
        
        executor.shutdown();
        
        long tempo = System.currentTimeMillis() - start;
        return tempo;
    }
}
