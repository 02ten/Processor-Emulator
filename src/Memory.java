import java.util.List;

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


    public void writeInstruction(List<Integer> instructions) {
        for(var i = 0; i < instructions.size(); i++){
            instructionMemory[i]= instructions.get(i);
        }
    }

    public Instructions fromMachineCodeToInstruction(int pc){
        return Instructions.fromMachineCode(instructionMemory[pc]);
    }
}
