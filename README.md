# Projeto Integrador - Threads e Paralelismo 🚀

Bem-vindo ao repositório do Projeto Integrador focado no estudo e aplicação prática de **Concorrência** e **Paralelismo** utilizando a linguagem Java.

Este repositório contém duas aplicações distintas, cada uma explorando um conceito fundamental da computação multithread:
1. **Mesa DJ (Threads I):** Foco em *Concorrência* e Sincronização.
2. **Rota Vital (Threads II):** Foco em *Paralelismo* e Ganho de Desempenho (Speedup).

---

## 🎧 Projeto 1: Mesa DJ (Concorrência)

Um simulador de mesa de DJ em formato de console (terminal). A aplicação usa threads independentes para reproduzir arquivos de áudio (bateria, baixo, synth) ao mesmo tempo. 

O grande desafio solucionado aqui é o uso de **Locks (Sincronização)** para permitir que o usuário digite comandos (como `pause bateria` ou `play baixo`) enquanto a música toca, pausando uma thread específica sem travar as demais e sem corromper o sistema.

### 🛠️ O que é necessário instalar?
- **Java 17 ou superior (JDK)** instalado na máquina.

### ▶️ Como executar?
1. Abra a pasta `MesaDJ`.
2. Baixe arquivos de áudio `.wav` curtos (loops) e coloque-os dentro da pasta `MesaDJ/audio` (com os nomes `bateria.wav`, `baixo.wav` e `synth.wav`).
3. Dê um duplo clique no arquivo **`run.bat`** (no Windows) para iniciar.
4. Digite comandos como `play bateria`, `pause baixo` e divirta-se!

---

## 🏥 Projeto 2: Rota Vital (Paralelismo)

Uma API REST construída com **Spring Boot** que processa um grande volume de dados (100.000 registros). Simulamos uma "Validação Cruzada de Estoque" muito pesada para o processador ($O(N)$ constante).

O objetivo aqui é puramente performático: a API executa a mesma tarefa gigante usando apenas 1 Thread (Sequencial), e depois divide a tarefa e executa simultaneamente usando 2, 4 e 8 Threads (usando o `ExecutorService` do Java). Os resultados provam o ganho de velocidade (*Speedup*). O projeto conta ainda com um Dashboard moderno em HTML/CSS para visualizar os tempos reais.

### 🛠️ O que é necessário instalar?
- **Java 17 ou superior (JDK)** instalado na máquina.
- (Opcional) Maven, mas o projeto já utiliza o Maven Wrapper (`mvnw`), então ele baixa tudo sozinho!

### ▶️ Como executar?
1. Abra a pasta `RotaVital`.
2. Dê um duplo clique no arquivo **`run.bat`** para iniciar o servidor Spring Boot.
3. Aguarde o servidor iniciar (quando a logo do Spring aparecer na tela preta).
4. Abra o seu navegador de internet e acesse: **[http://localhost:8080/](http://localhost:8080/)**
5. Clique no botão de processar e veja os resultados do Speedup na sua tela!

---

## 🧠 Concorrência vs Paralelismo (Resumo)
- Na **Mesa DJ (Concorrência)**, nós gerenciamos várias tarefas diferentes que concorriam pela atenção do sistema (tocar música, ler teclado, pausar), lidando com estados sem quebrar.
- No **Rota Vital (Paralelismo)**, pegamos uma única tarefa gigante de dados e quebramos em partes para serem executadas matematicamente ao mesmo tempo, focando puramente em velocidade.

Feito com 💙 para a disciplina de Sistemas Distribuídos / Programação Multithread.
