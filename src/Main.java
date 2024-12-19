public class Main {
    public static void main(String[] args) {
        Memory memory = new Memory(5, 256);

        memory.writeData(0, 5);
        memory.writeData(1, 10);
        memory.writeData(2, 15);
        memory.writeData(3, 20);
        memory.writeData(4, 0); // Result

        Assembler assembler = new Assembler();
        //Регистр 0 - Выделен под промежуточное значение
        //Регистр 1 - Выделен под инкремент
        //Регистр 2 - Выделен под размер массива
        //Регистр 3 - Выделен под след. элемент массива
        //TODO Из файла. Добавить метки
        memory.writeInstruction(0, assembler.assemble("LOAD R0 0 0"));
        memory.writeInstruction(1, assembler.assemble("INC R1 0 0"));
        memory.writeInstruction(2, assembler.assemble("LOAD_SIZE R2 0 0"));
        memory.writeInstruction(3, assembler.assemble("LOAD R3 1 0"));
        memory.writeInstruction(4, assembler.assemble("ADD R0 R0 R3"));
        memory.writeInstruction(5, assembler.assemble("INC R1 0 0"));
        memory.writeInstruction(6, assembler.assemble("JUMP_IF 2 R1 R2"));
        memory.writeInstruction(7, assembler.assemble("JUMP 3 0 0"));
        memory.writeInstruction(8, assembler.assemble("STORE R0 4 0"));
        memory.writeInstruction(9, assembler.assemble("STOP 0 0 0"));


        Emulator emulator = new Emulator(memory);
        emulator.execute();

        // Display the result
        System.out.println("Sum of array: " + memory.readData(4));
    }
}