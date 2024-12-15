public class Memory {
    private final int[] dataMemory;
    private final int[] instructionMemory;

    public Memory(int dataSize, int instructionSize) {
        dataMemory = new int[dataSize];
        instructionMemory = new int[instructionSize];
    }

    public int readData(int address) {
        return dataMemory[address];
    }

    public void writeData(int address, int value) {
        dataMemory[address] = value;
    }

    public int readInstruction(int address) {
        return instructionMemory[address];
    }
    //TODO Каким-то образом избавится от адресса напрямую
    public void writeInstruction(int address, int machineCode) {
        instructionMemory[address] = machineCode;
    }
}
