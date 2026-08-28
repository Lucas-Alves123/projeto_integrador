package src;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DjApp {
    private static final Map<String, InstrumentAudioThread> instruments = new HashMap<>();
    
    public static void main(String[] args) {
        System.out.println("=== Bem-vindo a Mesa DJ (Simulador Multithread) ===");
        System.out.println("Para ouvir áudio real, coloque arquivos .wav na pasta 'audio'!");
        
        addInstrument("bateria", "audio/bateria.wav");
        addInstrument("baixo", "audio/baixo.wav");
        addInstrument("synth", "audio/synth.wav");
        
        StatusDisplayThread statusThread = new StatusDisplayThread(instruments);
        Thread statusDisplay = new Thread(statusThread);
        statusDisplay.start();
        
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            
            if (input.equals("exit") || input.equals("sair")) {
                System.out.println("Encerrando a mesa DJ...");
                statusThread.stopDisplay();
                for (InstrumentAudioThread inst : instruments.values()) {
                    inst.stopThread();
                }
                break;
            }
            
            String[] parts = input.split(" ");
            if (parts.length >= 2) {
                String command = parts[0];
                String name = parts[1];
                InstrumentAudioThread inst = instruments.get(name);
                
                if (command.equals("play") || command.equals("start")) {
                    if (inst != null) inst.playInstrument();
                } else if (command.equals("pause")) {
                    if (inst != null) inst.pauseInstrument();
                } else if (command.equals("add") && parts.length >= 3) {
                    String path = parts[2];
                    addInstrument(name, path);
                } else if (command.equals("bpm") && parts.length >= 3) {
                    try {
                        int bpm = Integer.parseInt(parts[2]);
                        if (inst != null) inst.setBpm(bpm);
                    } catch (NumberFormatException e) {
                        System.out.println("BPM inválido.");
                    }
                }
            }
        }
        
        scanner.close();
    }
    
    private static void addInstrument(String name, String audioPath) {
        if (!instruments.containsKey(name)) {
            InstrumentAudioThread instThread = new InstrumentAudioThread(name, audioPath);
            instruments.put(name, instThread);
            new Thread(instThread).start();
        }
    }
}
