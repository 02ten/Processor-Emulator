import java.util.Arrays;
import java.util.Map;

public class Emulator {
    private static final int NUM_REGISTERS = 8;
    private int[] registers = new int[NUM_REGISTERS];
    private int programCounter = 0;
    private boolean running = true;
    private Memory memory;

    public Emulator(Memory memory) {
        this.memory = memory;
    }
    //TODO Пофиксить
    public void execute() {
        while (running) {
            Instructions instruction = memory.readInstruction(programCounter++);
            executeInstruction(instruction);
        }
    }

    private void executeInstruction(Instructions instruction) {
        String operation = instruction.getOpcode();
        int dest = instruction.getDest();
        int src1 = instruction.getSrc1();
        int src2 = instruction.getSrc2();

        switch (operation) {
            case "ADD":
                registers[dest] = registers[src1] + registers[src2];
                break;
            case "LOAD":
                registers[dest] = memory.readData(src1);
                break;
            case "STORE":
                memory.writeData(src1, registers[dest]);
                break;
            case "INC":
                registers[dest]++;
                break;
            case "JUMP":
                programCounter = dest;
                break;
            case "STOP":
                running = false;
                break;
            default:
                throw new IllegalArgumentException("Unknown operation: " + operation);
        }

        displayState(instruction);
    }

    private void displayState(Instructions instruction) {
        System.out.println("Executed: " + instruction);
        System.out.println("Registers: " + Arrays.toString(registers));
    }
}
