import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Memory memory = new Memory(5, 256);

        memory.writeData(0, 2);
        memory.writeData(1, 15);
        memory.writeData(2, 0); // Result

        Assembler assembler = new Assembler();
        //Регистр 0 - Выделен под промежуточное значение
        //Регистр 1 - Выделен под инкремент
        //Регистр 2 - Выделен под размер массива
        //Регистр 3 - Выделен под след. элемент массива
        //TODO Из файла. Добавить метки start и End для цикла
        List<String> lines = Files.readAllLines(Paths.get("src/commands.txt"));  // Чтение всех строк из файла
        List<Integer> binaryInstructions = assembler.assemble(lines);
        memory.writeInstruction(binaryInstructions);

        Emulator emulator = new Emulator(memory);
        emulator.execute();

        // Display the result
        System.out.println("Sum of array: " + memory.readData(4));
    }
}