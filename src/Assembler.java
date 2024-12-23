import java.util.*;

public class Assembler {
    private Map<String, Integer> labelTable = new HashMap<>();
    private Map<String, List<Integer>> unresolvedLabels = new HashMap<>();

    private Integer instructionsToMachineCode(String[] parts, Integer commandIndex) {
        //TODO заменить JUMP IF на IF. В нулевую ячейку положить размер массива.
        int opcodeBinary = 0;
        int dest = 0;
        int src1 = 0;
        int src2 = 0;
        switch (parts[0].toUpperCase()) {
            case "ADD" -> {
                dest = parts[1].length() > 1 ? Integer.parseInt(parts[1].substring(1)) : 0;
                src1 = parts[2].length() > 1 ? Integer.parseInt(parts[2].substring(1)) : 0;
                src2 = parts[3].length() > 1 ? Integer.parseInt(parts[3].substring(1)) : 0;
            } // 0
            case "LOAD" -> {
                opcodeBinary = 0b0001;
                dest = parts[1].length() > 1 ? Integer.parseInt(parts[1].substring(1)) : 0;
                if(parts[2].length() > 1){
                    src1 = 0b1000 | Integer.parseInt(parts[1].substring(1));
                }else{
                    src1 = !parts[2].isEmpty() ? Integer.parseInt(parts[2]) : 0;

                }
            } // 1
            case "STORE" -> {
                opcodeBinary = 0b0010;
                dest = parts[1].length() > 1 ? Integer.parseInt(parts[1].substring(1)) : 0;
                src1 = !parts[2].isEmpty() ? Integer.parseInt(parts[2]) : 0;
            } // 2
            case "JUMP" -> {
                opcodeBinary = 0b0011;
                if (!labelTable.containsKey(parts[1])) {
                    var values = new ArrayList<Integer>();
                    values.add(0);
                    values.add(commandIndex);
                    unresolvedLabels.put(parts[1], values);
                } else {
                    dest = labelTable.get(parts[1]);
                }
            } // 3
            case "IF" -> {
                opcodeBinary = 0b0100;
                if (!labelTable.containsKey(parts[1])) {
                    var values = new ArrayList<Integer>();
                    values.add(1);
                    values.add(commandIndex);
                    unresolvedLabels.put(parts[1], values);
                } else {
                    dest = labelTable.get(parts[1]);
                }
                src1 = parts[2].length() > 1 ? Integer.parseInt(parts[2].substring(1)) : 0;
                src2 = parts[3].length() > 1 ? Integer.parseInt(parts[3].substring(1)) : 0;
            }//4
            case "INC" -> {
                opcodeBinary = 0b0101;
                dest = parts[1].length() > 1 ? Integer.parseInt(parts[1].substring(1)) : 0;
            } // 5
            case "STOP" -> {
                opcodeBinary = 0b0110;
            } // 6
            default -> throw new IllegalArgumentException("Unknown operation: " + parts[0].toUpperCase());
        } ;
        System.out.printf("Бинарный код операции: %4s%n", Integer.toBinaryString(opcodeBinary));
        System.out.printf("Бинарный код адреса назначения: %4s%n", Integer.toBinaryString(dest));
        System.out.printf("Бинарный код первого адреса: %4s%n", Integer.toBinaryString(src1));
        System.out.printf("Бинарный код второго адреса: %4s%n", Integer.toBinaryString(src2));

        var machineCode = (opcodeBinary << 12) | (dest << 8) | (src1 << 4) | src2;

        System.out.printf("Инструкция в бинарном коде: %16s%n", Integer.toBinaryString(machineCode));

        return machineCode;
    }

    public List<Integer> assemble(List<String> lines) {
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


            System.out.println("Генерируем команду из " + line + ", commandIndex=" + commandIndex);
            int binaryInstruction = instructionsToMachineCode(parts, commandIndex);
            machineCode.add(binaryInstruction);
            commandIndex++;
        }
        resolveLabels(machineCode);
        return machineCode;
    }


    private void resolveLabels(List<Integer> machineCode) {
        for (var unresolved : unresolvedLabels.entrySet()) {
            String label = unresolved.getKey();
            int operandIndex = unresolved.getValue().get(0);
            int commandIndex = unresolved.getValue().get(1);

            if (!labelTable.containsKey(label)) {
                throw new IllegalStateException("Unresolved label: " + label);
            }

            int resolvedAddress = labelTable.get(label);

            int instruction = machineCode.get(commandIndex);
            if (operandIndex == 0) { // For JUMP
                machineCode.set(commandIndex, (instruction) | (resolvedAddress << 8));
            } else if (operandIndex == 1) {
                System.out.println(Integer.toBinaryString(machineCode.get(commandIndex)));// For IF
                machineCode.set(commandIndex, (instruction) | (resolvedAddress << 8));
                System.out.println(Integer.toBinaryString(machineCode.get(commandIndex)));
                System.out.println("");
            }
        }
    }
}
