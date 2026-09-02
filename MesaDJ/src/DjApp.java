package src;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DjApp {
    private static final Map<String, InstrumentAudioThread> instruments = new HashMap<>();
    
    public static void main(String[] args) {
        System.out.println("=== Bem-vindo a Mesa DJ (Simulador Multithread) ===");
        System.out.println("Para ouvir áudio real, coloque arquivos .wav na pasta 'audio'!");
        
        String audioDir = new java.io.File("MesaDJ/audio").exists() ? "MesaDJ/audio/" : "audio/";
        addInstrument("bateria", audioDir + "bateria.wav");
        addInstrument("baixo", audioDir + "baixo.wav");
        
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
                } else if (command.equals("volume") && parts.length >= 3) {
                    try {
                        int vol = Integer.parseInt(parts[2]);
                        if (inst != null) inst.setVolume(vol);
                    } catch (NumberFormatException e) {
                        System.out.println("[ERRO] Volume inválido. Use um número de 0 a 100.");
                    }
                }
            } else if (!input.isEmpty()) {
                System.out.println("[ERRO] Comando incompleto. Tente algo como: play baixo, ou volume bateria 50.");
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
