public class Main {
    public static void main(String[] args) {
        Memory memory = new Memory(256, 256);

        // Initialize data memory with an array of integers
        memory.writeData(0, 5);
        memory.writeData(1, 10);
        memory.writeData(2, 15);
        memory.writeData(3, 20);
        memory.writeData(4, 0); // Result

        // Load instructions (machine code)
        Assembler assembler = new Assembler();
        memory.writeInstruction(0, assembler.assemble("LOAD R0 0 0"));
        memory.writeInstruction(1, assembler.assemble("LOAD R1 1 0"));
        memory.writeInstruction(2, assembler.assemble("ADD R2 R0 R1"));
        memory.writeInstruction(3, assembler.assemble("LOAD R3 2 0"));
        memory.writeInstruction(4, assembler.assemble("ADD R4 R2 R3"));
        memory.writeInstruction(5, assembler.assemble("STORE R4 4 0"));

        // Run the emulator
        Emulator emulator = new Emulator(memory);
        emulator.execute();

        // Display the result
        System.out.println("Sum of array: " + memory.readData(4));
    }
}