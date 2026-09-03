package src;

import java.util.Map;

public class StatusDisplayThread implements Runnable {
    private final Map<String, InstrumentAudioThread> instruments;
    private volatile boolean running = true;
    
    public StatusDisplayThread(Map<String, InstrumentAudioThread> instruments) {
        this.instruments = instruments;
    }
    
    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(2000);
                
                System.out.println("\n\n=== [ PAINEL AO VIVO DA MESA DJ ] ===");
                for (Map.Entry<String, InstrumentAudioThread> entry : instruments.entrySet()) {
                    String status = entry.getValue().isPlaying() ? "[TOCANDO]" : "[PAUSADO]";
                    System.out.println(" > " + entry.getKey().toUpperCase() + " : " + status);
                }
                System.out.println("=======================================");
                System.out.println("Comandos: play <inst>, pause <inst>, add <inst> <arq>, bpm <inst> <valor>, volume <inst> <0-100>, exit/sair");
                System.out.print("DJ> ");
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    public void stopDisplay() {
        running = false;
    }
}
