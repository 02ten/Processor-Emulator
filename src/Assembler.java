public class Assembler {
    public int assemble(String line) {
        String[] parts = line.split(" ");
        String operation = parts[0];
        int dest = parts[1].length() > 1 ? Integer.parseInt(parts[1].substring(1)) : Integer.parseInt(parts[1]);
        int src1 = parts[2].length() > 1 ? Integer.parseInt(parts[2].substring(1)) : Integer.parseInt(parts[2]);
        int src2 = parts[3].length() > 1 ? Integer.parseInt(parts[3].substring(1)) : Integer.parseInt(parts[3]);

        return instructionsToMachineCode(operation, dest, src1, src2);
    }

    private Integer instructionsToMachineCode(String opCode, int dest, int src1, int src2) {
        //TODO заменить JUMP IF на IF. В нулевую ячейку положить размер массива.
        int opcodeBinary = switch (opCode.toUpperCase()) {
            case "ADD" -> 0b0000; // 0
            case "LOAD" -> 0b0001; // 1
            case "STORE" -> 0b0010; // 2
            case "JUMP" -> 0b0011; // 3
            case "JUMP_IF" -> 0b0100;//4
            case "LOAD_SIZE" -> 0b0101;//5
            case "INC" -> 0b0110; // 6
            case "STOP" -> 0b0111; // 7
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
}
