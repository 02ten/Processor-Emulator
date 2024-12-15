public class Instructions {

    private final int opcode;
    private final int dest;
    private final int src1;
    private final int src2;

    public Instructions(int opcode, int dest, int src1, int src2) {
        this.opcode = opcode;
        this.dest = dest;
        this.src1 = src1;
        this.src2 = src2;
    }

    public int getOpcode() {
        return opcode;
    }

    public int getDest() {
        return dest;
    }

    public int getSrc1() {
        return src1;
    }

    public int getSrc2() {
        return src2;
    }

    public static Instructions fromMachineCode(int machineCode) {
        int opcode = (machineCode >> 24) & 0xFF;
        int dest = (machineCode >> 16) & 0xFF;
        int src1 = (machineCode >> 8) & 0xFF;
        int src2 = machineCode & 0xFF;
        return new Instructions(opcode, dest, src1, src2);
    }

    public int toMachineCode() {
        return (opcode << 24) | (dest << 16) | (src1 << 8) | src2;
    }

}
