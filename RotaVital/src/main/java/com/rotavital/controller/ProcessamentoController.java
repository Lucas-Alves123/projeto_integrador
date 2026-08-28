package com.rotavital.controller;

import com.rotavital.service.ProcessamentoService;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/processamento")
public class ProcessamentoController {

    private final ProcessamentoService service;

    public ProcessamentoController(ProcessamentoService service) {
        this.service = service;
    }

    @GetMapping("/estoque")
    public Map<String, Object> processar(@RequestParam(defaultValue = "100000") int tamanho) throws Exception {
        List<String> dados = service.gerarDadosMock(tamanho);
        
        long tempoSeq = service.processarSequencial(dados);
        long tempoPar2 = service.processarParalelo(dados, 2);
        long tempoPar4 = service.processarParalelo(dados, 4);
        long tempoPar8 = service.processarParalelo(dados, 8);
        
        Map<String, Object> relatorio = new LinkedHashMap<>();
        relatorio.put("Tamanho_da_Entrada", tamanho);
        relatorio.put("1_Tempo_Sequencial_ms", tempoSeq);
        relatorio.put("2_Tempo_Paralelo_2_Threads_ms", tempoPar2);
        relatorio.put("3_Tempo_Paralelo_4_Threads_ms", tempoPar4);
        relatorio.put("4_Tempo_Paralelo_8_Threads_ms", tempoPar8);
        
        relatorio.put("Speedup_2_Threads", String.format("%.2fx", (double) tempoSeq / tempoPar2));
        relatorio.put("Speedup_4_Threads", String.format("%.2fx", (double) tempoSeq / tempoPar4));
        relatorio.put("Speedup_8_Threads", String.format("%.2fx", (double) tempoSeq / tempoPar8));
        
        return relatorio;
    }
}
