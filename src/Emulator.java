import java.util.Arrays;

public class Emulator {
    private static final int NUM_REGISTERS = 4;
    private final int[] registers = new int[NUM_REGISTERS];
    private int programCounter = 0;
    private boolean running = true;
    private final Memory memory;

    public Emulator(Memory memory) {
        this.memory = memory;
    }
    public void execute() {
        while (running) {
            Instructions instruction = memory.fromMachineCodeToInstruction(programCounter);
            executeInstruction(instruction);
            programCounter++;
        }
    }

    private void executeInstruction(Instructions instruction) {
        int operation = instruction.getOpcode();
        int dest = instruction.getDest();
        int src1 = instruction.getSrc1();
        int src2 = instruction.getSrc2();
        System.out.println(operation);
        System.out.printf("Программный счетчик %d%n",programCounter);
        switch (operation) {
            case 0:
                registers[dest] = registers[src1] + registers[src2];
                break;
            case 1:
                if(src1 >> 3 == 1){
                    registers[dest] = memory.readData(registers[src1 & 0xb0111]);
                }else {
                    registers[dest] = memory.readData(src1 & 0xb0111);
                }
                break;
            case 2:
                memory.writeData(src1, registers[dest]);
                break;
            case 3:
                programCounter = dest-1;
                break;
            case 4:
                if(registers[src1] == registers[src2]){
                    programCounter = dest-1;
                }
                break;
            case 5:
                registers[dest]++;
                break;
            case 6:
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
