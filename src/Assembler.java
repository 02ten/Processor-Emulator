import java.util.Map;

public class Assembler {
    private final Map<String, Integer> opcodeMap = Map.of(
            "ADD", 1,
            "LOAD", 2,
            "STORE", 3
    );
    //TODO Ассемблер должен переводить из текста в двоичный код. Пока не работает
    public int assemble(String line) {
        String[] parts = line.split(" ");
        String operation = parts[0];
        int dest = parts.length > 1 ? Integer.parseInt(parts[1].substring(1)) : 0;
        int src1 = parts.length > 2 ? Integer.parseInt(parts[2].substring(1)) : 0;
        int src2 = parts.length > 3 ? Integer.parseInt(parts[3].substring(1)) : 0;
        return new Instructions(operation, dest, src1, src2);
    }

    public String disassemble(int machineCode) {
        Instructions instruction = Instructions.fromMachineCode(machineCode);
        String operation = opcodeMap.entrySet().stream()
                .filter(e -> e.getValue() == instruction.getOpcode())
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse("UNKNOWN");
        return String.format("%s R%d, R%d, R%d", operation, instruction.getDest(), instruction.getSrc1(), instruction.getSrc2());
    }
}
