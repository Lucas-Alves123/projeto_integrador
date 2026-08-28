# Relatório: Paralelismo na Rota Vital

## 1. Justificativa da Operação Escolhida
A operação escolhida foi a **"Validação Cruzada de Estoque"**, que simula um processo intensivo de CPU onde cada registro passa por uma série de cálculos matemáticos pesados para validar regras de prioridade ($O(N)$ constante mas com alta carga por registro). Esta operação é ideal porque:
- O gargalo está na **CPU** (cálculos) e não na espera de Rede ou Banco de Dados (I/O).
- Os dados são perfeitamente **particionáveis**: validar a requisição do hospital A não depende da validação do hospital B, permitindo dividir a lista em fatias totalmente independentes.

## 2. Medições e Speedup
As requisições foram testadas na API local com `100.000` registros.

| Versão | Threads | Tempo de Resposta (ms) | Speedup (Ganho) |
| :--- | :--- | :--- | :--- |
| Sequencial | 1 | 9892 | 1.0x (Base) |
| Paralelo | 2 | 4835 | 2.01x |
| Paralelo | 4 | 2505 | 3.87x |
| Paralelo | 8 | 1896 | 5.28x |

## 3. Análise Final
O ganho de performance (*speedup*) tende a ser quase **linear**, mas estabiliza quando atinge o limite de núcleos físicos/lógicos do processador da máquina. A Big-O não mudou: a complexidade total continua sendo $O(N)$, a diferença é que o tempo de parede (Wall-Clock Time) cai porque dividimos os passos entre múltiplos trabalhadores simultâneos.

**Concorrência vs Paralelismo:**
Na *Mesa DJ (Threads I)* nós usamos **Concorrência**: tínhamos múltiplos instrumentos esperando por comandos aleatórios do usuário, ou seja, gerenciamos várias tarefas diferentes que concorriam pela atenção do sistema. Aqui no *Rota Vital*, usamos **Paralelismo**: pegamos uma única tarefa gigante (volume de dados) e quebramos em partes para serem executadas matematicamente ao mesmo tempo, focando puramente em velocidade. 

**Como a arquitetura pode evoluir?**
Quando 8 threads (ou até o limite da máquina) não bastarem, o paralelismo vertical esgota. A arquitetura precisará evoluir para o **Processamento Distribuído** (Escalabilidade Horizontal), separando o trabalho não apenas em Threads na mesma máquina, mas entre múltiplos Servidores em nuvem usando filas (como RabbitMQ/Kafka) ou processamento em lote com Spark.
