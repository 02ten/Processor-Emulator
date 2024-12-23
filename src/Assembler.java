import java.util.*;

public class Assembler {
    private Map<String, Integer> labelTable = new HashMap<>();

    private Integer instructionsToMachineCode(String opCode, int dest, int src1, int src2) {
        //TODO заменить JUMP IF на IF. В нулевую ячейку положить размер массива.
        int opcodeBinary = switch (opCode.toUpperCase()) {
            case "ADD" -> 0b0000; // 0
            case "LOAD" -> 0b0001; // 1
            case "STORE" -> 0b0010; // 2
            case "JUMP" -> 0b0011; // 3
            case "IF" -> 0b0100;//4
            case "INC" -> 0b0101; // 5
            case "STOP" -> 0b0110; // 6
            default -> throw new IllegalArgumentException("Unknown operation: " + opCode);
        };
        System.out.printf("Бинарный код операции: %4s%n", Integer.toBinaryString(opcodeBinary));
        System.out.printf("Бинарный код адреса назначения: %4s%n", Integer.toBinaryString(dest));
        System.out.printf("Бинарный код первого адреса: %4s%n", Integer.toBinaryString(src1));
        System.out.printf("Бинарный код второго адреса: %4s%n", Integer.toBinaryString(src2));

        var machineCode = (opcodeBinary << 12) | (dest << 8) | (src1 << 4) | src2;

        System.out.printf("Инструкция в бинарном коде: %16s%n", Integer.toBinaryString(machineCode));

        return machineCode;
    }

    public List<Integer> assemble(List<String> lines){
        List<Integer> machineCode = new ArrayList<>();
        int commandIndex = 0;

        // Первый проход: анализ меток и команд
        for (String line : lines) {
            String trimmedLine = line.trim();
            if (trimmedLine.isEmpty()) continue;

            // Лексический анализ: распознавание меток
            if (trimmedLine.endsWith(":")) {
                String label = trimmedLine.substring(0, trimmedLine.length() - 1);
                if (labelTable.containsKey(label)) {
                    throw new IllegalStateException("Метка " + label + " уже определена.");
                }
                labelTable.put(label, commandIndex);
                continue;
            }

            // Синтаксический анализ команды
            String[] parts = trimmedLine.split("\\s+");
            String cmdType = parts[0].toUpperCase();

            if (!isValidCommand(cmdType)) {
                throw new IllegalStateException("Неизвестная команда: " + cmdType);
            }
            int dest = 0;
            int src1 = 0;
            int src2 = 0;
            if(parts.length > 3){
                dest = parts[1].length() > 1 ? Integer.parseInt(parts[1].substring(1)) : 0;
                src1 = parts[2].length() > 1 ? Integer.parseInt(parts[2].substring(1)) : 0;
                src2 = parts[3].length() > 1 ? Integer.parseInt(parts[3].substring(1)) : 0;
            }
            if(parts.length > 2){
                dest = parts[1].length() > 1 ? Integer.parseInt(parts[1].substring(1)) : 0;
                src1 = parts[2].length() > 1 ? Integer.parseInt(parts[2].substring(1)) : 0;
            }
            System.out.println("Генерируем команду из cmdType=" + cmdType + ", operand1=" + src1 + ", operand2=" + src2 + ", commandIndex=" + commandIndex);
            int binaryInstruction = instructionsToMachineCode(cmdType, dest, src1, src2);
            machineCode.add(binaryInstruction);
            commandIndex++;
        }

        return machineCode;
    }

    private boolean isValidCommand(String cmdType) {
        // Логика для проверки валидности команды
        // Пример: проверка на известные команды
        Set<String> validCommands = new HashSet<>(Arrays.asList("ADD", "LOAD", "STORE", "JUMP", "IF", "INC", "STOP"));
        return validCommands.contains(cmdType);
    }

}
