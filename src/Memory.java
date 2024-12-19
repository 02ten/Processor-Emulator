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

    public int getSizeDataMemory(){
        return dataMemory.length-1;
    }

    public void writeData(int address, int value) {
        dataMemory[address] = value;
    }


    public void writeInstruction(int address, int machineCode) {
        instructionMemory[address] = machineCode;
    }

    public Instructions fromMachineCodeToInstruction(int pc){
        return Instructions.fromMachineCode(instructionMemory[pc]);
    }
}
